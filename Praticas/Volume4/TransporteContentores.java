import java.util.*;
/* Estado:
|  MooshackDAA- Vol 4- Prob K - TransporteContentores (2018)  |
|     Objetivo: Planear transporte de caixas de origem destino|
|               Se possível transportar qual a distMIN de O->D|
|     Observações:                                            |
|         -Não é garantido que destino é acessível a origem   |
|         -Algumas ligações restringem as dimensões da caixa  |
|         -É fornecida info da rede e comp do lado do CUBO    |
|         -A distância mínima não pode ultrapassar 1000 km    |
|--------------------------//---------------------------------|
|     INPUT:                                                  |
|       L1: origem destino comp_lado_cubo (m)                 |
|       L2: #traços a analisar e #nos_rede                    |
|       -->Descrição de trajeto bidireciona                   |
|         #n(k) ei peso(comp) ef                              |
|       Lt+1: numero de ramos problemáticos (se =0 ignora)    |
|       -->Descrição dos ramos:                               |
|         ei ef amax lmax (se -1 n está limitada)             |
|    OUTPUT:                                                  |
|       -distMini se possível efetuartransporte               |
|       -"Impossivel" caso contrário                          |
|Nota:                                                        |
|     -Depois de leitura posso fazer um bfsVisit de o-->d para|
|       verificar se o destino é acessível à origem caso seja |
|       falso posso logo retornar Impossivel                  |
|     -Para os ramos problemeático se n der para passar marco |
|       o ramo como inválido(dou valor -1 a peso) e quando est|
|       tiver a percorrer bfsVisit ou a calc comp ignoro-o    |
*/

class Arco {
    int no_final;
    int valor;

    Arco(int fim, int v){
	no_final = fim;
	valor = v;
    }

    int extremo_final() {
	return no_final;
    }

    int valor_arco() {
	return valor;
    }

    void novo_valor(int v) {
	valor = v;
    }
}
class No {
    //int label;
    LinkedList<Arco> adjs;

    No() {
	adjs = new LinkedList<Arco>();
    }
}
class Grafo {
    No verts[];
    int nvs, narcos;

    public Grafo(int n) {
	nvs = n;
	narcos = 0;
	verts  = new No[n+1];
	for (int i = 0 ; i <= n ; i++)
	    verts[i] = new No();
        // para vertices numerados de 1 a n (posicao 0 nao vai ser usada)
    }

    public int num_vertices(){
	return nvs;
    }

    public int num_arcos(){
	return narcos;
    }

    public LinkedList<Arco> adjs_no(int i) {
	return verts[i].adjs;
    }

    public void insert_new_arc(int i, int j, int valor_ij){
	verts[i].adjs.addFirst(new Arco(j,valor_ij));
        narcos++;
    }

    public Arco find_arc(int i, int j){
	for (Arco adj: adjs_no(i))
	    if (adj.extremo_final() == j) return adj;
	return null;
    }
}

class Qnode {
    int vert;
    int vertkey;

    Qnode(int v, int key) {
	vert = v;
	vertkey = key;
    }
}

class Heapmin {
    private static int posinvalida = 0;
    int sizeMax,size;

    Qnode[] a;
    int[] pos_a;

    Heapmin(int vec[], int n) {
	a = new Qnode[n + 1];
	pos_a = new int[n + 1];
	sizeMax = n;
	size = n;
	for (int i = 1; i <= n; i++) {
	    a[i] = new Qnode(i,vec[i]);
	    pos_a[i] = i;
	}

	for (int i = n/2; i >= 1; i--)
	    heapify(i);
    }

    boolean isEmpty() {
	if (size == 0) return true;
	return false;
    }

    int extractMin() {
	int vertv = a[1].vert;
	swap(1,size);
	pos_a[vertv] = posinvalida;  // assinala vertv como removido
	size--;
	heapify(1);
	return vertv;
    }

    void decreaseKey(int vertv, int newkey) {

	int i = pos_a[vertv];
	a[i].vertkey = newkey;

	while (i > 1 && compare(i, parent(i)) < 0) {
	    swap(i, parent(i));
	    i = parent(i);
	}
    }


    void insert(int vertv, int key)
    {
	if (sizeMax == size)
	    new Error("Heap is full\n");

	size++;
	a[size].vert = vertv;
	pos_a[vertv] = size;   // supondo 1 <= vertv <= n
	decreaseKey(vertv,key);   // diminui a chave e corrige posicao se necessario
    }

    void write_heap(){
	System.out.printf("Max size: %d\n",sizeMax);
	System.out.printf("Current size: %d\n",size);
	System.out.printf("(Vert,Key)\n---------\n");
	for(int i=1; i <= size; i++)
	    System.out.printf("(%d,%d)\n",a[i].vert,a[i].vertkey);

	System.out.printf("-------\n(Vert,PosVert)\n---------\n");

	for(int i=1; i <= sizeMax; i++)
	    if (pos_valida(pos_a[i]))
		System.out.printf("(%d,%d)\n",i,pos_a[i]);
    }

