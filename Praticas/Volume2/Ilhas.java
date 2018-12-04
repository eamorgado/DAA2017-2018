import java.util.*;

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



public class Ilhas{
  public static Scanner in = new Scanner(System.in);
  public static final int NOADJ = -1;
  public static final int ADJ = 0;
  public static Grafo0 makeGrafo(int n_nos, int n_ramos){ int u, v;
    Grafo0 g = new Grafo0(n_nos);
    for(int i=0; i<n_ramos; i++){
      u=in.nextInt(); v= in.nextInt(); in.nextLine();
      g.insert_new_arc(u,v);
      g.insert_new_arc(v,u);
    }
    return g;
  }
  public static void listaAdjacenciaMaxima(Grafo0 g, int s,int[] max){
    int n= g.num_vertices(),i,w;
    ArrayList<Integer> pai=new ArrayList<>(n+1);
    Queue<Integer> explorarAdjacencia = new LinkedList<Integer>();
    LinkedList<Arco> adjS = new LinkedList<>(); //guardar todas as possíveis adj de S
    boolean[] visitado = new boolean[n+1];
    //Inicializar-----------------
    pai.add(NOADJ);
    for(i=1;i<=n;i++){
      visitado[i]=false;
      pai.add(NOADJ);
    }
    //-------------------------
    visitado[s]=true;
    pai.set(s,ADJ);
    explorarAdjacencia.add(s);

    while(!explorarAdjacencia.isEmpty()){
      i=explorarAdjacencia.remove();
      adjS=g.adjs_no(i);
      while(!adjS.isEmpty()){
        w=adjS.removeFirst().extremo_final();
        if(!visitado[w]){//é acessível a s e ainda n foi encontrado
          explorarAdjacencia.add(w);
          visitado[w]=true;
          pai.set(w,i);
        }
      }
    }
    //já foram encontrados todos os nos acessíveis de s agora basta encontrar maximo
    //como pai está marcado, tudo o que é != de NOADJ é acessível
    //bast considerar esses valores e enconyrar o maximo
    int maximo = 0;
    for(i=1;i<pai.size();i++){
      if(pai.get(i) != NOADJ){
        if(i>maximo) maximo=i;
      }
    }
    //encontrei o maior valor
    //basta agora colocar em max
    for(i=1;i<pai.size();i++){
      if(pai.get(i) != NOADJ){
        if(maximo>max[i]) max[i]=maximo;
      }
    }
    return;
  }

  //FUNÇÕES AUXILIARES FLAGS
  public static void imprimeGrafo(Grafo0 g){
    LinkedList<Arco> lista = new LinkedList<>();
    int n = g.num_vertices();
    System.out.println("Imprimir grafo:");
    for(int i=1;i<=n;i++){
      lista=g.adjs_no(i);
        if(lista==null)System.out.println("Nãe existe adjacência para no "+i+";");
        else System.out.println("Lista de adjacência para no "+i+": "+imprimeAdj(lista)+";");
    }
    return;
  }
  public static String imprimeMax(int[] max,int n){
    String s="Max: [";
    for(int i=1;i<n;i++) s+=max[i]+" ,";
    s+=max[n]+"]\n";
    return s;
  }
  public static String imprimeAdj(LinkedList<Arco>l){
    LinkedList<Arco> lista =new LinkedList<Arco>(l);
    String s="{";
    while(!lista.isEmpty()){
      s+=lista.removeFirst().extremo_final()+", ";
    }s+="}";
    return s;
  }
  //-----------------------------------------
  public static void main(String[] args) {
    int n,r,q,i;
    n=in.nextInt(); r = in.nextInt();in.nextLine();

    Grafo0 g = makeGrafo(n,r);//cria o grafo
    //criar vetor de valores máximos para cada no; que irá guardar o maximo de acessível
    int[] max = new int[n+1];
    for(i=0;i<=n;i++)max[i]=NOADJ;
    //---------------------------------

    q=in.nextInt();
    int[] p = new int[q];
    for(i=0;i<q;i++)p[i]=in.nextInt();
    in.nextLine();  in.close();


    for(i=0;i<q;i++){
      r=p[i];
      if(max[r] != NOADJ) System.out.println("No "+r+": "+max[r]);
      else{
        listaAdjacenciaMaxima(g,r,max);
        if(max[r] == 0) System.out.println("No "+r+": "+r);
         System.out.println("No "+r+": "+max[r]);
      }
    }
  }
}
