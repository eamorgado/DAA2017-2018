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



public class BonsMausCaminhos{
  /* sequência de palavras Maius,ordem lexicográfica crescente,
  Objetivo: decobrir precedências entre as letras usadas.
  Como: Construir Grafo da relação formada pelos PARES DE LETRAS de palavras consecutivas
      -nós:letras maiúsculas nas palavras.
      -letra x precede letra y sse find_arc(x,y)!= null
      - sequência pode não garantir a unicidade da relação

      O programa: dada uma sequência de palavras:
    -analise uma sequência de caminhos em G, possivelmente incorretos,
    - para cada um, indique se constitui:
          -justificação detalhada de que a primeira letra no caminho precede a última.
  */
  public static Scanner in = new Scanner(System.in);
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
  public static void testaCaminho(Grafo0 g){
    String s=in.nextLine();
    while(s.charAt(0)!='#'){
      int i=s.charAt(0)-64; int k;
      for(k=1;k<s.length();k++){
        int j= s.charAt(k)-64;
          if(g.find_arc(i,j) == null) break;
        i=j;
      }
      if(k < s.length())
          System.out.println("Nao");
      else
          System.out.println("Sim");
      s = in.nextLine();
    }

    return;
  }
  public static void main(String[] args){
    Grafo0 alfabetoRelacao = criarGrafo();
    testaCaminho(alfabetoRelacao);
  }
}
