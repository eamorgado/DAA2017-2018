import java.util.*;
public class SopaLetras{
  public static Scanner in = new Scanner(System.in);
  public static void testaPalavra(String s1, String s2){
    int palavra1; int palavra2;
    if(s1.equals(s2)){System.out.println("Nenhum"); return;}
    if(s1.length()>s2.length()){
      for(int i=0;i<s2.length();i++){
        palavra1=(int)s1.charAt(i);
        palavra2=(int)s2.charAt(i);
        if(palavra1 != palavra2) {
          System.out.println((char)palavra1+""+(char)palavra2); return;
        }
      }
      System.out.println("Nenhum"); return;
    }
    else if(s1.length()<s2.length()){
      for(int i=0;i<s1.length();i++){
        palavra1=(int)s1.charAt(i);
        palavra2=(int)s2.charAt(i);
        if(palavra1 != palavra2) {
          System.out.println((char)palavra1+""+(char)palavra2); return;
        }
      }
      System.out.println("Nenhum"); return;
    }
    else{
      for(int i=0;i<s1.length();i++){
        palavra1=(int)s1.charAt(i);
        palavra2=(int)s2.charAt(i);
        if(palavra1 != palavra2) {
          System.out.println((char)palavra1+""+(char)palavra2); return;
        }
      }
      System.out.println("Nenhum"); return;
    }
  }
  public static void main(String[] args){
    int palavra1; int palavra2;
    String s1,s2;
    s1 = in.nextLine();
    s2=in.nextLine();
    testaPalavra(s1,s2);
  }
}
