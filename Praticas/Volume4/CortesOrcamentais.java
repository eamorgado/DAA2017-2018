import java.util.*;
/*Estado:terminado
|MooshackDAA-Vol4-Prog G-Cortes CortesOrcamentais (2018)   |
|  Objetivo: Saber se é possível transportar o animal,     |
|            SEM GASTAR MAIS que quantia predefinida.      |
|                                                          |
|  Considerações:                                          |
|      -O valor a pagar em cada ramo é o seu peso          |
|      -Validos apemas os Ramos que garantem condições     |
|        impostas de temperatura suportadas pelo animal    |
|  Nota: Semelhante a Transporte de Luxo (Prob F)          |
|    -Posso aplicar um alg de Dijsktra para heapMin (1x) e |
|       ver se o valor obtido é menor ou igual ao orçamento|
|-------------------------//-----------------------------  |
|    INPUT:                                                |
|      L1:tempMin tempMax origem destino (origem != destino|
|      L2: #nos #ramos da rede (grafo de 2 pesos)          |
|      -->Desc de ramos bidirecionais  n<=20000 r<=5000    |
|          -exInicial exFinal tempRamo custoTransporte     |
|      L(n+1): nCenarios--#cenarios a considerar >=1       |
|      -->Desc de cenários:                                |
|          -cada linha diz valor maximo a dispensar <10000 |
|    OUTPUT: para cada cenario                             |
|      -"Sim"-pode transportado sem ultrapassar montante   |
|      -"Nao"-caso contrário                               |
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

public class CortesOrcamentais{
  public static Scanner in = new Scanner(System.in);
  public static int t_min,t_max,origem,destino;
  public static boolean entre(int v1,int v2, int v3){
    return (v2>=v1&&v2<=v3);
  }
  public static Grafo criaGrafo(){
    int nos, ramos,ei,ef,temp,custo;

    t_min=in.nextInt(); t_max=in.nextInt();
    origem=in.nextInt(); destino=in.nextInt();
    nos=in.nextInt(); ramos=in.nextInt();

    Grafo g = new Grafo(nos);
    for(int i=0;i<ramos;i++){
      ei=in.nextInt(); ef=in.nextInt();
      temp=in.nextInt(); custo=in.nextInt();
      if(temp>=t_min && temp<=t_max){
        g.insert_new_arc(ei,ef,custo);
        g.insert_new_arc(ef,ei,custo);
      }
    }
    return g;
  }

  public static int custoPercurso(Grafo g, int s, int t){
    int n = g.num_vertices(),v;
    boolean enc=false;
    int[] custo = new int[n+1];
    boolean[] visitado = new boolean[n+1];
    for(int i=1;i<=n;i++) {custo[i]= 10001; visitado[i]=false;}
    custo[s]=0; visitado[s]=true;
    Heapmin Q = new Heapmin(custo,n);
    while(!Q.isEmpty()){
      v=Q.extractMin();
      for(Arco adjs : g.adjs_no(v)){
        int w=adjs.extremo_final(),peso=adjs.valor_arco();
          if((custo[v]+peso)<custo[w]){
            custo[w]=custo[v]+peso;
            if(!visitado[w]){//caso elemento ainda esteja na heap
              visitado[w]=true;
              Q.decreaseKey(w,custo[w]);
            }
          }
      }
    }
    return custo[t];
  }
  public static void testaCenario(Grafo g){
    int orcamento=0,n_c = in.nextInt();
    int orca_total = custoPercurso(g,origem,destino); //obtem o resulado apenas 1 vez
    for(int i =0;i<n_c;i++){
      orcamento=in.nextInt();
      if(orca_total<=orcamento)
        System.out.println("Sim");
      else
        System.out.println("Nao");
    }
    return;
  }
  public static void main(String[] args) {
    Grafo g = criaGrafo();
    testaCenario(g);
  }
}
