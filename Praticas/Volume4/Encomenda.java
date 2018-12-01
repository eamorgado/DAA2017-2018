import java.util.*;
import java.lang.*;

class Qnode {
    int vert;
    int vertkey;

    Qnode(int v, int key) {
	vert = v;
	vertkey = key;
    }
}
class Heapmax {
    private static int posinvalida = 0;
    int sizeMax,size;

    Qnode[] a;
    int[] pos_a;

    Heapmax(int vec[], int n) {
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

    int extractMax() {
	int vertv = a[1].vert;
	swap(1,size);
	pos_a[vertv] = posinvalida;  // assinala vertv como removido
	size--;
	heapify(1);
	return vertv;
    }

    void increaseKey(int vertv, int newkey) {

	int i = pos_a[vertv];
	a[i].vertkey = newkey;

	while (i > 1 && compare(i, parent(i)) > 0) {
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
	increaseKey(vertv,key);   // aumenta a chave e corrige posicao se necessario
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
	int l, r, largest;

	l = left(i);
	if (l > size) l = i;

	r = right(i);
	if (r > size) r = i;

	largest = i;
	if (compare(l,largest) > 0)
	    largest = l;
	if (compare(r,largest) > 0)
	    largest = r;

	if (i != largest) {
	    swap(i, largest);
	    heapify(largest);
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

public class Encomenda{
  public static Scanner in =new Scanner(System.in);
  public static final int n=1000;
  public static int origem,destino,lmin,lmax,cmin,cmax,amin;
  public static HashMap<Integer,Integer> map = new HashMap<>();

  public static Grafo lerGrafo(){
    int i,f,max_l,max_c,max_a;
    Grafo g = new Grafo(n);
    i=in.nextInt();
    while(i!=-1){
      f = in.nextInt();
      max_l = in.nextInt();
      max_c = in.nextInt();
      max_a = in.nextInt();
      if(!map.containsKey(i)) map.put(i,map.size()+1);
      if(!map.containsKey(f)) map.put(f,map.size()+1);
      if( max_c>=cmin && max_l>=lmin && max_a>=amin ){

        g.insert_new_arc(map.get(i),map.get(f),max_c);
        g.insert_new_arc(map.get(f),map.get(i),max_c);
      }
      i=in.nextInt();
    }
    return g;
  }
  public static int algDijkstra(Grafo g,int s, int t, int c_max){
    int n = g.num_vertices(),v;
    int[] cap = new int[n+1];
    for(int i=1;i<=n;i++) cap[i]= 0;
    cap[s]=c_max;
    Heapmax Q = new Heapmax(cap,n);
    while(!Q.isEmpty()){
      v=Q.extractMax();
      if(v==t||cap[v]==0)break;//cheguei a destino
      for(Arco adjs : g.adjs_no(v)){
        int w=adjs.extremo_final(),peso=adjs.valor_arco();
        if(Math.min(cap[v],peso)>cap[w]){
          cap[w]=Math.min(cap[v],peso);
          Q.increaseKey(w,cap[w]);
        }
      }
    }
    return cap[t];
  }
  public static void main(String[] args) {
    lmin=in.nextInt(); lmax=in.nextInt();
    cmin=in.nextInt(); cmax=in.nextInt();
    amin=in.nextInt(); //in.nextLine();

    origem=in.nextInt(); destino=in.nextInt(); //in.nextLine();
    map.put(origem,map.size()+1);
    map.put(destino,map.size()+1);

    Grafo g = lerGrafo();
    System.out.println(algDijkstra(g,1,2,cmax));
  }
}
