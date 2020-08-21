import java.lang.*;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;

public class GenerationOfUserSpecificHistoricalRequestSetUpdated {
	static File file1,file2,file3,file4;
	static File fileall;
	static FileWriter fw;
	static ArrayList<String> entry = new ArrayList<String>();
	static ArrayList<String> entry1 = new ArrayList<String>();
	static ArrayList<String> entry2 = new ArrayList<String>();
	static double mu_obj, mu_access;
	static int top_secret_obj,secret_obj,confidential_obj,unclassified_obj,view_access,edit_access,execute_access,total_case,time_frame,requester_type;
	static double initial_unalik_score,unalik_score,cma_unalik_score,probability_score;
	static int SSL;
	static String request_pick;
	
	public static void main(String[] args) {
		System.out.println("checkpoint : main");
		try {
			Random rand1 = new Random();
			int d = rand1.nextInt(5)+1;
			int id = rand1.nextInt(50)+1;
			System.out.println("entry case : 1 : "+d+","+id);
			make_request_set(d,id);
			//System.out.println("checkpoint : returned to main");
		}
		catch(Exception e) {
			
		}
	}
	
	public static void make_request_set(int d, int id) {
		System.out.println("checkpoint : make_request_set");
		top_secret_obj = 0;
		secret_obj = 0;
		confidential_obj = 0;
		unclassified_obj = 0;
		
		view_access = 0;
		edit_access = 0;
		execute_access = 0;
		total_case = 0;
		initial_unalik_score = 0;
		unalik_score = 0;
		cma_unalik_score = 0;
		time_frame = 0;
		
		
		requester_type = 0;
		request_pick = "";
		
		String r1 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/D"+d+"_requester.txt";
		String s1 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/User_specific_request_set_for_anomolous_users/D"+d+"_U"+id+"_request_set.txt";
		String s2 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/User_specific_request_set_for_normal_users/D"+d+"_U"+id+"_request_set.txt";

		String rs1 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/Request_set.txt";
		
		String fall = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/Historical_request_set.txt";
		
		
		try {
			file1 = new File(r1); //requester set
			
			file4 = new File(rs1); //request set
			
			fileall = new File(fall); 	//overall historical request set
			if(!fileall.exists()) {
				fileall.createNewFile();
			}
			FileWriter fwall = new FileWriter(fileall,true);
			
			Random rand2 = new Random();
			int size_of_request_file = 15;
			
			Scanner fr1 = new Scanner(file1);
			
			for(int i=1; i<id; i++) {
				fr1.nextLine();
			}
			
			String line = fr1.nextLine();
			String[] data = line.split(" ");
			
			SSL = Integer.parseInt(data[1]);
			
			initial_unalik_score = Double.parseDouble(data[2]);
			
			
			unalik_score = initial_unalik_score;
			
			cma_unalik_score = initial_unalik_score;
			time_frame = 1;
			
			if(initial_unalik_score > 0.5 && initial_unalik_score <= 1.0) {
				requester_type = 1;	//anamolous users
				
				file2 = new File(s1); //historical request set output file for anomolous users
				 
				file2.createNewFile();
				fw = new FileWriter(file2,true);
				
			}
			else if(initial_unalik_score >= 0 && initial_unalik_score <= 0.5) {
				requester_type = 2; //normal users
				
				file3 = new File(s2); //historical request set output file for normal users
				
				file3.createNewFile();
				
				fw = new FileWriter(file3,true);
			}
			
			request_pick = pick_a_request();
			String rout1 = "D"+d+"_U"+id+","+request_pick;  
			
			//entry.add(rout1);
			
			update_details(rout1);
			unalik_score = calculate_unalik_score();
			
			entry.add(rout1);
			
			
			time_frame++;
			cma_unalik_score = recompute_cma_unalik_score(cma_unalik_score);
			
			probability_score = set_prob_score();
			for(int i = 2; i<= size_of_request_file; ) {
				double random_prob_score = rand2.nextDouble();
				if(random_prob_score >= probability_score) {
					request_pick = pick_a_same_request();
				}
				else {
					request_pick = pick_a_different_request();
					System.out.println(request_pick);
				}
				rout1 = "D"+d+"_U"+id+","+request_pick;
				//entry.add(rout1);
				
				update_details(rout1);
				unalik_score = calculate_unalik_score();
				
				entry.add(rout1);
				
				
				time_frame++;
				
				cma_unalik_score = recompute_cma_unalik_score(cma_unalik_score);
				i++;
			}
			
			String line1 = top_secret_obj+","+secret_obj+","+confidential_obj+","+unclassified_obj+","+view_access+","+edit_access+","+execute_access+"\n";
			fw.write(line1);
			
			fw.write(initial_unalik_score+"\n");
			
			fw.write(unalik_score+"\n");
			
			fw.write(cma_unalik_score+"\n");
			
			for(int i=0; i<entry.size();i++) {
				fw.write(entry.get(i)+"\n");
				fwall.write(entry.get(i)+"\n");
			}
			fw.flush();
			fwall.flush();
			
			entry.clear();
			
			
	
		}
		catch(Exception e) {
			
		}
	
	
	}
	
