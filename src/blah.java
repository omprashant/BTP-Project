import java.util.ArrayList;

import org.cloudbus.cloudsim.Vm;

public class blah {
	
	ArrayList<Vm> a;
	int vm_num;
	public blah(ArrayList<Vm> a, int vm_num) {
		super();
		this.a = a;
		this.vm_num = vm_num;
	}
	public ArrayList<Vm> getA() {
		return a;
	}
	public void setA(ArrayList<Vm> a) {
		this.a = a;
	}
	public int getVm_num() {
		return vm_num;
	}
	public void setVm_num(int vm_num) {
		this.vm_num = vm_num;
	}
	
	

}
