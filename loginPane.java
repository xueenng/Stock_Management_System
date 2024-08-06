import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class loginPane extends Stage {

    private UserInfo userInfo = new UserInfo();
    private Scene scene;
    private TextField nameField = new TextField();

    public loginPane() {
        setTitle("Login");
        setResizable(false); // Not allowed to resize

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(25));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label nameLabel = new Label("Name:");
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameLabel.setTextFill(Color.DARKBLUE);

        nameField.setPromptText("Enter your name");
        nameField.setPrefWidth(200); // Set the preferred width to 200 pixels

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        loginButton.setOnAction(e -> {
            String name = getName(nameField);
            if (name != null) {
                userInfo.setName(name);
                userInfo.getUserId();
                System.out.println("Name: " + userInfo.getName());
                System.out.println("User ID: " + userInfo.getUserId());
                close();
            }
        });

        gridPane.add(nameLabel, 1, 1);
        gridPane.add(nameField, 2, 1);
        gridPane.add(loginButton, 2, 4);

        VBox root = new VBox(20);
        root.getChildren().addAll(gridPane);
        root.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20;");
        scene = new Scene(root, 400, 250);
        setScene(scene);
    }

    public void reset() {
        userInfo = new UserInfo(); // Reset user information
        nameField.setText(""); // Clear the name field
        // Add any additional fields to reset here
    }

    private String getName(TextField nameField) {
        String name = nameField.getText().trim(); // Remove leading and trailing spaces
        if (!name.matches("[a-zA-Z ]+")) {
            // If not letters or spaces entered, then prompt error message
            showAlert("Error", "Name must contain letters and spaces only.");
            return null;
        }
        return name;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public String getUserID() {
        return userInfo.getUserId();
    }

    public String getUserName() {
        return userInfo.getName();
    }
}
