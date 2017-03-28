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
 * Round Robin Task scheduling
 */
public class RR {
	private static String policy = "RR";
	/** The cloudlet list. */
	private static List<Cloudlet> CloudletList;

	/** The vmlist. */
	private static List<Vm> vmlist;
	
	private static int reqTasks = CloudletCreator.reqTasks;
	private static int reqVms = CloudletCreator.reqVms; 
    private static float timeSlice = (float) 8;

	/**
	 * Creates main() to run this example
	 */
	public static void main(String[] args) {
		Log.printLine("===========================================");
		Log.printLine("Creating a Task scheduling policy (RR)  : CREATION DATE: 01/02/2017");
		Log.printLine("===========================================");
		Log.printLine("Starting ROUND ROBIN...");
System.out.println("=============================="+reqTasks);
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
	            	RRBroker broker = createBroker();
	            	int brokerId = broker.getId();

	            	//Fourth step: Create one virtual machine
	            	vmlist = new VmsCreator().createRequiredVms(reqVms, brokerId);


	            	//submit vm list to the broker
	            	broker.submitVmList(vmlist);


	            	//Fifth step: Create Cloudlets
	            	CloudletList = new CloudletCreator().createUserCloudlet(reqTasks, brokerId);
      	
	            	//submit cloudlet list to the broker
	            	broker.submitCloudletList(CloudletList);
	            	
    	
	            	//call the scheduling function via the broker
	            	broker.scheduleTaskstoVms();
   	
            	
	            	// Sixth step: Starts the simulation
	            	CloudSim.startSimulation();


	            	// Final step: Print results when simulation is over
	            	List<Cloudlet> newList = broker.getCloudletReceivedList();

	            	CloudSim.stopSimulation();

	            	printCloudletList(newList);

	            	Log.printLine("RR finished!");
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
	    private static RRBroker createBroker(){

	    	RRBroker broker = null;
	        try {
			broker = new RRBroker("Broker");
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
	        int size = list.size();      
	        Cloudlet cloudlet;
	        
	        int i,j,k, pes=0;
	        float sum=0;
	        float burstTime[]=new float[size];
	        float waitTime[]=new float[size];
	        float turnAroundTime[]=new float[size];
	        float a[]=new float[size];
	        
	        DecimalFormat dft = new DecimalFormat("###.##");

	        for (i = 0; i < size; i++) {
	            cloudlet = list.get(i);
	            /*
            	 * Average Execution Time of all the Cloudlets/Jobs
            	 */
            	String t = dft.format(cloudlet.getActualCPUTime());
            	float x = (float) Double.parseDouble(t);           
            	burstTime[i] = x;               // Burst time = Execution time 
            }
	        
	        for(i=0;i<size;i++)
	        	a[i]=burstTime[i];
	        
	        for(i=0;i<size;i++)
	        	waitTime[i]=0;
	        
	        do
	        {
	        	for(i=0;i<size;i++)
	        	{
	        		if(burstTime[i]>timeSlice)
	        		{
	        			burstTime[i]-=timeSlice;
	        			for(j=0;j<size;j++)
	        			{
	        				if((j!=i)&&(burstTime[j]!=0))
	        					waitTime[j]+=timeSlice;
	        			}
	        		}
	        		else
	        		{
	        			for(j=0;j<size;j++)
	        			{
	        				if((j!=i)&&(burstTime[j]!=0))
	        					waitTime[j]+=burstTime[i];
	        			}
	        			burstTime[i]=0;
	        		}
	        	}
	        	sum=0;
	        	for(k=0;k<size;k++)
	        		sum = sum + burstTime[k];
	        	}
	        while(sum!=0);
	        
	        for(i=0;i<size;i++)
	        	turnAroundTime[i]=waitTime[i]+a[i];
	        
	        System.out.println("Cloudlet\t\tBT\tWT\tTAT");
	        System.out.println("--------------------------------------------------");
	        for(i=0;i<size;i++)
	        {
	            cloudlet = list.get(i);
	            pes = list.get(i).getNumberOfPes();

	        System.out.println("Cloudlet"+cloudlet.getCloudletId()+"\t\t"+a[i]+"\t"+waitTime[i]+"\t"+turnAroundTime[i]);
	        }
	        
	        float avg_wt=0;
	        float avg_tat=0;
	        for(j=0;j<size;j++)
	        {
	        avg_wt+=waitTime[j];
	        }
	        for(j=0;j<size;j++)
	        {
	        avg_tat+=turnAroundTime[j];
	        }
	        System.out.println("average waiting time "+(avg_wt/size)+"\n Average turn around time"+(avg_tat/size));
	        
        	String indent = "    ";
	        Log.printLine();
	        Log.printLine("========== OUTPUT ============================================");
	        Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
	                "Datacenter ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time" + indent + "Waiting Time");

	        for (i = 0; i < size; i++) {
	            cloudlet = list.get(i);
	            /*
            	 * Average Execution Time of all the Cloudlets/Jobs
            	 */
  
            	/*
            	 * Waiting Time of all cloudlets/jobs 
            	 */
            	
            	/*
            	 * Average waiting time 
            	 */
            	
            	Log.print(indent + cloudlet.getCloudletId() + indent + indent);
	            if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
	                Log.print("SUCCESS");

	            	Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + indent + cloudlet.getVmId() +
	                     indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+
                             indent + indent + turnAroundTime[i] + indent + indent + indent + waitTime[i]);
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
                    indent + indent + (avg_tat/size) + indent + indent + indent + indent + (avg_wt/size) +
                        indent + indent + indent + reqVms + indent + indent + size + indent + pes);
   	        Log.printLine("====================================================================================================");
	        }
	    }
//}
