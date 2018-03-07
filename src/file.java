import java.io.File;
import java.io.IOException;

/**
 * Created by wangy_000 on 2016/10/30.
 */
public class file {

    public static String project_path = System.getProperty("user.dir").replace("\\","/");

    public static void create(){
        try{
            File result = new File(project_path+"/src/data/result.txt");
            File html_sentence = new File(project_path+"/src/data/html_sentence.txt");
            File html_other = new File(project_path+"/src/data/html_other.txt");
            File details = new File(project_path+"/src/data/details.txt");
            html_sentence.createNewFile();
            html_other.createNewFile();
            result.createNewFile();
            details.createNewFile();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void delete(){
        File result = new File(project_path+"/src/data/result.txt");
        File html_sentence = new File(project_path+"/src/data/html_sentence.txt");
        File html_other = new File(project_path+"/src/data/html_other.txt");
        File details = new File(project_path+"/src/data/details.txt");
        details.delete();
        html_sentence.delete();
        html_other.delete();
        result.delete();
    }

    public static void setup(){
        File html_sentence = new File(project_path+"/src/data/html_sentence.txt");
        File result = new File(project_path+"/src/data/result.txt");
        File html_other = new File(project_path+"/src/data/html_other.txt");
        File details = new File(project_path+"/src/data/details.txt");
        boolean exists = html_sentence.exists() && result.exists() && html_other.exists() && details.exists();
        if (exists){
            delete();
            create();
        }else {
            create();
        }

    }
}
