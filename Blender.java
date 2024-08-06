
public class Blender extends Product {

	private String containerMaterial;
	private String function;
	private int RPM;//rotations per minute
	
	
	// parameterized constructor
	public Blender(int itemNo,String pName,String containerMaterial, int RPM,String function,int availableQty,double price) {
		super(itemNo, pName, availableQty, price);
		this.containerMaterial=containerMaterial;
		this.RPM=RPM;
		this.function=function;
	}
	
	
	//Getter and setter
	public String getContainerMaterial() {
		return containerMaterial;
	}


	public void setContainerMaterial(String containerMaterial) {
		this.containerMaterial = containerMaterial;
	}


	public int getRPM() {
		return RPM;
	}


	public void setRPM(int RPM) {
		this.RPM = RPM;
	}


	public String getFunction() {
		return function;
	}


	public String setFunction(String function) {
		return this.function = function;
	}
	
	//Calculate the value of the stock of blender
	public double calculateBlenderStockValue() {
		return getProductPrice()*getQuantityAvailable();
	}
	
	//Override the toString() method to return the information of the Blender obj
	@Override
	public String toString() {
		return super.toString()+ "\nContainer Material : " + containerMaterial + "\nFunction : " + function +"\nRotations per minute : " + RPM  ;
    }
	
	
	
}
