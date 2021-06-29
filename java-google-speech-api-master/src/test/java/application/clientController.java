package application;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import clientC.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class clientController implements Initializable {
	int portNumber = 1502;
	String serverAddress = "localhost";
	 Client client;
	 private Main main;
    @FXML
    private PasswordField passwordT;

    @FXML
    private TextField usernameT;

    @FXML
    private Button signInbutton;

  //  public static Controller con;
    public static ChatBoxController con;

    @FXML
    private Button registerB;

    private static clientController instance;

    private static BorderPane mainLayout;

    public clientController() {
        instance = this;
    }

    public static clientController getInstance() {
        return instance;
    }

    @FXML
    void logout()
    {
//    	clirngt obj;
//    	obj.logoyu();
    }
    @FXML
    void signInAction(ActionEvent event) throws IOException
 	{
	 System.out.println("hello");


//	 Parent mainLayout = null;
//	Scene s=new Scene(mainLayout, 900, 600);

	// main.showClientView(client,con,s);

	 FXMLLoader loader = new FXMLLoader();
     loader.setLocation(Main.class.getResource("ChatBox.fxml"));
     mainLayout = loader.load();
     con = loader.<ChatBoxController>getController();
//     Controller cvc = loader.getController(); // This did the "trick"
//     cvc.setClient(client);
//     cvc=con;
     client = new Client(serverAddress, portNumber, usernameT.getText(),passwordT.getText(),con);
	 client.start();
	 client.connect();

     Scene scene = new Scene(mainLayout, 900, 600);
//		Parent frontView=FXMLLoader.load(getClass().getResource( "FrontView.fxml" ) );
//		Scene chatView=new Scene(frontView);
//
		Stage window=(Stage)((Node)event.getSource()).getScene().getWindow();

		window .setScene(scene);
		window.show();


 	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}




}
