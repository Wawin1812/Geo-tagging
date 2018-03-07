
import java.util.ArrayList;

/**
 * Created by wangy_000 on 2016/10/5.
 */
public class BloomFilterTest{

    public static void main(String[] args) {
        double falsePositiveProbability = 0.01;
        int expectedSize = 10000;

        BloomFilter<String> bloomFilter = new BloomFilter<String>(falsePositiveProbability, expectedSize);

        final long startTime3 = System.nanoTime();
        ArrayList<String> dataSet = databaseQuery.getStreetSet();


        for(int i=0;i<dataSet.size()-1;i++){
            bloomFilter.add(dataSet.get(i));
        }

        System.out.println("done");
        System.out.println("Data size is: " + dataSet.size());
        final long endTime3 = System.nanoTime();
        System.out.println("Total execution time: " + (endTime3 - startTime3) );

        //bloomfilter search
        final long startTime1 = System.nanoTime();
        if (bloomFilter.contains("OODGEROO")){
            System.out.println("true");
        }else{
            System.out.println("false");
        }
        final long endTime1 = System.nanoTime();
        System.out.println("Total execution time: " + (endTime1 - startTime1) );

        //line search
        final long startTime = System.nanoTime();
        if (dataSet.contains("OODGEROO")){
            System.out.println("true");
        }else {
            System.out.println("false");
        }
        final long endTime = System.nanoTime();
        System.out.println("Total execution time: " + (endTime - startTime) );

        //database search
        final long startTime2 = System.nanoTime();
        if (databaseQuery.ifStreet("OODGEROO")){
            System.out.println("true");
        }else {
            System.out.println("false");
        }
        final long endTime2 = System.nanoTime();


        System.out.println("Total execution time: " + (endTime2 - startTime2) );


    }
}
