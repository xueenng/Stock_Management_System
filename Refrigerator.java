public class Refrigerator extends Product {

	private String doorDesign;
	private String color;
	private int capacity;
	
	//parameterized constructor
	
	public Refrigerator(String name, String doorDesign, String color,  int capacity, int quantityAvailable,double productPrice, 
		int itemNumber	) {
		super(itemNumber, name, quantityAvailable, productPrice);
		this.doorDesign = doorDesign;
		this.color = color;
		this.capacity = capacity;
	}
	
	//getters and setters
	
	public String getDoorDesign() {
		return doorDesign;
	}
	
	public void setDoorDesign(String doorDesign) {
		this.doorDesign = doorDesign;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	// method to calculate the value of stock of a refrigerator
	
	public double calculateRefrigeratorStockValue() {
		return getProductPrice() * getQuantityAvailable();
	}
	
	// method to override toString()
	
	@Override
	public String toString() {
		return super.toString() + "\n" +
	"Door Design : " + doorDesign + "\n"+
				"Color: " + color + "\n" +
	"Capacity (in litres) : " + capacity;
	}
	
}


