import java.lang.*;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;

public class GenerationOfCollaborationRequestAtSimulationRuntime {

	static File file1,file2,file3;
	static File parameter_file;
	//file1 : updated requester file
	//file2 : request set
	//file3 : runtime history
	static File file_runtime; //details of runtime simulation stored here
	
	static String requester,request;
	static double reputation_score, current_reputation_score;
	static double current_mu;
	//static double obj_sensitivity;
	static double risk_score_computed;
	static double[] parameter;
	//while creating sens_vs_risk file
	//static double normalized_object_sensitivity;
	
	public static void get_parameters_values() {
		double R1 = 0;
		 parameter=new double[7];
	        int z=0;
	        
	        try {
	        	
	        	parameter_file = new File("/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/parameters.txt");
	        	if(!parameter_file.exists()) {
	        		parameter_file.createNewFile();
	        	}
	        	
	        	Scanner pfr = new Scanner(parameter_file);
	        	String line;
	        	
	        	while ((line = pfr.nextLine()) != null) {
	                String[] line_details=line.split("\\s");
	                R1 = Double.parseDouble(line_details[1]);
	                
	                parameter[z]=R1;
	                z++;
	                }
	        }
	        catch(Exception e) {
	        	
	        }
	}
	
	public static void main(String[] args) {
		
		String f1 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/User_specific_request_set/updated_requester_file.txt";			//file 1 : requester file
		file1 = new File(f1);
		
		String f2 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/Request_set.txt";		//file 2 : request file
		file2 = new File(f2);
		
		
		String f3 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/runtime_simulation/runtime_historical_collaboration_set.txt";	//file 3 : output file
		file3 = new File(f3);
		
		String ff = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/runtime_simulation/updated_requester_file.txt";
		file_runtime = new File(ff);
		
		try {
		if(!file_runtime.exists()) {
			file_runtime.createNewFile();
		}
		}catch(Exception e) {
			
		}
		
		int runtime_time_instance = 10;
		Random rand = new Random();
		
		get_parameters_values();
		create_runtime_users_details();
		
		for(int i=1; i<=runtime_time_instance; i++) {
			int n = rand.nextInt(100)+1;
			System.out.println("task called : "+i);
			System.out.println(n);
			
			runtime_simulation(n);
			System.out.println("task completed : "+i);
		}
		 
		System.out.println("task completed");
		 
	}
	
	public static void runtime_simulation(int n) {
		
		
		
		Random rand = new Random();
		
		for(int i=1; i<=n; i++) {
			int d = rand.nextInt(5)+1;
			int id = rand.nextInt(5)+1;
			
			String uid = "D"+d+"_U"+id;
			/*
			 try {
			
			String f4 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/runtime_simulation/runtime_collaborations.txt";
			File file4 = new File(f4);
			if(!file4.exists()) {
				file4.createNewFile();
			}
			}
			catch(Exception e) {
				
			}
			*/
			
			
			
			requester = get_requester(uid);
			
			request = pick_a_request();
			
			double mu_score = get_mu_score(uid);
			
			double n2 = rand.nextDouble();
			
			
			
			if(n2 <= mu_score) {
				current_mu = mu_score;
				inverse_gompertz_function igf = new inverse_gompertz_function();
				current_reputation_score = igf.call_inverse_gompertz_function(mu_score);
						
				risk_score_calculated rsc = new risk_score_calculated();
				risk_score_computed = rsc.call_risk_score_computation(requester, request, current_mu, current_reputation_score); //call risk computation function
				double normalized_object_sensitivity = rsc.normalized_object_sensitivity;
				System.out.println(normalized_object_sensitivity);
				
				insert_into_users_runtime_historical_set(requester, request, risk_score_computed);
				insert_into_runtime_collaboration_set(requester,request, risk_score_computed,normalized_object_sensitivity,current_mu);
				
			
			}
			else if(n2 > mu_score) {
				
				double current_mu_score = recalculate_mu_score(uid);
				update_runtime_users_details(uid, current_mu_score);
				current_mu = get_mu_score(uid);
				//update historical request set to get mu_score
				
				inverse_gompertz_function igf = new inverse_gompertz_function();
				current_reputation_score = igf.call_inverse_gompertz_function(current_mu);
				
				//risk_score_calculated rsc = new risk_score_calculated();
				risk_score_calculated rsc = new risk_score_calculated();
				risk_score_computed = rsc.call_risk_score_computation(requester, request, current_mu, current_reputation_score); //call risk computation function
				double normalized_object_sensitivity = rsc.normalized_object_sensitivity;
				System.out.println(normalized_object_sensitivity);
				
				insert_into_users_runtime_historical_set(requester, request, risk_score_computed);
				insert_into_runtime_collaboration_set(requester,request,risk_score_computed,normalized_object_sensitivity,current_mu);
			}
			
			
		}
	
	}
	
