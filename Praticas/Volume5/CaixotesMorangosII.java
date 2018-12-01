import java.util.*;
import java.text.DecimalFormat;
/* Estado: Erro na solução
|  MooshackDAA- Vol 5- Prob c - CaixotesMorangosII (2018)     |
|     Objetivo: Determinar quantos caixotes deverá enviar para|
|            cada loja, de forma a maximizar o lucro          |
|Nota Erros Numéricos: não utlizar floats nem doubles, só ints|
|Notas de Lojas: cada loja tem local capacidadeArma numCli... |
|        log o lucro varia:                                   |
|         -É conhecido lucro de envio de n caixotes para cada |
|--------------------------//---------------------------------|
|     INPUT:                                                  |
|         L1: L C                                             |
|         --> Tabela de Lucros em euros lxC                   |
|                 pos (j,n)=lucro envo n caixotes para j      |
|    Restrições:                                              |
|    OUTPUT:                                                  |
|        - "Lucro Maximo = V" V-lucmax                        |
|        - numero de planos obtidos
*/
public class CaixotesMorangosII{
  public static Scanner in = new Scanner(System.in);
  public static void caixotesMorangos(int L,int C){
    long [] Nsols = new long[C+1];
    long [] Z = new long[C+1]; //lucros de 1..j-1
    long [] V = new long[C+1];
    Z[0]=0;
    //inicializar Nsols a 1 de 0--C---
    for(int i=0;i<=C;i++)Nsols[i]=1;
    //-----------------------------
    //lerLucros de Z-inicializar com loja 1
    for(int k=1;k<=C;k++){
      String[] s = in.next().split("\\.");
      long x=Integer.parseInt(s[0])*100;
      long y=Integer.parseInt(s[1]);
      Z[k]=x+y;
    }
    //--------------------------------
    for(int j=2;j<=L;j++){
      //ler lucros para loja V
      for(int i=1;i<=C;i++){
        String[] s = in.next().split("\\.");
        int x=Integer.parseInt(s[0])*100;
        int y=Integer.parseInt(s[1]);
        V[i]=x+y;
      }
      //-------------
      for(int k=C; k>1;k--){
        for(int t=0;t<k-1;t++){
          if(Z[t]+V[k-t]>Z[k]){
            V[k]=V[k-t]+Z[t];
            Z[k]=V[k];
            Nsols[k]=Nsols[t];
          }
          if(Z[t]+V[k-t]==Z[k]){
            Nsols[k]+=Nsols[t];
            Nsols[k]=Nsols[k]%1073741823;
          }
        }
      }
    }
    float f=(float)Z[C]/100;
    String s = String.format("%.2f",f).replace(",",".");
    System.out.println("Lucro Maximo = "+s);
    System.out.println("No.Sols = "+Nsols[C]);
    return;
  }
  public static void main(String[] args){
    int L,C;
    L=in.nextInt(); C=in.nextInt();
    caixotesMorangos(L,C);
  }
}
