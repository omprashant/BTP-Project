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
public class CloudletCreatorsimple {
		
		//Triangle Sequence generator 
		
		public ArrayList<Integer> getTriangleSequence(int seed){
			
			ArrayList<Integer> result = new ArrayList<>();
			
			int j=1;
			
			while(j<=seed){
	        	    			
				if(j<=seed/2){
	        	    
					result.add(j);
					
					j++;
	        	    
				}
	        	
				else{
	        	
					result.add((seed-j)+1);
					
					j++;
				
				}
	        	
			}
			
			//System.out.println("Result:" + result.toString());
			
			return result;
			
		}
		
	//cloudlet creator
	public List<Cloudlet> createUserCloudlet(int reqTasks,int brokerId){
		
    	UtilizationModel utilizationModel = new UtilizationModelFull();
    
    	Cloudlet task = null; // declaration outside switch statement
    	
    	ArrayList<Cloudlet> cloudletList = new ArrayList<Cloudlet>();
    		
        	//Cloudlet properties
        	int id = 0, number;
        	int pesNumber=2;
        	long length = 1000;
        	long fileSize = 300;
        	long outputSize = 300;
        	
        	System.out.println("  Press [1 : Square] & [2 : Uniform] & [3 : Triangle] ");
        	
		    	@SuppressWarnings("resource")
				Scanner in = new Scanner(System.in);
		    	int value = in.nextInt();    	
		    	
		    	if(value == 1)
		    		System.out.println("Profile : Simple Square Cloudlets :--");
		    	else if(value == 2)
		    		System.out.println("Profile : Simple Uniform Cloutlets :--");
		    	else if(value == 3)
		    		System.out.println("Profile : Simple Triangle type Cloudlets :--");
		    	else
		    		System.out.println("");
		
		    	ArrayList<Integer> triangleSequence = this.getTriangleSequence(reqTasks); 
		    	
    	//################################################################################################################
    	
	    	for(id=0;id<reqTasks;id++){
	    		
	    		//-------------------------------------------------------------------------
	    			//   JOB / CLOUDLET PROFILE SELECTION 
	    		//-------------------------------------------------------------------------
	    	
	            
	        	 switch(value){   
	        	
	        	 	case 1:
		    		// 1. Generating a square wave sequence of length of each Cloudelts.
	        	    //--------------------------------------------------------------------------
	        	 		if(id % 2 == 0){
	        	    		number = 4;
	        	    	}
	        	    	else
	        	    		number = 2;
	        	 			task = new Cloudlet(id, (length*(number)), pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
	        	    break;  
	        	    
	        	    case 2:  // 2. For Straight line format : Jobs with same burst time.
	        	    
	        				task = new Cloudlet(id, (length*(4)), pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
	        	    break;  
	        	    
	        	    case 3: //3. Jobs in Continuous format with different lengths and burst time
	        	    	
	        	    	task = new Cloudlet(id, (length*triangleSequence.get(id)), pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);	
	        	    break; 
	        	    
	        	    default:System.out.println("Please enter correct choice...");  
	        	 } 
	    		
	    		task.setUserId(brokerId);
	    		System.out.println("Length of inner Task / Cloudlets "+ id +" = "+(task.getCloudletLength()));
	    		//add the cloudlets to the list
	        	cloudletList.add(task);
	    	}
    	//########################################################################################################################
    	
    	System.out.println("------------------------------------------------");
    	System.out.println("SUCCESSFULLY Cloudletlist created :)");
    	System.out.println("------------------------------------------------");

		return cloudletList;
		
	}

}
