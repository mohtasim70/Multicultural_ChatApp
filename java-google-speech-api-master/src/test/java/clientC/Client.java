package clientC;
import java.net.*;

//import static com.messages.MessageType.CONNECTED;

import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;

import com.goxr3plus.speech.translator.GoogleTranslate;

//import com.client.chatwindow.ChatController;

import Messages.ChatMessage;
import application.ChatBoxController;
import application.clientController;

//import com.messages.Message;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import serverS.Status;

//import application.ChatMessage;
/*
006
 * The Client that can be run both as a console or a GUI
007
 */
public class Client  {

	// for I/O
	private ObjectInputStream sInput;       // to read from the socket
	private static ObjectOutputStream sOutput;     // to write on the socket

	private DataOutputStream dOutput; 		//to write data of file on socket
	private static FileInputStream fInput; // reading file byte by byte
	private Socket socket;
	// if I use a GUI or not
	//private ClientGUI cg;
	// the server, the port and the username
	private String server;
	private static String username;
	private String client2;
	private ChatMessage messg;
	private String password;
	private String msg;
	private static String language;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	private  String registered;
	private int port;
	boolean personal;


	 public ChatBoxController controller;//=new Controller();



	public Client(String server, int port, String username,String password) {
		this.server = server;
		this.port = port;
		Client.username = username;
		this.password=password;
		registered="";
		personal=false;
		// save if we are in GUI mode or not
		//  this.cg = cg;
	}
	public Client(String server, int port, String username,String password,ChatBoxController con) {
		this.server = server;
		this.port = port;
		Client.username = username;
		this.password=password;
		registered="";
		personal=false;
		controller=con;
		// save if we are in GUI mode or not
		//  this.cg = cg;
	}
	public static String getLanguage() {
		return language;
	}

	public static void setLanguage(String lang) {
		 language=lang;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public static String getUsername() {
		return username;
	}

	public  void setUsername(String username) {
		Client.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public boolean Registration(String username,String pass)
	{

		if(registered.equals("...1:Username already exists"))
		{
			System.out.println("Variable registered contains" + registered);
			System.out.println("Enter username again:: " );
			Scanner scan = new Scanner(System.in);
			username=scan.nextLine();
			// sendSMessage(username);
			System.out.println(username);
			ChatMessage x=new ChatMessage();
			x.setName(username);
			x.setPassword(pass);
			x.setType(4);
			x.setStatus(Status.ONLINE);
			sendMessage(x);
			return false;
		}
		else
		{

			return true;
		}


	}

	/*
046
	 * To start the dialog
047
	 */
	public  void connect() throws IOException {
        ChatMessage createMessage = new ChatMessage();
        createMessage.setName(username);
        createMessage.setPassword(password);
     //   createMessage.setType(CONNECTED);
    //    createMessage.setMessage(REGISTER);
        createMessage.setType(4);
    //    createMessage.setPicture(picture);
        sOutput.writeObject(createMessage);

    }
	public  void login() throws IOException {
        ChatMessage createMessage = new ChatMessage();
        createMessage.setName(username);
        createMessage.setPassword(password);
     //   createMessage.setType(CONNECTED);
    //    createMessage.setMessage(REGISTER);
        createMessage.setType(5);
    //    createMessage.setPicture(picture);
        sOutput.writeObject(createMessage);

    }

	public boolean start() throws IOException {
		// try to connect to the server
		try {
			socket = new Socket(server, port);
		}
		// if it failed not much I can so
		catch(Exception ec) {
			display("Error connectiong to server:" + ec);
			return false;
		}

		String msg = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);

		/* Creating both Data Stream */
		try
		{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}


		// creates the Thread to listen from the server
		ListenFromServer t1=new ListenFromServer();
		t1.start();

		return true;
	}




	/*
091
	 * To send a message to the console or the GUI
092
	 */
	private void display(String msg) {
		//        if(cg == null)
		System.out.println(msg);      // println in console mode
		//        else
		//            cg.append(msg + "\n");      // append to the ClientGUI JTextArea (or whatever)
	}

	public static void sendVoiceCall()
	{

	}


	public static void sendFile(ChatMessage msg)
	{
		try{
			msg.setName(username);

	//	 dOutput = new DataOutputStream(socket.getOutputStream());
		fInput = new FileInputStream(msg.getFilename());
		byte[] buffer = new byte[msg.getFilesize()];

		fInput.read(buffer, 0, buffer.length);
		msg.file=buffer;

		sOutput.writeObject(msg);

//		while (fInput.read(buffer) > 0) {
//			dOutput.write(buffer);
//		}

		fInput.close();
	//	dOutput.close();
		}
		catch(IOException e)
		{
			System.out.println("File not found");
		}
	}


	/*
101
	 * To send a message to the server
102
	 */

	public ChatMessage receiveMessage()
	{

		return messg;
	}


	public static void sendMessage(ChatMessage msg) {
		try {
			//	System.out.println(msg.getMessage());
			msg.setName(username);
			sOutput.writeObject(msg);

		}
		catch(IOException e) {
		//	display("Exception writing to server: " + e);
		}
	}
	void sendSMessage(String msg)
	{
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
			display("Exception writing to server: " + e);
		}

	}
	/*
113
	 * When something goes wrong
114
	 * Close the Input/Output streams and disconnect not much to do in the catch clause
115
	 */
	public void disconnect() {
		try {
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {} // not much else I can do
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {} // not much else I can do
		try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {} // not much else I can do
		// inform the GUI
		//        if(cg != null)
		//            cg.connectionFailed();
	}







	class ListenFromServer extends Thread {//Receives server message

		public void run() {
			  clientController.getInstance();
			while(true) {

					//controller=new Controller();
			//		String msg = (String) sInput.readObject();
					ChatMessage message=null;
					try {
						message=(ChatMessage) sInput.readObject();
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//Reading chat message and adding to controller HOW???
				//	setMsg(msg);
					if(message!=null)
					{
						System.out.println("In listen thread:: "+message.getName() +" "+message.getMessage());

						 switch (message.getType()) {
	                        case ChatMessage.MESSAGE:
//							try {
//								//String t2=GoogleTranslate.translate(language, message.getMessage());
//
//							//	message.setMessage(t2);
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
						//	message.setName(username);
	                            controller.addtoChat(message);
	                            break;
	                        case ChatMessage.FILE:
							try {
								controller.receiveFile(message);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
	                        case ChatMessage.LOGOUT:
	                        	controller.addToList(message);
	                        	controller.logoutScene();
	                        	break;
	                        case ChatMessage.CONNECTED:
	                        	controller.addToList(message);
	                        	break;

						 };

					}

		}
	}
	}
}