package application;

import java.awt.Button;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

@SuppressWarnings("unused")
public class LogInMain extends Application{

	private Stage primaryStage;

	public void start(Stage primaryStage){
		try {

			this.primaryStage=primaryStage;
			this.primaryStage.setTitle("SignIn Page");

			initStage();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void initStage(){
		try{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(LogInMain.class.getResource("LoginScreen.fxml"));

		BorderPane logIn = (BorderPane) loader.load();
		Scene scene = new Scene(logIn);

		LogInController controller = loader.getController();
		//controller.setMainApp(this);

		primaryStage.setScene(scene);
		primaryStage.show();

		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		launch(args);
	}
	public Stage getPrimaryStage(){

		return primaryStage;
	}
}
