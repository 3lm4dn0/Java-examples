package es.udc.fic.muei.dap.MapReduce;

import java.io.IOException;	

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Words count example
 * 		
 */
public class Translator {
  
  static String translateText;
  
	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {		
    
    if(args.length != 3){
      System.out.println("Usage: .jar Translator [input_path_texts] [\"translate_text\"]");
      return;
    }
    
    Path inputPath = new Path(args[0]);
    Path outputDir = new Path(args[1]);
    translateText = args[2];

		// Create configuration
		Configuration conf = new Configuration(true);

		// Create job
		Job job = new Job(conf, "Translator");
		job.setJarByClass(TranslatorMapper.class);

		// Setup MapReduce
		job.setMapperClass(TranslatorMapper.class);
		job.setReducerClass(TranslatorReducer.class);
		
		job.setNumReduceTasks(1);

		// Specify key / value
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		// Input
		FileInputFormat.addInputPath(job, inputPath);
		//job.setInputFormatClass(TextInputFormat.class);

		// Output
		FileOutputFormat.setOutputPath(job, outputDir);
		//job.setOutputFormatClass(TextOutputFormat.class);

		// Delete output if exists
		FileSystem hdfs = FileSystem.get(conf);
		if (hdfs.exists(outputDir))
			hdfs.delete(outputDir, true);

		// Execute job
		int code = job.waitForCompletion(true) ? 0 : 1;
		System.exit(code);
	}

}
