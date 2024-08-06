public abstract class Product {
    private int itemNumber;
    private String productName;
    private int quantityAvailable;
    private double productPrice;
    private boolean productStatus;
   
    
    
    

    // Default constructor
    public Product() {
        // Default values
        this.productStatus = true;
    }

    // Parameterized constructor
    public Product(int itemNumber, String productName, int quantityAvailable, double productPrice) {
        this.itemNumber = itemNumber;
        this.productName = productName;
        this.quantityAvailable = quantityAvailable;
        this.productPrice = productPrice;
        this.productStatus = true;
   
    }
    
 
    
    // Getter and setter methods
    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    // Get total inventory value
    public double getTotalInventoryValue() {
        return productPrice * quantityAvailable;
    }

    // Method to add stock
    public boolean addStock(int quantity) {
        if (!productStatus) {
            System.out.println("Cannot add stock to a discontinued product line.");
            return false;
        }
        
        if (quantity < 0) {
            System.out.println("Invalid quantity. Quantity must be a positive number.");
            return false;
        }
        
        quantityAvailable += quantity;
        return true;
    }

    
   
    
    // Method to deduct stock
    public void deductStock(int quantity) {
        if (quantity >= 0 && quantity <= quantityAvailable) {
            quantityAvailable -= quantity;
            /*System.out.println("Stock deducted successfully.");*/
        } else {
            /*System.out.println("Invalid quantity to deduct.");*/
        }
    }

    // Override toString method
    @Override
    public String toString() {
        return "Item number: " + itemNumber +
                "\nProduct name: " + productName +
                "\nQuantity available: " + quantityAvailable +
                "\nPrice (RM): " + productPrice +
                "\nInventory value (RM): " + getTotalInventoryValue() +
                "\nProduct status: " + (productStatus ? "Active" : "Discontinued");
    }

    
}
