import java.lang.*;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.io.*;

public class Request_Set_Generation {
	
	static FileOutputStream fos = null;
    static File file;
    
    public static void main (String[] args) {
    	int size_of_predefined_hist_dataset = 100;
    	
    	Random rand1=new Random();
    	//for no. of domains = 5
        int l1=1;
        int h1=5;
        
        
    	//have to create and open files to write the result //tobe done
        
        //for no. of servers each domain = 50
        int l2 = 1;
        int h2 = 50;
        try {
        String s1 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/Request_set.txt";
        file = new File(s1); // s1 is the file name, where data stored.
        
        fos = new FileOutputStream(file,true);
        
    	for(int i=1; i<= size_of_predefined_hist_dataset; i++) {
            int r1=(rand1.nextInt(h1-l1+1))+l1;  // request identity
            
            int r2 =(rand1.nextInt(h2-l2+1))+l1; //request's domain
            
           // int r3 = (rand1.nextInt(10))+1; // security level of the requester assigned by remote domain administrators
            
            int r4 = (rand1.nextInt(4))+1; //object mode : '1' -> Top secret, '2'-> secret, '3'->confidential, '4'->unclassified
            
            int r5 = (rand1.nextInt(3))+1; // acess mode : '1'-> VIEW , '2'-> EDIT, '3'-> EXECUTE
            
            float risk = (rand1.nextFloat());
            
            String ss = "D"+r1+",D"+r1+"_U"+r2+","+r4+","+r5+","+risk;
            System.out.println(ss);
            
            byte[] bytesArray = ss.getBytes();

            fos.write(bytesArray);
            
            String gg="\n";
            byte[] bytesArray1 = gg.getBytes();

             fos.write(bytesArray1);

             fos.flush();
 
    	}
    }
        catch(Exception e) {
        	
        }
}
}
