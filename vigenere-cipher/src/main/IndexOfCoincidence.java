package es.udc.fic.muei.csai.dap.main;

/**
 * The Index of Coincidence provides a measure of how likely it would be to draw 
 * two matching letters if you randomly selected two letters from a given text.
 * 
 * 
 * @author d.albela
 *
 */
public class IndexOfCoincidence {
  
  private static String cipherText, alphabetM;
  
  /* IC spanish language */
  private static final double IC_spanish = 0.07750;
  
  /* Default M */
  private static final String defaultM = "abcdefghijklmnopqrstuvwxyz";
 
  public static void main(String[] args) {
    
    if( (args.length != 2 ) && (args.length != 1 ))
    {

      System.out.println("Usage: "+Encrypter.class.getSimpleName()+" cipher-text [alphabet]");
      System.out.println("\nif alphabet is omitted, the program will use \""+defaultM+"\" as alphabet.");
      System.out.println("\nUse a key of 8 characters maximum!"); 
      
      System.out.println("\nExamples:\n");
      System.out.println(Encrypter.class.getSimpleName()+" umhiewcnmivrxfw abcdeghijklmnopqrstuvwxyz");
      System.out.println(Encrypter.class.getSimpleName()+" umhiewcnmivrxfw");
      System.exit(0);
    }    
    
    /* Get params */    
    cipherText = args[0];

    if(args.length==1){
      alphabetM = defaultM;
    }else{
      alphabetM = args[1];
    }
    
    System.out.println("Cipher text: "+ cipherText);
    System.out.println("Alphabet M: "+ alphabetM);
       
    System.out.println(String.format("IC spanish: %.5f", IC_spanish));
    System.out.println(String.format("IC random: %.5f", (double)(1.0/alphabetM.length())));
    
    double ic = getIC();    
    System.out.println(String.format("IC: %.5f", ic));
  }

  /**
   * Return index of coincidence from a given text 
   * 
   * @return
   */
  private static double getIC() {
    double N = cipherText.length();
    
    double f = 0.0,sum = 0.0;
    for(int i=0; i<alphabetM.length(); i++){
      f = getAbsoluteFrequency(alphabetM.charAt(i));
      sum += f*(f-1);
    }
    
    return (double)sum/(N*(N-1));
  }

  /**
   * Get absolute frequency from a character on cipherText  
   * 
   * @param charAt
   * @return
   */
  private static double getAbsoluteFrequency(char character) {
    
    double total = cipherText.length();
    double match = 0;
    
    for(int i = 0; i < total; i++)
    {
      if(character == cipherText.charAt(i)){
        match++;
      }
    }
    
    return match;
  }

}
