import java.util.*;
/* Estado:  Terminado
|  MooshackDAA- Vol 4- Prob E - TransporteRapido (2018)       |
|     Objetivo: A partir de rede de transportes com restrições|
|            Apresentar o numero de ramos em caminho minimo   |
|--------------------------//---------------------------------|
|     INPUT:                                                  |
|         L1: #nos                                            |
|         L2: lmin lmax cmin cmax amin                        |
|         L3: origem destino                                  |
|         --> Descrição de ramos bidirecionais: (While!=-1)   |
|           einicial efinal largura comprimento altura        |
|    OUTPUT:                                                  |
|        - Numero de ramos onde a caixa pode passar           |
|Nota:  -Devo inicialmente usar uma BFS_Visit para ver se é   |
|         consigo cheagar a destino por origem, se n Impossiv |
|       -Se conseguir aplicar Alg de Prim para minimumn       |
|         spanning tree de origem para destino.               |
|           return num_arcos[destino]                         |
*/

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


class Arco {
    int no_final;

    Arco(int fim){
	no_final = fim;
    }

    int extremo_final() {
	return no_final;
    }
}
class No {
    //int label;
    LinkedList<Arco> adjs;

    No() {
	adjs = new LinkedList<Arco>();
    }
}
class Grafo0 {
    No verts[];
    int nvs, narcos;

    public Grafo0(int n) {
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

    public void insert_new_arc(int i, int j){
	verts[i].adjs.addFirst(new Arco(j));
        narcos++;
    }

    public Arco find_arc(int i, int j){
	for (Arco adj: adjs_no(i))
	    if (adj.extremo_final() == j) return adj;
	return null;
    }
}

public class TransporteRapido{
  public static Scanner in = new Scanner(System.in);
  public static int origem,destino;

  public static Grafo0 criaGrafo(){
    //Valores L1-L2-L3
    int n, lmin,lmax,cmin,cmax,amin;
    //Valores ramos
    int ei,ef,l,c,a;

    //Leitura L1-L2-L3--
    n=in.nextInt();
    lmin=in.nextInt(); lmax=in.nextInt();
    cmin=in.nextInt(); cmax=in.nextInt();
    amin=in.nextInt();
    origem=in.nextInt(); destino=in.nextInt();

    Grafo0 g = new Grafo0(n);

    //Cria rede de grafo----
    ei=in.nextInt();
    while(ei!=-1){
      ef=in.nextInt();
      l=in.nextInt(); c=in.nextInt(); a=in.nextInt();
      //maximos de ramos atingem minorante de intervalos
      if(l>=lmin && c>=cmin && a>=amin){
        g.insert_new_arc(ei,ef);
        g.insert_new_arc(ef,ei);
      }
      ei=in.nextInt();
    }
    return g;
  }
  public static void procuraCaminhos(Grafo0 g,int s,int t){
    int n=g.num_vertices();
    int[]num_ramos = new int [n+1];
    int inf=n*g.num_arcos(),v;

    for(int i=1;i<=n;i++)num_ramos[i]=inf;

    num_ramos[s]=0;//raiz
    Heapmin Q = new Heapmin(num_ramos,n);

    while (!Q.isEmpty()) {
      v = Q.extractMin();
      if(v==t) break;
      for(Arco adjs : g.adjs_no(v)){
        int w=adjs.extremo_final();
        if(num_ramos[v]+1<num_ramos[w]){//usar o arco para ligar a arvore
          num_ramos[w]=num_ramos[v]+1;
          Q.decreaseKey(w,num_ramos[w]);
        }
      }
    }
    System.out.println(num_ramos[t]);
    return;
  }
  public static boolean bfsDestino(Grafo0 g, int s, int t){
    int n=g.num_vertices(),v,w;
    boolean[] visitado = new boolean[n+1];
    Queue<Integer> lista=new LinkedList<>();
    lista.add(s); visitado[s]=true;
    while(!lista.isEmpty()){
      v=lista.remove();
      if(v==t) return true;
      for(Arco adjs : g.adjs_no(v)){
        w=adjs.extremo_final();
        if(!visitado[w]){
          visitado[w]=true;
          lista.add(w);
        }
      }
    }
    return false;
  }
  public static void main(String[] args) {
    //Cria rede de percursos
    Grafo0 g = criaGrafo();
    if(bfsDestino(g,origem,destino))
      procuraCaminhos(g,origem,destino);
    else
      System.out.println("Impossivel");
  }
}
