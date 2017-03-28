/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;


/**
 * Fcfs Task scheduling
 */
public class FCFS {
	
	private static String policy = "Fcfs";
	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;

	/** The vmlist. */
	private static List<Vm> vmlist;
	
	
	private static int reqVms = 5; 
	
	//Delimiter used in CSV file
		private static final String COMMA_DELIMITER = ",";
		private static final String NEW_LINE_SEPARATOR = "\n";
		//CSV file header
		private static final String FILE_HEADER = "POLICY,Avg. TAT,Avg. WT,CORES, VMs, TASKS";

		private static OutputCapsule outputCapsule = null;
	/**
	 * Creates main() to run this example
	 */
	public static void main(String[] args) {
		
		List<String> filePath = new ArrayList<String>();


		File[] files = new File("/home/iiitv/input/").listFiles();
		{
		//If this pathname does not denote a directory, then listFiles() returns null. 
			
		for (File file : files) {
		    if (file.isFile()) {
		        filePath.add(file.getAbsolutePath());
		    }
		}
		
		CloudletCreator cc=null;
		
		int count = 1;
		
		ArrayList<OutputCapsule> oc = new ArrayList<>();
		
		for (String filepath : filePath) {
			
			cc = new CloudletCreator(filepath,6, 2);
			
			/*Log.printLine("===========================================");
			Log.printLine("Creating a Task scheduling policy (Fcfs)  : CREATION DATE: 01/02/2017");
			Log.printLine("===========================================");
			Log.printLine("Starting Fcfs...");
*/
		        try {
		        	// First step: Initialize the CloudSim package. It should be called
		            	// before creating any entities.
		            	int num_user = 1;   // number of cloud users
		            	Calendar calendar = Calendar.getInstance();
		            	boolean trace_flag = false;  // mean trace events

		            	// Initialize the CloudSim library
		            	CloudSim.init(num_user, calendar, trace_flag);

		            	// Second step: Create Datacenters
		            	//Datacenters are the resource providers in CloudSim. We need at list one of them to run a CloudSim simulation
		            	@SuppressWarnings("unused")
						Datacenter datacenter0 = createDatacenter("Datacenter_0");

		            	//Third step: Create Broker
		            	FcfsBroker broker = createBroker();
		            	int brokerId = broker.getId();

		            	//Fourth step: Create one virtual machine
		            	vmlist = new VmsCreator().createRequiredVms(reqVms, brokerId);


		            	//submit vm list to the broker
		            	broker.submitVmList(vmlist);


		            	//Fifth step: Create Cloudlets
		            	
		            	cloudletList = cc.createUserCloudlet(brokerId);
	      	
		            	//submit cloudlet list to the broker
		            	broker.submitCloudletList(cloudletList);
		            	
	    	
		            	//call the scheduling function via the broker
		            	broker.scheduleTaskstoVms(cc);
	   	
	            	
		            	// Sixth step: Starts the simulation
		            	CloudSim.startSimulation();


		            	// Final step: Print results when simulation is over
		            	List<Cloudlet> newList = broker.getCloudletReceivedList();

		            	CloudSim.stopSimulation();

		            	oc.add(printCloudletList(newList, cc.getFilePath(), cc.getReqTasks()));

		            	Log.printLine("Fcfs finished! | Iteration:" + (count++));
		        }
		        catch (Exception e) {
		            e.printStackTrace();
		            //Log.printLine("The simulation has been terminated due to an unexpected error");
		        }
			
		}
		
		  FileWriter fileWriter = null;
	   	     
	 		try {
	 			
	 			fileWriter  = new FileWriter("/home/iiitv/input/output.csv");

	 			//Write the CSV file header
	 			fileWriter.append(FILE_HEADER.toString());
	 		
	 			for (OutputCapsule outputCapsule : oc) {
	 				
	 				//Add a new line separator after the header
		 			fileWriter.append(NEW_LINE_SEPARATOR);
		 			
		 			//Write a new student object list to the CSV file
		 				fileWriter.append(outputCapsule.getPolicy());
		 				fileWriter.append(COMMA_DELIMITER);
		 				fileWriter.append(Float.toString(outputCapsule.getAvg_exe_time()));
		 				fileWriter.append(COMMA_DELIMITER);
		 				fileWriter.append(Float.toString(outputCapsule.getAvg_wait_time()));
		 				fileWriter.append(COMMA_DELIMITER);
		 				fileWriter.append(Float.toString(2));
		 				fileWriter.append(COMMA_DELIMITER);
		 				fileWriter.append(Float.toString(outputCapsule.getNum_vms()));
		 				fileWriter.append(COMMA_DELIMITER);
		 				fileWriter.append(Float.toString(outputCapsule.getJobs()));
		 				
	 			}
	 			
	 			System.out.println("CSV file was created successfully !!!");
	 			
	 		} catch (Exception e) {
	 			System.out.println("Error in CsvFileWriter !!!");
	 			e.printStackTrace();
	 		} finally {
	 			
	 			try {
	 				fileWriter.flush();
	 				fileWriter.close();
	 			} catch (IOException e) {
	 				System.out.println("Error while flushing/closing fileWriter !!!");
	                 e.printStackTrace();
	 			}
	 			
	 			
	 		}
		
		}
		
	    }

