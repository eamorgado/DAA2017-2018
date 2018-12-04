import java.util.*;
/* Estado: Terminado
|  MooshackDAA- Teste 1- Prob A - Halloween (2018)            |
|     Objetivo:                                               |
|       -Se aboboras de supermercado estiverem esgotadas det  |
|         supermercado com num de abóboras maior entre os aces|
|--------------------------//---------------------------------|
|     INPUT:                                                  |
|       L1: #nos                                              |
|       L2: a1,a2,..an (num de aboboras em lojas)             |
|       L3: #ramos                                            |
|       -->Descrição de ramos bidirecionais                   |
|         -ei ef                                              |
|       Lramos+1: #casos                                      |
|       Lr+2: linha de lojas onde estamos em cada caso        |
|    OUTPUT:                                                  |
|       -"Impossivel" n existem abóboras nem no sup nem en out|
|       -k:                                                   |
|         k=loja onde estamos se tiver aboboras               |
|         k=loja com maior num de aboboras acessível caso cont|
|Nota: para empates de abóboras escolhemos o com menor ind    |
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



public class Halloween{
  public static Scanner in = new Scanner(System.in);
  public static int[] aboboras_loja;
  public static Grafo0 criaLojas(int n){
    int ramos=in.nextInt(), ei,ef;
    Grafo0 g=new Grafo0(n);
    for(int i=0;i<ramos;i++){
      ei=in.nextInt();
      ef=in.nextInt();
      g.insert_new_arc(ei,ef);
      g.insert_new_arc(ef,ei);
    }
    return g;
  }
  public static int bfsAboboraMaxima(Grafo0 g,int s){
    int n=g.num_vertices(),v,w;
    int max_abo=0,ind_max=s;
    boolean[] visitado = new boolean[n+1];
    Queue<Integer> lista = new LinkedList<>();
    visitado[s]=true; lista.add(s);
    while(!lista.isEmpty()){
      v=lista.remove();
      if(aboboras_loja[v]>max_abo){
        max_abo=aboboras_loja[v]; ind_max=v;
      }
      else if(aboboras_loja[v]==max_abo&&v<ind_max){
        ind_max=v;
      }
      for(Arco adj:g.adjs_no(v)){
        w=adj.extremo_final();
        if(!visitado[w]){
          visitado[w]=true;
          lista.add(w);
        }
      }
    }
    return ind_max;
  }
  public static void main(String[] args) {
    int n=in.nextInt(),k,tmp,v;
    aboboras_loja=new int[n+1]; aboboras_loja[0]=0;
    for(int i=1;i<=n;i++){
      tmp = in.nextInt();
      aboboras_loja[i]=tmp;
    }
    Grafo0 g=criaLojas(n);

    k=in.nextInt();
    for(int i=0;i<k;i++){
      tmp=in.nextInt();
      if(aboboras_loja[tmp]!=0) System.out.println(tmp);
      else{
        v=bfsAboboraMaxima(g,tmp);
        if(aboboras_loja[v]==0) System.out.println("Impossivel");
        else System.out.println(v);
      }
    }
  }
}
