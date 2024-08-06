import javafx.application.Application;



public class UserInfo {
	private String name;
	private String userID;
	
	public UserInfo() {
		this.name=null;
		this.userID="";
	}
	public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getUserId() {
        if (this.name.contains(" ")) {
            String[] parts = this.name.split(" ");
            if (parts.length >= 2) {
                this.userID = parts[0].substring(0, 1).toUpperCase() + parts[parts.length - 1];
            } else {
                this.userID = "guest";
            }
        } else {
            this.userID = "guest";
        }
        return this.userID;
    }
    

    public static void main(String[] args) {
   Application.launch(args);
   }
		
}

