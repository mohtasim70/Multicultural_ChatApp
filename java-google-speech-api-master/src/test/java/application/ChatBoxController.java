package application;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;



import Messages.ChatMessage;
import clientC.Client;
import clientC.CellRenderer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import serverS.User;



public class ChatBoxController {

    @FXML
    private TextField text;

    @FXML
    private Button send;

    @FXML
    private Button videoCall;

    @FXML
    private Button audioCall;

    @FXML
    private Button attachment;

    @FXML
    private TextField noOfclients;

    @FXML
    private ListView<User> list;

    @FXML
    private TextArea messageArea;

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private TextField languageBox;

    @FXML
    private Button choosePersonB;

    User targetUser;
    @FXML
    private Button langB;

    @FXML
    private Button saveB;

    @FXML
    private Button logoutB;

    @FXML
    private Button groupChatB;

    FileChooser fileChooser;
    private Desktop desktop = Desktop.getDesktop();



    @FXML
    void changeLang(ActionEvent event) {
    	ChatMessage x=new ChatMessage(ChatMessage.LANG, text.getText());
    	x.setLanguage(languageBox.getText());
    	System.out.println(" "+languageBox.getText(0, 2));
    	Client.setLanguage(languageBox.getText(0, 2));
    	Client.sendMessage(x);
    }

    @FXML
    void personal_chatAction(ActionEvent event) {
    	ObservableList<User> users;


    	User x=list.getSelectionModel().getSelectedItem();
    	targetUser=x;
    	System.out.println(x.getName());



    }

    @FXML
    void attachmentPressed(ActionEvent event) {
    	 FileChooser chooser = new FileChooser();
    	 File file = chooser.showOpenDialog(attachment.getParent().getScene().getWindow());
         if (file != null) {
        	 System.out.println("File:: "+file.getAbsolutePath());
        	 System.out.println("File szie:: "+file.getTotalSpace());
       //      openFile(file);
         }


if(targetUser==null)
{

//    	 Button savefile=new Button("File received");
//    	 anchorpane=new AnchorPane();
//    	 anchorpane.getChildren().add(savefile);


         ChatMessage msg=new ChatMessage();
         msg.setType(ChatMessage.FILE);
         msg.setFilename(file.getAbsolutePath());
         Client.sendFile(msg);
}
else
{
	ChatMessage msg=new ChatMessage();
    msg.setType(ChatMessage.FILESINGLE);
    msg.setTargetUser(targetUser);
    msg.setFilename(file.getAbsolutePath());
    Client.sendFile(msg);


}

    }

    @FXML
    void audioPressed(ActionEvent event) {

    }



    @FXML
    void videoPressed(ActionEvent event) {

    }


    @FXML
    void pressGroupChat(ActionEvent event) {
    	targetUser=null;
    }

    @FXML
    void pressLogout(ActionEvent event)
    {
    	ChatMessage x=new ChatMessage();
    	x.setType(ChatMessage.LOGOUT);

    	Client.sendMessage(x);
    }



    @FXML
    void sendPressed(ActionEvent event) {

    	if(targetUser==null)
    		{
    		ChatMessage x=new ChatMessage(ChatMessage.MESSAGE, text.getText());
    	//    	x.setLanguage(languageBox.getText(0, 1));
    	//    	Client.setLanguage(languageBox.getText(0, 1));
    	//x.setName(name);
    	Client.sendMessage(x);

    		}
    	else
    	{
    		ChatMessage x=new ChatMessage(ChatMessage.SINGLE, text.getText(),targetUser);
    	//	x.setName(Client.getUsername());
    	//	System.out.println("Static:  "+Client.getUsername());
    		Client.sendMessage(x);
    	}
    	text.clear();

    }
    public void addtoChat(ChatMessage msg)
    {
	   System.out.println("client add to chat " +msg.getName()+" " + msg.getMessage());
	   if(targetUser==null)
		   messageArea.appendText(msg.getName()+" :" + msg.getMessage());
	   else
		   messageArea.appendText("Personal Chat  "+msg.getName()+" :" + msg.getMessage());
    //	Message.appendText(msg.getMessage());
	   addToList(msg);


    }
    byte[] recFile=null;
    String filename;
    public void receiveFile(ChatMessage msg) throws IOException
    {
    	recFile=new byte[msg.getFilesize()];
    	System.out.println("In receive file:: ");
    	recFile=msg.file;
    	filename=msg.getFilename();
    	  messageArea.appendText(msg.getName()+":FILE RECEIVED:" + msg.getMessage() + ":"+msg.getFilename());
    	  messageArea.appendText("\n");

         //Set extension filter
//         FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//         fileChooser.getExtensionFilters().add(extFilter);

//    	  FileChooser fileChooser = new FileChooser();
//         //Show save file dialog
//         File file = fileChooser.showSaveDialog(attachment.getParent().getScene().getWindow());
//
//    	FileOutputStream fo=new FileOutputStream(msg.getFilename());
//
//
//    	fo.write(recFile);
//    	fo.close();
    }
    @FXML
    void savePressed(ActionEvent event) throws IOException
    {
    	System.out.println("Hellodod");
    	if(recFile!=null)
    	{
   	 FileChooser chooser = new FileChooser();
      //Show save file dialog
      File file = chooser.showSaveDialog(attachment.getParent().getScene().getWindow());
      System.out.println("In save FIle afterr");

      	if(file!=null)
      	{
    	FileOutputStream fo=new FileOutputStream(file,false);


    	fo.write(recFile);
    	fo.close();
    	 messageArea.appendText("File Saved Successfully");
    	 messageArea.appendText("\n");
    	recFile=null;
    	filename=null;
      	}

    	}
    	else{
    		 messageArea.appendText("File not received or null data");
    		 messageArea.appendText("\n");
    	}


    }




  public void addToList(ChatMessage msg)
  {
	   Platform.runLater(() -> {
          ObservableList<User> users = FXCollections.observableList(msg.getUsers());
          list.setItems(users);


          list.setCellFactory(new CellRenderer());
     //     noOfclients.setText("33");
          noOfclients.setText(String.valueOf(msg.getUsers().size()));
       //   setOnlineLabel(String.valueOf(msg.getUserlist().size()));
      });
  }

  public void logoutScene() {
      Platform.runLater(() -> {
          FXMLLoader fmxlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
          Parent window = null;
          try {
              window = (Pane) fmxlLoader.load();
          } catch (IOException e) {
              e.printStackTrace();
          }
          Stage stage = Main.getPrimaryStage();
          Scene scene = new Scene(window);
//          stage.setMaxWidth(350);
//          stage.setMaxHeight(420);
          stage.setResizable(true);
          stage.setScene(scene);
          stage.centerOnScreen();
      });
  }



}




