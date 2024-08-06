
public class TV extends Product{

	private String scrType;
	private String resolution;
	private double displaySize;
	
	// parameterized constructor
	public TV(String pName,String scrType,String resolution,double displaySize,int availableQty,double price,int itemNo) {
		super(itemNo, pName, availableQty, price);
		this.scrType=scrType;
		this.resolution=resolution;
		this.displaySize=displaySize;
	}
	
	//Getter and Setter
	public String getScrType() {
		return scrType;
	}

	public void setScrType(String scrType) {
		this.scrType = scrType;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public double getDisplaySize() {
		return displaySize;
	}

	public void setDisplaySize(double displaySize) {
		this.displaySize = displaySize;
	}

	
	//Calculate the value of the stock of TV
	public double calculateTVStockValue() {
		return getProductPrice()*getQuantityAvailable();
	}
	
	
	//Override the toString()method to return the information of the TV obj
	@Override
	public String toString() {
		return super.toString()+ "\nScreen type : " + scrType + "\nResolution : " + resolution + "\nDisplay size : " + displaySize;
    }
		
	
	
}