	public static double get_mu_score(String uid) {
		double mu_score = 0;
		try {
			Scanner fr = new Scanner(file_runtime);
			
			String line = "";
			String[] line_details = new String[6];
			while(fr.hasNextLine()) {
				line = fr.nextLine();
				line_details = line.split(",");
				if(line_details[0].contentEquals(uid)) {
					mu_score = Double.parseDouble(line_details[5]);
					break;
				}
			}
		 
		  		  
		  reputation_score = Double.parseDouble(line_details[4]);
		}
		catch(Exception e) {
			
		}
		
		return mu_score;
	}
	
	public static String get_requester(String uid) {
		String requester1 = "";
		
		try {
			Scanner fr1 = new Scanner(file1);
			String line;
			String[] data = new String[3];
			
			 while(fr1.hasNextLine()) {
				line = fr1.nextLine();
				data = line.split(",");
				if(data[0].contentEquals(uid)) {
					requester1 = line;
					break;
				}
			 }
		}
		catch(Exception e) {
			
		}

		
		return requester1;
	}

	public static String pick_a_request() {
		String request1 = "";
		
		Random rand = new Random();
		int n = rand.nextInt(100)+1;
		try {
		Scanner fr2 = new Scanner(file2);
		for(int i=1; i<n;i++)
			fr2.nextLine();
		
		request1 = fr2.nextLine();	
		
		}
		catch(Exception e) {
			
		}
		
		return request1;
	}

	public static void insert_into_users_runtime_historical_set(String requester, String request, double risk) {
		
		try {
			String[] details;
			details = requester.split(",");
			
		String f5 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/runtime_simulation/"+details[0]+"_runtime_historical_set.txt";
		File file5 = new File(f5);
		
		if(!file5.exists()) {
			file5.createNewFile();
		}
		
		FileWriter fw5 = new FileWriter(file5,true);
		
		
		
				
		String output = details[0]+","+details[1]+","+details[2]+","+request+","+risk;
		fw5.append(output+"\n");
		fw5.flush();
				
		}
		catch(Exception e) {
			
		}
	}
	
	public static void insert_into_runtime_collaboration_set(String requester, String request, double risk,double normalized_object_sensitivity, double current_mu ) {
		try {
			
			String f4 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/runtime_simulation/runtime_collaborations.txt";
			File file4 = new File(f4);
			
			if(!file4.exists()) {
				file4.createNewFile();
			}
			String[] details = requester.split(",");
			
			FileWriter fw4 = new FileWriter(file4,true);
			
			//object_sensitivity;
			//String output = details[0]+","+details[1]+","+details[2]+","+request+","+risk+","+current_mu+","+ reputation_score;
			String output = details[0]+","+details[1]+","+details[2]+","+request+","+risk+","+normalized_object_sensitivity+","+current_mu;
						
			fw4.append(output+"\n");
			
			fw4.flush();
					
			}
			catch(Exception e) {
				
			}
	}

