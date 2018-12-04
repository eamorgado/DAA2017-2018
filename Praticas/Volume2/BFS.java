import java.util.*;
public class BFS{
  public static final int UNDEF = 0; //pai
  public static Scanner in = new Scanner(System.in);
  public static void BFS_visit(int root,Grafo g, ArrayList pai){
    int v, w, n =g.num_vertices();
    Queue a_visitar = new Queue();
    LinkedList<Arco> adjs;
    boolean[] visitado = new boolean[n+1];
    for(v=1; v <= n; v++){ //iniviar tds os valores
      visitado[v] = false;
      pai.add(v,UNDEF);
    }

    visitado[s]=true; //raiz ja visitada;
    fila.enqueue(s);
    do {
      v=file.dequeue();
      adjs = g.adjs_no(v); // vou guardar tdos os adjacentes --acessiveis
      while(adjs != null){
        w = adjs.extremo_final();
        if(visitado[w] == false){
            lista.enqueue(w);
            visitado[w]=true;
            pai.set(w,v);
        }
        adjs.removeFirst();
      }
    } while (fila.isEmpty() != false);

  }
  public static Grafo makeGrafo(){
    int n_vertices, n_ramos, u, v;
    n_vertices= Integer.parseInt(in.next());
    n_ramos = Integer.parseInt(in.next()); in.nextLine();
    Grafo g = new Grafo(n_vertices);

    while(n_ramos > 0){
      u= Integer.parseInt(in.next());
      v = Integer.parseInt(in.next()); in.nextLine();
      g.insert_new_arc(u,v);
    }
    return g;
  }
  public static void escreveCaminho(int v, ArrayList pai){
    if(pai.get(v) != UNDEF){
      escreve_caminho(pai.get(v),pai);
      System.out.print(" ");
    }
  }
  public static void main(String[] args) {
    int s, n, v;
    ArrayList pai= new ArrayList(); // alocado dinamicamente sort of
    Grafo g = makeGrafo();
    n=g.num_vertices();
    s = Integer.parseInt(in.nextLine()); // guarda raiz

    BFS_visit(s,g,pai);
    for(v=1;v<=n;v++){
      if(pai.get(v) != UNDEF){
        escreveCaminho(v,pai);
        //putchar(\n);
      }
    }
  }
}
