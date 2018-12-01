import java.util.*;

class Arco {
    int no_final;
    int valor;

    Arco(int fim, int v){
	     no_final = fim;
	     valor = v;
    }
    int extremo_final() {return no_final;}
    int valor_arco() {return valor;}
    void novo_valor(int v) {valor = v;}
}
class No {
    //int label;
    LinkedList<Arco> adjs;
    No() {adjs = new LinkedList<Arco>();}
}
class Grafo {
    No verts[];
    int nvs, narcos;

    public Grafo(int n) {
	     nvs = n;
       narcos = 0;
	     verts  = new No[n+1];
	     for (int i = 0 ; i <= n ; i++)
	      verts[i] = new No();
        // para vertices numerados de 1 a n (posicao 0 nao vai ser usada)
    }
    public int num_vertices(){return nvs;}
    public int num_arcos(){return narcos;}
    public LinkedList<Arco> adjs_no(int i) {return verts[i].adjs;}
    public void insert_new_arc(int i, int j, int valor_ij){
	     verts[i].adjs.addFirst(new Arco(j,valor_ij));
        narcos++;
    }
    public Arco find_arc(int i, int j){
	     for (Arco adj: adjs_no(i))
	      if (adj.extremo_final() == j) return adj;
	     return null;
    }
}




public class SelecaoRota{
  public static Scanner in = new Scanner(System.in);
  public static final int INV = -1; //marca reservaa como inpp
  public static int lugares,partida,chegada;
  public static int prob_min,rota_valida;

  public static Grafo criaGrafo(){
    int paragens,rotas,p_partida,p_chegada,problemas;
    Grafo g;

    paragens=in.nextInt(); rotas=in.nextInt(); in. nextLine();
    g=new Grafo(paragens);//cria grafo
    for(int i=0;i<rotas;i++){//criar rede
      p_partida=in.nextInt(); p_chegada=in.nextInt(); problemas=in.nextInt();
      in.nextLine();
      //               pPartida     pChegada     Problema
      g.insert_new_arc(in.nextInt(),in.nextInt(),in.nextInt());
    }return g;
  }
  public static int testaReserva(Grafo g){
    int nos_reserva = in.nextInt(), n_problemas = 0;
    boolean tem_partida = false,valida=false;
    int v = in.nextInt();
    while(nos_reserva > 1){
        nos_reserva--;
        int l = in.nextInt();
        int k = in.nextInt();
        if( v == partida ) tem_partida=true;
        if(l < lugares )//não tem lugares suficientes
            break;
        Arco adjs = g.find_arc(v, k);
        n_problemas += adjs.valor_arco();
        if(tem_partida && (k == chegada)){//chegamos ao destino
          valida=true;
          break;
        }
        v = k;
    }
    if(nos_reserva == 1 && !valida){ //acabei a leitura desta reserva
        return g.num_vertices() + 1;//a reserva n pode ser feita
    }
    else {
        while(nos_reserva > 1){ //ainda tenho coisas para ler
            in.nextInt();
            in.nextInt();
            nos_reserva--;
        }
    }
    if(valida)//a reserva pode ser feitaa
        return n_problemas;
    return g.num_vertices() + 1;
}

  public static void main(String[] args){
    lugares = in.nextInt();
    partida=in.nextInt();
    chegada=in.nextInt();
    in.nextLine();

    Grafo g= criaGrafo();
    int reservas = in.nextInt();
    int prob_min=g.num_vertices(); //sei que nunca iráultrapassar este valor
    rota_valida=0;
    for(int i=1;i<=reservas;i++){
      int prob_na_rota= testaReserva(g);
      if(prob_na_rota < prob_min){
          prob_min = prob_na_rota;
          rota_valida = i;
      }
    }
    if(rota_valida != 0) System.out.println("Reserva na rota "+rota_valida+": "+prob_min);
    else System.out.println("Impossivel");
  }
}