		private static Datacenter createDatacenter(String name){
			Datacenter datacenter=new DataCenterCreator().createUserDatacenter(name, reqVms);			

	        return datacenter;

	    }

	    //We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
	    //to the specific rules of the simulated scenario
	  /*  private static FcfsBroker createBroker(){

	    	FcfsBroker broker = null;
	        try {
			broker = new FcfsBroker("Broker");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	    	return broker;
	    }
	    */
	    private static FcfsBroker createBroker(){

	    	FcfsBroker broker = null;
	        try {
			broker = new FcfsBroker("Broker");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	    	return broker;
	    }

	    /**
	     * Prints the Cloudlet objects
	     * @param list  list of Cloudlets
	     */
	    private static OutputCapsule printCloudletList(List<Cloudlet> list, String filePath, int reqTasks) {
	        int pes=0;    //  number of processing elements.
	    	int size = list.size();
	        float average_Exe_time = 0, average_waiting_time = 0;
	        float waiting_time[] = new float[size];	        
	        waiting_time[0] = 0;
	        Cloudlet cloudlet;
        	float burst_time[] = new float[size];

	        String indent = "    ";
	        //Log.printLine();
	        //Log.printLine("========== OUTPUT ============================================");
	        //Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
	               // "Datacenter ID" + indent + "VM ID" + indent + "Burst Time" + indent + "Start Time" + indent + "Finish Time" + indent + "Waiting Time");

	        DecimalFormat dft = new DecimalFormat("###.##");
	        for (int i = 0; i < size; i++) {
	            cloudlet = list.get(i);
	            pes = list.get(i).getNumberOfPes();
	            /*
            	 * Average Execution Time of all the Cloudlets/Jobs
            	 */
            	String t = dft.format(cloudlet.getActualCPUTime());
            	float x = (float) Double.parseDouble(t);
            	burst_time[i] = x;               // Burst time = Execution time 
            	average_Exe_time = average_Exe_time + x;
            	
            	/*
            	 * Waiting Time of all cloudlets/jobs 
            	 */
            	
            	if(i!=0){
            	waiting_time[i] = waiting_time[i-1] + burst_time[i-1]; 
            	}
            	
            	/*
            	 * Average waiting time 
            	 */
            	average_waiting_time = average_waiting_time + waiting_time[i];
            	
            	Log.print(indent + cloudlet.getCloudletId() + indent + indent);
	            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
	                Log.print("SUCCESS");

	            	/*Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + indent + cloudlet.getVmId() +
	                     indent + indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+
                             indent + indent + dft.format(cloudlet.getFinishTime()) + indent + indent + indent + waiting_time[i]);
	    	        Log.printLine("====================================================================================================");
*/
	   	            }
	        }
            /*
             *  Even one cloudlet fails then all will fail.
             */
	        //if (cloudlet_status == Cloudlet.SUCCESS){
            
	   //     Log.printLine("");
	/*        Log.printLine("========== Analysis ============================================");
	        Log.printLine("Policy" + indent + "STATUS" + indent +
	                "Avg. Exec. Time" + indent + "Avg. Wait. Time" + indent + "VMs" + indent + "Tasks" + indent + "Cores");
	        Log.printLine("----------------------------------------------------------------------------------------------------");
	        Log.printLine( policy + indent + "SUCCESS" +
                    indent + indent + average_Exe_time/size + indent + indent + indent + indent + average_waiting_time/size +
                        indent + indent + indent + reqVms + indent + indent + size + indent + indent + pes +" (Uniform)");
   	        Log.printLine("====================================================================================================");
	    
   	 */
   	        outputCapsule = new OutputCapsule(policy, filePath  , average_Exe_time, average_waiting_time, reqVms, reqTasks);
	    	    
   	        return outputCapsule;
   	        
	    }
	   
}
