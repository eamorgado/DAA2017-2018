import java.util.*;
/* Estado: Terminado
|  MooshackDAA- Vol 4- Prob A - BacalhausCongelados (2018)    |
|     Objetivo: A partir de rede de transportes de temp,      |
|            Apresentar para cada percurso a tempMin e tempMax|
|     Considerações:                                          |
|           - Existem restrições de tempMinima e máxima;      |
|--------------------------//---------------------------------|
|     INPUT:                                                  |
|         L1: #nos #ramos  n<=20000                           |
|         --> Descrição de ramos bidirecionais:               |
|           einicial efinal temperatura custo                 |
|         -->descrição de percurso (While(in.nextInt()!=0))   |
|           #numNos seguido da descrição do percurso          |
|    OUTPUT:                                                  |
|        - Para cada percuso apresentar tempMin e tempMax     |
| Nota: podemos percorrer os percursos e ir atualizando valor |
|         de tMin e tMax;                                     |
*/
public class BacalhausCongelados{
  public static Scanner in = new Scanner(System.in);
  public static int soma=0;

  public static Grafo criaGrafo(){
    int n=in.nextInt(),r=in.nextInt();
    int ei,ef,temp,custo;
    Grafo g= new Grafo(n);
    for(int i=0;i<r;i++){
      ei=in.nextInt(); ef=in.nextInt();
      temp=in.nextInt(); custo=in.nextInt();
      //if(g.find_arc(ei,ef)!=null){
        g.insert_new_arc(ei,ef,temp);
        g.insert_new_arc(ef,ei,temp);
        soma+=Math.abs(temp);//guardar valor absoluto de temperaturas
      //}
    }
    return g;
  }
  public static void lerPercursos(Grafo g){
    int i=in.nextInt(),tmin=soma,tmax=-1*soma,v1,v2,w=0;
    while(i!=0){
      v1=in.nextInt();
      for(int j=1;j<i;j++){
        v2=in.nextInt();
        Arco adj = g.find_arc(v1,v2);
        if(adj!=null){
          w=adj.valor_arco();
          if(w<tmin)tmin=w;
          if(w>tmax)tmax=w;
        }
        v1=v2;
      }
      System.out.println(tmin+" "+tmax);
      tmin=soma;tmax=-1*soma;
      i=in.nextInt();
    }
    return;
  }

  public static void main(String[] args) {
    Grafo g = criaGrafo();
    lerPercursos(g);
  }
}
