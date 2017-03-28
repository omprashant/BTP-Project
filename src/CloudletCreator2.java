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
public class CloudletCreator2 {
		
	public static int reqTasks;
	public static int reqVms = 6; 
		
	//cloudlet creator
	public List<Cloudlet> createUserCloudlet(int reqTasks,int brokerId){
		
    	UtilizationModel utilizationModel = new UtilizationModelFull();
    
    	Cloudlet task = null; // declaration outside switch statement
    	
    	ArrayList<Cloudlet> cloudletList = new ArrayList<Cloudlet>();
    		
        	//Cloudlet properties
        	int id = 0,l=0;
        	int pesNumber=2;
        	//long length = 1000;
        	long fileSize = 300;
        	long outputSize = 300;
        	
        	//------------------------------------------------Inputs--------------------
        	System.out.println("  Press [1 : Square] & [1 with DC=1 : Uniform] & [2 : Triangle]");
        	int difference = 0;
		    	@SuppressWarnings("resource")
				Scanner in = new Scanner(System.in);
		    	int value = in.nextInt(); 
		    	
		    	System.out.print("Duty Cycle : ");
		    	float DC = in.nextFloat();
		    	
		    	System.out.print("Peak point (length): ");
		    	int PP = in.nextInt();
		    	
		    	System.out.print("# of cycles : ");
		    	int cycles = in.nextInt();
		    	
		    	System.out.print("# of Cloudlets in 1st half (T1) : ");
		    	int T1 = in.nextInt();
		    	
		    	System.out.print("# of Cloudlets in 2nd half (T2) : ");
		    	int T2 = in.nextInt(); 

		    	reqTasks = (T1 + T2)*cycles;   // calculating total no of cloudlets
		    	System.out.println("-----------------------------------------------");
		    	if(value == 1)
		    		System.out.println("Profile : Simple Square Cloudlets :--");
		    	else if(value == 2){
		    		System.out.println("difference b/w Cloudelts size : ");
		    		difference = in.nextInt();
		    		System.out.println("Profile : Triangular type Cloudlets :--");
		    	}
		    	else
		    		System.out.println("");
		    	System.out.println("-----------------------------------------------");
	
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
	    		System.out.println("Length of inner Task / Cloudlets "+ l +" = "+(task.getCloudletLength()));
	    		//add the cloudlets to the list
	        	cloudletList.add(task);
	        	l++;
	    	}
    }
    	//########################################################################################################################
    	
    	System.out.println("------------------------------------------------");
    	System.out.println("SUCCESSFULLY Cloudletlist created :)");
    	System.out.println("------------------------------------------------");

		return cloudletList;
		
	}

}
