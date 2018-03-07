/**
 * Created by wangy_000 on 2016/10/29.
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


/**
 * Example program to list links from a URL.
 */
public class webProcess {

    public static void textExtractor(String url) throws IOException {
        String project_path = System.getProperty("user.dir").replace("\\","/");
        file.setup();

        Document doc = Jsoup.connect(url).get();
        Elements paragraphs = doc.select("p,li,h1, h2, h3, h4, h5, h6");

        try {
            File file1 = new File(project_path+"/src/data/html_sentence.txt");
            File file2 = new File(project_path+"/src/data/html_other.txt");

            FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw1);

            FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
            BufferedWriter bw2 = new BufferedWriter(fw2);


            for (Element paragraph : paragraphs) {
                String result = paragraph.text();
                if (!result.isEmpty()) {
                    if (Character.isUpperCase(result.split("")[0].charAt(0)) && Objects.equals(result.split("")[result.split("").length-1], ".")) {
                        bw.write(result + '\r'+'\n');
                    }else if(Objects.equals(result.split("")[result.split("").length-1], "")){
                        bw.write(result + '\r'+'\n');
                    }else if (result.split(" ").length >= 4) {
                        result = result + ".";
                        bw.write(result + '\r'+'\n');
                    } else if (result.split(" ").length < 4) {
                        bw2.write(result + '\r'+'\n');
                    }
                }
            }

            /*
            for (Element paragraph : paragraphs) {
                System.out.println(paragraph.text());
            }
            */

            bw.close();
            bw2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String catchDetail(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        String title = doc.title();
        return title+"\n"+
                "URL:"+url+'\n'+
                "Catch success!";
    }
}

