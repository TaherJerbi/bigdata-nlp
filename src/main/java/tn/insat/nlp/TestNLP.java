package tn.insat.nlp;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import java.io.IOException;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;

public class TestNLP {

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Sentiment Analysis MR");
        job.setJarByClass(TestNLP.class);

        Scan scan = new Scan();
        scan.setCaching(500);        // 1 is the default in Scan, which will be bad for MapReduce jobs
        scan.setCacheBlocks(false);  // don't set to true for MR jobs
        // set other scan attrs
        TableName sourceTable = TableName.valueOf("comments_raw");
        TableName targetTable = TableName.valueOf("mr_job_sentiment_analysis");

        TableMapReduceUtil.initTableMapperJob(
                sourceTable,        // input table
                scan,               // Scan instance to control CF and attribute selection
                SentimentMapper.class,     // mapper class
                Text.class,         // mapper output key
                IntWritable.class,  // mapper output value
                job);

        TableMapReduceUtil.initTableReducerJob(
                targetTable.getNameAsString(),        // output table
                IntSumReducer.class,    // reducer class
                job);



        job.setNumReduceTasks(0);

        boolean b = job.waitForCompletion(true);
        if (!b) {
            throw new IOException("error with job!");
        }

    }
}
