public class AirConditioner extends Product {
    private double heatingCapacity;
    private String compressorType;
    private double coolingCapacity; // Cooling capacity in BTU

    // Constructor
    public AirConditioner(int itemNumber, String productName, int quantityInStock, double price,
    					String compressorType, double heatingCapacity, double coolingCapacity) {
        super(itemNumber, productName, quantityInStock, price);
        this.heatingCapacity = heatingCapacity;
        this.compressorType = compressorType;
        this.coolingCapacity = coolingCapacity;
    }

    // Getters and setters
    public double heatingCapacity() {
        return heatingCapacity;
    }

    public void setheatingCapacity(double heatingCapacity) {
        this.heatingCapacity = heatingCapacity;
    }

    public String getcompressorType() {
        return compressorType;
    }

    public void setcompressorType(String compressorType) {
        this.compressorType = compressorType;
    }

    public double getCoolingCapacity() {
        return coolingCapacity;
    }

    public void setCoolingCapacity(double coolingCapacity) {
        this.coolingCapacity = coolingCapacity;
    }
    
    // Method to calculate the inventory value of AirConditioner products
    public double getTotalStockValue() {
        return getProductPrice() * getQuantityAvailable();
    }

    // Override toString method
    @Override
    public String toString() {
        return super.toString() + "\nCompressor Type: " + compressorType + "\nHeating Capacity (kW): " + heatingCapacity + "\nCooling Capacity (kW): " + coolingCapacity;
    }
}
