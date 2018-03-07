import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by wangy_000 on 2016/9/25.
 */
public class locationPhrase {

    public static ArrayList<ArrayList<String>> locationsClassify(ArrayList<String> location_list){

        String state_string = "ACT,NSW,NT,QLD,SA,TAS,VIC,WA";

        ArrayList<ArrayList<String>> kinds_of_locations = new ArrayList<>();
        ArrayList<String> state = new ArrayList<>();
        ArrayList<String> locality = new ArrayList<>();
        ArrayList<String> street = new ArrayList<>();
        ArrayList<String> address = new ArrayList<>();
        ArrayList<String> other_place = new ArrayList<>();

        for (String aLocation_list : location_list) {
            if (aLocation_list == "AUSTRALIA") {
                other_place.add(aLocation_list);
            } else {
                if (state_string.contains(aLocation_list)) {
                    state.add(aLocation_list);
                } else {
                    if (databaseQuery.ifLocality(aLocation_list)) {
                        locality.add(aLocation_list);
                    } else {
                        if (databaseQuery.ifStreet(aLocation_list)) {
                            street.add(aLocation_list);
                        } else {
                            if (isInteger(aLocation_list.split(" ")[0], 1000)) {
                                address.add(aLocation_list);
                            } else {
                                other_place.add(aLocation_list);
                            }
                        }
                    }
                }
            }
        }

        ArrayList<String> state2 = new ArrayList<>();
        state2.add("AUSTRALIAN CAPITAL TERRITORY");state2.add("NEW SOUTH WALES");state2.add("NEW SOUTH WALES");
        kinds_of_locations.add(state);
        kinds_of_locations.add(locality);
        kinds_of_locations.add(street);
        kinds_of_locations.add(address);
        kinds_of_locations.add(other_place);


        return kinds_of_locations;
    }

    private static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }


    /*
    public static BloomFilter createFilter(){
        double falsePositiveProbability = 0.01;
        int expectedSize = 10000;

        BloomFilter<String> bloomFilter = new BloomFilter<String>(falsePositiveProbability, expectedSize);

        ArrayList<String> dataSet = databaseQuery.getStreetSet();

        for(int i=0;i<dataSet.size()-1;i++){
            bloomFilter.add(dataSet.get(i));
        }
        return bloomFilter;
    }

    address format:
    street_number street_name street_type suburb state postcode Australia

    street could be: simple street number or unit_number/street_number
    eg:
    157 oodgeroo st
    3/63 evelyn owen crescent

    street name could be more than one works (but most of them don't over two words):
    eg:
    HORSE PARK DRIVE

    street_type is usually one word.

    suburb could be more than one works (but most of them don't over two words):
    eg:
    FRANKLIN
    WODEN VALLEY

    state:
    ACT Australian Capital Territory
    NSW New South Wales
    NT  Northern Territory
    QLD Queensland
    SA  South Australia
    TAS Tasmania
    VIC Victoria
    WA  Western Australia

    postcode is 4-dig number.



    public static String phraseNormalization(String locationPhrase){
        return locationPhrase.toUpperCase();
    }

    public static boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }


    length = 1: suburb with single word  -- suburb_format

    length = 2: suburb with two words -- suburb_2_format
    length = 2: street with one word and street type -- street_type_format

    length = 3: street with two words and street type -- street_2_type_format
    length = 3: street number, street with one word and street type -- number_street_type_format

    length = 4: street with three words and street type -- street_3_type_format
    length = 4: street number, street with two words and street type -- number_street_2_type_format
    length = 4: street number, street with one word, street type and suburb -- number_street_type_suburb_format





    public static String phraseAnalyzer(String locationPhrase){
        int phraseLength = locationPhrase.split(" ").length;
        String result;
        if (phraseLength == 0){
            System.out.println("Error, location can not be empty.");
        }else if (phraseLength == 1 ){
            if (isInteger(locationPhrase, 5)){
                result = "Error, wrong phrase format.";
                return result;
            }else {
                if(Objects.equals(databaseQuery.isSuburb(locationPhrase), locationPhrase)){
                    result = "suburb_format";
                    return result;
                }else {
                    result = "Error, wrong phrase format.";
                    return result;
                }
            }
        }else if (phraseLength == 2){
            String first_word = locationPhrase.split(" ")[0];

            if(Objects.equals(databaseQuery.isSuburb(locationPhrase),locationPhrase)){
                result = "suburb_2_format";
                return result;
            } else if(Objects.equals(databaseQuery.isStreet(locationPhrase),locationPhrase)){
                result = "street_type_format";
                return result;
            } else {
                result = "Error, wrong phrase format.";
                return result;
            }

        }else if (phraseLength == 3){
            String first_word = locationPhrase.split(" ")[0];
            String second_word = locationPhrase.split(" ")[1];
            String third_word = locationPhrase.split(" ")[2];

            if (Objects.equals(databaseQuery.isStreet(locationPhrase),locationPhrase)){
                result = "street_2_type_format";
                return result;
            } else if(isInteger(first_word,10) && Objects.equals(databaseQuery.isStreet(second_word+" "+third_word),second_word+" "+third_word)){
                result = "number_street_type_format";
                return result;
            } else {
                result = "Error, wrong phrase format.";
                return result;
            }

        }else if (phraseLength == 4){
            String first_word = locationPhrase.split(" ")[0];
            String second_word = locationPhrase.split(" ")[1];
            String third_word = locationPhrase.split(" ")[2];
            String last_word = locationPhrase.split(" ")[3];

            if (Objects.equals(databaseQuery.isStreet(locationPhrase),locationPhrase)){
                result = "street_3_type_format";
                return result;
            } else if(isInteger(first_word,10) && Objects.equals(databaseQuery.isStreet(second_word+" "+third_word+" "+last_word),second_word+" "+third_word+" "+last_word)){
                result = "number_street_2_type_format";
                return result;
            } else if(isInteger(first_word,10) && Objects.equals(databaseQuery.isStreet(second_word+" "+third_word),second_word+" "+third_word) && Objects.equals(databaseQuery.isSuburb(last_word),last_word)){
                result = "number_street_type_suburb_format";
                return result;
            } else {
                result = "Error, wrong phrase format.";
                return result;
            }
        }
        return "Error, wrong phrase format.";
    }

    */



}
