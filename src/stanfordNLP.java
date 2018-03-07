import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import edu.stanford.nlp.simple.*;


/**
 * Created by Fred on 2017/3/7.
 */
/*
about the usage of StanfordNLP, see Slack.

*/

public class stanfordNLP {

    private static int countWords(String s){

        int wordCount = 0;

        boolean word = false;
        int endOfLine = s.length() - 1;

        for (int i = 0; i < s.length(); i++) {
            // if the char is a letter, word = true.
            if (Character.isLetter(s.charAt(i)) && i != endOfLine) {
                word = true;
                // if char isn't a letter and there have been letters before,
                // counter goes up.
            } else if (!Character.isLetter(s.charAt(i)) && word) {
                wordCount++;
                word = false;
                // last word of String; if it doesn't end with a non letter, it
                // wouldn't count without this.
            } else if (Character.isLetter(s.charAt(i)) && i == endOfLine) {
                wordCount++;
            }
        }
        return wordCount;
    }

    private static String textExtractor(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements paragraphs = doc.select("p,li,h1, h2, h3, h4, h5, h6");

        String sentences = "";
        for (Element paragraph : paragraphs) {
            String result = paragraph.text();
            if (!result.isEmpty()) {
                if (Character.isUpperCase(result.split("")[0].charAt(0)) &&
                        Arrays.asList(".","?","!",":",";").contains(result.split("")[result.split("").length - 1])) {
                    sentences = sentences + result + "\n";
                }
                // add " as a factor of defining sentences
                if ( result.split("")[0].charAt(0) == '\"' &&
                        Arrays.asList(".","?","!",":").contains(result.split("")[result.split("").length - 1])) {
                    sentences = sentences + result + "\n";
                }
            }
        }
        return sentences;
    }

    public static void findLocation_multi(String[] urls) throws IOException {

        final long startTime = System.nanoTime();

        for (String url:urls) {
            Sentence sent = new Sentence(textExtractor(url));
            List<String> LOCATION = sent.mentions("LOCATION");
            System.out.println("The URL is : "+url+"\n"+countWords(textExtractor(url))+" words");
            System.out.println(LOCATION.size() + " locations" + "\n" + LOCATION.toString());
        }

        final long stopTime = System.nanoTime();

        NumberFormat formatter = new DecimalFormat("#0.00");
        System.out.println("time used: "+formatter.format((stopTime-startTime)/1000000000)+" seconds.");
    }



    static ArrayList<String> findLocation_single(String url) throws IOException {

        Sentence sent = new Sentence(textExtractor(url));
        List<String> LOCATION = sent.mentions("LOCATION");
        Set<String> hs = new HashSet<>();
        hs.addAll(LOCATION);
        LOCATION.clear();
        LOCATION.addAll(hs);
        ArrayList<String> resultSet = new ArrayList<>();
        for (int i=0;i<LOCATION.size();i++){
            resultSet.add(LOCATION.get(i).toUpperCase());
        }

        return resultSet;
    }
}
