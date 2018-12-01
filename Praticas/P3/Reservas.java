import java.util.*;

class Arco {
    int no_final;
    int valor0;
    int valor1;

    Arco(int fim, int v0, int v1){no_final = fim;	valor0  = v0;	valor1 = v1;}
    int extremo_final() {return no_final;}
    int valor0_arco() {return valor0;}
    int valor1_arco() {return valor1;}
    void novo_valor0(int v) {valor0 = v;}
    void novo_valor1(int v) {valor1 = v;}
}
class No {
    //int label;
    LinkedList<Arco> adjs;
    No() {adjs = new LinkedList<Arco>();}
}
class Grafo2 {
    No verts[];
    int nvs, narcos;

    public Grafo2(int n) {
      nvs = n;
       narcos = 0;
        verts  = new No[n+1];
         for (int i = 0 ; i <= n ; i++)  verts[i] = new No();
        // para vertices numerados de 1 a n (posicao 0 nao vai ser usada)
    }
    public int num_vertices(){return nvs;}
    public int num_arcos(){return narcos;}
    public LinkedList<Arco> adjs_no(int i) {return verts[i].adjs;}
    public void insert_new_arc(int i, int j, int valor0, int valor1){
	      verts[i].adjs.addFirst(new Arco(j,valor0,valor1));
        narcos++;
    }
    public Arco find_arc(int i, int j){
	     for (Arco adj: adjs_no(i))
	      if (adj.extremo_final() == j) return adj;
	    return null;
    }
}


