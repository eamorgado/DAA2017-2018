import java.util.*;
/* Estado: Terminado
|  MooshackDAA- Vol 4- Prob C - InfraEstruturas (2018)        |
|     Objetivo: A partir de rede de transportes com restrições|
|            Apresentar o numero de percursos possível        |
|--------------------------//---------------------------------|
|     INPUT:                                                  |
|         L1: #nos                                            |
|         L2: lmin lmax cmin cmax amin                        |
|         L3: origem destino                                  |
|         --> Descrição de ramos bidirecionais: (While!=-1)   |
|           einicial efinal largura comprimento altura        |
|    OUTPUT:                                                  |
|        - Numero de ramos onde a caixa pode passar           |
|Nota: neste caso nem preciso de criar grafo                  |
*/

public class InfraEstruturas{
  public static Scanner in = new Scanner(System.in);
  public static void contaCaminhos(){
    //----Valores antes de Leitura rede
    int n,lmin,lmax,cmin,cmax,amin,origem,destino,caminhos=0;
    //----Valores de rede
    int ei,ef,t_lmax,t_cmax,t_amax;
    //----Leitura L1-L2-L3-------------
    n=in.nextInt();
    lmin=in.nextInt(); lmax=in.nextInt();
    cmin=in.nextInt();  cmax=in.nextInt(); amin=in.nextInt();
    origem=in.nextInt(); destino=in.nextInt();
    //---------Inicia Leitura grafo------
    ei=in.nextInt();
    while(ei!=-1){
      ef=in.nextInt();
      t_lmax=in.nextInt(); t_cmax=in.nextInt(); t_amax=in.nextInt();
      if(t_lmax>=lmin && t_lmax<=lmax && t_cmax>=cmin && t_cmax<=cmax && t_amax>=amin)
        caminhos++;
      ei=in.nextInt();
    }
    //---Retorna caminhos---------------
    System.out.println(caminhos);
    return;
  }
  public static void main(String[] args) {
    contaCaminhos();
  }
}
