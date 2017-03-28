
public class OutputCapsule {
	
	private String policy;
	private String fileName;
	private float avg_exe_time;
	private float avg_wait_time;
	private int num_vms;
	private int jobs;
	public String getPolicy() {
		return policy;
	}
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public float getAvg_exe_time() {
		return avg_exe_time;
	}
	public void setAvg_exe_time(float avg_exe_time) {
		this.avg_exe_time = avg_exe_time;
	}
	public float getAvg_wait_time() {
		return avg_wait_time;
	}
	public void setAvg_wait_time(float avg_wait_time) {
		this.avg_wait_time = avg_wait_time;
	}
	public int getNum_vms() {
		return num_vms;
	}
	public void setNum_vms(int num_vms) {
		this.num_vms = num_vms;
	}
	public int getJobs() {
		return jobs;
	}
	public void setJobs(int jobs) {
		this.jobs = jobs;
	}
	public OutputCapsule(String policy, String fileName, float avg_exe_time, float avg_wait_time, int num_vms,
			int jobs) {
		super();
		this.policy = policy;
		this.fileName = fileName;
		this.avg_exe_time = avg_exe_time;
		this.avg_wait_time = avg_wait_time;
		this.num_vms = num_vms;
		this.jobs = jobs;
	}
	

}
