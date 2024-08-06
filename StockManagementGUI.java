import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StockManagementGUI extends Application {
    private ArrayList<Product> products = new ArrayList<>();
    

    private Scene mainScene; // Define mainScene as a class-level variable
    
    @Override
    public void start(Stage primaryStage) {
    	
    	//create a login pane
    	loginPane loginPane = new loginPane();
    	
    	//show the login pane and wait for it to close after the user has logged in to it
    	loginPane.showAndWait();
    	
    	 // Get the logged-in user's ID and name
        String userID = loginPane.getUserID();
        String userName = loginPane.getUserName();
    	
        // If the user provided user name, proceed
        if (userName != null) {
        	// Create UI components
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(20));
            gridPane.setHgap(20);
            gridPane.setVgap(20);
        
            //Display welcome message
            Label welcomeLabel = new Label("Welcome to the Stock Management System");
            welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
            gridPane.add(welcomeLabel, 0, 0, 3, 1);
       
            // Display current date and time
            Label dateTimeLabel = new Label("Current Date and Time: " + LocalDateTime.now());
            gridPane.add(dateTimeLabel, 0, 1, 3, 1);

            // Display group members
            Label groupLabel = new Label("Group Members (Alphabetical Order):");
            groupLabel.setStyle("-fx-font-size: 16px;");
            gridPane.add(groupLabel, 0, 2, 3, 1);

            TextArea groupTextArea = new TextArea();
            groupTextArea.setEditable(false);
            groupTextArea.setText("Chang Joo Yee\nNg Xue En\nWong Sin Yew\nWong Xin Tong");
            groupTextArea.setPrefHeight(100);
            gridPane.add(groupTextArea, 0, 3, 3, 1);

            // Check if user wants to add products
            Button addProductsBtn = new Button("Add Products");
            addProductsBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            addProductsBtn.setOnAction(e -> addProducts());
            
            // Button to show menu
            Button showMenuBtn = new Button("Show Menu");
            showMenuBtn.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white;");
            showMenuBtn.setOnAction(e -> showMenu(primaryStage));
            
            // Create labels for displaying user ID and user name
            Label userIDLabel = new Label("User ID: " + userID);
            Label userNameLabel = new Label("Username: " + userName);
            userIDLabel.setAlignment(Pos.BOTTOM_LEFT);
            userNameLabel.setAlignment(Pos.BOTTOM_LEFT);

            // Apply styling to the labels
            userIDLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #336699; -fx-background-color: #f0f0f0; -fx-padding: 5px;");
            userNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #336699; -fx-background-color: #f0f0f0; -fx-padding: 5px;");

            // Add labels to the grid pane
            gridPane.add(userIDLabel, 0, 6); // Add user ID label to the bottom left
            gridPane.add(userNameLabel, 1, 6); // Add user name label to the bottom left

       
            // Add buttons to a HBox for better alignment
            HBox buttonsBox = new HBox(20);
            buttonsBox.getChildren().addAll(addProductsBtn, showMenuBtn);
            gridPane.add(buttonsBox, 0, 5, 3, 1);

            // Set scene and stage
            mainScene = new Scene(gridPane, 600, 400); // Initialize mainScene
            primaryStage.setScene(mainScene);
            primaryStage.setTitle("Stock Management System");
            primaryStage.show();
            
        }
        
        else {
            // If the user did not any user name, then close the application
            primaryStage.close();
        }
    }
    
    public void handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                viewProducts(products);
                break;
            case 2:
                addStock();
                break;
            case 3:
                deductStock();
                break;
            case 4:
            	//For discontinuing or activating, need to select product first
                discontinueProduct();
                break;
            case 5:
                // For displaying products, we need to prompt the user for the product type
            	displayProducts(products);
                break;
            case 0:
                // do nothing
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                break;
        }
    }

    public void showMenu(Stage primaryStage) {
        // Create UI components for menu
        GridPane menuPane = new GridPane();
        menuPane.setPadding(new Insets(20));
        menuPane.setHgap(20);
        menuPane.setVgap(20);

        // Label for menu message
        Label menuLabel = new Label("Please choose a menu option");
        menuLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #008CBA;");
        menuPane.add(menuLabel, 0, 0);
        
        Button viewProductsBtn = new Button("View Products");
        viewProductsBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        viewProductsBtn.setOnAction(e -> handleMenuChoice(1)); // Method to view products

        // Add buttons for menu options
        Button addStockBtn = new Button("Add Stock");
        addStockBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        addStockBtn.setOnAction(e -> handleMenuChoice(2)); // No product type needed for adding stock

        Button deductStockBtn = new Button("Deduct Stock");
        deductStockBtn.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white;");
        deductStockBtn.setOnAction(e -> handleMenuChoice(3)); // No product type needed for deducting stock
        
        Button discontinueBtn = new Button("Discontinue Product"); // Discontinue Product Button
        discontinueBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");
        discontinueBtn.setOnAction(e -> handleMenuChoice(4)); // Method to discontinue product

        Button displayBtn = new Button("Display Product Details");
        displayBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        displayBtn.setOnAction(e -> handleMenuChoice(5)); // No product type initially provided

        // Button to go back to the main grid pane
        Button backToMainBtn = new Button("Back to Main");
        backToMainBtn.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white;");
        backToMainBtn.setOnAction(e -> primaryStage.setScene(mainScene)); // Use mainScene here

        // Add buttons to menu layout
        menuPane.add(viewProductsBtn, 0, 1);
        menuPane.add(addStockBtn, 0, 2);
        menuPane.add(deductStockBtn, 0, 3);
        menuPane.add(displayBtn, 0, 4);
        menuPane.add(discontinueBtn, 0, 5);
        menuPane.add(backToMainBtn, 0, 6);

        // Create scene for menu
        Scene menuScene = new Scene(menuPane, 300, 400);

        // Set menu scene to stage
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Menu");
        primaryStage.show();
    }

    // Method to add products
    public void addProducts() {
        while (true) {
            TextInputDialog quantityDialog = new TextInputDialog();
            quantityDialog.setTitle("Add Product");
            quantityDialog.setHeaderText(null);
            quantityDialog.setContentText("Enter the number of products to add:"); //Prompt user how many products they want to add
            Optional<String> quantityResult = quantityDialog.showAndWait();

            if (quantityResult.isPresent()) {
                try {
                    int quantityToAdd = Integer.parseInt(quantityResult.get());
                    for (int i = 0; i < quantityToAdd; i++) {
                        Product product = getProductDetails(); //Enter the product details
                        if (product != null) {
                            products.add(product); //Add the product to the array/list
                            showAlert("Success", "Product added successfully: " + product.getProductName());
                        } else {
                            showAlert("Warning", "Product addition canceled.");
                            break; // Break the loop if user cancels adding more products
                        }
                    }
                    break; // Exit the loop if quantity is successfully entered and products are added
                } catch (NumberFormatException e) {
                    showAlert("Invalid input", "Please enter a valid integer for number of products to add.");
                }
            } else {
                showAlert("Warning", "Product addition canceled.");
                break; // Exit the loop if user cancels adding products
            }
        }
    }

    public Product getProductDetails() {
        while (true) {
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Air Conditioner", "Refrigerator", "TV","Blender","Smart Phone"); 
            dialog.setTitle("Add Product");
            dialog.setHeaderText(null);
            dialog.setContentText("Select a product:"); //Allow user to choose which type of product they want to add
            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                String productName = result.get();
                switch(productName) {
                case "Air Conditioner" :
                	return createAirConditionerProduct();
                case "Refrigerator":
                	return createRefrigeratorProduct();
                case "TV" : 
                	return createTVProduct();
                case "Blender":
                	return createBlenderProduct();
                case "Smart Phone":
                	return createSmartPhoneProduct();
                	default:
                		return null;
                }
                
                }else {
                	return null;
                }
                
        }
    }
    
    public AirConditioner createAirConditionerProduct() {
        String name = null;
        String compressorType = null;
        double coolingCapacity = 0.00;
        double heatingCapacity = 0.00;
        int quantity = 0;
        double price = 0.00;
        int itemNumber = 0;

        while (name == null || name.isEmpty() || !isProductNameUnique(name)) { //While loop until user enter a valid input
            TextInputDialog nameDialog = new TextInputDialog();
            nameDialog.setTitle("Add Air Conditioner");
            nameDialog.setHeaderText(null);
            nameDialog.setContentText("Enter the name:");
            Optional<String> nameResult = nameDialog.showAndWait();
            if (nameResult.isPresent()) {
                name = nameResult.get();
                if (!isProductNameUnique(name)) { //Show alert if have the same product name in array/list 
                    showAlert("Invalid Input", "Product with this name already exists. Please enter a unique name.");
                }
            } else {
                return null; // User canceled input
            }
        }

        while (compressorType == null || compressorType.isEmpty()) { //While loop until user enter a valid input
            TextInputDialog typeDialog = new TextInputDialog();
            typeDialog.setTitle("Add Air Conditioner");
            typeDialog.setHeaderText(null);
            typeDialog.setContentText("Enter the compressor type:");
            Optional<String> typeResult = typeDialog.showAndWait();
            if (typeResult.isPresent()) {
                compressorType = typeResult.get();
            } else {
                return null; // User canceled input
            }
        }

        while (true) {
            TextInputDialog coolingDialog = new TextInputDialog();
            coolingDialog.setTitle("Add Air Conditioner");
            coolingDialog.setHeaderText(null);
            coolingDialog.setContentText("Enter the cooling capacity (kW):");
            Optional<String> coolingResult = coolingDialog.showAndWait();
            if (coolingResult.isPresent()) {
                try {
                    coolingCapacity = Double.parseDouble(coolingResult.get());
                    break; // Exit the loop if parsing succeeds
                } catch (NumberFormatException e) { //Check the input validation (not empty or string type)
                    showAlert("Invalid input", "Invalid input for cooling capacity. Please enter a valid number.");
                }
            } else {
                return null; // User canceled input
            }
        }

        while (true) {
            TextInputDialog heatingDialog = new TextInputDialog();
            heatingDialog.setTitle("Add Air Conditioner");
            heatingDialog.setHeaderText(null);
            heatingDialog.setContentText("Enter the heating capacity (kW):");
            Optional<String> heatingResult = heatingDialog.showAndWait();
            if (heatingResult.isPresent()) {
                try {
                    heatingCapacity = Double.parseDouble(heatingResult.get());
                    break; // Exit the loop if parsing succeeds
                } catch (NumberFormatException e) { //Check the input validation (not empty or string type)
                    showAlert("Invalid input", "Invalid input for heating capacity. Please enter a valid number.");
                }
            } else {
                return null; // User canceled input
            }
        }

        while (true) {
            TextInputDialog quantityDialog = new TextInputDialog();
            quantityDialog.setTitle("Add Air Conditioner");
            quantityDialog.setHeaderText(null);
            quantityDialog.setContentText("Enter the quantity available:");
            Optional<String> quantityResult = quantityDialog.showAndWait();
            if (quantityResult.isPresent()) {
                try {
                    quantity = Integer.parseInt(quantityResult.get());
                    if (quantity <= 0) { //Quantity of products less than or equal to 0
                        showAlert("Invalid input", "Invalid quantity. Quantity must be a positive number.");
                    } else {
                        break; // Exit the loop if parsing succeeds
                    }
                } catch (NumberFormatException e) { //Check the input validation (not empty or others type)
                    showAlert("Invalid input", "Invalid input for quantity. Please enter a valid integer.");
                }
            } else {
                return null; // User canceled input
            }
        }

        while (true) {
            TextInputDialog priceDialog = new TextInputDialog();
            priceDialog.setTitle("Add Air Conditioner");
            priceDialog.setHeaderText(null);
            priceDialog.setContentText("Enter the price:");
            Optional<String> priceResult = priceDialog.showAndWait();
            if (priceResult.isPresent()) {
                try {
                    price = Double.parseDouble(priceResult.get());
                    break; // Exit the loop if parsing succeeds
                } catch (NumberFormatException e) { //Check the input validation (not empty or string type)
                    showAlert("Invalid input", "Invalid input for price. Please enter a valid number.");
                }
            } else {
                return null; // User canceled input
            }
        }

        // Prompt the user to enter the item number
        while (true) {
            TextInputDialog itemDialog = new TextInputDialog();
            itemDialog.setTitle("Add Air Conditioner");
            itemDialog.setHeaderText(null);
            itemDialog.setContentText("Enter the item number:");
            Optional<String> itemResult = itemDialog.showAndWait();
            if (itemResult.isPresent()) {
                try {
                    itemNumber = Integer.parseInt(itemResult.get());
                    // Check if the entered item number is already used
                    if (!isItemNumberUnique(itemNumber)) { //Check the item number of product must be unique
                        showAlert("Invalid input", "Item number already exists. Please enter a unique item number.");
                    } else {
                        break; // Exit the loop if item number is unique
                    }
                } catch (NumberFormatException e) { //Check the input validation (not empty or others type)
                    showAlert("Invalid input", "Invalid input for item number. Please enter a valid integer.");
                }
            } else {
                return null; // User canceled input
            }
        }

        return new AirConditioner(itemNumber, name, quantity, price, compressorType, heatingCapacity, coolingCapacity);
    }
    
    public Refrigerator createRefrigeratorProduct() {
	String name = null;
	String doorDesign = null;
	String color = null;
	int capacity = 0;
	int quantityAvailable = 0;
	double productPrice = 0.0;
	int itemNumber = 0;
	
	while(name == null || name.isEmpty()||!isProductNameUnique(name)) {
		TextInputDialog nameDialog = new TextInputDialog();
		nameDialog.setHeaderText(null);
		nameDialog.setContentText("Enter the name:");
		Optional<String> nameResult = nameDialog.showAndWait();
		if(nameResult.isPresent()) {
			name = nameResult.get();
			if(!isProductNameUnique(name)) {
				showAlert("Invalid input", "Product with this name already exists. Please enter an unique name.");
			}
			
		}else {
			return null;
		}
	}
	
	while(doorDesign == null || doorDesign.isEmpty()) {
		TextInputDialog doorDialog = new TextInputDialog();
		doorDialog.setHeaderText(null);
		doorDialog.setContentText("Enter the door design :");
		Optional<String> doorResult = doorDialog.showAndWait();
		if(doorResult.isPresent()) {
			doorDesign = doorResult.get();
		}else {
			return null;
		}
	}
	
	while(color == null || color.isEmpty()) {
		TextInputDialog colorDialog = new TextInputDialog();
		colorDialog.setHeaderText(null);
		colorDialog.setContentText("Enter the color :");
		Optional<String> colorResult = colorDialog.showAndWait();
		if(colorResult.isPresent()) {
			color = colorResult.get();
		}else {
			return null;
		}
	}
	
	 while (true) {
         TextInputDialog capacityDialog = new TextInputDialog();
         capacityDialog.setHeaderText(null);
         capacityDialog.setContentText("Enter the capacity (in Litres):");
         Optional<String> capacityResult = capacityDialog.showAndWait();
         if (capacityResult.isPresent()) {
             try {
                 capacity = Integer.parseInt(capacityResult.get());
                 break; // Exit the loop if parsing succeeds
             } catch (NumberFormatException e) { //Check the input validation (not empty or string type)
                 showAlert("Invalid input", "Invalid input for capacity. Please enter a valid number.");
             }
         } else {
             return null; // User canceled input
         }
     }
	 
	 while (true) {
         TextInputDialog quantityDialog = new TextInputDialog();
         quantityDialog.setHeaderText(null);
         quantityDialog.setContentText("Enter the quantity available:");
         Optional<String> quantityResult = quantityDialog.showAndWait();
         if (quantityResult.isPresent()) {
             try {
                 quantityAvailable = Integer.parseInt(quantityResult.get());
                 if (quantityAvailable <= 0) { 
                     showAlert("Invalid input", "Invalid quantity. Quantity must be a positive number.");
                 } else {
                     break; 
                 }
             } catch (NumberFormatException e) { 
                 showAlert("Invalid input", "Invalid input for quantity. Please enter a valid integer.");
             }
         } else {
             return null; // User canceled input
         }
     }


     while (true) {
         TextInputDialog priceDialog = new TextInputDialog();
         priceDialog.setHeaderText(null);
         priceDialog.setContentText("Enter the price:");
         Optional<String> priceResult = priceDialog.showAndWait();
         if (priceResult.isPresent()) {
             try {
                 productPrice = Double.parseDouble(priceResult.get());
                 break; // Exit the loop if parsing succeeds
             } catch (NumberFormatException e) { //Check the input validation (not empty or string type)
                 showAlert("Invalid input", "Invalid input for price. Please enter a valid number.");
             }
         } else {
             return null; // User canceled input
         }
     }
     while (true) {
         TextInputDialog itemDialog = new TextInputDialog();
         itemDialog.setHeaderText(null);
         itemDialog.setContentText("Enter the item number:");
         Optional<String> itemResult = itemDialog.showAndWait();
         if (itemResult.isPresent()) {
             try {
                 itemNumber = Integer.parseInt(itemResult.get());
                 // Check if the entered item number is already used
                 if (!isItemNumberUnique(itemNumber)) { //Check the item number of product must be unique
                     showAlert("Invalid input", "Item number already exists. Please enter a unique item number.");
                 } else {
                     break; // Exit the loop if item number is unique
                 }
             } catch (NumberFormatException e) { //Check the input validation (not empty or others type)
                 showAlert("Invalid input", "Invalid input for item number. Please enter a valid integer.");
             }
         } else {
             return null; // User canceled input
         }
     }

     return new Refrigerator(name, doorDesign, color, capacity, quantityAvailable,productPrice,itemNumber);

}
    
    public Smartphone createSmartPhoneProduct() {
        String name = null;
        String displaytyp = null;
        String chipset = null;
        int maincamera = 0;
        int quantity = 0;
        double price = 0.0;
        int itemNumber = 0;

        while (name == null || name.isEmpty() || !isProductNameUnique(name)) { //While loop until user enter a valid input
            TextInputDialog nameDialog = new TextInputDialog();
            nameDialog.setHeaderText(null);
            nameDialog.setContentText("Enter the name:");
            Optional<String> nameResult = nameDialog.showAndWait();
            if (nameResult.isPresent()) {
                name = nameResult.get();
                if (!isProductNameUnique(name)) { //Show alert if have the same product name in array/list 
                    showAlert("Invalid Input", "Product with this name already exists. Please enter a unique name.");
                }
            } else {
                return null; // User canceled input
            }
        }

        while (displaytyp == null || displaytyp.isEmpty()) { //While loop until user enter a valid input
            TextInputDialog type = new TextInputDialog();
            type.setHeaderText(null);
            type.setContentText("Enter the display type :");
            Optional<String> typeR = type.showAndWait();
            if (typeR.isPresent()) {
                displaytyp = typeR.get().trim();//remove leading and trailing white spaces
                if(!displaytyp.isEmpty()){ //check whether input is empty
                	break; // Exit the loop if input is not empty
                }
                else {
                	showAlert("Invalid input", "Display type cannot be empty. Please enter a valid string.");
                }
            } else {
                return null; // User canceled input
            }
        }

        while (true) {
            TextInputDialog chipDialog = new TextInputDialog();
            chipDialog.setHeaderText(null);
            chipDialog.setContentText("Enter the chipset :");
            Optional<String> chipR = chipDialog.showAndWait();
            if (chipR.isPresent()) {
                    chipset = chipR.get().trim();// remove leading ad trailing white spaces 
                    if(!chipset.isEmpty()){ //Check the input validation (not empty)
                    	break; // Exit the loop if input is not empty
                    }
                    else {
                    	showAlert("Invalid input", "Chipset cannot be empty. Please enter a valid string.");
                    }
            } else {
                return null; // User canceled input
            }
        }

        while (true) {
            TextInputDialog cameraDialog = new TextInputDialog();
            cameraDialog.setHeaderText(null);
            cameraDialog.setContentText("Enter the main camera(MP) :");
            Optional<String> cameraResult = cameraDialog.showAndWait();
            if (cameraResult.isPresent()) {
                try {
                    maincamera = Integer.parseInt (cameraResult.get());
                    break; // Exit the loop if parsing succeeds
                } catch (NumberFormatException e) { //Check the input validation (not empty or string type)
                    showAlert("Invalid input", "Invalid input for main camera specification. Please enter a valid number.");
                }
            } else {
                return null; // User canceled input
            }
        }

        while (true) {
            TextInputDialog quantityDialog = new TextInputDialog();
            quantityDialog.setHeaderText(null);
            quantityDialog.setContentText("Enter the quantity available:");
            Optional<String> quantityResult = quantityDialog.showAndWait();
            if (quantityResult.isPresent()) {
                try {
                    quantity = Integer.parseInt(quantityResult.get());
                    if (quantity <= 0) { //Quantity of products less than or equal to 0
                        showAlert("Invalid input", "Invalid quantity. Quantity must be a positive number.");
                    } else {
                        break; // Exit the loop if parsing succeeds
                    }
                } catch (NumberFormatException e) { //Check the input validation (not empty or others type)
                    showAlert("Invalid input", "Invalid input for quantity. Please enter a valid integer.");
                }
            } else {
                return null; // User canceled input
            }
        }

        while (true) {
            TextInputDialog priceDialog = new TextInputDialog();
            priceDialog.setHeaderText(null);
            priceDialog.setContentText("Enter the price:");
            Optional<String> priceResult = priceDialog.showAndWait();
            if (priceResult.isPresent()) {
                try {
                    price = Double.parseDouble(priceResult.get());
                    break; // Exit the loop if parsing succeeds
                } catch (NumberFormatException e) { //Check the input validation (not empty or string type)
                    showAlert("Invalid input", "Invalid input for price. Please enter a valid number.");
                }
            } else {
                return null; // User canceled input
            }
        }

        // Prompt the user to enter the item number
        while (true) {
            TextInputDialog itemDialog = new TextInputDialog();
            itemDialog.setHeaderText(null);
            itemDialog.setContentText("Enter the item number:");
            Optional<String> itemResult = itemDialog.showAndWait();
            if (itemResult.isPresent()) {
                try {
                    itemNumber = Integer.parseInt(itemResult.get());
                    // Check if the entered item number is already used
                    if (!isItemNumberUnique(itemNumber)) { //Check the item number of product must be unique
                        showAlert("Invalid input", "Item number already exists. Please enter a unique item number.");
                    } else {
                        break; // Exit the loop if item number is unique
                    }
                } catch (NumberFormatException e) { //Check the input validation (not empty or others type)
                    showAlert("Invalid input", "Invalid input for item number. Please enter a valid integer.");
                }
            } else {
                return null; // User canceled input
            }
        }

        return new Smartphone(itemNumber, name, quantity, price, displaytyp, chipset, maincamera);
    }

    public TV createTVProduct() {
    	String name = null;
    	String screenTyp = null;
    	String resolution = null;
    	double dispSize = 0;
    	int quantityAvailable = 0;
    	double productPrice = 0.0;
    	int itemNumber = 0;
    	
    	while(name == null || name.isEmpty()||!isProductNameUnique(name)) {
    		TextInputDialog nameDialog = new TextInputDialog();
    		nameDialog.setHeaderText(null);
    		nameDialog.setContentText("Enter the name:");
    		Optional<String> nameResult = nameDialog.showAndWait();
    		if(nameResult.isPresent()) {
    			name = nameResult.get();
    			if(!isProductNameUnique(name)) {
    				showAlert("Invalid input", "Product with this name already exists. Please enter an unique name.");
    			}
    			
    		}else {
    			return null;
    		}
    	}
    	
    	while(screenTyp == null || screenTyp.isEmpty()) {
    		TextInputDialog screenDialog = new TextInputDialog();
    		screenDialog.setHeaderText(null);
    		screenDialog.setContentText("Enter the screen type :");
    		Optional<String> screenResult = screenDialog.showAndWait();
    		if(screenResult.isPresent()) {
    			screenTyp =screenResult.get().trim();
    		}else {
    			showAlert("Invalid input", "Screen type cannot be empty. Please enter a valid string.");
    			return null;
    		}
    	}
    	
    	while(resolution == null || resolution.isEmpty()) {
    		TextInputDialog resDialog = new TextInputDialog();
    		resDialog.setHeaderText(null);
    		resDialog.setContentText("Enter the resolution :");
    		Optional<String> resResult = resDialog.showAndWait();
    		if(resResult.isPresent()) {
    			resolution= resResult.get();
    		}else {
    			return null;
    		}
    	}
    	
    	 while (true) {
             TextInputDialog dispSizeDialog = new TextInputDialog();
             dispSizeDialog.setHeaderText(null);
             dispSizeDialog.setContentText("Enter the display size :");
             Optional<String> dispSizeResult = dispSizeDialog.showAndWait();
             if (dispSizeResult.isPresent()) {
                 try {
                     dispSize = Integer.parseInt(dispSizeResult.get());
                     break; // Exit the loop if parsing succeeds
                 } catch (NumberFormatException e) { //Check the input validation (not empty or string type)
                     showAlert("Invalid input", "Invalid input for display size. Please enter a valid value.");
                 }
             } else {
                 return null; // User canceled input
             }
         }
    	 
    	 while (true) {
             TextInputDialog quantityDialog = new TextInputDialog();
             quantityDialog.setHeaderText(null);
             quantityDialog.setContentText("Enter the quantity available:");
             Optional<String> quantityResult = quantityDialog.showAndWait();
             if (quantityResult.isPresent()) {
                 try {
                     quantityAvailable = Integer.parseInt(quantityResult.get());
                     if (quantityAvailable <= 0) { 
                         showAlert("Invalid input", "Invalid quantity. Quantity must be a positive number.");
                     } else {
                         break; 
                     }
                 } catch (NumberFormatException e) { 
                     showAlert("Invalid input", "Invalid input for quantity. Please enter a valid integer.");
                 }
             } else {
                 return null; // User canceled input
             }
         }


         while (true) {
             TextInputDialog priceDialog = new TextInputDialog();
             priceDialog.setHeaderText(null);
             priceDialog.setContentText("Enter the price:");
             Optional<String> priceResult = priceDialog.showAndWait();
             if (priceResult.isPresent()) {
                 try {
                     productPrice = Double.parseDouble(priceResult.get());
                     break; // Exit the loop if parsing succeeds
                 } catch (NumberFormatException e) { //Check the input validation (not empty or string type)
                     showAlert("Invalid input", "Invalid input for price. Please enter a valid number.");
                 }
             } else {
                 return null; // User canceled input
             }
         }
         while (true) {
             TextInputDialog itemDialog = new TextInputDialog();
             itemDialog.setHeaderText(null);
             itemDialog.setContentText("Enter the item number:");
             Optional<String> itemResult = itemDialog.showAndWait();
             if (itemResult.isPresent()) {
                 try {
                     itemNumber = Integer.parseInt(itemResult.get());
                     // Check if the entered item number is already used
                     if (!isItemNumberUnique(itemNumber)) { //Check the item number of product must be unique
                         showAlert("Invalid input", "Item number already exists. Please enter a unique item number.");
                     } else {
                         break; // Exit the loop if item number is unique
                     }
                 } catch (NumberFormatException e) { //Check the input validation (not empty or others type)
                     showAlert("Invalid input", "Invalid input for item number. Please enter a valid integer.");
                 }
             } else {
                 return null; // User canceled input
             }
         }

         return new TV(name,screenTyp, resolution,dispSize, quantityAvailable,productPrice,itemNumber);

    }
    
    public Blender createBlenderProduct() {
        String name = null;
        String material = null;
        String function=null;
        int rpm = 0;
        int quantity = 0;
        double price = 0.0;
        int itemNumber = 0;

        while (name == null || name.isEmpty() || !isProductNameUnique(name)) { //While loop until user enter a valid input
            TextInputDialog nameDialog = new TextInputDialog();
            nameDialog.setHeaderText(null);
            nameDialog.setContentText("Enter the name:");
            Optional<String> nameResult = nameDialog.showAndWait();
            if (nameResult.isPresent()) {
                name = nameResult.get();
                if (!isProductNameUnique(name)) { //Show alert if have the same product name in array/list 
                    showAlert("Invalid Input", "Product with this name already exists. Please enter a unique name.");
                }
            } else {
                return null; // User canceled input
            }
        }

        while (material == null || material.isEmpty()) { //While loop until user enter a valid input
            TextInputDialog type = new TextInputDialog();
            type.setHeaderText(null);
            type.setContentText("Enter the container material :");
            Optional<String> typeR = type.showAndWait();
            if (typeR.isPresent()) {
                material = typeR.get().trim();//remove leading and trailing white spaces
                if(!material.isEmpty()){ //check whether input is empty
                	break; // Exit the loop if input is not empty
                }
                else {
                	showAlert("Invalid input", "Container material cannot be empty. Please enter a valid string.");
                }
            } else {
                return null; // User canceled input
            }
        }
        
        while (function== null || function.isEmpty()) { //While loop until user enter a valid input
            TextInputDialog func = new TextInputDialog();
            func.setHeaderText(null);
            func.setContentText("Enter the function :");
            Optional<String> funcR = func.showAndWait();
            if (funcR.isPresent()) {
                function = funcR.get().trim();//remove leading and trailing white spaces
                if(!function.isEmpty()){ //check whether input is empty
                	break; // Exit the loop if input is not empty
                }
                else {
                	showAlert("Invalid input", "Function cannot be empty. Please enter a valid string.");
                }
            } else {
                return null; // User canceled input
            }
        }
        
        while (true) {
            TextInputDialog rpmDialog = new TextInputDialog();
            rpmDialog.setHeaderText(null);
            rpmDialog.setContentText("Enter the number of rotations per minute :");
            Optional<String> rpmResult = rpmDialog.showAndWait();
            if (rpmResult.isPresent()) {
                try {
                    rpm = Integer.parseInt (rpmResult.get());
                    break; // Exit the loop if parsing succeeds
                } catch (NumberFormatException e) { //Check the input validation (not empty or string type)
                    showAlert("Invalid input", "Invalid input for number of rotations per minute. Please enter a valid number.");
                }
            } else {
                return null; // User canceled input
            }
        }
        

        while (true) {
            TextInputDialog quantityDialog = new TextInputDialog();
            quantityDialog.setHeaderText(null);
            quantityDialog.setContentText("Enter the quantity available:");
            Optional<String> quantityResult = quantityDialog.showAndWait();
            if (quantityResult.isPresent()) {
                try {
                    quantity = Integer.parseInt(quantityResult.get());
                    if (quantity <= 0) { //Quantity of products less than or equal to 0
                        showAlert("Invalid input", "Invalid quantity. Quantity must be a positive number.");
                    } else {
                        break; // Exit the loop if parsing succeeds
                    }
                } catch (NumberFormatException e) { //Check the input validation (not empty or others type)
                    showAlert("Invalid input", "Invalid input for quantity. Please enter a valid integer.");
                }
            } else {
                return null; // User canceled input
            }
        }

        while (true) {
            TextInputDialog priceDialog = new TextInputDialog();
            priceDialog.setHeaderText(null);
            priceDialog.setContentText("Enter the price:");
            Optional<String> priceResult = priceDialog.showAndWait();
            if (priceResult.isPresent()) {
                try {
                    price = Double.parseDouble(priceResult.get());
                    break; // Exit the loop if parsing succeeds
                } catch (NumberFormatException e) { //Check the input validation (not empty or string type)
                    showAlert("Invalid input", "Invalid input for price. Please enter a valid number.");
                }
            } else {
                return null; // User canceled input
            }
        }

        // Prompt the user to enter the item number
        while (true) {
            TextInputDialog itemDialog = new TextInputDialog();
            itemDialog.setHeaderText(null);
            itemDialog.setContentText("Enter the item number:");
            Optional<String> itemResult = itemDialog.showAndWait();
            if (itemResult.isPresent()) {
                try {
                    itemNumber = Integer.parseInt(itemResult.get());
                    // Check if the entered item number is already used
                    if (!isItemNumberUnique(itemNumber)) { //Check the item number of product must be unique
                        showAlert("Invalid input", "Item number already exists. Please enter a unique item number.");
                    } else {
                        break; // Exit the loop if item number is unique
                    }
                } catch (NumberFormatException e) { //Check the input validation (not empty or others type)
                    showAlert("Invalid input", "Invalid input for item number. Please enter a valid integer.");
                }
            } else {
                return null; // User canceled input
            }
        }

        return new Blender(itemNumber,name,material,rpm,function,quantity,price);
    }
    
    public void addStock() {
        while (true) {
            if (products.isEmpty()) {
                showAlert("Warning", "No products available to add stock."); //Show alert when there is empty product list
                return;
            }

            // Create TableView
            TableView<Product> tableView = createProductTableView();

            // Initialize the SortedList with observable list and comparator
            SortedList<Product> sortedProducts = new SortedList<>(FXCollections.observableList(products), Comparator.comparing(Product::getItemNumber));

            // Bind the comparator
            sortedProducts.comparatorProperty().bind(tableView.comparatorProperty());

            // Populate TableView with sorted products
            tableView.setItems(sortedProducts);
 
            // Create input fields
            TextField quantityField = new TextField();
            quantityField.setPromptText("Enter Quantity");
            

            // Create a layout for the dialog
            VBox layout = new VBox(20);
            layout.getChildren().addAll(tableView, quantityField);
           


            // Show dialog to prompt user for product index and quantity
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            dialog.setHeaderText(null);
            dialog.setTitle("Add Stock");
            dialog.getDialogPane().setContent(layout);
            layout.setPrefSize(800, 600); // Set the preferred width and height of the layout container


            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Get selected product from TableView
                Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
                if (selectedProduct == null) { //Prompt user select a product to add its respective stock amount
                    showAlert("Warning", "Please select a product.");
                    continue; // Restart the loop
                }

                try {
                    int quantity = Integer.parseInt(quantityField.getText());
                    if (quantity <= 0) { //Stock quantity cannot less than or equal to 0
                        showAlert("Warning", "Invalid quantity. Quantity must be a positive number.");
                        continue; // Restart the loop
                    }
                    // Inside the addStock method in your GUI class
                    if (selectedProduct.addStock(quantity)) {
                        showAlert("Success", "Stock added successfully for " + selectedProduct.getProductName()); //Display success message
                        return; // Exit the method
                    } else {
                        showAlert("Warning", "Cannot add stock to a discontinued product line."); //Display error message when the status of product is discontinue
                        return; // Exit the method
                    }

                } catch (NumberFormatException ex) { //Check the input validation (not empty or others type)
                    showAlert("Error", "Invalid input. Please enter a valid quantity.");
                    continue; // Restart the loop
                }
            } else {
            	showAlert("Warning", "Stock addition canceled.");
                return; // User canceled operation
            }
        }
    }
    
    public void deductStock() {
        while (true) {
            if (products.isEmpty()) {
                showAlert("Warning", "No products available to deduct stock."); //Show alert when there is empty product list
                return;
            }

            // Create TableView
            TableView<Product> tableView = createProductTableView();

            // Initialize the SortedList with observable list and comparator
            SortedList<Product> sortedProducts = new SortedList<>(FXCollections.observableList(products), Comparator.comparing(Product::getItemNumber));

            // Bind the comparator
            sortedProducts.comparatorProperty().bind(tableView.comparatorProperty());

            // Populate TableView with sorted products
            tableView.setItems(sortedProducts);

            // Create input fields
            TextField quantityField = new TextField();
            quantityField.setPromptText("Enter Quantity");

            // Create a layout for the dialog
            VBox layout = new VBox(10);
            layout.getChildren().addAll(tableView, quantityField);

            // Show dialog to prompt user for product index and quantity
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            dialog.setHeaderText(null);
            dialog.setTitle("Deduct Stock");
            dialog.getDialogPane().setContent(layout);
            layout.setPrefSize(800, 600); // Set the preferred width and height of the layout container


            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) { //Prompt user select a product to add its respective stock amount
                    try {
                        int quantity = Integer.parseInt(quantityField.getText());
                        if (quantity <= 0 || quantity > selectedProduct.getQuantityAvailable()) { //Check the stock quantity entered is between the quantity available
                            showAlert("Warning", "Invalid quantity. Quantity must be between 1 and the current stock amount.");
                            continue; // Restart the loop
                        }
                        selectedProduct.deductStock(quantity);
                        showAlert("Success", "Stock deducted successfully for " + selectedProduct.getProductName()); //Display success message
                        return; // Exit the method
                    } catch (NumberFormatException ex) { //Check the input validation (not empty or others type)
                        showAlert("Error", "Invalid input. Please enter a valid quantity.");
                        continue; // Restart the loop
                    }
                } else {
                    showAlert("Warning", "Please select a product.");
                    continue; // Restart the loop
                }
            } else {
            	showAlert("Warning", "Stock deduction canceled.");
                return; // User canceled operation
            }
        }
    }
  
    
    public void discontinueProduct() {
        while (true) {
            if (products.isEmpty()) {
                showAlert("Warning", "No products available to discontinue product.");
                return;
            }

            // Create TableView
            TableView<Product> tableView = createProductTableView();

            // Initialize the SortedList with observable list and comparator
            SortedList<Product> sortedProducts = new SortedList<>(FXCollections.observableList(products), Comparator.comparing(Product::getItemNumber));

            // Bind the comparator
            sortedProducts.comparatorProperty().bind(tableView.comparatorProperty());

            // Populate TableView with sorted products
            tableView.setItems(sortedProducts);

            // Create a choice box for status
            ChoiceBox<String> statusChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Active", "Discontinued"));
           
            // Bind disable property of choice box to selected item property of TableView
            statusChoiceBox.disableProperty().bind(Bindings.isNull(tableView.getSelectionModel().selectedItemProperty()));

            // Create a layout for the dialog
            VBox layout = new VBox(10);
            layout.getChildren().addAll(tableView, statusChoiceBox);

            // Show dialog to prompt user for product selection and status
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
            dialog.setHeaderText(null);
            dialog.setTitle("Discontinue Product");
            dialog.getDialogPane().setContent(layout);
            layout.setPrefSize(800, 600);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
                if (selectedProduct != null) {
                    // Update the status based on the choice box selection
                    String selectedStatus = statusChoiceBox.getValue();
                    
                    if ("Active".equals(selectedStatus)) {
                        selectedProduct.setProductStatus(true);
                    } else if ("Discontinued".equals(selectedStatus)) {
                        selectedProduct.setProductStatus(false);
                    }

                    showAlert("Success", "Product status updated successfully to " + (selectedProduct.isProductStatus() ? "Active" : "Discontinued"));
                    return;
                } else {
                    showAlert("Warning", "Please select a product.");
                    continue;
                }
            } else {
                showAlert("Warning", "Product status update cancelled.");
                return;
            }
        }
    }
   
    
    @SuppressWarnings("unchecked")
    public TableView<Product> createProductTableView() {
        // Create TableView to display available products
        TableView<Product> tableView = new TableView<>();
        TableColumn<Product, Integer> indexColumn = new TableColumn<>("Item No");
        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity Available");
        TableColumn<Product, String> statusColumn = new TableColumn<>("Status");
       

        // Define how to extract values from Product object for each column
        indexColumn.setCellValueFactory(new PropertyValueFactory<>("itemNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantityAvailable"));

        // Create a choice box for status
        ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Active", "Discontinued"));
        // Set text color and font weight for non-editable cells
        choiceBox.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-font-size: 14px; -fx-opacity: 1.0;"); // Adjust opacity

        // Create a cell factory for the status column to display a choice box
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("")); // Placeholder, not used for displaying data
        statusColumn.setStyle("-fx-text-fill: black; -fx-font-weight: bold; -fx-opacity: 1.0;");
        statusColumn.setCellFactory(column -> new TableCell<Product, String>() {
            private final ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList("Active", "Discontinued"));

            {
                choiceBox.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    String selectedStatus = choiceBox.getValue();
                    if ("Discontinued".equals(selectedStatus)) {
                        product.setProductStatus(false); // Discontinue the product
                    } else {
                        product.setProductStatus(true); // Set product status to active
                    }
                    getTableView().refresh(); // Refresh the table to reflect the change
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Product product = getTableView().getItems().get(getIndex());
                    if (product == null) {
                        setGraphic(null);
                    } else {
                        if (tableView.getProperties().containsKey("discontinueProductEntered") && (boolean) tableView.getProperties().get("discontinueProductEntered")) {
                            choiceBox.setDisable(false); // Enable choice box if in discontinueProduct() function
                        } else {
                            choiceBox.setDisable(true); // Disable choice box if not in discontinueProduct() function
                        }
                        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                            if ("Discontinued".equals(newValue)) {
                                product.setProductStatus(false); // Discontinue the product
                                tableView.refresh(); // Refresh the table to reflect the change
                            } else if ("Active".equals(newValue)) {
                                product.setProductStatus(true); // Set product status to active
                                tableView.refresh(); // Refresh the table to reflect the change
                            }
                        });
                        choiceBox.setValue(product.isProductStatus() ? "Active" : "Discontinued");
                        setGraphic(choiceBox);
                    }
                }
            }
        });

        // Listener to enable choice box when a product is selected
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                choiceBox.setDisable(false);
            } else {
                choiceBox.setDisable(true);
            }
        });

        // Add columns to TableView
        tableView.getColumns().addAll(indexColumn, nameColumn, quantityColumn, statusColumn);

        // Set the preferred width of each column to fit the content
        indexColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        nameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
        quantityColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        statusColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));
       

        // By default, TableView sorts in ascending order
        tableView.getSortOrder().add(indexColumn);
        
        
        return tableView;
    }
    

    // Method to show an alert
    public static void showAlert(String title, String message) {
    	Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    // Method to check if the product name is unique
    public boolean isProductNameUnique(String productName) {
        for (Product product : products) {
            if (product.getProductName().equals(productName)) {
                return false;
            }
        }
        return true;
    }
    
    // Method to check if the entered item number is unique
    public boolean isItemNumberUnique(int itemNumber) {
        for (Product product : products) {
            if (product.getItemNumber() == itemNumber) {
                return false; // Item number already exists
            }
        }
        return true; // Item number is unique
    }
    
	
    public static void displayProducts(ArrayList<Product> products) {
        // Create a list of product types
        ArrayList<String> productTypes = new ArrayList<>();
        productTypes.add("Air Conditioner");
        productTypes.add("Refrigerator");
        productTypes.add("TV");
        productTypes.add("Blender");
        productTypes.add("Smartphone");

        // Display a choice dialog for the user to select the product type
        ChoiceDialog<String> typeDialog = new ChoiceDialog<>(productTypes.get(0), productTypes);
        typeDialog.setTitle("Select Product Type");
        typeDialog.setHeaderText("Choose a product type to display:");
        typeDialog.setContentText("Product Type:");
        Optional<String> result = typeDialog.showAndWait();

        // If a product type is selected, display relevant products
        result.ifPresent(selectedType -> {
            ArrayList<Product> selectedProducts = new ArrayList<>();
            for (Product product : products) {
            	if ((selectedType.equalsIgnoreCase("Air Conditioner") && product instanceof AirConditioner) ||
            		    (selectedType.equalsIgnoreCase("Refrigerator") && product instanceof Refrigerator) ||
            		    (selectedType.equalsIgnoreCase("TV") && product instanceof TV) ||
            		    (selectedType.equalsIgnoreCase("Blender") && product instanceof Blender) || 
            		    (selectedType.equalsIgnoreCase("Smartphone") && product instanceof Smartphone)) {
            		    selectedProducts.add(product);
            		}
            }

            // If no products of the selected type are available, show an alert
            if (selectedProducts.isEmpty()) {
                showAlert("Information", "There is no " + selectedType + " product available.");
                return;
            }

            // Sort selected products by item number in ascending order
            selectedProducts.sort(Comparator.comparing(Product::getItemNumber));

            // Concatenate details of selected products
            StringBuilder productDetails = new StringBuilder();
            for (Product product : selectedProducts) {
                productDetails.append(product.toString()).append("\n\n");
            }

            // Show an alert with the product details
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Product Information");
            alert.setHeaderText(null);
            alert.setContentText(productDetails.toString());
            alert.showAndWait();
        });
    }

    //Method to update if the user want to change the detail of TV product
    public void changeTVProduct(ArrayList<Product> products) {
        //Get the list of TV item number 
    	ArrayList<Integer> tvitemNo = new ArrayList<Integer>();

    	for (Product product : products) {
    	    if (product instanceof TV) {
    	    	tvitemNo.add(product.getItemNumber());
    	    }
    	}
        // Display dialog with TV item number
        ChoiceDialog<Integer> productDialog = new ChoiceDialog<Integer>();
        ObservableList<Integer> list = productDialog.getItems();
        list.addAll(tvitemNo);
        productDialog.setTitle("Select TV Product");
        productDialog.setHeaderText(null);
        productDialog.setContentText("Select an item number of TV product:");
        Optional<Integer> productResult = productDialog.showAndWait();

        TV tv = null;
        if (productResult.isPresent()) {
            Integer choiceItemNo = productResult.get();
            for (Product product : products) {
            	if (product instanceof TV && product.getItemNumber() == choiceItemNo) {
                    tv = (TV) product;
                    break;
                }
            }
        }
        
        if (tv != null) {
            ChoiceDialog<String> infoDialog = new ChoiceDialog<String>("Product Name","Screen Type", "Resolution", "Display Size","Price");
            infoDialog.setTitle("Select Detail of TV to Update");
            infoDialog.setHeaderText(null);
            infoDialog.setContentText("Select a detail of TV to update:");
            Optional<String> info = infoDialog.showAndWait();

            
            if (info.isPresent()) {
                String infoTyp = info.get();
      
                switch (infoTyp) 
                {
                case "Product Name":
                	String name=null;
                	while (name == null || name.isEmpty()) {
                        TextInputDialog nameDialog = new TextInputDialog();
                        nameDialog.setTitle("Update Product Name");
                        nameDialog.setHeaderText(null);
                        nameDialog.setContentText("Enter new product name:");
                        
                        Optional<String> nameResult = nameDialog.showAndWait();
                        if (nameResult.isPresent()) {
                        	if (nameResult.isPresent()) {
                                name = nameResult.get();
                                if (name.isEmpty()) {
                                	showAlert("Invalid input", "Name cannot be empty. Please enter a valid string.");
                                }
                                else if (!isProductNameUnique(name)) {
                                    showAlert("Invalid Input", "Product with this name already exists. Please enter a unique name.");
                                }

                            } else {
                                return; // Return nothing if User canceled input
                            }
                        }

                	}
                    tv.setProductName(name);
                    break;
                    
                case "Screen Type":
                	String screenTyp = null;
                    while (screenTyp == null || screenTyp.isEmpty()) {
                        TextInputDialog screenTypDialog = new TextInputDialog();
                        screenTypDialog.setTitle("Update Screen Type");
                        screenTypDialog.setHeaderText(null);
                        screenTypDialog.setContentText("Enter new screen type:");
                        
                        Optional<String> screenTypResult = screenTypDialog.showAndWait();
                        if (screenTypResult.isPresent()) {
                            screenTyp = screenTypResult.get().trim();
                            if (screenTyp.isEmpty()) {
                                showAlert("Invalid input", "Screen type cannot be empty. Please enter a valid string.");
                            }
                        } else {
                            return; 
                        }
                    }
                    tv.setScrType(screenTyp);
                    break;
                    
                case "Resolution":
                	String resolution = null;
                     while (resolution == null || resolution.isEmpty()) {
                         TextInputDialog resolutionDialog = new TextInputDialog(tv.getResolution());
                         resolutionDialog.setTitle("Update Resolution");
                         resolutionDialog.setHeaderText(null);
                         resolutionDialog.setContentText("Enter new resolution:");
                         
                         Optional<String> resolutionResult = resolutionDialog.showAndWait();
                         if (resolutionResult.isPresent()) {
                             resolution = resolutionResult.get().trim();
                             if (resolution.isEmpty()) {
                                 showAlert("Invalid input", "Resolution cannot be empty. Please enter a valid string.");
                             }
                         } else {
                             return; 
                         }
                     }
                     tv.setResolution(resolution);
                     break;
                     
                case "Display Size":
                	
                	 while (true) {
                         TextInputDialog dispSizeDialog = new TextInputDialog();
                         dispSizeDialog.setTitle("Update Display Size");
                         dispSizeDialog.setHeaderText(null);
                         dispSizeDialog.setContentText("Enter new display size:");
                         Optional<String> dispSizeResult = dispSizeDialog.showAndWait();
                         if (dispSizeResult.isPresent()) {
                             try {
                                 double dispSize = Double.parseDouble(dispSizeResult.get());
                                 if (dispSize <= 0) {
                                     showAlert("Invalid input", "Display size must be a positive value.");
                                 } else {
                                     tv.setDisplaySize(dispSize);
                                     break; // Exit the loop after validation
                                 }
                             } catch (NumberFormatException e) {
                                 showAlert("Invalid input", "Invalid input for display size. Please enter a valid value.");
                             }
                         } else {
                             return;
                         }
                     }
                	 
                     break;
                     
                case "Price":
                	
                	 while (true) {
                         TextInputDialog priceDialog = new TextInputDialog();
                         priceDialog.setTitle("Update Price");
                         priceDialog.setHeaderText(null);
                         priceDialog.setContentText("Enter new price:");
                         Optional<String> priceResult = priceDialog.showAndWait();
                         if (priceResult.isPresent()) {
                             try {
                                 double price = Double.parseDouble(priceResult.get());
                                 if (price <= 0) {
                                     showAlert("Invalid input", "Price must be a positive value.");
                                 } else {
                                	 tv.setProductPrice(price);
                                     break; // Exit the loop after validation
                                 }
                             } catch (NumberFormatException e) {
                                 showAlert("Invalid input", "Invalid input for price. Please enter a valid value.");
                             }
                         } else {
                             return;
                         }
                     }
                	
                	break;
                   
                default:
                    break;
            }
                    
           }
        }
    }
    
    //Method to update if the user want to change the detail of blender product
    public void changeBlenderProduct(ArrayList<Product> products) {
    	//Get the list of blender item number 
        ArrayList<Integer> blenderitemNo = new ArrayList<Integer>();

        for (Product product : products) {
            if (product instanceof Blender) {
    	    	blenderitemNo.add(product.getItemNumber());

            }
        }
        // Display dialog with Blender item number
        ChoiceDialog<Integer> productDialog = new ChoiceDialog<Integer>();
        ObservableList<Integer> list = productDialog.getItems();
        list.addAll(blenderitemNo);
        productDialog.setTitle("Select Blender Product");
        productDialog.setHeaderText(null);
        productDialog.setContentText("Select an item number of blender product:");
        Optional<Integer> productResult = productDialog.showAndWait();

        Blender blender = null;
        if (productResult.isPresent()) {
        	Integer choiceItemNo = productResult.get();
            for (Product product : products) {
                if (product instanceof Blender && product.getItemNumber() == choiceItemNo) {
                    blender = (Blender) product;
                    break;
                }
            }

            if (blender != null) {
                ChoiceDialog<String> infoDialog = new ChoiceDialog<>("Product Name", "Container Material", "Function", "Rotations per minute (RPM)","Price");
                infoDialog.setTitle("Select Detail of Blender to Update");
                infoDialog.setHeaderText(null);
                infoDialog.setContentText("Select a detail of blender to update:");
                Optional<String> info = infoDialog.showAndWait();

                if (info.isPresent()) {
                	String infoTyp = info.get();
                
                    switch (infoTyp) {
                    	case "Product Name":
                    		String name=null;
                        	while (name == null || name.isEmpty()) {
                                TextInputDialog nameDialog = new TextInputDialog();
                                nameDialog.setTitle("Update Product Name");
                                nameDialog.setHeaderText(null);
                                nameDialog.setContentText("Enter new product name:");
                                
                                Optional<String> nameResult = nameDialog.showAndWait();
                                if (nameResult.isPresent()) {
                                	if (nameResult.isPresent()) {
                                        name = nameResult.get();
                                        if (name.isEmpty()) {
                                        	showAlert("Invalid input", "Name cannot be empty. Please enter a valid string.");
                                        }
                                        else if (!isProductNameUnique(name)) {
                                            showAlert("Invalid input", "Product with this name already exists. Please enter a unique name.");
                                        }

                                    } else {
                                        return; // Return nothing if User canceled input
                                    }
                                }

                        	}
                            blender.setProductName(name);
                            break;

                        case "Container Material":
                        	String containerMaterial = null;
                            while (containerMaterial == null || containerMaterial.isEmpty()) {
                                TextInputDialog containerMaterialDialog = new TextInputDialog(blender.getContainerMaterial());
                                containerMaterialDialog.setTitle("Update Container Material");
                                containerMaterialDialog.setHeaderText(null);
                                containerMaterialDialog.setContentText("Enter new container material:");
                                Optional<String> containerMaterialResult = containerMaterialDialog.showAndWait();
                                if (containerMaterialResult.isPresent()) {
                                    containerMaterial = containerMaterialResult.get().trim();
                                    if (containerMaterial.isEmpty()) {
                                        showAlert("Invalid input", "Container material cannot be empty. Please enter a valid string.");
                                    }
                                } else {
                                    return;
                                }
                            }
                            blender.setContainerMaterial(containerMaterial);
                            break;
                        case "Function":
                            String function = null;
                            while (function == null || function.isEmpty()) {
                                TextInputDialog functionDialog = new TextInputDialog(blender.getFunction());
                                functionDialog.setTitle("Update Function");
                                functionDialog.setHeaderText(null);
                                functionDialog.setContentText("Enter new function:");
                                Optional<String> functionResult = functionDialog.showAndWait();
                                if (functionResult.isPresent()) {
                                    function = functionResult.get().trim();
                                    if (function.isEmpty()) {
                                        showAlert("Invalid input", "Function cannot be empty. Please enter a valid string.");
                                    }
                                } else {
                                    return; 
                                }
                            }
                            blender.setFunction(function);
                            break;
                            
                        case "Rotations per minute (RPM)":
                        	
                            while (true) {
                                TextInputDialog rpmDialog = new TextInputDialog(String.valueOf(blender.getRPM()));
                                rpmDialog.setTitle("Update Rotations per minute (RPM)");
                                rpmDialog.setHeaderText(null);
                                rpmDialog.setContentText("Enter new rotations per minute (RPM):");
                                Optional<String> rpmResult = rpmDialog.showAndWait();
                                if (rpmResult.isPresent()) {
                                    try {
                                        int RPM = Integer.parseInt(rpmResult.get());
                                        if (RPM <= 0) {
                                            showAlert("Invalid input", "Rotations per minute (RPM) must be a positive value.");
                                        } else {
                                            blender.setRPM(RPM);
                                            break; // Exit the loop after validation
                                        }
                                    } catch (NumberFormatException e) {
                                        showAlert("Invalid input", "Invalid input for rotations per minute (RPM). Please enter a valid value.");
                                    }
                                } else {
                                    break; 
                                }
                            }
                            break;
                            
                        case "Price":
                        	 
                        	 while (true) {
                                 TextInputDialog priceDialog = new TextInputDialog();
                                 priceDialog.setTitle("Update Price");
                                 priceDialog.setHeaderText(null);
                                 priceDialog.setContentText("Enter new price:");
                                 Optional<String> priceResult = priceDialog.showAndWait();
                                 if (priceResult.isPresent()) {
                                     try {
                                         double price = Double.parseDouble(priceResult.get());
                                         if (price <= 0) {
                                             showAlert("Invalid input", "Price must be a positive value.");
                                         } else {
                                             blender.setProductPrice(price);
                                             break; // Exit the loop after validation
                                         }
                                     } catch (NumberFormatException e) {
                                         showAlert("Invalid input", "Invalid input for price. Please enter a valid value.");
                                     }
                                 } else {
                                     return;
                                 }
                             }
                        	break;

                            
                        default:
                            break;
                    }
                }
            }
        }
    }

  //Method to update if the user want to change the detail of air conditioner product
    public void changeAirConditionerProduct(ArrayList<Product> products) {
        //Get the list of air conditioner item number 
    	ArrayList<Integer> airConitemNo = new ArrayList<Integer>();

    	for (Product product : products) {
    	    if (product instanceof AirConditioner ) {
    	    	airConitemNo.add(product.getItemNumber());
    	    }
    	}
        // Display dialog with air conditioner item number
        ChoiceDialog<Integer> productDialog = new ChoiceDialog<Integer>();
        ObservableList<Integer> list = productDialog.getItems();
        list.addAll(airConitemNo);
        productDialog.setTitle("Select Air Conditioner Product");
        productDialog.setHeaderText(null);
        productDialog.setContentText("Select an item number of air conditioner product:");
        Optional<Integer> productResult = productDialog.showAndWait();

        AirConditioner aircon = null;
        if (productResult.isPresent()) {
            Integer choiceItemNo = productResult.get();
            for (Product product : products) {
            	if (product instanceof AirConditioner && product.getItemNumber() == choiceItemNo) {
                    aircon = (AirConditioner) product;
                    break;
                }
            }
        }
        
        if (aircon != null) {
            ChoiceDialog<String> infoDialog = new ChoiceDialog<>("Product Name","Compressor Type", "Heating Capacity", "Cooling Capacity","Price");
            infoDialog.setTitle("Select Detail of Air Conditioner to Update");
            infoDialog.setHeaderText(null);
            infoDialog.setContentText("Select a detail of air conditioner to update:");
            Optional<String> info = infoDialog.showAndWait();

            
            if (info.isPresent()) {
                String infoTyp = info.get();
      
                switch (infoTyp) 
                {
                case "Product Name":
                	String name=null;
                	while (name == null || name.isEmpty()) {
                        TextInputDialog nameDialog = new TextInputDialog();
                        nameDialog.setTitle("Update Product Name");
                        nameDialog.setHeaderText(null);
                        nameDialog.setContentText("Enter new product name:");
                        
                        Optional<String> nameResult = nameDialog.showAndWait();
                        if (nameResult.isPresent()) {
                        	if (nameResult.isPresent()) {
                                name = nameResult.get();
                                if (name.isEmpty()) {
                                	showAlert("Invalid input", "Name cannot be empty. Please enter a valid string.");
                                }
                                else if (!isProductNameUnique(name)) {
                                    showAlert("Invalid Input", "Product with this name already exists. Please enter a unique name.");
                                }

                            } else {
                                return; 
                            }
                        }

                	}
                	aircon.setProductName(name);
                    break;
                case "Compressor Type":
                    String compresTyp = null;
                    while (true) {
                        TextInputDialog compressorTypeDialog = new TextInputDialog(aircon.getcompressorType());
                        compressorTypeDialog.setHeaderText(null);
                        compressorTypeDialog.setContentText("Enter new compressor type:");
                        Optional<String> compressorTypeResult = compressorTypeDialog.showAndWait();
                        if (compressorTypeResult.isPresent()) {
                            compresTyp = compressorTypeResult.get().trim();
                            if (compresTyp.isEmpty()) {
                                showAlert("Invalid input", "Compressor type cannot be empty. Please enter a valid string.");
                            } else {
                                aircon.setcompressorType(compresTyp);
                                break; 
                            }
                        } else {
                            return; 
                        }
                    }
                    break;

                case "Heating Capacity":
                	
                    while (true) {
                        TextInputDialog heatCapacityDialog = new TextInputDialog(String.valueOf(aircon.heatingCapacity()));
                        heatCapacityDialog.setTitle("Update Heating Capacity");
                        heatCapacityDialog.setHeaderText(null);
                        heatCapacityDialog.setContentText("Enter new heating capacity (kW):");
                        Optional<String> heatCapacityResult = heatCapacityDialog.showAndWait();
                        if (heatCapacityResult.isPresent()) {
                            String result = heatCapacityResult.get();
                            try {
                                double heatCapacity = Double.parseDouble(result);
                                if (heatCapacity <= 0) {
                                    showAlert("Invalid input", "Heating capacity must be a positive value.");
                                } else {
                                    aircon.setheatingCapacity(heatCapacity);
                                    break; 
                                }
                            } catch (NumberFormatException e) {
                                showAlert("Invalid input", "Invalid input for heating capacity. Please enter a valid number.");
                            }
                        } else {
                            break; 
                        }
                    }
                    break;

                case "Cooling Capacity":
                    while (true) {
                        TextInputDialog coolCapacityDialog = new TextInputDialog(String.valueOf(aircon.getCoolingCapacity()));
                        coolCapacityDialog.setTitle("Update Cooling Capacity");
                        coolCapacityDialog.setHeaderText(null);
                        coolCapacityDialog.setContentText("Enter new cooling capacity (kW):");
                        Optional<String> coolCapacityResult = coolCapacityDialog.showAndWait();
                        if (coolCapacityResult.isPresent()) {
                            String input = coolCapacityResult.get();
                            try {
                                double coolCapacity = Double.parseDouble(input);
                                if (coolCapacity <= 0) {
                                    showAlert("Invalid input", "Cooling capacity must be a positive value.");
                                } else {
                                    aircon.setCoolingCapacity(coolCapacity);
                                    break; 
                                }
                            } catch (NumberFormatException e) {
                                showAlert("Invalid input", "Invalid input for cooling capacity. Please enter a valid number.");
                            }
                        } else {
                            break; 
                        }
                    }
                    break;
                    
                case "Price":
           
                	 while (true) {
                         TextInputDialog priceDialog = new TextInputDialog();
                         priceDialog.setTitle("Update Price");
                         priceDialog.setHeaderText(null);
                         priceDialog.setContentText("Enter new price:");
                         Optional<String> priceResult = priceDialog.showAndWait();
                         if (priceResult.isPresent()) {
                             try {
                                 double price = Double.parseDouble(priceResult.get());
                                 if (price <= 0) {
                                     showAlert("Invalid input", "Price must be a positive value.");
                                 } else {
                                	 aircon.setProductPrice(price);
                                     break; // Exit the loop after validation
                                 }
                             } catch (NumberFormatException e) {
                                 showAlert("Invalid input", "Invalid input for price. Please enter a valid value.");
                             }
                         } else {
                             return;
                         }
                     }
                	
                	break;
                   
                default:
                    break;
            }
                    
           }
        }
}

  //Method to update if the user want to change the detail of refrigerator product
    public void changeRefrigeratorProduct(ArrayList<Product> products) {
        //Get the list of  refrigerator item number 
    	ArrayList<Integer> rfgitemNo = new ArrayList<Integer>();

    	for (Product product : products) {
    	    if (product instanceof  Refrigerator) {
    	    	rfgitemNo.add(product.getItemNumber());
    	    }
    	}
        // Display dialog with  refrigerator item number
        ChoiceDialog<Integer> productDialog = new ChoiceDialog<Integer>();
        ObservableList<Integer> list = productDialog.getItems();
        list.addAll(rfgitemNo);
        productDialog.setTitle("Select Refrigerator Product");
        productDialog.setHeaderText(null);
        productDialog.setContentText("Select an item number of refrigerator product:");
        Optional<Integer> productResult = productDialog.showAndWait();

        Refrigerator  refrigerator = null;
        if (productResult.isPresent()) {
            Integer choiceItemNo = productResult.get();
            for (Product product : products) {
            	if (product instanceof Refrigerator && product.getItemNumber() == choiceItemNo) {
            		 refrigerator = (Refrigerator) product;
                    break;
                }
            }
        }
        
        if ( refrigerator != null) {
            ChoiceDialog<String> infoDialog = new ChoiceDialog<>("Product Name","Door Design", "Color", "Capacity","Price");
            infoDialog.setTitle("Select Detail of Refrigerator to Update");
            infoDialog.setHeaderText(null);
            infoDialog.setContentText("Select a detail of  refrigerator to update:");
            Optional<String> info = infoDialog.showAndWait();

            
            if (info.isPresent()) {
                String infoTyp = info.get();
      
                switch (infoTyp) 
                {
                case "Product Name":
                	String name=null;
                	while (name == null || name.isEmpty()) {
                        TextInputDialog nameDialog = new TextInputDialog();
                        nameDialog.setTitle("Update Product Name");
                        nameDialog.setHeaderText(null);
                        nameDialog.setContentText("Enter new product name:");
                        
                        Optional<String> nameResult = nameDialog.showAndWait();
                        if (nameResult.isPresent()) {
                        	if (nameResult.isPresent()) {
                                name = nameResult.get();
                                if (name.isEmpty()) {
                                	showAlert("Invalid input", "Name cannot be empty. Please enter a valid string.");
                                }
                                else if (!isProductNameUnique(name)) {
                                    showAlert("Invalid Input", "Product with this name already exists. Please enter a unique name.");
                                }

                            } else {
                                return; // Return nothing if User canceled input
                            }
                        }

                	}
                	 refrigerator.setProductName(name);
                    break;
                    
                case "Door Design":
                    String doorDesign = null;
                    while (doorDesign == null || doorDesign.isEmpty()) { 
                        TextInputDialog doorDesignDialog = new TextInputDialog(refrigerator.getDoorDesign());
                        doorDesignDialog.setTitle("Update Door Design");
                        doorDesignDialog.setHeaderText(null);
                        doorDesignDialog.setContentText("Enter new door design:");
                        Optional<String> doorDesignResult = doorDesignDialog.showAndWait();
                        if (doorDesignResult.isPresent()) {
                            doorDesign = doorDesignResult.get().trim();
                            if (doorDesign.isEmpty()) {
                                showAlert("Invalid input", "Door design cannot be empty. Please enter a valid string.");
                            }
                        } else {
                            return; 
                        }
                    }
                    refrigerator.setDoorDesign(doorDesign);
                    break;

                case "Color":
                    String color = null;
                    while (color == null || color.isEmpty()) { 
                        TextInputDialog colorDialog = new TextInputDialog(refrigerator.getColor());
                        colorDialog.setTitle("Update Color");
                        colorDialog.setHeaderText(null);
                        colorDialog.setContentText("Enter new color:");
                        Optional<String> colorResult = colorDialog.showAndWait();
                        if (colorResult.isPresent()) {
                            color = colorResult.get().trim();
                            if (color.isEmpty()) {
                                showAlert("Invalid input", "Color cannot be empty. Please enter a valid string.");
                            }
                        } else {
                            return; 
                        }
                    }
                    refrigerator.setColor(color);
                    break;

                case "Capacity":
                    while (true) {
                        TextInputDialog capacityDialog = new TextInputDialog(String.valueOf(refrigerator.getCapacity()));
                        capacityDialog.setTitle("Update Capacity");
                        capacityDialog.setHeaderText(null);
                        capacityDialog.setContentText("Enter new capacity (in litres):");
                        Optional<String> capacityResult = capacityDialog.showAndWait();
                        if (capacityResult.isPresent()) {
                        	String result = capacityResult.get();
                        	
                        	try {
                        		int capacity = Integer.parseInt(result);
                            if (capacity <= 0) {
                                showAlert("Invalid input", "Capacity must be a positive value.");
                            } 

                            else {
                           	 refrigerator.setCapacity(capacity);
                                break; // Exit the loop after validation
                            }
                        } catch (NumberFormatException e) {
                            showAlert("Invalid input", "Invalid input for capacity. Please enter a valid value.");
                         } 
                        }else {
                            return;
                        }
                    }
                    break;
                case "Price":
                	double price=0.0;
                	 while (true) {
                         TextInputDialog priceDialog = new TextInputDialog();
                         priceDialog.setTitle("Update Price");
                         priceDialog.setHeaderText(null);
                         priceDialog.setContentText("Enter new price:");
                         Optional<String> priceResult = priceDialog.showAndWait();
                         if (priceResult.isPresent()) {
                             try {
                                 price = Double.parseDouble(priceResult.get());
                                 if (price <= 0) {
                                     showAlert("Invalid input", "Price must be a positive value.");
                                 } else {
                                	 refrigerator.setProductPrice(price);
                                     break; // Exit the loop after validation
                                 }
                             } catch (NumberFormatException e) {
                                 showAlert("Invalid input", "Invalid input for price. Please enter a valid value.");
                             }
                         } else {
                             return;
                         }
                     }
                	
                	break;

                	default:
                         break;
                    }
                }
            }
        
    }

  //Method to update if the user want to change the detail of smart phone product
    public  void changeSmartphoneProduct(ArrayList<Product> products) {
        //Get the list of smart phone item number 
    	ArrayList<Integer> smartphitemNo = new ArrayList<Integer>();

    	for (Product product : products) {
    	    if (product instanceof Smartphone) {
    	    	smartphitemNo.add(product.getItemNumber());
    	    }
    	}
        // Display dialog with smart phone item number
        ChoiceDialog<Integer> productDialog = new ChoiceDialog<Integer>();
        ObservableList<Integer> list = productDialog.getItems();
        list.addAll(smartphitemNo);
        productDialog.setTitle("Select Smart Phone Product");
        productDialog.setHeaderText(null);
        productDialog.setContentText("Select an item number of smart phone product:");
        Optional<Integer> productResult = productDialog.showAndWait();

        Smartphone smartphone = null;
        if (productResult.isPresent()) {
            Integer choiceItemNo = productResult.get();
            for (Product product : products) {
            	if (product instanceof Smartphone && product.getItemNumber() == choiceItemNo) {
                    smartphone = (Smartphone) product;
                    break;
                }
            }
        }
        
        if (smartphone != null) {
            ChoiceDialog<String> infoDialog = new ChoiceDialog<>("Product Name","Display Type", "Chipset", "Main Camera","Price");
            infoDialog.setTitle("Select Detail of Smartphone to Update");
            infoDialog.setHeaderText(null);
            infoDialog.setContentText("Select a detail of smart phone to update:");
            Optional<String> info = infoDialog.showAndWait();

            
            if (info.isPresent()) {
                String infoTyp = info.get();
      
                switch (infoTyp) 
                {
                case "Product Name":
                	String name=null;
                	while (name == null || name.isEmpty()) {
                        TextInputDialog nameDialog = new TextInputDialog();
                        nameDialog.setTitle("Update Product Name");
                        nameDialog.setHeaderText(null);
                        nameDialog.setContentText("Enter new product name:");
                        
                        Optional<String> nameResult = nameDialog.showAndWait();
                        if (nameResult.isPresent()) {
                        	if (nameResult.isPresent()) {
                                name = nameResult.get();
                                if (name.isEmpty()) {
                                	showAlert("Invalid input", "Name cannot be empty. Please enter a valid string.");
                                }
                                else if (!isProductNameUnique(name)) {
                                    showAlert("Invalid Input", "Product with this name already exists. Please enter a unique name.");
                                }

                            } else {
                                return; 
                            }
                        }

                	}
                    smartphone.setProductName(name);
                    break;
                    

                    case "Display Type":
                        String displayTyp = null;
                        while (displayTyp == null || displayTyp.isEmpty()) { 
                            TextInputDialog displayTypDialog = new TextInputDialog(smartphone.getDisplaytyp());
                            displayTypDialog.setTitle("Update Display Type");
                            displayTypDialog.setHeaderText(null);
                            displayTypDialog.setContentText("Enter new display type:");
                            Optional<String> displayTypResult = displayTypDialog.showAndWait();
                            if (displayTypResult.isPresent()) {
                                displayTyp = displayTypResult.get().trim();
                                if (displayTyp.isEmpty()) {
                                    showAlert("Invalid input", "Display type cannot be empty. Please enter a valid string.");
                                }
                            } else {
                                return; 
                            }
                        }
                        smartphone.setDisplaytyp(displayTyp);
                        break;

                    case "Chipset":
                        String chipset = null;
                        while (chipset == null || chipset.isEmpty()) { 
                            TextInputDialog chipsetDialog = new TextInputDialog(smartphone.getChipset());
                            chipsetDialog.setTitle("Update Chipset");
                            chipsetDialog.setHeaderText(null);
                            chipsetDialog.setContentText("Enter new chipset:");
                            Optional<String> chipsetResult = chipsetDialog.showAndWait();
                            if (chipsetResult.isPresent()) {
                                chipset = chipsetResult.get().trim();
                                if (chipset.isEmpty()) {
                                    showAlert("Invalid input", "Chipset cannot be empty. Please enter a valid string.");
                                }
                            } else {
                                return; 
                            }
                        }
                        smartphone.setChipset(chipset);
                        break;

                    case "Main Camera":
                        while (true) {
                            TextInputDialog mainCameraDialog = new TextInputDialog(String.valueOf(smartphone.getMaincamera()));
                            mainCameraDialog.setTitle("Update Main Camera");
                            mainCameraDialog.setHeaderText(null);
                            mainCameraDialog.setContentText("Enter new main camera resolution :");
                            Optional<String> mainCameraResult = mainCameraDialog.showAndWait();
                            if (mainCameraResult.isPresent()) {
                                String result = mainCameraResult.get();
                                try {
                                    int mainCamera = Integer.parseInt(result);
                                    if (mainCamera <= 0) {
                                        showAlert("Invalid input", "Main camera must be a positive value.");
                                    } else {
                                        smartphone.setMaincamera(mainCamera);
                                        break; 
                                    }
                                } catch (NumberFormatException e) {
                                    showAlert("Invalid input", "Invalid input for main camera. Please enter a valid value.");
                                }
                            } else {
                                break; 
                            }
                        }
                        break;
                        
                    case "Price":
                    	
                    	 while (true) {
                             TextInputDialog priceDialog = new TextInputDialog();
                             priceDialog.setTitle("Update Price");
                             priceDialog.setHeaderText(null);
                             priceDialog.setContentText("Enter new price:");
                             Optional<String> priceResult = priceDialog.showAndWait();
                             if (priceResult.isPresent()) {
                                 try {
                                     double price = Double.parseDouble(priceResult.get());
                                     if (price <= 0) {
                                         showAlert("Invalid input", "Price must be a positive value.");
                                     } else {
                                    	 smartphone.setProductPrice(price);
                                         break; // Exit the loop after validation
                                     }
                                 } catch (NumberFormatException e) {
                                     showAlert("Invalid input", "Invalid input for price. Please enter a valid value.");
                                 }
                             } else {
                                 return;
                             }
                         }
                    	
                    	break;


                        default:
                            break;
                    }
                }
            }
        
    }

    public void viewProducts(ArrayList<Product> products) {
    	// Create a list for product types
        ArrayList<String> productlist= new ArrayList<String>();
        productlist.add("All"); // Add "All" option to list all the products
        productlist.add("AirConditioner");
        productlist.add("Refrigerator");
        productlist.add("TV");
        productlist.add("Blender");
        productlist.add("Smartphone");

       
        VBox viewProductVB = new VBox(10);
        viewProductVB.setPadding(new Insets(20));

        Label productTypeLabel = new Label("Please select the view options:");
        
        ComboBox<String> pTypeCB = new ComboBox<String>();
        pTypeCB.getItems().addAll(productlist);
        pTypeCB.setStyle("-fx-color:LIGHTBLUE");
       
        TextArea productInfoTxtArea = new TextArea();
        productInfoTxtArea.setEditable(false);
        productInfoTxtArea.setPrefWidth(300);
        productInfoTxtArea.setPrefHeight(200);

        pTypeCB.setOnAction(event -> {
            String selectedType = pTypeCB.getValue();
            ArrayList<Product> selectedProduct = new ArrayList<Product>();
            if (selectedType != null) {
                if (selectedType.equals("All")) {
                    selectedProduct.addAll(products); // Add all products if "All" is selected
                } else {
                    for (Product product : products) {//Add according to the type of product
                        if (product.getClass().getSimpleName().equals(selectedType)) {
                            selectedProduct.add(product);
                        }
                    }
                }

                // If the selected type of product does not contain any product display no product type
                if (selectedProduct.isEmpty()) {
                	productInfoTxtArea.setText("No Product Type!");
                } else {
                    // Sort the product according to item number in ascending order
                    selectedProduct.sort(Comparator.comparing(Product::getItemNumber));
                    
                    StringBuilder productInfo = new StringBuilder();
                    for (Product product : selectedProduct) {
                    	productInfo.append(product.toString()).append("\n\n");
                    }

                    productInfoTxtArea.setText(productInfo.toString());//Display the product information
                }
            }
        });

        viewProductVB .getChildren().addAll(productTypeLabel, pTypeCB, productInfoTxtArea);
        
        Text txtUpdate=new Text("Note: Please click the update button if you want to the detail of product.");
        HBox Updatehb=new HBox(20);
        Button btUpdate=new Button("Update");
        btUpdate.setStyle("-fx-background-color: DARKSEAGREEN; -fx-text-fill: white;");
        Updatehb.getChildren().addAll(txtUpdate,btUpdate);
        Updatehb.setAlignment(Pos.CENTER);
     
        btUpdate.setOnAction(e->{
            while (true) {
                ChoiceDialog<String> typdialog = new ChoiceDialog<String>("Air Conditioner", "Refrigerator", "TV", "Blender", "Smart Phone");
                typdialog.setTitle("Change Product Detail");
                typdialog.setHeaderText(null);
                typdialog.setContentText("Select a product type:");
                Optional<String> result = typdialog.showAndWait();

                if (result.isPresent()) {
                    String pName = result.get();
                    switch (pName) {
                        case "Air Conditioner":
                            changeAirConditionerProduct(products);
                            // Refresh the text area after update
                            pTypeCB.fireEvent(new ActionEvent()); // Trigger ComboBox action
                            break;
                        case "Refrigerator":
                            changeRefrigeratorProduct(products);
                        	pTypeCB.fireEvent(new ActionEvent()); 
                            break;
                        case "TV":
                            changeTVProduct(products); 
                            pTypeCB.fireEvent(new ActionEvent()); 
                            break;
                        case "Blender":
                            changeBlenderProduct(products);
                        	pTypeCB.fireEvent(new ActionEvent()); 
                            break;
                        case "Smart Phone":
                            changeSmartphoneProduct(products);
                        	pTypeCB.fireEvent(new ActionEvent()); 
                            break;
                        default:
                            break;
                    }
                } else {
                    break;
                }
            }
        });

        

        BorderPane bp=new BorderPane();
        bp.setTop(viewProductVB);
        bp.setBottom(Updatehb);
        BorderPane.setMargin(viewProductVB, new Insets(10));
        BorderPane.setMargin(Updatehb, new Insets(10));
        
        Stage viewProductStage = new Stage();//create a stage for view product
        Scene sc=new Scene(bp);
        viewProductStage.setScene(sc);
        viewProductStage.setTitle("View Products");
        viewProductStage.show();
    }
  
    public static void main(String[] args) {

        launch(args);
    }
    
    
}



