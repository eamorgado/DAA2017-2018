import java.util.*;

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


public class QuantasDepois{
  public static Scanner in = new Scanner(System.in);
  public static boolean[] visitado = new boolean[27];

  public static Grafo0 criarGrafo(){
    Grafo0 g = new Grafo0(26);

    String p1 = in.nextLine();
    String p2 = in.nextLine();

    while(p2.charAt(0) != '#'){
     int size = Math.min(p1.length(), p2.length());
     for(int i = 0; i < size; i++){
       int l1 = p1.charAt(i) - 64;
       int l2 = p2.charAt(i) - 64;
         if(l1 != l2){
           if(g.find_arc(l1, l2) == null)
            g.insert_new_arc(l1,l2);  break;
         }
     } p1 = p2;  p2 = in.nextLine();
    }return g;
  }
  public static void quantosDepois(Grafo0 g){
    Queue<Integer> fila = new LinkedList<>();
    int raiz = (int)((in.nextLine()).charAt(0)-64); // raiz de toda a arvore
    int v,w,quantos_depois = 0;
    visitado[raiz]=true;
    fila.add(raiz);
    while(!fila.isEmpty()){
      v=fila.remove();
      for(Arco adjs : g.adjs_no(v)){
        w=adjs.extremo_final();
        if(!visitado[w]){
          visitado[w]=true;
          quantos_depois++;
          fila.add(w);
        }
      }
    }
    System.out.println(quantos_depois);
    return;
  }
  public static void main(String[] args){
    Grafo0 g = criarGrafo();
    quantosDepois(g);
  }
}
