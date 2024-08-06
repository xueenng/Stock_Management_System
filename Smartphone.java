
public class Smartphone extends Product {
	private String displaytyp;
	private String chipset;
	private int maincamera;
	
	public Smartphone(int itemNumber, String productName, int quantityInStock, double price,String dis, String chip, int camera) {
		super(itemNumber, productName, quantityInStock, price);
		this.displaytyp=dis;
		this.chipset=chip;
		this.maincamera=camera;
	}
	
	public String getDisplaytyp() {
		return displaytyp;
	}
	public void setDisplaytyp(String displaytyp) {
		this.displaytyp = displaytyp;
	}
	public String getChipset() {
		return chipset;
	}
	public void setChipset(String chipset) {
		this.chipset = chipset;
	}
	public int getMaincamera() {
		return maincamera;
	}
	public void setMaincamera(int maincamera) {
		this.maincamera = maincamera;
	}
	
	// Method to calculate the inventory value of Smart Phone products
    public double getTotalStockValue() {
        return getProductPrice() * getQuantityAvailable();
    }
	
	@Override
	public String toString() {
		
		return super.toString()+
                "\nDisplay Type: "+getDisplaytyp() +
                "\n Chipset: "+getChipset() +
                "\nMain Camera: "+getMaincamera();
	}

}
