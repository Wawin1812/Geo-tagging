
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangy_000 on 2016/10/30.
 */
public class test {
    public static void main(String[] args) throws IOException {
        String[] urls_set1 = {
                "https://en.wikipedia.org/wiki/Australian_Capital_Territory",
                "https://en.wikipedia.org/wiki/Canberra",
                "https://en.wikipedia.org/wiki/Sydney",
                "https://en.wikipedia.org/wiki/Melbourne"
        };

        String[] urls_set2 = {
                "http://www.news.com.au/finance/economy/world-economy/uk-government-under-fire-for-plans-to-create-empire-20-after-brexit/news-story/1250cabb839406b9d980a61cdfab0cca",
                "http://www.news.com.au/finance/work/leaders/wikileaks-password-is-an-anticia-jfk-quote/news-story/d6c1cc5385c4f1330bb87f1be2ecefa3",
                "http://www.news.com.au/lifestyle/parenting/school-life/this-16yearolds-response-to-a-boy-asking-for-nude-pics-is-perfect/news-story/8b3881054dbf78e140e064d78b37bbb1",
                "http://www.news.com.au/national/crime/salt-creek-backpacker-attack-case-begins-in-south-australia/news-story/b1f64cfca6949e248f2049db1f8047a5",
                "http://www.australia.com/en/places/canberra.html",
                "http://www.canberra.edu.au/",
                "https://www.tripadvisor.com.au/Tourism-g255057-Canberra_Australian_Capital_Territory-Vacations.html",
                "http://www.canberratourism.com.au/",
                "http://www.act.gov.au/",
                "https://sydney.edu.au/",
                "https://www.lonelyplanet.com/australia/sydney",
                "https://www.sydneyoperahouse.com/",
                "https://gitlab.cecs.anu.edu.au/u5542170/GEo-tagging/tree/1cacd6e34aa97316f1f6ebba27d234316dcbdc5d",
                "http://techfinder.stanford.edu/technology_detail.php?ID=29724",
                "http://stanfordnlp.github.io/CoreNLP/index.html#license",
                "https://wattlecourses.anu.edu.au/course/view.php?id=19828"

        };


        System.out.println(locationPhrase.locationsClassify(stanfordNLP.findLocation_single("https://en.wikipedia.org/wiki/Canberra")));
        //stanfordNLP.findLocation_multi(urls_set1);

        //System.out.println(databaseQuery.getLocalityDetail("SYDNEY"));
        //System.out.println(databaseQuery.getStreetDetail("GEORGE STREET"));
        //System.out.println(databaseQuery.getAddressDetail("157","OODGEROO AVENUE","FRANKLIN"));
    }
}
