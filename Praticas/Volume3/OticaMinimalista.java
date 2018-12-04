import java.util.*;
/* Estado:
|  MooshackDAA- Vol 2- Prob F - OticaMinimalista (2018)       |
|     Objetivo:                                               |
|       -Definir ligações de nos pelo rendimento bruto        |
|       -Reduzir ao maximo os custos de manutenção global     |
|       -Maximizar o rendimento líquido total                 |
|NOTA:os custos de manutenção são inguais para todas as ligaçõ|
|--------------------------//---------------------------------|
|     INPUT:                                                  |
|       L1: #nos #ramos #custo_manutenção_ligação             |
|       -->Desc de ligações                                   |
|         -ei ef rendimento bruto                             |
|    OUTPUT:                                                  |
|     -"rendimento otimo: x" x-rend otimo de qq rede optima   |
|     -"impossivel" se n garantir CONECTIVIDADE               |
|Nota:                                                        |
| -Usar bfsVisit para ver se o grafo é conexo retornar val==n |
| -Usar alg de prim para encontra max spinning tree           |
*/

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



public class OticaMinimalista{
  public static Scanner in = new Scanner(System.in);
  public static int INF,custo,n,r,rendimento_maximo=0;
  public static Grafo criaGrafo(int n,int r){
    int o,d,ren;
    Grafo g=new Grafo(n);
    for(int i=0;i<r;i++){
      o = in.nextInt();
      d = in.nextInt();
      ren=in.nextInt();
      in.nextLine();
      if(r>rendimento_maximo) rendimento_maximo=r; //obter peso maximo
      g.insert_new_arc(o,d,ren);
      g.insert_new_arc(d,o,ren);
    }
    return g;
  }
  public static int bfsVisit(int s, Grafo g){
    int nosligados;
    boolean[] visitado= new boolean[n+1];
    Queue<Integer> lista = new LinkedList<>();

    for(int i=1;i<=n;i++) visitado[i]=false;
    lista.add(s); visitado[s]=true;
    while(!lista.isEmpty()){
      int d=lista.remove();
      for(Arco adjs : g.adjs_no(d)){
        if(!visitado[adjs.extremo_final()]){
          nosligados++;
          visitado[adjs.extremo_final()]=true;
        }
      }
    }
    return nosligados;
  }
  public static int maxPrim(Grafo g){
    int[]dist = new int [n+1];
    int peso=-INF,v; //peso=-INF pos peso raiz = INF
    boolean[] valido = new boolean[n+1];
    for(int i=1;i<=n;i++){dist[i]=0; valido[i]=false;}

    dist[1]=INF;//raiz
    Heapmax Q = new Heapmax(dist,n);

    while (!Q.isEmpty()) {
      v = Q.extractMax();
      valido[v]=true;
      peso+=dist[v]; //vertice é ligado a arvore  peso contribui para o peso total

      for(Arco adjs : g.adjs_no(v)){
        int w=adjs.extremo_final();
        if((!valido[w]) && (adjs.valor_arco()>dist[w])){
          //usar o arco para ligar a arvore
          dist[w]=adjs.valor_arco();
          Q.increaseKey(w,dist[w]);
        }
      }
    }return peso; //rendimento max
  }
  public static void isConexo(Grafo g){
    if(bfsVisit(1,g)!=n) // grafo n isConexo
      System.out.println("impossivel");
    else System.out.println("rendimento otimo: "+maxPrim(g));
    return;
  }
  public static void main(String[] args) {
    n = in.nextInt();r=in.nextInt();custo=in.nextInt();
    in.nextLine();
    INF = custo*n + 1;
    Grafo g = criaGrafo(n,r);
    isConexo(g);
  }
}
