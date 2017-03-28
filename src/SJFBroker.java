import org.cloudbus.cloudsim.DatacenterBroker;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

/**
 * A Broker that schedules Tasks to the VMs 
 * as per "Sortest Job First Scheduling Policy"
 * 
 *
 */
public class SJFBroker extends DatacenterBroker {

	public SJFBroker(String name) throws Exception {
		super(name);
		// TODO Auto-generated constructor stub
	}
    
	//scheduling function
	public void scheduleTaskstoVms(){
		int reqTasks=cloudletList.size();
		int reqVms=vmList.size();
		int Key[] = new int[reqTasks];
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Set<Entry<Integer, Integer>> set = null;
		List<Entry<Integer, Integer>> list = null;
		
		System.out.println("\n\tSJF Broker Schedules...\n");
		for(int i=0;i<reqTasks;i++){
		
			/*
			 * 
			 * Sorting all jobs/cloudlets in ascending order (based no length of cloudlets)
			 * 
			 */
			
			long j = cloudletList.get(i).getCloudletLength();
			
			map.put((int) j, i);
			
			set = map.entrySet();
	        
			list = new ArrayList<Entry<Integer, Integer>>(set);
	        
    	}
		
		Collections.sort( list, new Comparator<Map.Entry<Integer, Integer>>()
        {
            public int compare( Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2 )
            {
                return (o1.getKey()).compareTo( o2.getKey() );
            }
        } );
		
		int index=0;
		
		for (Entry<Integer, Integer> entry : list) {
			
			System.out.println("index:"+index+" | value:"+entry.getValue());
			Key[index++] = entry.getValue();
		}
		System.out.println("----------------------------------------------------------------");
		System.out.println("After sorting all cloudlets...");
		
		for(Map.Entry<Integer, Integer> entry:list){
            System.out.println(entry.getKey()+" ==== "+entry.getValue());
        }
		
		System.out.println("-----------------------------------------------------------------");
		
		for(int i=0;i<reqTasks;i++){
		bindCloudletToVm(Key[i], (i%reqVms));
		System.out.println("Task"+cloudletList.get(Key[i]).getCloudletId()+" is bound with VM"+vmList.get(i%reqVms).getId());
		}
		
    	System.out.println("\n");
	}
}
