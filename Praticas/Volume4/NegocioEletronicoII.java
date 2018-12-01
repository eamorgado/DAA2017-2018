import java.util.*;
/* Estado:
|  MooshackDAA- Vol 4- Prob H - NegocioEletronico (2018)      |
|     Objetivo: A partir de rede de transportes com restrições|
|            Apresentar o numero de percursos possível        |
|--------------------------//---------------------------------|
|     INPUT:                                                  |
|         L1: #nos                                            |
|         L2: lmin lmax cmin cmax amin                        |
|         L3: origem destino                                  |
|         --> Descrição de ramos bidirecionais: (While!=-1)   |
|           einicial efinal largura comprimento altura        |
|    OUTPUT:                                                  |
|        - Numero de ramos onde a caixa pode passar           |
|Nota:  -neste caso nem preciso de criar grafo                |
|       -Quando nos dão valor máximo não significa o valor do |
|        ramo em questão mas sim um limite para o seu valor ou|
|        seja desde que esse valor n seja inferior ao         |
|         lmin/cmin/amin o ramo é válido                      |
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
      //nota: se o valor for igual devo imprimir o valor de menor indicador
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



public class NegocioEletronicoII{
  public static Scanner in=new Scanner(System.in);
  public static final int INF=Integer.MAX_VALUE;
  public static int destino;
  //Recebe input de utilizar enquanto !=-1 i cria o grafo da respetiva rede
  public static Grafo criaGrafo(){
    int n,ei,ef,p;
    //Leitura
    n=in.nextInt(); destino=in.nextInt();
    Grafo g = new Grafo(n);
    ei=in.nextInt();
    while(ei!=-1){
      ef=in.nextInt(); p=in.nextInt();
      g.insert_new_arc(ei,ef,p);
      g.insert_new_arc(ef,ei,p);
      ei=in.nextInt();
    }
    return g;
  }
  public static void algDijkstra(Grafo g,int s){
    int n=g.num_vertices(),v;
    int[] dist = new int[n+1];
    for(int i=0;i<=n;i++) dist[i]= INF;
    dist[s]=0;
    Heapmin Q = new Heapmin(dist,n);
    while(!Q.isEmpty()){
      v=Q.extractMin();
      for(Arco adjs : g.adjs_no(v)){
        int w=adjs.extremo_final(),peso=adjs.valor_arco();
        if(dist[w]!=INF &&((dist[v]+peso)<dist[w])){
          dist[w]=dist[v]+peso;
          Q.decreaseKey(w,peso);
        }
      }
      if(v==destino)
        System.out.print(destino);
      else
        System.out.print(" "+v);
    }
    return;
  }
  public static void main(String[] args) {
    Grafo g = criaGrafo();
    algDijkstra(g,destino);
  }
}
