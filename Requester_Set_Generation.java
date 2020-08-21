import java.lang.*;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.util.Random;


public class Requester_Set_Generation
{
	static FileOutputStream fos = null;
    static File file;
    
    public static void main(String args[]) {
    	int number_of_domain=5;
        int number_of_user=5;
        Random rand1 = new Random();
        
        try {
        	for(int i=1; i<= number_of_domain; i++) {
        		String h1 = "/C:/Users/HP/Desktop/MTECH 1ST SEM NOTES/PROJECT BY OWN/src/";
        		String h2 = "D"+i+"_requester.txt";
        		h1 = h1+h2;
        		
        		file=new File(h1);
            	fos = new FileOutputStream(file,true);
            	
            	for(int j=1;j<=number_of_user;j++)
            	{
            		int j1 = rand1.nextInt(10)+1; // security level of the user 
            		float j2= rand1.nextFloat(); // initial random unalikability score // to be normalized further for future
            		
            		String hh="D"+i+"_U"+j+" "+j1+" "+j2;
            		System.out.println(hh);

                 byte[] bytesArray = hh.getBytes();

                 fos.write(bytesArray);
                  String gg="\n";
                  byte[] bytesArray1 = gg.getBytes();

                   fos.write(bytesArray1);

                   fos.flush();
            	}
            }
        	}
        catch(Exception e)
        {

        }
        }
    }