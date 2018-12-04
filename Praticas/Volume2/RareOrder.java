/*-------------------------------------------------------------------*\
|  Definicao de GRAFOS SEM PESOS                                      |
|     Assume-se que os vertices sao numerados de 1 a |V|.             |
|                                                                     |
|   A.P.Tomas, CC2001 (material para prova pratica), DCC-FCUP, 2017   |
|   Last modified: 2017.12.18                                         |
\--------------------------------------------------------------------*/

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

public class RareOrder{
  public static Scanner stdin = new Scanner(System.in);
  public static boolean visitado[] = new boolean[27];
  public static Stack<Integer> S = new Stack<Integer>();

  public static Grafo0 criarGrafo(){
    Grafo0 g = new Grafo0(26);

    String p1 = stdin.nextLine();
    String p2 = stdin.nextLine();
    while(p2.charAt(0) != '#'){
     int size = Math.min(p1.length(), p2.length());
     for(int i = 0; i < size; i++){
       int l1 = p1.charAt(i) - 64;
       int l2 = p2.charAt(i) - 64;
         if((l1 != l2)&&(g.find_arc(l1, l2) == null)){
            g.insert_new_arc(l1,l2);  break;
         }
     }
     p1 = p2;  p2 = stdin.nextLine();
    }
    return g;
  }
  public static void dfsVisit(Grafo0 g, int i){
    visitado[i] = true;//Marca como visitado para prevenir ciclos
    for(Arco adj: g.adjs_no(i)){//Explora todas as adjacências
      int w = adj.extremo_final();
      if(!visitado[w]) dfsVisit(g, w);
    }
    S.push(i);//como e recursiova no final, stack.top é o primeiro na ordem
  }
  public static void topSort(Grafo0 g){
    for(int i = 1; i <= 26; i++){//percorro o alfabeto
      if(!g.adjs_no(i).isEmpty() && !visitado[i])//tenho adjacências por visitar
        dfsVisit(g, i);
    }
    while(!S.isEmpty())  System.out.print((char)(S.pop() + 64));//imprime a ordem
    System.out.println();
  }

  public static void main(String args[]){
    Grafo0 g = criarGrafo();
    topSort(g);
  }
}
