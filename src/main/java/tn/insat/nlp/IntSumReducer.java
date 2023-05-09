package tn.insat.nlp;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;

public class IntSumReducer
        extends TableReducer<Text, IntWritable, Text> {
    public static final byte[] CF = "cf".getBytes();
    public static final byte[] COUNT = "count".getBytes();
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values,
                       Context context
    ) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            System.out.println("value: "+val.get());
            sum += val.get();
        }
        System.out.println("--> Sum = "+sum);
        Put put = new Put(Bytes.toBytes(key.toString()));
        put.addColumn(CF, COUNT, Bytes.toBytes(String.valueOf(sum)));

        context.write(key, put);
    }
}
