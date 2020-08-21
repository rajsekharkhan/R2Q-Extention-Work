import java.lang.*;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;


public class inverse_gompertz_function {

	static File file;
	static double[] parameter;
	
	static double reputation_score;
	public static void main(String[] args) {
		try {
			Random rand = new Random();
			int d = rand.nextInt(5)+1;
			int id = rand.nextInt(50)+1;
			String u_id = "D"+d+"_U"+id;
			double mu_score = 0;
			reputation_score = call_inverse_gompertz_function(0.86346948);
			System.out.println("reputation score : "+reputation_score);
		}
		catch(Exception e) {
			
		}
	}
	
	public static void get_parameters_values() {
		parameter = new double[7];
		try {
        	
        	file = new File("/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/parameters.txt");
        	if(!file.exists()) {
        		file.createNewFile();
        	}
        	
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
	public static double call_inverse_gompertz_function(double mu_score) {
		
		double reputation_score_calculated = 0;
		try {
			
		 get_parameters_values();
			
		 
		
		 double mu_score_in_percentage = convert_unalikability_score(mu_score);
		
		 double A= parameter[0];
		 double B= parameter[1];
		 double C= parameter[2];
		
		 double mu_1 = mu_score_in_percentage;
		   
		 double r1=C*mu_1;
		 r1=(-1)*(r1);
		    
		 double m1= B*Math.exp(r1);
		 m1=(-1)*m1;
		    
		 reputation_score_calculated = (1-(A* Math.exp(m1)));
	    
		}
		catch(Exception e) {
			
		}
		return reputation_score_calculated;
	}
	
	public static double convert_unalikability_score(double unalik_score) {
		double converted_unalik_score = unalik_score * 100;
		
		return converted_unalik_score;
	}

}