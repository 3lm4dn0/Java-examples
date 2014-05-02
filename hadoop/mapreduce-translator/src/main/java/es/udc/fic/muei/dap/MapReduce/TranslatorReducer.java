package es.udc.fic.muei.dap.MapReduce;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TranslatorReducer extends
		Reducer<Text, IntWritable, Text, Text>
	{
	public void reduce(Text key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
	  		
		for (Text val : values)
		{
		  context.write(key, new Text(val));
		}
				
	}
}
