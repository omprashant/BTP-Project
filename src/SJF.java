/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;


/**
 * SJF Task scheduling
 */
public class SJF {
	private static String policy = "SJF";
	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;

	/** The vmlist. */
	private static List<Vm> vmlist;

	private static int reqTasks = CloudletCreator.reqTasks;
	private static int reqVms = CloudletCreator.reqVms; 
	/**
	 * Creates main() to run this example
	 */
	public static void main(String[] args) {
		Log.printLine("===========================================");
		Log.printLine("Creating a Task scheduling policy (SJF)  : CREATION DATE: 01/02/2017");
		Log.printLine("===========================================");
		Log.printLine("Starting SJF...");

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
	            	SJFBroker broker = createBroker();
	            	int brokerId = broker.getId();

	            	//Fourth step: Create one virtual machine
	            	vmlist = new VmsCreator().createRequiredVms(reqVms, brokerId);


	            	//submit vm list to the broker
	            	broker.submitVmList(vmlist);


	            	//Fifth step: Create two Cloudlets
	            	cloudletList = new CloudletCreator().createUserCloudlet(reqTasks, brokerId);
      	
	            	//submit cloudlet list to the broker
	            	broker.submitCloudletList(cloudletList);
	            	
    	
	            	//call the scheduling function via the broker
	            	broker.scheduleTaskstoVms();
   	
            	
	            	// Sixth step: Starts the simulation
	            	CloudSim.startSimulation();


	            	// Final step: Print results when simulation is over
	            	List<Cloudlet> newList = broker.getCloudletReceivedList();

	            	CloudSim.stopSimulation();

	            	printCloudletList(newList);

	            	Log.printLine("SJF finished!");
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	            Log.printLine("The simulation has been terminated due to an unexpected error");
	        }
	    }

		private static Datacenter createDatacenter(String name){
			Datacenter datacenter=new DataCenterCreator().createUserDatacenter(name, reqVms);			

	        return datacenter;

	    }

	    //We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
	    //to the specific rules of the simulated scenario
	  /*  private static SJFBroker createBroker(){

	    	SJFBroker broker = null;
	        try {
			broker = new SJFBroker("Broker");
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
	    	return broker;
	    }
	    */
	    private static SJFBroker createBroker(){

	    	SJFBroker broker = null;
	        try {
			broker = new SJFBroker("Broker");
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
	    private static void printCloudletList(List<Cloudlet> list) {
	        int size = list.size(), pes=0;
	        float average_time = 0, average_waiting_time = 0;
	        float waiting_time[] = new float[size];	        
	        waiting_time[0] = 0;
	        Cloudlet cloudlet;
        	float burst_time[] = new float[size];

	        String indent = "    ";
	        Log.printLine();
	        Log.printLine("========== OUTPUT ============================================");
	        Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
	                "Datacenter ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time" + indent + "Waiting Time");

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
            	average_time = average_time + x;
            	
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

	            	Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + indent + cloudlet.getVmId() +
	                     indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+
                             indent + indent + dft.format(cloudlet.getFinishTime()) + indent + indent + indent + waiting_time[i]);
	    	        Log.printLine("====================================================================================================");

	   	            }
	        }
            /*
             *  Even one cloudlet fails then all will fail.
             */
	        //if (cloudlet_status == Cloudlet.SUCCESS){
            
	        Log.printLine("");
	        Log.printLine("========== Analysis ============================================");
	        Log.printLine("Policy" + indent + "STATUS" + indent +
	                "Avg. Exec. Time" + indent + "Avg. Wait. Time" + indent + "VMs" + indent + "Tasks" + indent + "Cores");
	        Log.printLine("----------------------------------------------------------------------------------------------------");
	        Log.printLine( policy + indent + "SUCCESS" +
                    indent + indent + average_time/size + indent + indent + indent + indent + average_waiting_time/size +
                        indent + indent + indent + reqVms + indent + indent + size + indent + pes);
   	        Log.printLine("====================================================================================================");
	        }
	    }
//}
