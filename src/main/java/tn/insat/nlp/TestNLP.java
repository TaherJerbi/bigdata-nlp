package tn.insat.nlp;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class TestNLP {

    public static void main(String[] args) {

        // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,sentiment");
        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // create a document object
        CoreDocument doc = new CoreDocument("I love this. I hate this.");
        // annotate
        pipeline.annotate(doc);
        // display sentences
        for (CoreSentence sentence : doc.sentences()) {
            String sentiment = sentence.sentiment();
            System.out.println(sentiment + " = " + sentence);
        }
    }
}
