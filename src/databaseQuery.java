/**
 * Created by wangy_000 on 2016/8/25.
 */
import java.sql.*;
import java.util.ArrayList;

public class databaseQuery {
    private static final String DB_URL = "jdbc:postgresql://localhost:5434/GNAF";
    private static final String USER = "postgres";
    private static final String PASS = "wawin";

    //get suburb(locality) detail(locality_name state postcode geocode) by using suburb(locality) name
    static ArrayList<String> getLocalityDetail(String locality_name){
        Connection conn = null;
        Statement stmt = null;
        ArrayList<String> resultSet= new ArrayList<>();
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "select locality_name, state, postcode, latitude, longitude " +
                    "from gnaf.localities " +
                    "where locality_name='"+locality_name+"'";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String name = rs.getString("locality_name");
                String state = rs.getString("state");
                String postcode = rs.getString("postcode");
                String latitude = rs.getString("latitude");
                String longitude = rs.getString("longitude");
                resultSet.add(name+" "+state+" "+postcode+" ("+latitude+", "+longitude+")");
            }
            rs.close();

        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException ignored){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try;
        return resultSet;
    }

    //get street detail(street_name locality_name state postcode geocode) by using street name
    static ArrayList<String> getStreetDetail(String street_name){
        Connection conn = null;
        Statement stmt = null;
        ArrayList<String> resultSet= new ArrayList<>();
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "select full_street_name, locality_name, state, postcode, latitude, longitude " +
                    "from gnaf.streets " +
                    "where full_street_name='"+street_name+"'";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String name = rs.getString("full_street_name");
                String locality_name = rs.getString("locality_name");
                String state = rs.getString("state");
                String postcode = rs.getString("postcode");
                String latitude = rs.getString("latitude");
                String longitude = rs.getString("longitude");
                resultSet.add(name+" "+locality_name+" "+state+" "+postcode+" ("+latitude+", "+longitude+")");
            }
            rs.close();

        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException ignored){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try;
        return resultSet;
    }

    //
    static String getAddressDetail(String street_number, String street_name, String suburb) {
        Connection conn = null;
        Statement stmt = null;
        String result = "";

        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "select street_locality_pid, full_street_name, locality_name, state, postcode " +
                    "from gnaf.streets " +
                    "where full_street_name='"+street_name+"' and locality_name='"+suburb+"'";

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String street_locality_pid = rs.getString("street_locality_pid");
                String full_street_name =rs.getString("full_street_name");
                String locality_name =rs.getString("locality_name");
                String state =rs.getString("state");
                String postcode =rs.getString("postcode");

                String address_detail_pid = get_address_detail_pid(street_locality_pid,street_number);
                result = street_number+" "+full_street_name+" "+locality_name+" "+state+" "+postcode+" "+get_geocode(address_detail_pid);
            }
            rs.close();
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException ignored){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try;

        return result;
    }

    public static String get_address_detail_pid(String street_locality_pid,String street_number){
        Connection conn = null;
        Statement stmt = null;
        String result= "";
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "select address_detail_pid " +
                    "from raw_gnaf.address_detail " +
                    "where street_locality_pid='"+street_locality_pid+"' and number_first='" +street_number+"'";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                result = rs.getString("address_detail_pid");
            }
            rs.close();
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException ignored){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try;
        return result;
    }

    public static String get_geocode(String address_detail_pid){
        Connection conn = null;
        Statement stmt = null;
        String result= "";
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "select longitude, latitude " +
                    "from raw_gnaf.address_default_geocode " +
                    "where address_detail_pid='"+address_detail_pid+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                result = "("+ rs.getString("longitude")+", "+rs.getString("latitude")+")";
            }
            rs.close();
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException ignored){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try;
        return result;
    }

    static boolean ifStreet(String street_name){
        Connection conn = null;
        Statement stmt = null;
        boolean result = false;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "select full_street_name " +
                    "from gnaf.streets " +
                    "where full_street_name='"+street_name+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                result = true;
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
        return result;
    }

    static boolean ifLocality(String locality_name){
        Connection conn = null;
        Statement stmt = null;
        boolean result = false;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "select locality_name " +
                    "from gnaf.localities " +
                    "where locality_name='"+locality_name+"'";
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                result = true;
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
        return result;
    }




    public static ArrayList<String> getStreetSet(){
        Connection conn = null;
        Statement stmt = null;
        String result= "";
        ArrayList<String> streetSet= new ArrayList<>();
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            String sql = "select street_name " +
                    "from raw_gnaf.street_locality " ;
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                result = rs.getString("street_name");
                streetSet.add(result);
            }
            rs.close();

        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException ignored){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try;
        return streetSet;
    }




}
