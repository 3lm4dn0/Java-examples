package es.udc.fic.muei.csai.dap.main;

import java.io.IOException;


/**
 * Sample program for Vigenère encryption algorithm
 * Usage: Main [-c | --cipher] | [-d | --decipher] sometext alphabet key
 * or Main [-c | --cipher] | [-d | --decipher] sometext key
 * 
 * if alphabet is omitted the program will use "abcdefghijklmnopqrstuvwxyz" as alphabet.
 * 
 * @author d.albela
 *
 */
public class Encrypter {

  /**
   * 
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {       
    
    /* M = alphabet */
    String M;
    
    /* K = key */
    String K;
    
    /* text for cipher */
    String text;
    
    String result;
    
    /* Default M */
    String defaultM = "abcdefghijklmnopqrstuvwxyz";
    
    if( (args.length != 4 ) && (args.length != 3 ))
    {

      System.out.println("Usage: "+Encrypter.class.getSimpleName()+" option text [alphabet] key");
      System.out.println("Options:\n");
      System.out.println(" -c, --cipher\tCipher text with K key in M alphabet");
      System.out.println(" -d, --decipher\tDecipher text with K key in M aplhabet");
      System.out.println("\nif alphabet is omitted, the program will use \""+defaultM+"\" as alphabet.");
      
      System.out.println("\nExamples:\n");
      System.out.println(Encrypter.class.getSimpleName()+" -c cifradovigenere abcdeghijklmnopqrstuvwxyz secreto");
      System.out.println(Encrypter.class.getSimpleName()+" -c cifradovigenere secreto");
      System.out.println(Encrypter.class.getSimpleName()+" -d umhiewcnmivrxfw abcdefghijklmnopqrstuvwxyz secreto");
      System.out.println(Encrypter.class.getSimpleName()+" -d umhiewcnmivrxfw secreto");
      System.exit(0);
    }
    
    /* Get params */
    text = args[1];

    if(args.length==3){
      M = defaultM;
      K = args[2];
    }else{
      M = args[2];
      K = args[3];
    }
    
    System.out.println("Text: "+ text);
    System.out.println("M: "+ M);
    System.out.println("K: "+ K);    
    
    /* Create Vigenère object with alphabet M */
    VigenereAlgoritm vigenere = new VigenereAlgoritm(M);
        
    /* Get cipher or decipher text */
    if(args[0].equals("-d") || args[0].equals("--decipher"))
    {
      result = vigenere.decipher(text, K);
      System.out.println("Decipher text: " + result);      
    }else if(args[0].equals("-c") || args[0].equals("--cipher"))
    {
      result = vigenere.cipher(text, K);
      System.out.println("Cipher text: " + result);      
      
    }else{
      throw new IllegalArgumentException("Not a valid argument: "+args[0]);
    }   
  }

}