	public static double recalculate_mu_score(String uid) {
		double prev_mu_score = 0;
		double mu_score_runtime_simulation = 0;
		double current_mu_avg = 0;
		
		try {
		String ff = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/User_specific_request_set/updated_requester_file.txt";
		File file = new File(ff);
		Scanner fr = new Scanner(file);
		String[] details = new String[5];
		
		while(fr.hasNextLine()) {
			String line = fr.nextLine();
			details = line.split(",");
			if(uid.contentEquals(details[0])) {
				prev_mu_score = Double.parseDouble(details[3]);
				break;
			}
		}
		
		mu_score_runtime_simulation = compute_runtime_unalik_score(uid);
		
		current_mu_avg = ((10*prev_mu_score)+mu_score_runtime_simulation)/(10+1);
		}
		catch(Exception e) {
			
		}
		return current_mu_avg;
	}
	/*public static double get_init_unalik_score(String uid) {
		
		double init_unalik_score = 0;
		
		try {
		String[] u_id = new String[2];
		u_id = uid.split("_");
		
		String f = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/"+u_id[0]+"_requester.txt";
		File file = new File(f);
		Scanner fr = new Scanner(file);
		
		String[] details = new String[3];
		
		while(fr.hasNextLine()) {
			String line = fr.nextLine();
			details = line.split("\\s");
			if(details[0].contentEquals(uid)) {
				init_unalik_score = Double.parseDouble(details[2]);
				break;
			}
		}
		}
		catch(Exception e) {
			
		}
		return init_unalik_score;
	}
	*/
	public static double compute_runtime_unalik_score(String uid) {
		
		double runtime_mu = 0;
		
		int top_secret = 0;
		int secret = 0;
		int confidential = 0;
		int unclassified = 0;
		
		int view = 0;
		int edit =0;
		int execute =0;
		
		int total = 0;
		
		
		try {
		String ff = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/runtime_simulation/"+uid+"_runtime_historical_set.txt";
		File file = new File(ff);
		
		Scanner fr = new Scanner(file);
		
		String line = "";
		String[] details = new String[9];
		
		while(fr.hasNextLine()) {
			total++;
			line = fr.nextLine();
			details = line.split(",");
			if(Integer.parseInt(details[5])==1) {
				top_secret++;
			}
			else if(Integer.parseInt(details[5])==2) {
				secret++;				
			}
			else if(Integer.parseInt(details[5])==3) {
				confidential++;
			}
			else if(Integer.parseInt(details[5])==4) {
				unclassified++;
			}
			
			if(Integer.parseInt(details[6])==1) {
				view++;
			}
			else if(Integer.parseInt(details[6])==2) {
				edit++;
			}
			else if(Integer.parseInt(details[6])==3) {
				execute++;
			}
		}
		
		double pv = (double)view/total;
		double pe = (double)edit/total;
		double pex = (double)execute/total;
		
		double ptop_secret = (double)top_secret/total;
		double psecret = (double)secret/total;
		double pconfidential = (double)confidential/total;
		double punclassified = (double)unclassified/total;
		
		double mu_obj = 1 - Math.pow(ptop_secret,2) - Math.pow(psecret,2) - Math.pow(pconfidential, 2) - Math.pow(punclassified, 2);
		double mu_access = 1 - Math.pow(pv,2) - Math.pow(pe, 2) - Math.pow(pex, 2);
    	double sum = (mu_obj + mu_access);
    	
    	runtime_mu = Math.pow(sum, (1-sum));
	
		}
		catch(Exception e) {
			
		}
		
		return runtime_mu;
	}
	
	public static void create_runtime_users_details() {
		
		try {
		
		FileWriter fwfru = new FileWriter(file_runtime);
		String new_data = "";
		
		String old_data;
		String[] old_details;
		Scanner fr1 = new Scanner(file1);
		
		while(fr1.hasNextLine()) {
			old_data = fr1.nextLine();
			old_details = old_data.split(",");
			
			new_data = new_data+old_data+","+old_details[3]+System.lineSeparator();
		}
		
		fwfru.write(new_data);
		fwfru.flush();
	
		}
		catch(Exception e) {
			
		}
	
	}

	public static void update_runtime_users_details(String uid, double current_mu_score) {
		try {
			Scanner fr = new Scanner(file_runtime);
			
			
			String prev_data_content = "";
			String line = "";
			String[] line_details = new String[6];
			
			String line_to_be_replaced = "";
			String line_replaced_with = "";
			
			while(fr.hasNextLine()) {
				line = fr.nextLine();
				line_details = line.split(",");
				
				prev_data_content = prev_data_content+line+"\n";
				if(line_details[0].contentEquals(uid)) {
					line_to_be_replaced = line;
					line_replaced_with = line_details[0]+","+line_details[1]+","+line_details[2]+","+line_details[3]+","+line_details[4]+","+current_mu_score;
				}
			}
			
			String new_data_content = prev_data_content.replaceAll(line_to_be_replaced, line_replaced_with);
			
			
			if (file_runtime.exists() && file_runtime.isFile()) 
			  { 
			  file_runtime.delete(); 
			  } 
			file_runtime.createNewFile(); 

			FileWriter fwfr = new FileWriter(file_runtime);
			
			fwfr.write(new_data_content);
			fwfr.flush();			
		}
		catch(Exception e) {
			
		}
	}

	


}
