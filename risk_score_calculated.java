import java.lang.*;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;

public class risk_score_calculated {
	
	static String request, requester;
	static double unalik_score;
	static double reputation_score;
	static int SSL;
	static double[] parameter;
	
	static double coefficient_SSL;
	static double normalized_SSL;
	
	static int object_id;
	static String object_sensitivity;
	static double normalized_object_sensitivity;
	static double coefficient_object_sensitivity;
	
	static double delta;
	
	static double expected_threat;
	static double normalized_threat;
	
	static double risk_score_computed;
	
	public static void main(String args[]) {
		get_parameters_values();
		//uncertainty_measure(0.02632155);
		//System.out.println(coefficient_SSL);
		//SSL = 10;
		//calculate_normalized_SSL();
		//System.out.println(normalized_SSL);
		//request = "D4,D4_U16,4,1,0.8602";
		//calculate_expected_damage();
		//System.out.println(delta);
		//coefficient_object_sensitivity = calculate_coefficient_object_sensitivity();
		//System.out.println(delta);
		//System.out.println(coefficient_object_sensitivity);
		//object_id = 4;
		//normalized_object_sensitivity = calculate_normalized_object_sensitivity();
		//System.out.println(normalized_object_sensitivity);
		/*coefficient_object_sensitivity = 0.68441692;
		normalized_object_sensitivity = 0.55211533;
		coefficient_SSL = 0.999999;
		normalized_SSL = 1.0;
		expected_threat = calculate_weighted_linear_regression_function();
		System.out.println(expected_threat);
		*/
		/*risk_score_computed = compute_risk(1.377876073);
		System.out.println(risk_score_computed);
		*/
		double risk = call_risk_score_computation("D4_U2,10,1,0.8634694820602696,0.02632155835345995", "D4,D4_U16,4,1,0.8602",0.86346948,0.026321558888554586);
	}
	
	public static double call_risk_score_computation(String requester1, String request1, double mu_score1, double reputation_score1) {
		
		//System.out.println("checkpoint: call risk score computation");		
		//System.out.println("mu_score : "+mu_score1);
		request = request1;
		requester = requester1;
		unalik_score = mu_score1;
		reputation_score = reputation_score1;
		
		System.out.println(request);
		System.out.println(requester);
		System.out.println(unalik_score);
		System.out.println(reputation_score);
		
		double risk_score = 0;
		//System.out.println("risk score initilized to 0");
		
		//unalik_score = get_mu_score(requester);
		SSL = get_SSL(requester);
		System.out.println("SSL : "+SSL);
		
		calculate_normalized_SSL();
		
		get_parameters_values();
		
		//inverse_gompertz_function igf = new inverse_gompertz_function();
		//reputation_score = igf.call_inverse_gompertz_function(unalik_score);
		
		uncertainty_measure(reputation_score);
		System.out.println("Wl : "+coefficient_SSL);
		
		object_id = get_object_id(request);
		System.out.println("Object id : "+object_id);
		
		normalized_object_sensitivity = calculate_normalized_object_sensitivity();
		//normalized_object_sensitivity = 0.55639670;
		System.out.println("Normalized object sensiitivity : "+normalized_object_sensitivity);
		
		coefficient_object_sensitivity = calculate_coefficient_object_sensitivity();
		System.out.println("coefficient of object sensitivity : "+coefficient_object_sensitivity );
		
		expected_threat = calculate_weighted_linear_regression_function();
		System.out.println("expected threat : "+expected_threat);
		
		//normalized_threat = (expected_threat /2);
		//System.out.println("normalized threat : "+normalized_threat);
		
		
		risk_score = compute_risk(expected_threat);
		System.out.println("RISK SCORE : "+risk_score);
		
		//System.out.println(requester+","+request+","+risk_score+","+unalik_score+","+reputation_score+","+coefficient_SSL+","+coefficient_object_sensitivity+","+delta+","+expected_threat);
		
		
		return risk_score;
		
	}
		
	public static double get_mu_score(String requester) {
		String[] data = new String[5];
		
		data = requester.split(",");
		double mu_score = Double.parseDouble(data[3]);
		
		return mu_score;	
	}
	
	public static void get_parameters_values() {
		parameter = new double[7];
		try {
        	
        	File file = new File("/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/parameters.txt");
        	
        	Scanner fr = new Scanner(file);
        	String line;
        	int z= 0;
        	
        	while ((line = fr.nextLine()) != null) {
                String[] line_details=line.split("\\s");
                double R1 = Double.parseDouble(line_details[1]);
                
                parameter[z]=R1;
                z++;
                }
        }
        catch(Exception e) {
        	
        }
	}
	
	public static int get_SSL(String requester) {
		String[] data = new String[5];
		
		data = requester.split(",");
		int ssl = Integer.parseInt(data[1]);
		
		return ssl;
	}
	
	public static void uncertainty_measure(double reputation)
	 {

	 	if(reputation==0)
	 	{
	 		coefficient_SSL=1;
	 	}
	 	else
	 	{
	          
	          double lamda=parameter[3];
	          double r1=lamda/reputation;
	          r1=-1*r1;
	          double m1= Math.exp(r1);
	          coefficient_SSL =1-m1;

	 	}
	 }

	public static void calculate_normalized_SSL() {
		double min = 1;
		double max = 10;
		normalized_SSL = (double)(SSL - min)/(max - min);
		//return normalized_SSL;
	}
	
	public static int get_object_id(String request) {
		int object;
		String[] data = new String[5];
		data = request.split(",");
		
		object = Integer.parseInt(data[2]);
		
		return object;
	}
	
