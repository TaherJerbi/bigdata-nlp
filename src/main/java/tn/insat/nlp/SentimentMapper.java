package tn.insat.nlp;

import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Map;

public class SentimentMapper extends TableMapper<Text, IntWritable> {

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        public void map(Object key, Text value, Mapper.Context context
        ) throws IOException, InterruptedException {
            // SentimentAnalyzer singleton
            SentimentAnalyzer sentimentAnalyzer = SentimentAnalyzer.getInstance();

            System.out.println("value: " + value.toString());
            String cleanText = value.toString();
            System.out.println("cleanText: " + cleanText);
            context.write(new Text("test"), one);
//            for (Map.Entry<String, Integer> entry : sentimentAnalyzer.getSentiment(cleanText).entrySet()) {
//                String k = entry.getKey();
//                Integer v = entry.getValue();
//                word.set(k);
//                context.write(word, new IntWritable(v));
//                // (Sentiment, Count)
//            }
        }
}