	public static double set_prob_score() {
		System.out.println("checkpoint : set_prob_score");
		double prob_score = 0;
		
		if(requester_type == 1) {
			prob_score = 0.9;
		}
		else if(requester_type == 2) {
			prob_score = 0.1;
		}
		
		return prob_score;
	}
	
	public static String pick_a_request() {
		System.out.println("checkpoint : pick_a_request");
		String line = "";
		Random rand = new Random();
		int n = rand.nextInt(100)+1;
		try {
			Scanner fr4 = new Scanner(file4);
			for(int i=1; i<n; i++)
				fr4.nextLine();
		
		line = fr4.nextLine();
		}
		catch(Exception e) {
			
		}
		return line;
	}
	
	public static String pick_a_same_request() {
		System.out.println("chcekpoint : pick_a_same_request");
		
		String s = "";
		s = request_pick;		
		
		return s;
	}
	
	public static String pick_a_different_request() {
		System.out.println("checkpoint : pick_a_different_request");
		String s_picked = "";
		Random rand = new Random();
		try {
		int n = rand.nextInt(100)+1;
		Scanner fr = new Scanner(file4);
		for(int i=1; i<n;i++) {
			fr.nextLine();
		}
		s_picked = fr.nextLine();
		System.out.println(request_pick);
		System.out.println(s_picked);
		}
		catch(Exception e) {
			
		}
		if(ensure_different_request(request_pick,s_picked)) {
			return s_picked;
		}
		else {
			s_picked = pick_a_different_request();
		}
		System.out.println("returned from the called function");
		
		return s_picked;	
	}
	
	public static void update_details(String rout) {
		System.out.println("checkpoint : update_details");
		
		String[] rout_details = rout.split(",");
		
		int obj_type =Integer.parseInt(rout_details[3]);
		int acc_type =Integer.parseInt(rout_details[4]);
		
		if(obj_type == 1) {
			 top_secret_obj++;
		 }
		 else if(obj_type == 2) {
			 secret_obj++;
		 }
		 else if(obj_type == 3) {
			 confidential_obj++;
		 }
		 else if(obj_type == 4) {
			 unclassified_obj++;
		 }
		 
		 if(acc_type == 1) {
			 view_access++;
		 }
		 else if(acc_type == 2) {
			 edit_access++;
		 }
		 else if(acc_type == 3) {
			 execute_access++;
		 }
		 
		 
		 total_case++;
	}
	
	public static double calculate_unalik_score() {
		System.out.println("checkpoint : claculate_unalik_score");
		
		double recomputed_unalik_score = 0;
		try {
		double ptopsecret = (double)top_secret_obj/total_case;
    	double psecret = (double) secret_obj/total_case;
    	double pconfidential = (double)confidential_obj/total_case;
    	double punclassified = (double)unclassified_obj/total_case;
    
    	double pv = (double)view_access/total_case;
    	double pe = (double)edit_access/total_case;
    	double pex = (double)execute_access/total_case;
    	
    	//double mu_obj = 1 - Math.pow(psens,2) - Math.pow(pnonsens,2);
    	mu_obj = 1 - Math.pow(ptopsecret,2) - Math.pow(psecret,2) - Math.pow(pconfidential, 2) - Math.pow(punclassified, 2);
    	
    	//double mu_access = 1 - Math.pow(pv,2) - Math.pow(pe, 2) - Math.pow(pex, 2);
    	mu_access = 1 - Math.pow(pv,2) - Math.pow(pe, 2) - Math.pow(pex, 2);
    	double sum = (mu_obj + mu_access);
    	
    	recomputed_unalik_score = Math.pow(sum, (1-sum));
    	
	}
	catch(Exception e) {
		
	}
	
	return recomputed_unalik_score;
	}
	
	public static double recompute_cma_unalik_score(double prev_cma_unalik_score) {
		System.out.println("checkpoint : recompute_cma_unalik_score");
		
		double recomputed_cma_unalik_score = 0;
		
		recomputed_cma_unalik_score = (unalik_score + ((time_frame-1)*prev_cma_unalik_score))/time_frame;
		
		
		//have to check with NaN values.
		
		if(Double.isNaN(recomputed_cma_unalik_score)) {
			System.out.println("NaN case arised");
			System.exit(0);
		}
		return recomputed_cma_unalik_score;
	}
	
	public static boolean ensure_different_request(String prev_request, String s) {
		System.out.println("checkpoint : ensure_different_request");
		System.out.println(prev_request);
		System.out.println(s);
		//boolean result;
		
		String[] data1 = prev_request.split(",");
		int obj_type1 = Integer.parseInt(data1[2]);
		int acc_type1 = Integer.parseInt(data1[3]);
		
		System.out.println("x");
		
		String[] data2 = s.split(",");
		int obj_type2 = Integer.parseInt(data2[2]);
		int acc_type2 = Integer.parseInt(data2[3]);
		
		System.out.println("1");
		if((obj_type1 == obj_type2)&&(acc_type1 == acc_type2)) {
			System.out.println("2");
			return false;
		}
		else {
			System.out.println("3");
			return true;
	}
	}
}