	public static double calculate_normalized_object_sensitivity() {
		double normalized_obj_sens;
		
		double L1=0,H1=0,R1=0;
		Random r1 = new Random();
		
		if(object_id == 1) {
			object_sensitivity = "top secret";
			L1=.901;
	    	H1=1;
	    	R1 = (r1.nextDouble()*(H1-L1)) + L1;
		}
		else if(object_id == 2) {
			object_sensitivity = "secret";
			L1=.751;
	    	H1=0.9;
	    	R1 = (r1.nextDouble()*(H1-L1)) + L1;
		}
		else if(object_id ==3) {
			object_sensitivity = "confidential";
			L1=0.601;
	    	H1=0.75;
	    	R1 = (r1.nextDouble()*(H1-L1)) + L1;
		}
		else if(object_id == 4) {
			object_sensitivity = "unclassified";
			
			L1 = 0.501;
			H1 = 0.6;
			R1 = (r1.nextDouble()*(H1-L1)) + L1;
			System.out.println("unclassified");
			
		}
		
		normalized_obj_sens = R1;
		
		return normalized_obj_sens;
	}

	public static double calculate_coefficient_object_sensitivity() {
		
		double delta = calculate_expected_damage();
		//System.out.println("=> DELTA : "+delta);
		
		double v = parameter[4];  	//risk_tolerance 
		//System.out.println("risk tollerance : "+v);
		
		double m1 = (delta/v);
		m1=-1*m1;
		double r1=Math.exp(m1);
		
		double coefficient_obj_sens = 1-r1;
		//System.out.println("co_eff object sensitivity = "+coefficient_obj_sens);
		
		return coefficient_obj_sens;
		
	}
	
	public static double calculate_expected_damage() {
		
		delta=0;
		double C=0,I=0,A=0;
		
		double Pv = 0.346;
		double Pe = 0.313;
		double Px = 0.341;
		
		String sensitivity = combine_object_type();
		//System.out.println("sensitivity : "+sensitivity);
		
		String access_id = decide_access_type();
		//System.out.println("access id : "+access_id);
		
		if(sensitivity.equals("sensitive")) {
			if(access_id.contentEquals("view")) {
				//System.out.println("Sens+view");
				 C=1;
			     I=0;
			     A=0;
			     delta= Pv*(C+I+A);
			}
			else if(access_id.contentEquals("edit")) {
				//System.out.println("Sens+edit");
				 C=0;
			     I=1;
			     A=1;
			     delta = Pe*(C+I+A);
			}
			else if(access_id.contentEquals("execute")) {
				//System.out.println("Sens+execute");
				C=0;
			    I=1;
			    A=1;
			    delta=Px*(C+I+A);
			}
		}
		else {		//non_sensitive
			if(access_id.contentEquals("view")) {
				//System.out.println("non_sens+view");
				 C=0;
			     I=0;
			     A=1;
			     delta=Pv*(C+I+A);
			}
			else if(access_id.contentEquals("edit")) {
				//System.out.println("non_sens+edit");
				 C=0;
			     I=1;
			     A=1;
			     delta=Pe*(C+I+A);
			}
			else if(access_id.contentEquals("execute")) {
				//System.out.println("non_sens+execute");
				 C=0;
			     I=1;
			     A=1;
			     delta=Px*(C+I+A);
			}
		}
		//System.out.println("DELTA INSIDE FUNCTION :"+delta);
		return delta;
	}
	
	public static String combine_object_type() {
		String object_mode = "";
		
		String[] split_req = request.split(",");
		
		
		if((split_req[2].equals("1"))||(split_req[2].equals("2"))||(split_req[2].equals("3"))) {
			object_mode = "sensitive";
		}
		else if(split_req[3].equals("4")) {
			object_mode = "non_sensitive";
		}
		
		return object_mode;
	}
	
	public static String decide_access_type() {
		String access_type = "";
		String[] split_req = request.split(",");
		
		if(split_req[3].equals("1")) {
			access_type = "view";
		}
		else if(split_req[3].equals("2")) {
			access_type = "edit";
		}
		else if(split_req[3].equals("3")) {
			access_type = "execute";
		}
		return access_type;
	}

	public static double calculate_weighted_linear_regression_function(){
		  
	 	double threat = (coefficient_object_sensitivity*normalized_object_sensitivity)+(coefficient_SSL*normalized_SSL);
	 	double expect_threat = Math.min(threat, 1);
	 	System.out.println("within calculate_weighted_linear_regression_function()");
	 	System.out.println("Ws : "+coefficient_object_sensitivity);
	 	System.out.println("normalozed_sensitivity : "+normalized_object_sensitivity);
	 	System.out.println("Wl :	"+coefficient_SSL);
	 	System.out.println("normalized_SSl : "+normalized_SSL);
	 	
	   System.out.println(expect_threat);
	   System.out.println("out from the function");
	 	return expect_threat;
	 }

	public static double compute_risk(double threat) {
		
		double risk=0;
		double L1=0,H1=0,R1=0;
		Random r1 = new Random();
	        if(threat>=(0.5))
	        {
	               //System.out.println("Randomly choose phi");
	               
	     	       R1 =parameter[5];
	     	       double phi=R1;
	     	       risk =Math.pow(threat,phi);

	        }
	        else if(threat<0.5)
	        {
	        	//System.out.println("Choose gamma gretaer than 1");
	        	double gam=parameter[6];
	        	risk =Math.pow(threat,gam);
	        	

	        }
	return risk ;
}

}





