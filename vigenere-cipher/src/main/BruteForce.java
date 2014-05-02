package es.udc.fic.muei.csai.dap.main;

import java.io.IOException;

/**
 * This class try break cipher algoritm Vigenère
 * by brute force attack with a private key with 8 chars as maximum 
 * 
 * Usage: java BruteForce plainText cipherText alphabet
 * if alphabet is omitted, the program will use default alphabet "abcdefghijklmnopqrstuvwxyz"
 * 
 * @author d.albela
 *
 */
public class BruteForce {

  private static String plainText;
  private static String cipherText;
  private static String alphabetM;
  
  private static long startTime;
  
  /* Default M */
  private static final String defaultM = "abcdefghijklmnopqrstuvwxyz";
  
  /* Max length search for a key */
  private static final int MAXIMUM_KEY_LENGTH = 8;

  public static void main(String[] args) throws IOException {       
    
    if( (args.length != 3 ) && (args.length != 2 ))
    {

      System.out.println("Usage: "+Encrypter.class.getSimpleName()+" plain-text cipher-text [alphabet]");
      System.out.println("\nif alphabet is omitted, the program will use \""+defaultM+"\" as alphabet.");
      System.out.println("\nUse a key of 8 characters maximum!"); 
      
      System.out.println("\nExamples:\n");
      System.out.println(Encrypter.class.getSimpleName()+" cifradovigenere umhiewcnmivrxfw abcdeghijklmnopqrstuvwxyz");
      System.out.println(Encrypter.class.getSimpleName()+" cifradovigenere umhiewcnmivrxfw");
      System.exit(0);
    }
    
    /* Get params */
    plainText = args[0];
    cipherText = args[1];

    if(args.length==2){
      alphabetM = defaultM;
    }else{
      alphabetM = args[2];
    }   
    
    System.out.println("Plain text: "+ plainText);
    System.out.println("Cipher text: "+ cipherText);
    System.out.println("Alphabet M: "+ alphabetM);
    
    startTime = System.nanoTime();
    
    /* Create all possible keys */
    for(int i=1; i<=MAXIMUM_KEY_LENGTH; i++)
    {
      /* obtain all permutation keys with i length */
      /* and find key with key i length */
      System.out.println("Searching key with "+i+" length...");
      permutation("", i);
    }
        
    System.out.println("Key not found.");
    
  }

  /**
   * Try key to decipher the text 
   * 
   * @param key
   * @param plainText
   * @param cipherText
   * @param alphabetM
   * @return
   */
  private static String ckeckPossibleKey(String key) {
    VigenereAlgoritm vigenere = new VigenereAlgoritm(alphabetM);

    try {
      String result = vigenere.decipher(cipherText, key);
      if(result.equals(plainText))
      {
        return key;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return null;

  }

  /**
   * Permutation for key with max length
   * 
   * @param prefix
   * @param length
   * @return
   */
  private static void permutation(String prefix, int length)
  {   
    if (length == 0){
      
      String result; 
      if( (result=ckeckPossibleKey(prefix)) != null )
      {
        long duration = System.nanoTime() - startTime;
        
        System.out.println("Finded key: " + result);
        
        System.out.println("Time: " + ((double)duration / 1000000000.0) + " seconds");
        System.exit(0);
      }
      
      //System.out.println(prefix);
    }
    else {
        for (int i = 0; i < alphabetM.length(); i++){
            permutation(prefix + alphabetM.charAt(i), length-1);
        }
    }
  }
  
}
