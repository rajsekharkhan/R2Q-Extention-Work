import java.lang.*;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;

public class GenerationOfUserSpecificRequestSetForAllUsers {

	static File file5,file6,file7;
	
	
	static ArrayList<String> entry_final_anomolous = new ArrayList<String>();
	static ArrayList<String> entry_final_normal = new ArrayList<String>();
	static ArrayList<String> entry_final = new ArrayList<String>();
	static double[] entry_total_mu_score = new double[10];
	static double initial_unalik_score,average_cma_unalik_score;
	static int SSL;
	static double reputation_score_calculated;
	static int requester_type;
	//static String requester_type;
	
	public static void main(String[] args) {
		
		try {
			String f5 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/User_specific_request_set_for_anomolous_users/updated_requester_file.txt";
			file5 = new File(f5); //for anomolous users
			
			String f6 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/User_specific_request_set_for_normal_users/updated_requester_file.txt";
			file6 = new File(f6); //for normal users
			
			String f7 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/User_specific_request_set/updated_requester_file.txt"; 
			file7 = new File(f7);
			
						
			
			FileWriter fw5 = new FileWriter(file5);
			FileWriter fw6 = new FileWriter(file6);
			FileWriter fw7 = new FileWriter(file7);
			
			
			
			for(int i=1; i<=5; i++) {
			
				int d = i;
				
				for(int j=1; j<=5; j++) {
				int id = j;
				System.out.println(d+","+id);
				
				average_cma_unalik_score = 0;
				
				for(int k=1;k<=10;k++) {
					call_make_request(d,id);
				}
				
				average_cma_unalik_score = (average_cma_unalik_score /10);
				
				inverse_gompertz_function igf = new inverse_gompertz_function();
				
				reputation_score_calculated = igf.call_inverse_gompertz_function(average_cma_unalik_score);
				
				String output = "D"+d+"_U"+id+","+SSL+","+requester_type+","+average_cma_unalik_score+","+reputation_score_calculated+"\n";
				System.out.println(output);
				
				entry_final.add(output);
				
				
				if(requester_type == 1) {
					entry_final_anomolous.add(output);				
					System.out.println("checkpoint : "+d+","+id+": returned");
				}
				else if(requester_type == 2) {
					entry_final_normal.add(output);				
					System.out.println("checkpoint : "+d+","+id+": returned");
				}
				
				
				}
				
				}
			
			for(int i = 0; i<entry_final.size();i++) {
				fw7.write(entry_final.get(i));
			}
				fw7.flush();
			
			for(int i=0; i<entry_final_anomolous.size();i++) {
				fw5.write(entry_final_anomolous.get(i));
			}
			fw5.flush();
		
		for(int i=0; i<entry_final_normal.size();i++) {
			fw6.write(entry_final_normal.get(i));
		}
		fw6.flush();
	
		}
		catch(Exception e) {
			
		}
	}
	public static void call_make_request(int d, int id) {
	GenerationOfUserSpecificHistoricalRequestSetUpdated generator = new GenerationOfUserSpecificHistoricalRequestSetUpdated();
	generator.make_request_set(d,id);
	average_cma_unalik_score = average_cma_unalik_score + generator.cma_unalik_score;
	
	initial_unalik_score = generator.initial_unalik_score;
	SSL = generator.SSL;
	//System.out.println("SSL : "+SSL);
	requester_type = generator.requester_type;
}
}
