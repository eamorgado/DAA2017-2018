import java.util.*;
/* Estado: Terminado
|  MooshackDAA- Vol 3- Prob e - Sociologia (2018)  |
|     Objetivo:                                    |
|       -Determinar grupos de 4 ou + pessoas       |
|       -Quantas pessoas é que n estão nesses grupo|
|     Observações:                                 |
|       -cada individuo conta a sua hist e tds as q|
|         lhe são contadas                         |
|       -grupos podem ser formado por 1 pessoa     |
|--------------------------//----------------------|
|     INPUT:                                       |
|       L1: #cenários (num de expe-grafos novos)   |
|       -->Desc de cenários/grafos:                |
|         Lc1:#nos/alunos=#ramos n>=4              |
|         -->História (1linha por aluno)           |
|           no #amigos [amigos] (#amigos>=0)       |
|    OUTPUT: Para cada cenário                     |
|     L1: "Caso #x"                                |
|     L2: grupos4OuMais pessoasFora                |
|Nota:                                             |
| -Num grupo, se eu contar a minha hs e todas as q |
|   me foram contadas só conto às pessoas que me   |
|   contaram histórias em primeiro lugar, logo tds |
|   essas pessoas+eu formamos uma CFC do grafo     |
| -Podemos aplicar, para cada cenário o alg de     |
|   Kosaraju-Shari para obter a stack de componente|
|   fortemente conexas e imprimir s.size() e       |
|   #alunos-s.size()                               |
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


public class Sociologia{
  public static Scanner in = new Scanner(System.in);
  public static int grupos4=0,elementos4=0;
  public static void contaHistoria(Grafo0 g,Grafo0 g_t,int n){
    int ei, n_colegas,ef;
    for(int i=0;i<n;i++){
      ei=in.nextInt();
      n_colegas=in.nextInt();
      if(g.find_arc(ei,ei)==null){
        g.insert_new_arc(ei,ei);
        g_t.insert_new_arc(ei,ei);
      }
      while(n_colegas>0){
        ef=in.nextInt();
        if(g.find_arc(ei,ef)==null){
          g.insert_new_arc(ei,ef);
          g_t.insert_new_arc(ef,ei);
        }
        n_colegas--;
      }
    }
    return;
  }
  public static void dfsVisit(int v, boolean[] visitado,Grafo0 g,Stack<Integer> S){
    visitado[v]=true;
    //Verifica acessíveis
    for(Arco adjs:g.adjs_no(v))
      if(!visitado[adjs.extremo_final()])
        dfsVisit(adjs.extremo_final(),visitado,g,S);
    //---------------
    S.push(v);
  }
  public static void kosarajuHistoria(Grafo0 g,Grafo0 gt,int n){
    int v;
    Stack<Integer> S = new Stack<>();
    boolean[] visitado=new boolean[n+1];
    //Criação de pilha ordem topologica
    for(int i=1;i<=n;i++)
      if(!visitado[i])
        dfsVisit(i,visitado,g,S);
    //-------------------
    //Repor estados
    for(int i=1;i<=n;i++)visitado[i]=false;
    while(!S.isEmpty()){
      v=S.pop();
      if(!visitado[v]){
        Stack<Integer> CFC=new Stack<>();
        //Nova pilha de compi
        dfsVisit(v,visitado,gt,CFC);
        //o grupo de hist tem mais de 4 elem
        if(CFC.size()>=4){
          grupos4++;
          elementos4+=CFC.size();
        }
      }
    }
    return;
  }
  public static void main(String[] args) {
    int cenarios,n_alunos;
    cenarios=in.nextInt();
    for(int i=1;i<=cenarios;i++){
      n_alunos=in.nextInt();
      //crio G e G^t
      Grafo0 h=new Grafo0(n_alunos);
      Grafo0 h_T=new Grafo0(n_alunos);
      //Formo rede e sua transposta----
      contaHistoria(h,h_T,n_alunos);
      //num de pessoas em grupos de 4 ou mais
      kosarajuHistoria(h,h_T,n_alunos);
      System.out.println("Caso #"+i);
      System.out.println(grupos4+" "+(n_alunos-elementos4));
      grupos4=0; elementos4=0;
    }
  }
}
