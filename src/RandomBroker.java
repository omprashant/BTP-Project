import org.cloudbus.cloudsim.DatacenterBroker;
import java.util.Random;
/**
 * A Broker that schedules Tasks to the VMs 
 * as per "Random Scheduling Policy"
 * 
 *
 */
public class RandomBroker extends DatacenterBroker {

	public RandomBroker(String name) throws Exception {
		super(name);
		// TODO Auto-generated constructor stub
	}

	//scheduling function
	public void scheduleTaskstoVms(){
		int reqTasks=cloudletList.size();
		int reqVms=vmList.size();
				
		System.out.println("\n\tRandom Broker Schedules...\n");
    	
		for(int i=0;i<reqTasks;i++){
    		Random rand = new Random();
    		Integer  n = rand.nextInt(reqVms) + 0;  // Creating a Random value from 0 to (no. of reqVms -1)
    		bindCloudletToVm(i, n);
    		System.out.println("Task"+cloudletList.get(i).getCloudletId()+" is bound with VM"+vmList.get(n).getId());
    	}
    	
    	System.out.println("\n");
	}
}
