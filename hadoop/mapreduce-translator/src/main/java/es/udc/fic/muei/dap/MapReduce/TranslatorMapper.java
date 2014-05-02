package es.udc.fic.muei.dap.MapReduce;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import es.udc.fic.muei.dap.MapReduce.utils.StringUtils;

public class TranslatorMapper extends Mapper<Object, Text, Text, Text> {
  	
	private Text original = new Text();
	private Text translate = new Text();
	private static double UMBRAL = 0.80; 

	public void map(Object key, Text value, Context context)
			throws IOException, InterruptedException {
		String line = value.toString();
		
		/* get fields from each line */
		String fields[] = StringUtils.tokenizer(line, "\t").toArray(new String[0]);		

		// if exists the word
		if(fields.length == 5)
		{
	    /* removes personal Names */
	    String english = StringUtils.replacement(fields[2]);
	    String spanish = StringUtils.replacement(fields[3]);
	    
		  if(line.contains(Translator.translateText))
		  {
		    mapBySentence(english, spanish, context);
    	// if not exist the text to translate split the words  		
  		}
  		else
  		{
        mapByWord(english, spanish, context);
      }
    }
	}
	
	/**
	 * Search a similar sentences comparing words from a sentence
	 * 
	 * @param english
	 * @param spanish
	 * @param context
	 */
	private void mapByWord(String english, String spanish, Context context)
	{
    /* split translate in words */
	  List<String> words = StringUtils.tokenizer(Translator.translateText, ".:;?!() ");
	  
    /* split fields in sentences */
    List<String> fieldEnglish = StringUtils.tokenizer(english, ".:;?!()");
    List<String> fieldSpanish = StringUtils.tokenizer(spanish, ".:;?!()");          
 
    if(fieldEnglish.size() == fieldSpanish.size())
    {
      Iterator<String> it = fieldSpanish.iterator();
      for(String sentence : fieldEnglish)
      {
        // Count total words from text to translate compared with sentence 
        int exists = 0;
        for(String w : words)
        {
          if(sentence.contains(w))
          {
            exists++;
          }
        }

        // if text to translate has UMBRAL (80%) from the word of this sentence, add to output
        if((double)exists >= words.size() * UMBRAL)
        {
          // add value
          original.set(sentence);
          
          translate.set(it.next());
        }
      }
    }	  	 
	    
  }

  private void mapBySentence(String english, String spanish, Context context) throws IOException, InterruptedException
	{

    
    /* split fields in sentences */
    List<String> fieldEnglish = StringUtils.tokenizer(english, ".:;?!()");
    List<String> fieldSpanish = StringUtils.tokenizer(spanish, ".:;?!()");          
 
    if(fieldEnglish.size() == fieldSpanish.size())
    {
      Iterator<String> it = fieldSpanish.iterator();
      for(String s : fieldEnglish)
      {
        // add value
        original.set(s);
        
        translate.set(it.next());
        
        // set pair key-value if exist the word
        if(s.contains(Translator.translateText))
        {
          context.write(original, translate);
        }
      }
    }	  
	}
}