public class Reservas{
  public static Scanner userin = new Scanner(System.in);
  public static Grafo2 criaRede(int nos_rede, int r_ligacoes, Scanner userin){
    //Cria o grafo original da rede
    int e_inicial,e_final,lugares,preco;
    Grafo2 rede_transporte= new Grafo2(nos_rede);

    for(int i=0; i<r_ligacoes;i++){
      //extremo inicial e extremo final de caminho
      e_inicial = userin.nextInt(); e_final = userin.nextInt();
      //disponibilidade e preco
      lugares=userin.nextInt(); preco=userin.nextInt();
      rede_transporte.insert_new_arc(e_inicial,e_final,lugares,preco);
      userin.nextLine();
    }
    return rede_transporte;
  }
  public static Grafo2 clonaGrafo(Grafo2 original){
    //Cria cópia indeoendente de grafo
    Grafo2 duplo = new Grafo2(original.num_vertices());
    for(int i=0;i<=duplo.num_vertices();i++){
      duplo.verts[i].adjs= new LinkedList<Arco>(original.adjs_no(i));
    }
    duplo.narcos=original.narcos;
    return duplo;
  }
  public static void testaReserva(Grafo2 g,String[] v,int i,Scanner in){
    String reserva = in.nextLine();
    Scanner user = new Scanner(reserva);
    int k_lugares,p_nos_percurso,x,y; int c_total=0;

    k_lugares=user.nextInt();
    p_nos_percurso=user.nextInt();
    x=user.nextInt(); //1º valor de reserva

    while(user.hasNextInt()){//função de teste
      y=user.nextInt();
      if(g.find_arc(x,y)==null){//caminho n existe posso fechar
        v[i]="("+x+","+y+") inexistente";
        user.close(); return;
      }
      else{//existe caminho
        if(g.find_arc(x,y).valor0_arco() >= k_lugares) //tem disponibilidade
          c_total+= k_lugares*g.find_arc(x,y).valor1_arco();
        else{ //não tem disponibilidade
          v[i]="Sem lugares suficientes em ("+x+","+y+")";
          user.close(); return;
        }
      }
      x=y;
    }
    user.close();
    //Se chegar aqui => o caminho pode ser feito
    Scanner val = new Scanner(reserva);
    val.nextInt(); val.nextInt(); //avançar k e p_nos_percurso
    v[i]="Total a pagar: "+c_total;
    x=val.nextInt();
    while(val.hasNextInt()){
      y=val.nextInt();
      g.find_arc(x,y).novo_valor0(g.find_arc(x,y).valor0_arco()-k_lugares);
      x=y;
    }
    val.close();
    return;
  }
  /*Mesma mas usando grafo teste para laterar
  public static void testaReserva(Grafo2 original,String[] v,int i,Scanner in){
    //Utilização de grafo auxiliar para nTeste
    //no final se o percurso for valido original = teste
    String reserva = in.nextLine();
    Scanner user = new Scanner(reserva);
    int k_lugares,p_nos_percurso,x,y; int c_total=0;
    Grafo2 teste = clonaGrafo(original);

    System.out.println("Original para reserva "+i+":");
    imprimeGrafo(original);
    System.out.println("\nTeste para reserva "+i+":");
    imprimeGrafo(teste);

    k_lugares=user.nextInt();
    p_nos_percurso=user.nextInt();
    x=user.nextInt(); //1º valor de reserva

    for(int j=1;j<p_nos_percurso;j++){
      y=user.nextInt();

      Arco viagem = teste.find_arc(x,y);
      if(viagem==null){v[i]="("+x+","+y+") inexistente";
        user.close(); return;
      }
      else{//existe viagem
        if(viagem.valor0_arco() >= k_lugares){
          c_total+= k_lugares*viagem.valor1_arco();
          viagem.novo_valor0(viagem.valor0_arco()-k_lugares);
        }
        else {
          v[i]="Sem lugares suficientes em ("+x+","+y+")";

          System.out.println("\nOriginal para reserva Inválida "+i+":");
          imprimeGrafo(original);
          System.out.println("\nTeste inválido para reserva "+i+":");
          imprimeGrafo(teste);

          user.close();
          return;
        }
      }
      x=y;
    }
    System.out.println("Original para reserva "+i+":");
    imprimeGrafo(original);
    System.out.println("\nTeste para reserva "+i+":");
    imprimeGrafo(teste);

    original=teste;
    v[i]="Total a pagar: "+c_total;
    user.close();
    return;
  }
  */
  //FUNÇÕES AUXILIARES---------------------------
  public static void imprimeGrafo(Grafo2 g){
    LinkedList<Arco> lista = new LinkedList<>();
    int n = g.num_vertices();
    System.out.println("Imprimir grafo:");
    for(int i=1;i<=n;i++){
      lista=g.adjs_no(i);
        if(lista==null)System.out.println("Nãe existe adjacência para no "+i+";");
        else System.out.println("Lista de adjacência para no "+i+": "+imprimeAdj(lista)+";");
    }
    return;
  }
  public static String imprimeAdj(LinkedList<Arco>l){
    LinkedList<Arco> lista =new LinkedList<Arco>(l);
    Arco ramo;
    String s="{";
    while(!lista.isEmpty()){ ramo=lista.removeFirst();
      s+="["+ramo.extremo_final()+", disponibilidade "+ramo.valor0_arco()+",preço "+ramo.valor1_arco()+"], ";
    }s+="}";
    return s;
  }
  //-------------------------
  public static void main(String[] agrs){
    int nos_rede, r_ligacoes, t_reservas;
    nos_rede=userin.nextInt(); r_ligacoes=userin.nextInt();
    userin.nextLine();

    //Cria grafo para rede de transpostes original
    Grafo2 rede_valida=criaRede(nos_rede,r_ligacoes,userin);

    //quantas reservas fazer?
    t_reservas = userin.nextInt(); userin.nextLine();

    String[] respostas = new String[t_reservas];
    for(int i=0;i<t_reservas;i++){
      /*System.out.println("Original para nova reserva "+i+":");
      imprimeGrafo(rede_valida);*/
      testaReserva(rede_valida,respostas,i,userin);
    }
    //imprimir valores
    for(int i=0;i<t_reservas;i++){
      System.out.println(respostas[i]);
    }

  }
}
