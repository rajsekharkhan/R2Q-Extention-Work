import java.lang.*;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;

public class normal_anomolous_separator {

	public static void main(String args[]) {
		String f1 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/object sensitivity vs risk simulation/sorted_runtime_collaborations.txt"; //input file
		String f2 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/object sensitivity vs risk simulation/anomolous.txt";
		String f3 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/object sensitivity vs risk simulation/normals.txt";
		try {
			File file = new File(f1);
			
			File file1 = new File(f2);
			if(!file1.exists()) {
				file1.createNewFile();
			}
		
			File file2 = new File(f3);
			if(!file2.exists()) {
				file2.createNewFile();
			}
			
			Scanner fr = new Scanner(file);
			
			FileWriter fw1 = new FileWriter(file1);
			FileWriter fw2 = new FileWriter(file2);
			
			String line;
			String[] line_details;
			
			while(fr.hasNextLine()) {
				line = fr.nextLine();
				line_details = line.split(",");
				if(line_details[2].contentEquals("1")) {
					fw1.write(line+"\n");
				}
				else if(line_details[2].contentEquals("2")) {
					fw2.write(line+"\n");
				}
			}
			fw1.flush();
			fw2.flush();
		}
		catch(Exception e) {
			
		}
		
	}
}
