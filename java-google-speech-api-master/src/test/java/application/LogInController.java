package application;

import java.io.IOException;

import clientC.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LogInController {
	int portNumber = 1502;
	String serverAddress = "localhost";
	 Client client;

    @FXML
    private TextField usernameT;

    @FXML
    private Button signinbutton;

    @FXML
    private Button signupbutton;

    @FXML
    private PasswordField passwordT;

    public static ChatBoxController con;

    private static LogInController instance;

    private static BorderPane mainLayout;

    public LogInController() {
        instance = this;
    }

    public static LogInController getInstance() {
        return instance;
    }

    @FXML
    void LogInPressed(ActionEvent event) throws IOException {
    	 FXMLLoader loader = new FXMLLoader();
         loader.setLocation(Main.class.getResource("ChatBox.fxml"));

         mainLayout = loader.load();
         con = loader.<ChatBoxController>getController();
//         Controller cvc = loader.getController(); // This did the "trick"
//         cvc.setClient(client);
//         cvc=con;
         client = new Client(serverAddress, portNumber, usernameT.getText(),passwordT.getText(),con);
    	 client.start();
    	 client.connect();

         Scene scene = new Scene(mainLayout);
//    		Parent frontView=FXMLLoader.load(getClass().getResource( "FrontView.fxml" ) );
//    		Scene chatView=new Scene(frontView);
    //
    		Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    		window.setTitle("Chat App");
    		window .setScene(scene);
    		window.show();
    }

    @FXML
    void signUpPressed(ActionEvent event) throws IOException {
//   	 Parent mainLayout = null;
//    	Scene s=new Scene(mainLayout, 900, 600);

    	// main.showClientView(client,con,s);

    	 FXMLLoader loader = new FXMLLoader();
         loader.setLocation(Main.class.getResource("ChatBox.fxml"));

         mainLayout = loader.load();
         con = loader.<ChatBoxController>getController();
//         Controller cvc = loader.getController(); // This did the "trick"
//         cvc.setClient(client);
//         cvc=con;
         client = new Client(serverAddress, portNumber, usernameT.getText(),passwordT.getText(),con);
    	 client.start();
    	 client.connect();

         Scene scene = new Scene(mainLayout);
//    		Parent frontView=FXMLLoader.load(getClass().getResource( "FrontView.fxml" ) );
//    		Scene chatView=new Scene(frontView);
    //
    		Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();
    		window.setTitle("Chat App");
    		window .setScene(scene);
    		window.show();

    }

}
