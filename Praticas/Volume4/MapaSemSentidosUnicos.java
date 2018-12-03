import java.util.*;
/* Estado: Terminado
|  MooshackDAA- Vol 4- Prob J - MapaSemSentidosUnicos (2018)  |
|     Objetivo: dado vários tranjetos, apresentar o comp      |
|       bem como o numero de ajacentes de todos os nos da rede|
|               -Cada trajeto é da forma:                     |
|                 k v1 c1 v2 ... vk-1 ck-1 vk                 |
|                 -k +e o numero de nos no percurso           |
|                 -Vx Cx Vy é o ramo bidirecional (Vx,Vy)     |
|                  com peso Cx onde peso é o seu comprimento  |
|--------------------------//---------------------------------|
|     INPUT:                                                  |
|       L1: #traços a analisar e #nos_rede                    |
|       -->Descrição de trajeto bidireciona                   |
|         #n(k) ei peso ef                                    |
|    OUTPUT: 2 blocos                                         |
|       -Info sobre comp total "Trajeto p: c"                 |
|       -#adjacentes de cada no da rede "No v: t"             |
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


public class MapaSemSentidosUnicos{
  public static Scanner in=new Scanner(System.in);
  public static void infoGrafo(){
    int trajetos,nos,k,tmp_k,ei,peso,ef,comprimento=0;
    trajetos=in.nextInt(); nos=in.nextInt();
    Grafo0 g = new Grafo0(nos);
    //Criar grafo e imprimir comprimentos
    for(int i=0;i<trajetos;i++){
      k=in.nextInt(); k--;
      ei=in.nextInt();
      while(k>0){
        peso=in.nextInt();
        ef=in.nextInt();
        comprimento+=peso;
        if(g.find_arc(ei,ef)==null){
          g.insert_new_arc(ei,ef);
          g.insert_new_arc(ef,ei);
        }
        ei=ef;
        k--;
      } in.nextLine();
      System.out.println("Trajeto "+(i+1)+": "+comprimento);
      comprimento=0;
    }
    //-----------------------------------
    for(int i=1;i<=nos;i++){
      System.out.println("No "+i+": "+g.adjs_no(i).size());
    }
    return;
  }
  public static void main(String[] args) {
    infoGrafo();
  }
}
