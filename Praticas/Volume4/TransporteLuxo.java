import java.util.*;
/* Terminado
|  MooshackDAA- Vol 4- Prob F - Transporte de Luxo (2018)     |
|     Objetivo: Saber se é possível transportar animal        |
|            - Se POSSIVEL: indicar comp minimo do percurso   |
|     Considerações:                                          |
|           - Comprimeto é o numero de ramos;                 |
|           - Existem restrições de tempMinima e máxima;      |
|--------------------------//---------------------------------|
|     INPUT:                                                  |
|         L1: 4 inteiros-- tempMin,tempMax,origem,destino     |
|         L2: #nos, #ramos     n<=20000,r<=5000               |
|         --> Descrição de ramos bidirecionais:               |
|           4 inteiros- einicial,efinal,temperatua,custoTrans |
|    OUTPUT:                                                  |
|        - "Sim "+compMin caso seja possível                  |
|        - "Nao" caso contrario                               |
| Nota: podemos resolver este problema utilizando uma BFSDist |
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
public class TransporteLuxo{
  public static Scanner in = new Scanner(System.in);
  public static int tmin,tmax,or,des;
  public static Grafo0 criaGrafo(){
    int n,r,temp,custo,ei,ef;
    tmin=in.nextInt(); tmax=in.nextInt();
    or=in.nextInt(); des=in.nextInt();
    n=in.nextInt(); r=in.nextInt();
    Grafo0 g = new Grafo0(n);
    for(int i=0;i<r;i++){
      ei=in.nextInt(); ef=in.nextInt();
      temp=in.nextInt(); custo=in.nextInt();
      if(temp<=tmax && temp>=tmin){
        g.insert_new_arc(ei,ef);
        g.insert_new_arc(ef,ei);
      }
    }
    return g;
  }
  public static void testaCaminhos(Grafo0 g,int s, int t){
    int n=g.num_vertices(),v=0;
    int[] comp = new int[n+1];
    boolean[] visitado = new boolean[n+1];
    boolean encontrou=false;
    Queue<Integer> lista = new LinkedList<>();
    for(int i=1;i<=n;i++){
      visitado[i]=false; comp[i]=0;
    }
    visitado[s]=true;
    lista.add(s);
    while(!lista.isEmpty()){
      v=lista.remove();
      if(v==t){encontrou=true;break;}
      for(Arco adjs : g.adjs_no(v)){
        int w=adjs.extremo_final();
        if(!visitado[w]){
          comp[w]=comp[v]+1;
          visitado[w]=true;
          lista.add(w);
        }
      }
    }
    if(encontrou) System.out.println("Sim "+comp[v]);
    else System.out.println("Nao");
    return;
  }
  public static void main(String[] args) {
    Grafo0 g = criaGrafo();
    testaCaminhos(g,or,des);
  }
}
