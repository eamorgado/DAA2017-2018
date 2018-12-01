import java.util.*;
/* Estado: Terminado
|  MooshackDAA- Vol 4- Prob D - CapacidadeTrajeto (2018)      |
|     Objetivo: A partir de percurso proposto, se for caminho |
|            de origem a destino,indicar a largura maxima     |
|--------------------------//---------------------------------|
|     INPUT:                                                  |
|         L1: #nos                                            |
|         L2: lmin lmax cmin cmax amin                        |
|         L3: origem destino                                  |
|         --> Descrição de ramos bidirecionais: (While!=-1)   |
|           einicial efinal largura comprimento altura        |
|         -->descrição de percurso (While(in.nextInt()!=0))   |
|           #nospercurso sequencia de nos                     |
|    OUTPUT:                                                  |
|        - Largura maxima para percurso válido                |
|Nota: quando dizem largura maxima é a largura que satistaz   |
|       tds os ramos do percurso, logo a minima do perc       |
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


public class CapacidadeTrajeto{
  public static Scanner in = new Scanner(System.in);
  public static int lmx,origem,destino;
  public static Grafo criaGrafo(){
    //-----Valores L1-L2-L3----------------------
    int n,lmin,lmax,cmin,cmax,amin;
    //-----Valores rede--------------------------
    int ei,ef,t_l,t_c,t_a;
    //-----Leitura L1-L2-L3----------------------
    n=in.nextInt();
    lmin=in.nextInt(); lmax=in.nextInt(); lmx=lmax;
    cmin=in.nextInt(); cmax=in.nextInt();
    amin=in.nextInt();
    origem=in.nextInt(); destino=in.nextInt();

    Grafo g = new Grafo(n);

    //-----Criação de Rede-----------------------
    ei=in.nextInt();
    while(ei!=-1){
      ef=in.nextInt();
      t_l=in.nextInt(); t_c=in.nextInt(); t_a=in.nextInt();
      //maximos de ramos atingem minorante de intervalos
      if(t_l>=lmin && t_c>=cmin && t_a>=amin){
        g.insert_new_arc(ei,ef,t_l);
        g.insert_new_arc(ef,ei,t_l);
      }
      ei=in.nextInt();
    }
    return g;
  }
  public static void larguraPercursos(Grafo g){
    boolean tem_origem=false,tem_destino=false,percurso_invalido=false;
    int i,valor1,valor2,lmax=lmx,w;

    i=in.nextInt();
    while(i!=0){
      valor1=in.nextInt();
      for(int j=1;j<i;j++){
        valor2=in.nextInt();
        if(!percurso_invalido){
          Arco adj = g.find_arc(valor1,valor2);
          //Caminho existe/segue restrições impostas
          if(adj!=null){
            w=adj.valor_arco();
            if(w<lmax)lmax=w;
            if((valor1==origem)&& !tem_origem && !tem_destino) tem_origem=true;
            //j=i-1 sei que o ultimo nó é o destino, pelo que chego a destino
            if((valor2==destino) && tem_origem &&!tem_destino && j==i-1) tem_destino=true;
          }
          else
            percurso_invalido=true;
        }
        valor1=valor2;
      }
      if(percurso_invalido || !tem_origem || !tem_destino)System.out.println("Nao");
      else System.out.println(lmax);
      lmax=lmx; tem_origem=false;tem_destino=false; percurso_invalido=false;
      i=in.nextInt();
    }
      return;
  }
  public static void main(String[] args){
    Grafo g_larguras = criaGrafo();
    larguraPercursos(g_larguras);
  }
}
