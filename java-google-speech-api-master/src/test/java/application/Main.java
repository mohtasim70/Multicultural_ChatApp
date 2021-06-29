package application;

import java.io.IOException;


import clientC.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.goxr3plus.speech.translator.GoogleTranslate;


public class Main extends Application {
    private static Stage primaryStage;
    private static BorderPane mainLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        showLoginView();
    }

    public static void showLoginView() throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
       loader.setLocation(Main.class.getResource("LoginScreen.fxml"));
     //  loader.setLocation(Main.class.getResource("ChatBox.fxml"));
        mainLayout = loader.load();


        Scene scene = new Scene(mainLayout);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

//    public static void showClientView(Client client,Controller con,Scene s) throws IOException {
////        FXMLLoader loader = new FXMLLoader();
////        loader.setLocation(Main.class.getResource("FrontView.fxml"));
////        mainLayout = loader.load();
////        con = loader.<Controller>getController();
////        Controller cvc = loader.getController(); // This did the "trick"
////     //   cvc.setClient(client);
////        cvc=con;
////
////        Scene scene = new Scene(mainLayout, 900, 600);
////        s=scene;
//////        primaryStage.setScene(scene);
//////        primaryStage.setResizable(true);
//////        primaryStage.show();
//    }

	public static Stage getPrimaryStage() {
		// TODO Auto-generated method stub
		return primaryStage;
	}
}


//public class Main extends Application {
//	@Override
//	public void start(Stage primaryStage) {
//		try {
//			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource( "LoginScreen.fxml" ) );;
////			Parent root2 =
////					FXMLLoader.load(
////					getClass().getResource( "LoginScreen.fxml" ) );
////			primaryStage.setTitle( "FXML Welcome!" );
//			//primaryStage.setScene( new Scene( root ) );
//			Scene scene = new Scene(root,400,400);
//			//scene.get
//		//	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//			primaryStage.setScene(scene);
//			primaryStage.show();
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//
//	public static void main(String[] args) {
//		launch(args);
//	}
//}
