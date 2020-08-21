import java.lang.*;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;

public class nomal_access_type_separator {
	public static void main(String args[]) {
		
		
		String f = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/object sensitivity vs risk simulation/normals.txt";
		String f_v = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/object sensitivity vs risk simulation/normals_view.txt";
		String f_e = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/object sensitivity vs risk simulation/normals_edit.txt";
		String f_ex = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/object sensitivity vs risk simulation/normals_execute.txt";
		/*
		String f = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/object sensitivity vs risk simulation/anomolous.txt";
		String f_v = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/object sensitivity vs risk simulation/anomolous_view.txt";
		String f_e = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/object sensitivity vs risk simulation/anomolous_edit.txt";
		String f_ex = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/object sensitivity vs risk simulation/anomolous_execute.txt";
		*/
		
		try {
		
		File file = new File(f);
		
		File file_v = new File(f_v);
		if(!file_v.exists()) {
			file_v.createNewFile();
		}
		
		File file_e = new File(f_e);
		if(!file_e.exists()) {
			file_e.createNewFile();
		}
		File file_ex = new File(f_ex);
		if(!file_ex.exists()) {
			file_ex.createNewFile();
		}
	
		Scanner fr = new Scanner(file);
		
		FileWriter fwv = new FileWriter(file_v);
		FileWriter fwe = new FileWriter(file_e);
		FileWriter fwex = new FileWriter(file_ex);
		
		String line;
		String[] line_details;
		
		while(fr.hasNextLine()) {
			line = fr.nextLine();
			line_details = line.split(",");
			if(line_details[6].contentEquals("1")) {
				fwv.write(line+"\n");
			}
			else if(line_details[6].contentEquals("2")) {
				fwe.write(line+"\n");
			}
			else if(line_details[6].contentEquals("3")) {
				fwex.write(line+"\n");
			}
		}
		
		fwv.flush();
		fwe.flush();
		fwex.flush();
				
	}
	catch(Exception e) {
		
	}
	}
}
