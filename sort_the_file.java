import java.io.*;
import java.util.*;

public class sort_the_file {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/object sensitivity vs risk simulation/unsorted_runtime_collaborations.txt"));
        Map<Double, String> map=new TreeMap<Double, String>();
        String line="";
        while((line=reader.readLine())!=null){
            map.put(getField(line),line);
        }
        reader.close();
        FileWriter writer = new FileWriter("/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/object sensitivity vs risk simulation/sorted_runtime_collaborations.txt");
        for(String val : map.values()){
            writer.write(val);  
            writer.write('\n');
        }
        writer.close();
    }

    private static Double getField(String line) {
        return Double.parseDouble(line.split(",")[9]);//extract value you want to sort on
    }
}