import java.util.*;
/* Estado:
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
*/

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
    lmin=in.nextInt(); lmax=in.nextInt();
    cmin=in.nextInt(); cmax=in.nextInt();
    amin=in.nextInt();
    origem=in.nextInt(); destino=in.nextInt();
    //-----criaGrafo-----------------------------
    Grafo g = new Grafo(n);
    //-----Criação de Rede-----------------------
    ei=in.nextInt();
    while(ei!=-1){
      ef=in.nextInt();
      t_l=in.nextInt(); t_c=in.nextInt(); t_a=in.nextInt();
      if(t_l>=lmin && t_l<=lmax && t_c>=cmin && t_c<=cmax && t_a>=amin){
        g.insert_new_arc(ei,ef,t_a);
        g.insert_new_arc(ef,ei,t_a);
      }
      ei=in.nextInt();
    }
    return g;
  }
  public static void larguraPercursos(Grafo g){
    boolean o=false,d=false,invalido=false;
    int i,v1,v2,lmax=0,w;
    i=in.nextInt();
    while(i!=0){
      v1=in.nextInt();
      for(int j=1;j<i;j++){
        v2=in.nextInt();
        if(!invalido){
          Arco adj = g.find_arc(v1,v2);
          //Caminho existe/segue restrições impostas
          if(adj!=null){
            w=adj.valor_arco();
            if(w>lmax)lmax=w;
            if((v1==origem)&& o==false && d==false) o=true;
            if((v2==destino) && o&&!d) d=true;
          }
          //------------------------------------------
          //Caminho n existe/n segue restrições impostas
          else
            invalido=true;
        }
        v1=v2;
      }
      if(invalido || !o || !d)System.out.println("Nao");
      else System.out.println(lmax);
      lmax=0; o=false;d=false; invalido=true;
      i=in.nextInt();
    }
      return;
  }
  public static void main(String[] args){
    Grafo g_larguras = criaGrafo();
    larguraPercursos(g_larguras);
  }
}
