import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Administrator on 26/09/2016.
 */

public class locationFinder {

    public static void main(String sentence, ArrayList<String> result) {
        String project_path = System.getProperty("user.dir").replace("\\","/");

        InputStream modelInToken = null;
        InputStream modelIn = null;
        //System.out.println("The original sentece is: ");
        //System.out.println(sentence);

        try {

            //1. convert sentence into tokens
            modelInToken = new FileInputStream(project_path+"/src/openNLP/en-token.bin");
            TokenizerModel modelToken = new TokenizerModel(modelInToken);
            Tokenizer tokenizer = new TokenizerME(modelToken);
            String tokens[] = tokenizer.tokenize(sentence);

            //2. find names
            modelIn = new FileInputStream(project_path+"/src/openNLP/en-ner-location.bin");
            TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
            NameFinderME nameFinder = new NameFinderME(model);

            Span nameSpans[] = nameFinder.find(tokens);

            //find probabilities for names
            double[] spanProbs = nameFinder.probs(nameSpans);

            //3. print names
            for( int i = 0; i<nameSpans.length; i++) {
                //System.out.println("Span: "+nameSpans[i].toString());
                String first = tokens[nameSpans[i].getStart()];
                String second = tokens[nameSpans[i].getStart()+1];
                if (Character.isUpperCase(second.split("")[0].charAt(0))){
                    result.add(tokens[nameSpans[i].getStart()] + " " + tokens[nameSpans[i].getStart()+1]);
                    //System.out.println("Covered text is: "+tokens[nameSpans[i].getStart()] + " " + tokens[nameSpans[i].getStart()+1]);
                } else {
                    //System.out.println("Covered text is: "+tokens[nameSpans[i].getStart()]);
                    result.add(tokens[nameSpans[i].getStart()]);
                }
                //System.out.println("Probability is: "+spanProbs[i]);
            }
            //Span: [0..2) person
            //Covered text is: Jack London
            //Probability is: 0.7081556539712883
        }
        catch (Exception ex) {}
        finally {
            try { if (modelInToken != null) modelInToken.close(); } catch (IOException e){};
            try { if (modelIn != null) modelIn.close(); } catch (IOException e){};
        }
    }

    public static void listToText(ArrayList<String> results){
        String project_path = System.getProperty("user.dir").replace("\\","/");

        try {
            File file1 = new File(project_path+"/src/data/details.txt");

            FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw1);

            ArrayList<String> locations = new ArrayList<String>();
            for (int i = 0; i < results.size(); i++){
                if (!locations.contains(results.get(i))){
                    locations.add(results.get(i));
                }
            }

            for (String location : locations) {
                bw.write(location + '\r'+'\n');
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}