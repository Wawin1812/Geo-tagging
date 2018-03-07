package New;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/24.
 */
public class databasePhrase {

    static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "123";

    public static ArrayList<ArrayList<String>> Phrase(ArrayList<String> location_list){

        String state_string = "ACT,NSW,NT,QLD,SA,TAS,VIC,WA";

        ArrayList<ArrayList<String>> locations = new ArrayList<>();
        ArrayList<String> Country = new ArrayList<>();
        ArrayList<String> State = new ArrayList<>();
        ArrayList<String> City = new ArrayList<>();
        ArrayList<String> Suburb = new ArrayList<>();
        ArrayList<String> Street = new ArrayList<>();
        ArrayList<String> Address = new ArrayList<>();
        ArrayList<String> other_place = new ArrayList<>();

        String city_sql ="";
        String suburb_sql ="";
        String street_sql ="";

        for (int i = 0; i < location_list.size(); i++){

            if (location_list.get(i) == "AU" || location_list.get(i) == "Australia"){
                    Country.add("Australia");
                }else {
                    if (state_string.contains(location_list.get(i))){
                        State.add(location_list.get(i));
                    }else {

                        Connection conn = null;
                        Statement stmt = null;

                        try{
                            Class.forName("org.postgresql.Driver");
                            conn = DriverManager.getConnection(DB_URL, USER, PASS);
                            stmt = conn.createStatement();

                            ResultSet rs = stmt.executeQuery(city_sql+location_list.get(i));

                            if (rs.next()){
                                City.add(location_list.get(i));
                            }else {
                                rs = stmt.executeQuery(suburb_sql+location_list.get(i));
                                if (rs.next()){
                                    Suburb.add(location_list.get(i));
                                }else {
                                    rs = stmt.executeQuery(street_sql+location_list.get(i));
                                    if (rs.next()){
                                        Street.add(location_list.get(i));
                                    }else {
                                        if (Character.isDigit(location_list.get(i).charAt(0))){
                                            Address.add(location_list.get(i));
                                        }else {
                                            other_place.add(location_list.get(i));
                                        }
                                    }
                                }
                            }
                            rs.close();
                        } catch(Exception e){
                            e.printStackTrace();
                        }finally{
                            try{
                                if(stmt!=null)
                                    conn.close();
                            }catch(SQLException ignored){
                            }
                            try{
                                if(conn!=null)
                                    conn.close();
                            }catch(SQLException se){
                                se.printStackTrace();
                            }
                        }

                    }
            }
        }

        return locations;
    }
}
