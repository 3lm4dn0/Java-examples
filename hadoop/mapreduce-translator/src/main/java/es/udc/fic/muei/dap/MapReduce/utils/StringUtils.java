package es.udc.fic.muei.dap.MapReduce.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class StringUtils {
  
  public static List<String> tokenizer(String line, String separators)
  {
    List<String> terms = new ArrayList<String>();
    
    StringTokenizer tokenizer = new StringTokenizer(line, separators);
    while (tokenizer.hasMoreTokens())
    {
      terms.add(tokenizer.nextToken());

    }
    
    return terms;
  }

  /**
   * Replace common Names to <Name>
   * 
   * @param string
   * @return
   */
  public static String replacement(String string)
  {
    /* replace special characters */
    return string.replaceAll("(¡|¿)", "");
    
    //return string.replaceAll("[A-Z]\\w", "<Name>");
  }

}
