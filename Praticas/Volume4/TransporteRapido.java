import java.util.*;
/* Estado: Terminado
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
|Nota: neste caso posso usar um BFSDist                       |
*/

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

  public static boolean inBetween(int a, int b, int c){
    return (b>=a&&b<=c);
  }
  public static Grafo0 criaGrafo(){
    //Valores L1-L2-L3
    int n, lmin,lmax,cmin,cmax,amin,origem,destino;
    //Valores ramos
    int ei,ef,l,c,a;
    //Leitura L1-L2-L3--
    n=in.nextInt();
    lmin=in.nextInt(); lmax=in.nextInt();
    cmin=in.nextInt(); cmax=in.nextInt();
    amin=in.nextInt();
    origem=in.nextInt(); destino=in.nextInt();
    //----------------------------------------
    //Cria o grafo
    Grafo0 g = new Grafo0(n);
    //Cria rede de grafo----
    ei=in.nextInt();
    while(ei!=-1){
      ef=in.nextInt();
      //Valores de largura comp e altura
      l=in.nextInt(); c=in.nextInt(); a=in.nextInt();
      //Verifica se são válidos
      if(inBetween(lmin,l,lmax)&&inBetween(cmin,c,cmax)&&(a>=amin)){
        //São válidos insere para ramos bidirecionais
        g.insert_new_arc(ei,ef);
        g.insert_new_arc(ef,ei);
        //----------------------
      }
      //------------------
      //Atualiza ei
      ei=in.nextInt();
      //System.out.println("linha 54");
    }
    return g;
  }
  public static void procuraMinRamos(Grafo0 g){
    int n=g.num_vertices();
    //lista de visitas
    boolean[] visitado = new boolean[n+1];
    ArrayList<Integer> num_ramos = new ArrayList<>();
    for(int i=1;i<=n;i++) visitado[i]=false;
    for(int i=1;i<=n;i++){
      int total_ramos=bfsDist(g,i,visitado);
      if(total_ramos!=0) num_ramos.add(total_ramos);
    }
    int[] t =num_ramos.toArray();
    Arrays.sort(t);
    System.out.println(t[0]);
    return;
  }
  public static int bfsDist(Grafo0 g,int s,boolean[] visitado){
    int n=g.num_vertices(),v,w;
    int[] num_ramos= new int[n+1];
    //Lista para guardar nos a visitar
    Queue<Integer> lista = new LinkedList<>();
    //------------------------------------------
    //inicializa valores de visitado e num_ramos
    for(int i=1;i<=n;i++){num_ramos[i]=0;}
    //cria árvore de raiz s
    visitado[s]=true;
    lista.add(s);
    //-----------------
    while(!lista.isEmpty()){
      //retira valor à cabeça, o nó mais proximo
      v=lista.remove();
      //---------------------------------
      for(Arco adj : g.adjs_no(v)){
        w=adj.extremo_final();
        //por visitar
        if(!visitado[w]){
          //Atualiza numero de ramos de s para v para w
          num_ramos[w]=num_ramos[v]+1;
          //guarda w para teste
          lista.add(w);
          //marca como visitado
          visitado[w]=true;
        }
      }
    }
    return num_ramos[v];
  }
  public static void main(String[] args) {
    //Cria rede de percursos
    Grafo0 g = criaGrafo();
    //Encontra distancia minima
    procuraMinRamos(g);
  }
}
