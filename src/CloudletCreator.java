import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
/**
 * CloudletCreator Creates Cloudlets as per the User Requirements.
 *
 *
 */
public class CloudletCreator {
		
	public int reqTasks;
	public int reqVms; 
	private final String COMMA_DELIMITER = ",";	
	//public static String filePath = "/home/iiitv/input1.txt";
	public String filePath;
	
	public CloudletCreator(String path, int reqTasks, int reqVMs){
		
		this.filePath = path;
		this.reqTasks = reqTasks;
		this.reqVms = reqVMs;
		
	}
	
	

	public int getReqTasks() {
		return reqTasks;
	}



	public void setReqTasks(int reqTasks) {
		this.reqTasks = reqTasks;
	}



	public int getReqVms() {
		return reqVms;
	}



	public void setReqVms(int reqVms) {
		this.reqVms = reqVms;
	}



	public String getFilePath() {
		return filePath;
	}



	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}



	public String getCOMMA_DELIMITER() {
		return COMMA_DELIMITER;
	}



	//cloudlet creator
	public List<Cloudlet> createUserCloudlet(int brokerId) throws FileNotFoundException{
		
    	UtilizationModel utilizationModel = new UtilizationModelFull();
    
    	Cloudlet task = null; // declaration outside switch statement
    	
    	ArrayList<Cloudlet> cloudletList = new ArrayList<Cloudlet>();
    		
        	//Cloudlet properties
        	int id = 0,l=0;
        	//long length = 1000;
        	long fileSize = 300;
        	long outputSize = 300;
        	
        	//------------------------------------------------Inputs--------------------
        	Scanner scan = null;
            File f = new File(this.filePath);
            try {
                scan = new Scanner(f);
             } catch (FileNotFoundException e) {
                //System.out.println("File not found.");
                System.exit(0);
             }
            float[] tall = new float[8];
            int i = 0;
            while (scan.hasNextLine()) { //Note change
                String currentLine = scan.nextLine();
                //split into words
                String words[] = currentLine.split(" ");
                for(String str : words) {
                    try {
                       int num = Integer.parseInt(str);
                       tall[i++] = num;
                    }catch(NumberFormatException nfe) { }; //word is not an integer, do nothing
                 }
              } //end while 
            scan.close();
            //------------------------------------------------------------------------------
        	int pesNumber= (int) tall[7];
        	float DC = tall[0];
        	int PP = (int) tall[1];
        	int cycles = (int) tall[2];
        	int T1 = (int) tall[3];
        	int T2 = (int) tall[4];
        	int difference = (int) tall[5];
        	int value = (int) tall[6];
        	for(int n=0; n<tall.length; n++){
        		//System.out.println("=-=-=-"+ tall[n]);
        	}
        		reqTasks = (T1 + T2)*cycles;   // calculating total no of cloudlets
		    	//System.out.println("-----------------------------------------------");
		    	/*if(value == 1)
		    	//	System.out.println("Profile : Simple Square Cloudlets :--");
		    	else if(value == 2){
		    		System.out.println("Profile : Triangular type Cloudlets :--");
		    	}
		    	else
		    		System.out.println("");
		    	System.out.println("-----------------------------------------------");
	*/
    	//################################################################################################################
    for(int k=0; k<cycles; k++){
    	int count=0;
    	int baseT1 = (PP-((T1-1)*difference));
    	int baseT2 = (PP-(difference));
	    	for(id=0;id<(T1+T2);id++){
	    		
	    		//-------------------------------------------------------------------------
	    			//   JOB / CLOUDLET PROFILE SELECTION 
	    		//-------------------------------------------------------------------------
	    	
	            
	        	 switch(value){   
	        	
	        	 	case 1:
		    		// 1. Generating a square wave sequence of length of each Cloudelts.
	        	 			if(DC>=1 && count<T1){
		        				task = new Cloudlet(l, (PP), pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
	        	 			}
	        	 			else if(DC>=1 && count>=T1 && count<(T1+T2)){
		        				task = new Cloudlet(l, (long) (PP/DC), pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
	        	 			}
	        	 			else if(DC<1 && count<T1){
		        				task = new Cloudlet(l, (long) (PP*DC), pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
	        	 			}
	        	 			else if(DC<1 && count>=T1 && count<(T1+T2)){
		        				task = new Cloudlet(l, (PP), pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
	        	 			}
	        	 	count++;
	        	    break; 
	        	    
	        	    case 2:  // 2. For Triangular format :
	        	    	if(difference>=0 && count<T1){
	        				task = new Cloudlet(l, (baseT1), pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
	    	        	    baseT1 = baseT1 + difference;
	        	    	}
	        	    	else if(difference>=0 && count>=T1 && count<(T1+T2)){
	        				task = new Cloudlet(l, (long) (baseT2), pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
	        				baseT2 = baseT2 - difference; 
	        	    	}
	        	    count++;
	        	    break;
	        	    default:System.out.println("Please enter correct choice...");  
	        	 } 
	    		task.setUserId(brokerId);
	    		//System.out.println("Length of inner Task / Cloudlets "+ l +" = "+(task.getCloudletLength()));
	    		//add the cloudlets to the list
	        	cloudletList.add(task);
	        	l++;
	    	}
    }
    	//########################################################################################################################
    	
    	/*System.out.println("------------------------------------------------");
    	System.out.println("SUCCESSFULLY Cloudletlist created :)");
    	System.out.println("------------------------------------------------");
*/
		return cloudletList;
		
	}

}
