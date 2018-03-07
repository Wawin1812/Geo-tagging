import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Created by wangy_000 on 2016/10/30.
 */
public class geo {

    public JTextField textField1;
    private JPanel panel1;
    private JButton searchButton;

    private JButton quitButton;
    public JTextArea textArea1;

    public static final String project_path = System.getProperty("user.dir").replace("\\","/");
    public String result_file = project_path+"/src/data/result.txt";

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Geo Tagging System");
        frame.setContentPane(new geo().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //JTextArea textArea1 = new JTextArea ("");
        //JScrollPane scroll = new JScrollPane (textArea1, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //frame.getContentPane().add(scroll);
        //frame.add(scroll);
        frame.pack();
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    public geo(){

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    search();
                    JOptionPane.showMessageDialog(null, "Webpage has been successfully captured!");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void search() throws IOException {
        String url = textField1.getText();
        String result ="";

        ArrayList<ArrayList<String>> kinds_of_lists = locationPhrase.locationsClassify(stanfordNLP.findLocation_single(url));

        ArrayList<String> state = kinds_of_lists.get(0);
        ArrayList<String> locality = kinds_of_lists.get(1);
        ArrayList<String> street = kinds_of_lists.get(2);
        ArrayList<String> address = kinds_of_lists.get(3);
        ArrayList<String> other_place = kinds_of_lists.get(4);

        if (state != null){
            for (int i=0;i<state.size();i++){
                result =result + state.get(i)+"\n";
            }
        }

        if (locality != null){
            for (int i=0;i<locality.size();i++){
                for (int j=0;j<databaseQuery.getLocalityDetail(locality.get(i)).size();j++){
                    result = result + databaseQuery.getLocalityDetail(locality.get(i)).get(j)+"\n";
                }
            }
        }

        if (street != null){
            for (int i=0;i<street.size();i++){
                for (int j=0;j<databaseQuery.getStreetDetail(street.get(i)).size();j++){
                    result = result + databaseQuery.getStreetDetail(street.get(i)).get(j)+"\n";
                }
            }
        }

        if (other_place != null){
            for (int i=0;i<other_place.size();i++){
                result=result+other_place.get(i)+"\n";
            }
        }

        textArea1.setText(result);

    }



}