    private int parent(int i){
	return i/2;
    }
    private int left(int i){
	return 2*i;
    }
    private int right(int i){
	return 2*i+1;
    }

    private int compare(int i, int j) {
	if (a[i].vertkey < a[j].vertkey)
	    return -1;
	if (a[i].vertkey == a[j].vertkey)
	    return 0;
	return 1;
    }


    private void heapify(int i) {
	int l, r, smallest;

	l = left(i);
	if (l > size) l = i;

	r = right(i);
	if (r > size) r = i;

	smallest = i;
	if (compare(l,smallest) < 0)
	    smallest = l;
	if (compare(r,smallest) < 0)
	    smallest = r;

	if (i != smallest) {
	    swap(i, smallest);
	    heapify(smallest);
	}

    }

    private void swap(int i, int j) {
	Qnode aux;
	pos_a[a[i].vert] = j;
	pos_a[a[j].vert] = i;
	aux = a[i];
	a[i] = a[j];
	a[j] = aux;
    }

    private boolean pos_valida(int i) {
	return (i >= 1 && i <= size);
    }
}



public class TransporteContentores{
  public static Scanner in = new Scanner(System.in);
  public static final int INF=Integer.MAX_VALUE;
  public static int origem,destino;
  public static Grafo criaGrafo(){
    int num_perc,nos,k,ei,ef,comprimento,lado_cubo;
    int problematicos, amax,lmax;
    origem=in.nextInt(); destino=in.nextInt();
    lado_cubo=in.nextInt();
    num_perc=in.nextInt(); nos=in.nextInt();
    Grafo g = new Grafo(nos);
    //Leitura de criação de grafo
    for(int i=0;i<num_perc;i++){
      k=in.nextInt(); k--;
      ei=in.nextInt();
      while(k>0){
        comprimento=in.nextInt();
        ef=in.nextInt();
        if(g.find_arc(ei,ef)==null){
          g.insert_new_arc(ei,ef,comprimento);
          g.insert_new_arc(ef,ei,comprimento);
        }
        ei=ef;
        k--;
      }
    }
    //-----------------------------
    //Verificação de problemáticos e atualização
    problematicos=in.nextInt();
    if(problematicos==0) return g;
    for(int i=0;i<problematicos;i++){
      ei=in.nextInt(); ef=in.nextInt();
      amax=in.nextInt(); lmax=in.nextInt();
      if(!(amax==-1&&lmax==-1)){
        if(amax<lado_cubo&&lmax<lado_cubo){
          g.find_arc(ei,ef).novo_valor(-1);
          g.find_arc(ef,ei).novo_valor(-1);
        }
      }
      else{//amax==-1&&lmax==-1
        g.find_arc(ei,ef).novo_valor(-1);
        g.find_arc(ef,ei).novo_valor(-1);
      }
    }
    return g;
  }
  public static boolean testaAcessivel(Grafo g,int s,int t){
    int n=g.num_vertices(),v,w,comp;
    boolean[] visitado=new boolean[n+1];
    Queue<Integer> lista=new LinkedList<>();
    visitado[s]=true;
    lista.add(s);
    while(!lista.isEmpty()){
      v=lista.remove();
      //t é acessível a s
      if(v==t) return true;
      //------
      for(Arco adjs : g.adjs_no(v)){
        w=adjs.extremo_final(); comp=adjs.valor_arco();
        if(!visitado[w] && comp!=-1){
          visitado[w]=true;
          lista.add(w);
        }
      }
    }
    return false;
  }
  public static void calculaComp(Grafo g, int s, int t){
    int n=g.num_vertices(),v,w,comp;
    int[] comp_min=new int[n+1];
    for(int i=0;i<=n;i++) comp_min[i]=INF;
    comp_min[s]=0;

    Heapmin Q=new Heapmin(comp_min,n);
    while(!Q.isEmpty()){
      v=Q.extractMin();
      if(v==t || comp_min[v]==INF)break;
      for(Arco adjs:g.adjs_no(v)){
        w=adjs.extremo_final(); comp=adjs.valor_arco();
        if(comp!=-1 && comp_min[v]+comp<comp_min[w]){
          comp_min[w]=comp_min[v]+comp;
          Q.decreaseKey(w,comp_min[w]);
        }
      }
    }
    //Mesmo que t n seja acessível a s o seu comp_min sera INF>1000
    if(comp_min[t]>1000) System.out.println("Impossivel");
    else System.out.println(comp_min[t]);
    return;
  }
  public static void main(String[] args) {
    Grafo g = criaGrafo();
    if(!testaAcessivel(g,origem,destino))
      System.out.println("Impossivel");
    else
      calculaComp(g,origem,destino);
  }
}
