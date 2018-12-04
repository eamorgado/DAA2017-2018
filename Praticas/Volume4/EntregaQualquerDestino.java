import java.util.*;
/* Estado:
|  MooshackDAA- Vol 4- Prob  - EntregaQualquerDestino (2018)  |
|     Objetivo:                                               |
|--------------------------//---------------------------------|
|     INPUT:                                                  |
|    OUTPUT:                                                  |
*/

public  class EntregaQualquerDestino{
  public static Scanner in = new Scanner(System.in);
  public static int origem,vmax,vmin;
  public static Grafo criaGrafo(){
    int traco,nos,k,ei,ef,peso,problematicos,amax,lmax;
    origem=in.nextInt();
    vmin=in.nextInt(); vmax=in.nextInt();
    traco=in.nextInt(); nos=in.nextInt();
    Grafo g=new Grafo(nos);

    for(int i=0;i<traco;i++){
      k=in.nextInt(); k--;
      ei=in.nextInt();
      while(k>0){
        peso=in.nextInt();
        ef=in.nextInt();
        if(g.find_arc(ei,ef)==null){
          g.insert_new_arc(ei,ef,vmax);
          g.insert_new_arc(ef,ei,vmax);
        }
        ei=ef;
        k--;
      }
    }
    return g;
  }
  public static void bfsVisit(Grafo g,int s){
    int n=g.num_vertices(),v,w;
    int[] pai=new int[n+1];
    boolean[] visitado=new boolean[n+1];
    Queue<Integer> lista = new LinkedList<>();
    for(int i=0;i<=n;i++) pai[i]=-1;
    pai[s]=0; visitado[s]=true; lista.add(s);
    while(!lista.isEmpty()){
      v=lista.remove();
      for(Arco adjs:g.adjs_no(v)){
        w=adjs.extremo_final();
        if(!visitado[w]){
          visitado[w]=true;
          lista.add(w);
          pai[w]=v;
        }
      }
    }
    for(int i=1;i<=n;i++)
      if(pai[i]==-1)
        System.out.println("No "+i+": 0");
    return;
  }
  public static void atualizaRede(Grafo g,int n,int min,int max){
    int ei,ef,a,l;
    for(int i=0;i<n;i++){
      ei=in.nextInt(); ef=in.nextInt();
      a=in.nextInt(); l=in.nextInt();
      /*Situações a,l
      1) -1 -1                       -->Inv
      2) -1 b<vmin || a<vmin -1      -->Inv
      3) a>vmax b>vmax               -->peso=vmax
      4) a>vmax bE[vmin,vmax]        -->peso=b
      5) aE[vmin,vmax] b>vmax        -->peso=a
      6) aE[vmin,vmax] bE[vmin,vmax] -->peso=max(a,b)
      */
      if((a==-1&&l==-1) || ((a==-1&&l<vmin) || (l==-1&&a<vmin))){
        //1) e 2)
        g.find_arc(ei,ef).novo_valor(-1);
        g.find_arc(ef,ei).novo_valor(-1);
      }
      else if(a>vmax&&l>vmax){//3)
        g.find_arc(ei,ef).novo_valor(vmax);
        g.find_arc(ef,ei).novo_valor(vmax);
      }
      else if(a>vmax && Math.max(Math.min(l,vmax),vmin)==l){
        g.find_arc(ei,ef).novo_valor(l);
        g.find_arc(ef,ei).novo_valor(l);
      }
      else if(l>vmax && Math.max(Math.min(a,vmax),vmin)==a) {
        g.find_arc(ei,ef).novo_valor(a);
        g.find_arc(ef,ei).novo_valor(a);
      }
      else{
        g.find_arc(ei,ef).novo_valor(Math.max(a,l));
        g.find_arc(ef,ei).novo_valor(Math.max(a,l));
      }
    }
    return;
  }
  public static void minDjs(Grafo g,int s, int max){
    int n=g.num_vertices(),tmp_max,v,w,peso;
    int[]
  }
  public static void main(String[] args) {
    int problematicos;
    Grafo g = criaGrafo();
    problematicos=in.nextInt();
    if(problematicos==0)
      bfsVisit(g,origem);
    else{
      atualizaRede(g,problematicos,vmin,vmax);
      bfsMax(g,origem,vmax);
    }
  }
}
