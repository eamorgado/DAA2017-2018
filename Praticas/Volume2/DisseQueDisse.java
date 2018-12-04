public class DisseQueDisse{
  public static int MAXN = 101;
  public static Scanner userin = (system.in);
  public static void lerSeq(int[] v, int n){
    for(int i=0;i<n;i++){
      v[i]=userin.NextLine();
    }
  }
  public static void resolver(int[]v, int n){
    int fora=0, inicio=1;
    int ne,rep,prox;
    prox=v[inicio];
 	 while(prox != inicio){
 	  ne++;
 	  if(prox > rep){
 	   rep=prox;
 	  prox=v[prox];
 	 }if(ne<3){
 	  fora++;
 	  if(n2==2){v[v[inicio]]=-1;}
 	  v[inicio]=-1
 	 }
 	 else{
    System.out.print(" "+prox);
 	  prox = v[rep];
 	  v[rep]=-1;
 	  while(v[prox] != -1){
 	   System.out.print(" "+prox);
 	   rep=v[prox];
 	   v[prox]=1;
 	   prox=rep;
 	  }
 	 System.out.println();
 	 }
 	 do{inicio++;}while((inicio<=n) && (v[inicio] != -1))
 	}while(inicio<=n)
 }
  public static void main() {
    int n, v[MAXN];
    n=userin.NextLine();
    lerSeq(v,n);
    resolver(v,n);
  }
}
