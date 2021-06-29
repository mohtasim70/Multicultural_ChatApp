package serverS;
import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

import com.goxr3plus.speech.translator.GoogleTranslate;

import Messages.ChatMessage;
import clientC.Client;
import databaseIntegration.DBStorage;

//import com.exception.DuplicateUsernameException;

//import com.messages.Message;

//import com.messages.Status;

//import application.ChatMessage;
public class Server extends DBStorage
{
	public static int uniqueId;

	// an ArrayList to keep the list of the Client
	private Vector<ClientThread> online_clients;
	private ArrayList<ClientThread> registered_clients;

	private ArrayList<ClientThread> all_clients;
	private ArrayList<String> usernames;
	private HashSet<String> test;
	private HashSet<ObjectOutputStream> writers = new HashSet<>();
	private ArrayList<String> passwords;
	private ArrayList<User> users;
	private ArrayList<User> online_users;
	ArrayList<InetAddress> addr;
	ArrayList<Integer> hosts;

	private boolean login;

	// if I am in a GUI
	//private ServerGUI sg;
	// to display time
	private SimpleDateFormat sdf;
	// the port number to listen for connection
	private int port;
	// the boolean that will be turned of to stop the server
	private boolean keepGoing;
	private boolean regOrnot;
	public Server(int port) {
		this.port=port;
		sdf = new SimpleDateFormat("HH:mm:ss");
		online_clients=new Vector<ClientThread>();
		registered_clients=new ArrayList<ClientThread>();
		all_clients=new ArrayList<ClientThread>();
		users=new ArrayList<User>();
		online_users=new ArrayList<User>();
		usernames=new ArrayList<String>();
		passwords=new ArrayList<String>();
		addr=new ArrayList<>();
	    hosts=new ArrayList<Integer>();

		test=new HashSet<String>();
	}

	public void buildDatabase() throws SQLException
	{
		Connection conn = null;
		try {
			conn = this.getConnection();
			System.out.println("connection name is :: "+conn.getClass().getName());
			System.out.println("Connected to database");
		} catch (SQLException e) {
			System.out.println("ERROR: Could not connect to the database");
			e.printStackTrace();
			return;
		}

		 Statement statement2 = conn.createStatement() ;
		    ResultSet resultset2 = statement2.executeQuery("SELECT * FROM students.userinfo") ;
		    User sng=new User();
		    while(resultset2.next())
		    {
		    	String name=resultset2.getString("USERNAME");
		    	String pass=resultset2.getString("PASSWORD");
		    	String stat=resultset2.getString("STATUS");

		    	sng.setName(name);
		    	sng.setPassword(pass);
		    	sng.setStatus(stat);

		    	users.add(sng);


		    	sng=new User();
		    }

		    resultset2.close();
		    statement2.close();

	}

	public void start() throws ClassNotFoundException{
		keepGoing = true;

		//create socket server and wait
		try{
			// the socket used by the server
			ServerSocket serverSocket = new ServerSocket(port);
			try {
				buildDatabase();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while(keepGoing)
			{

				display("Server waiting for Clients on port " + port + ".");
				Socket socket = serverSocket.accept();


				// if I was asked to stop
				if(!keepGoing)
					break;
				ClientThread t = new ClientThread(socket);  // make a thread of it

				if(regOrnot==false)
				{
					System.out.println(" Invalid Username");
			   	   t.writeMsg("Duplicate Username connection closes");
			   	 t.close();

				}
				socket = serverSocket.accept();
				   t = new ClientThread(socket);
//				if(login==true)
//				{
			//	if(t.yes)
					online_clients.add(t);

			//Scanner s=new Sc


						addr.add(socket.getInetAddress());
						hosts.add(socket.getPort());


//						Scanner myObj = new Scanner(System.in);  // Create a Scanner object
//					    System.out.println("1. No of connected clients"
//					    				+ " 2.  connected client details"
//					    				+ " 3. registered clients details"
//					    				+ " 4. client connection details ");

					   // int choice = myObj.nextInt();
//					    if(choice==1)
					    System.out.println("/////////////////////////////// ///////////////");
					    System.out.println("////////////1. No of connected clients///////////////");
//					    {
					    	System.out.println("Total no of connected clients= " + online_clients.size());


//					    }
//					    else if(choice==2)
//					    {
					    	 System.out.println("////////////2.  connected client details///////////////");
					    	for(int i=0;i<online_clients.size();i++)
					    	{
					    		System.out.println("Client username :  "+online_clients.get(i).user.getName()+" "+online_clients.get(i).user.getPassword());


					    	}

//					    }
//					    else if(choice==3)
//					    {
					   	 System.out.println("////////////3. registered clients details///////////////");
					    	for(int i=0;i<users.size();i++)
					    	{
					    		System.out.println("Registered Client username and password :  "+users.get(i).getName()+" "+users.get(i).getPassword());

					    	}

//					    }
//					    else if(choice==4)
//					    {
					   	 System.out.println("////////////4. client connection details ///////////////");
					    	for(int i=0;i<addr.size();i++)
					    	{
					    		System.out.println("Client username, IP and Port No:  "+online_clients.get(i).user.getName()+" "+addr.get(i)+ "  "+hosts.get(i));
					    	}


//					    }

						   	 System.out.println("/////////////////////////////// ///////////////");

//					System.out.println("/////////////////");
//
//					System.out.println("User details: " +t.user.getName());
//					System.out.println("Client IP" + socket.getInetAddress());
//					System.out.println(" Client Port " + socket.getPort());
//					System.out.println("/////////////////");
				t.start();
			}
			try {
				serverSocket.close();
				for(int i = 0; i < online_clients.size(); ++i) {
					ClientThread tc = online_clients.get(i);
					try {
						tc.sInput.close();
						tc.sOutput.close();
						tc.socket.close();
					}
					catch(IOException ioE) {
						// not much I can do
					}
				}
			}
			catch(Exception e) {
				display("Exception closing the server and clients: " + e);
			}
		}
		// something went bad
		catch (IOException e) {
			String msg = sdf.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
			display(msg);
		}




	}

	protected void stop() {
		keepGoing=false;
		//Connect to myself as client to exit statement
		//	 Socket socket = serverSocket.accept();

		// new Socket("localhost", port);

	}

	private void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
		//		        if(sg == null)
		System.out.println(time);
		//	        else
		//        	sg.appendEvent(time + "\n");

	}

	//To send message to online client in (friendlist not yet implemented)
	private synchronized void single_broadcast(ChatMessage message,User user) {
		String time = sdf.format(new Date());
		String messageLf =message.getMessage() + "\n";
		// display message on console or GUI
		//     if(sg == null)
		System.out.print(messageLf);
		//     else
		//       sg.appendRoom(messageLf);     // append in the room window
		// we loop in reverse order in case we would have to remove a Client
		// because it has disconnected
		for(int i = online_clients.size(); --i >= 0;) {
			//display("onlin cli"+online_clients.get(i).username+"  :targetuser:  "+user.getName());
			if(online_clients.get(i).user.getName().equals(user.getName()) || online_clients.get(i).user.getName().equals(message.getName()))
			{
				ClientThread ct = online_clients.get(i);
				// try to write to the Client if it fails remove it from the list
				message.setUsers(online_users);
				message.setCount(online_clients.size());
				message.setMessage(messageLf);
				message.setType(ChatMessage.MESSAGE);
				if(!ct.writeMsg(message)) {
					online_clients.remove(i);
					online_users.remove(i);
					display("Disconnected Client " + ct.username + " removed from list.");
				}
				//break;
			}
		}



	}
	private synchronized void broadcastFile (ChatMessage message)
	{
		for(int i = online_clients.size(); --i >= 0;)
		{

			ClientThread ct = online_clients.get(i);
			// try to write to the Client if it fails remove it from the list
			message.setUsers(online_users);
			message.setCount(online_clients.size());
			message.setMessage("Transferring File");
			System.out.println("Sending file");
		//	message.setType(ChatMessage.MESSAGE);
			if(!ct.writeMsg(message)) {
				online_clients.remove(i);
				online_users.remove(i);
				display("Disconnected Client " + ct.username + " removed from list.");
			}



		}


	}
	private synchronized void broadcastFileSingle(ChatMessage message,User user)
	{
		for(int i = online_clients.size(); --i >= 0;)
		{display("onlince clients  "+online_clients.get(i).user.getName());
			display("target User:: "+user.getName()+"  Messsage from user"+message.getName() );
			if(online_clients.get(i).user.getName().equals(user.getName()) || online_clients.get(i).user.getName().equals(message.getName()))
			{
				display("Inside SIngleFile ");

			ClientThread ct = online_clients.get(i);
			// try to write to the Client if it fails remove it from the list
			//message.setUsers(users);
			message.setUsers(online_users);
			message.setCount(online_clients.size());
			message.setMessage("Transferring Personal File");
			message.setType(ChatMessage.FILE);
		//	System.out.println("Sending file");
		//	message.setType(ChatMessage.MESSAGE);
			if(!ct.writeMsg(message)) {
				online_clients.remove(i);
				online_users.remove(i);
				display("Disconnected Client " + ct.username + " removed from list.");
			}

			}



		}


	}
	private synchronized void broadcast(ChatMessage message) throws IOException {
		String time = sdf.format(new Date());
		//String messageLf = time + " " + message.getMessage() + "\n";
		String messageLf=message.getMessage() + "\n";
		// display message on console or GUI
		//     if(sg == null)
		System.out.print(messageLf);
		//     else
		//       sg.appendRoom(messageLf);     // append in the room window
		// we loop in reverse order in case we would have to remove a Client
		// because it has disconnected
		for(int i = online_clients.size(); --i >= 0;) {
			//		        	 if(online_clients.get(i).username.equals(usern))
			//		         {
			ClientThread ct = online_clients.get(i);
			message.setUsers(online_users);
			//	message.set
			message.setCount(online_clients.size());

			String Tmsg=GoogleTranslate.translate(users.get(i).getLanguage(),messageLf)+ "\n";
			System.out.println("Language of  "+ users.get(i).name+"  "+users.get(i).getLanguage());
		//	String Tmsg=GoogleTranslate.translate("es",messageLf);


			message.setMessage(Tmsg);
			// try to write to the Client if it fails remove it from the list
			if(!ct.writeMsg(message)) {
				online_clients.remove(i);
				online_users.remove(i);
				display("Disconnected Client " + ct.username + " removed from list.");
			}
			//    break;
			//		       	}
		}



	}
	synchronized void remove(int id) {//Log out button
		// scan the array list until we found the Id
		for(int i = 0; i < online_clients.size(); ++i) {
			ClientThread ct = online_clients.get(i);
			// found it
			if(ct.id == id) {
				online_clients.remove(i);
				online_users.remove(i);
				return;
			}
		}
	}

	public boolean Login(ChatMessage firstMessage)//logging in using username and password
	{
		String pname=firstMessage.getName();
		String pass=firstMessage.getPassword();
	//	System.out.println(pass);
		try {
			if( register_User( pname, pass)==false)
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}

		return true;
	}
	public static void main(String[] args) throws ClassNotFoundException {
		int portn=1502;
		Server server = new Server(portn);
		server.start();



	}



	public class ClientThread extends Thread {//Receives client message
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;

		private DataOutputStream dOutput; 		//to write data of file on socket
		private FileInputStream fInput; // reading file byte by byte
		// my unique id (easier for deconnection)
		int id;
		// the Username of the Client
		User user;
		String username;
		String password;

		ArrayList<Client> friendlist;
		boolean yes;
		//String language;
		String client2_name;//personal chat client
		// the only type of message a will receive
		ChatMessage cm;
		// the date I connect
		String date;
		ChatMessage firstMessage;

		ClientThread(Socket socket) {
			id = ++uniqueId;
			this.socket = socket;
			/* Creating both Data Stream */
			System.out.println("Thread trying to create Object Input/Output Streams");
			try
			{
				// create output first
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput  = new ObjectInputStream(socket.getInputStream());

		//		 dOutput = new DataOutputStream(socket.getOutputStream());
		//			fInput = new FileInputStream("test.txt");
				// read the username
				regOrnot=true;
			//	username = (String) sInput.readObject();

				try {
					firstMessage = (ChatMessage) sInput.readObject();
				} catch (ClassNotFoundException | IOException e1) {
					// TODO Auto-generated catch block
					display(username + " Exception reading Streams: " );
					e1.printStackTrace();
				}
				switch(firstMessage.getType()) {

				case ChatMessage.REGISTER:

//					while(true)
//					{
						try {
							if(Registration(firstMessage))
							{
							//	keepGoing2=false;
								writers.add(sOutput);
								user=new User();

								user.setName(firstMessage.getName());
								user.setStatus("ONLINE");
								users.add(user);
								online_users.add(user);



								yes=true;
								regOrnot=true;
								break;
							}
							else{
								yes=false;
							}
//							else
//							{
//
//								writeMsg("...1:Username already exists");
//							//	String message = cm.getMessage();
//
//	//							username=message;
//								regOrnot=false;
//								//	username = (String) sInput.readObject();
//								//	display("Resended Username::  " + username);
//								break;
//							}
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						catch(DuplicateUsernameException d)
						{
						//	writeMsg("...1:Username already exists");
							String message = cm.getName();
							//
							username=message;
							System.out.println("Username already exists");
						}
				//	}

					break;
				case ChatMessage.LOGIN:
//					while(true)
//					{
						if(Login(firstMessage)==true)
						{
							writers.add(sOutput);
							user=new User();

							user.setName(firstMessage.getName());
							user.setStatus("ONLINE");
							int p=1;
							for(int i=0;i<users.size();i++)
							{
								if(users.get(i).getName().equals(firstMessage.getName()))
								{
									online_users.add(users.get(i));
									p=0;
									break;
								}
							}
							if(p==1)
								online_users.add(user);
						}
						else
						{


						}

						break;

						}
				//	}
					//break;


			}
			catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			}
		}
			// have to catch ClassNotFoundException

//			catch (ClassNotFoundException e) {
//			}
//			date = new Date().toString() + "\n";
//
//		}


		@Override
		public void run()
		{

			try {
				addTolist();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			boolean keepGoing=true;
			while(keepGoing || socket.isConnected()) {
				// read a String (which is an object)
				try {
					cm = (ChatMessage) sInput.readObject();
				}
				catch (IOException e) {
					display(username + " Exception reading Streams: " + e);
					break;
				}
				catch(ClassNotFoundException e2) {
					break;
				}

				// the messaage part of the ChatMessage


				// Switch on the type of message receive
				switch(cm.getType()) {
				case ChatMessage.CONNECTED:
					try {
						addTolist();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;

				case ChatMessage.MESSAGE:
					try {
						cm.setName(user.name);
						broadcast(cm);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case ChatMessage.FILE:
					cm.setName(user.name);
					broadcastFile(cm);
					break;
				case ChatMessage.FILESINGLE:
					cm.setName(user.name);
					broadcastFileSingle(cm,cm.getTargetUser());
					break;
				case ChatMessage.LANG:
					editUserLang(cm.getLanguage());
					break;
				case ChatMessage.SINGLE:
					cm.setName(user.name);
					display("Target User:: "+ cm.getTargetUser().name);
					single_broadcast(cm ,cm.getTargetUser());
					break;
				case ChatMessage.LOGOUT:
					display(username + " disconnected with a LOGOUT message.");
					cm.setName(user.name);
					online_users.remove(user);
					online_clients.remove(user);
					try {
						removeFromList();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					keepGoing = false;
					break;
				case ChatMessage.WHOISIN:
					writeMsg("List of the users connected at " + sdf.format(new Date()) + "\n");
					// scan al the users connected
					for(int i = 0; i < online_clients.size(); ++i) {
						ClientThread ct = online_clients.get(i);
						writeMsg((i+1) + ") " + ct.username + " since " + ct.date);
					}
					break;
				}

			}
			// remove myself from the arrayList containing the list of t
			// connected Client
//			try {
//				removeFromList();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			remove(id);

			close();

		}




		private void close() {
			// try to close the connection
			try {
				if(sOutput != null) sOutput.close();
			}
			catch(Exception e) {}
			try {
				if(sInput != null) sInput.close();
			}
			catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			}
			catch (Exception e) {}
//			try {
//				removeFromList();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}


		public boolean writeMsg(String msg) {
			// if Client is still connected send the message to it
			if(!socket.isConnected()) {
				close();
				return false;
			}
			// write the message to the stream
			try {
				sOutput.writeObject(msg);
			}
			// if an error occurs, do not abort just inform the user
			catch(IOException e) {
				//     display("Error sending message to " + username);
				System.out.println("Error sending message to " + username);
				System.out.println(e.toString());
				//  display(e.toString());
			}
			return true;
		}
		public void editUserLang(String lang)
		{

			for(int i=0;i<online_users.size();i++)
			{
			//	System.out.println("username "+ username);

				if(user.getName().equals(users.get(i).getName()))
				{
					users.get(i).language=lang;
				}
			}
		}

		public ChatMessage addTolist() throws IOException{
			ChatMessage msg;
			msg=new ChatMessage();
			msg.setUsers(online_users);
			msg.setType(6);
			msg.setName(getName());
			msg.setStatus(Status.ONLINE);
			writeMsgEvery(msg);
			return msg;

		}
		 private ChatMessage removeFromList() throws IOException {

			 ChatMessage msg = new ChatMessage();

	            msg.setMessage("has left the chat.");
	            msg.setType(ChatMessage.LOGOUT);
	          //  msg.setName("SERVER");
	            msg.setList(online_users);
	            writeMsg(msg);

	            return msg;
	        }

		public boolean writeMsg(ChatMessage msg) {
			// if Client is still connected send the message to it
			if(!socket.isConnected()) {
				close();
				return false;
			}
			// write the message to the stream
			try {
				sOutput.writeObject(msg);
			}
			// if an error occurs, do not abort just inform the user
			catch(IOException e) {
				//     display("Error sending message to " + username);
				System.out.println("Error sending message to " + username);
				System.out.println(e.toString());
				//  display(e.toString());
			}
			return true;
		}
		public boolean writeMsgEvery(ChatMessage msg) throws IOException {
			// if Client is still connected send the message to it
			  for (ObjectOutputStream writer : writers) {
	            //    msg.setUserlist(names);
	                msg.setUsers(online_users);
	              //  msg.setOnlineCount(names.size());
	                writer.writeObject(msg);
	                writer.reset();
	            }
			return true;
		}

		public boolean Registration(ChatMessage firstMessage) throws ClassNotFoundException, IOException,DuplicateUsernameException{


			String pname=firstMessage.getName();
			String pass=firstMessage.getPassword();
		//	System.out.println(pass);
			try {
				if( register_User( pname, pass)==false)
					return false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			//user.setName(pname);
//			for(int i=0;i<users.size();i++)
//			{
//				// System.out.println("users online " + online_clients.get(i).username );
//				if(users.get(i).name.equals(pname))
//				{
////					System.out.println("Username already exists ");
////					ClientThread ct = online_clients.get(i);
////					ct.writeMsg("...1:Username already exists");
//					 throw new DuplicateUsernameException(firstMessage.getName() + " Already exists");
//				//	return false;
//				}
//
//
//			}

			//password also

		//	usernames.add(firstMessage);
		//	display(user.getName() + " registered");

			ChatMessage msg;
			msg=new ChatMessage();
			msg.setUsers(users);
			msg.setType(6);
			msg.setName(getName());
			msg.setStatus(Status.ONLINE);


			writeMsgEvery(msg);

//			System.out.println(usernames.size());
//			for(int i=0;i<users.size();i++)
//			{
//				System.out.println("User arrayList  " + users.get(i).name);
//			}
//			//        client2_name=(String) sInput.readObject();
//			//           display(client2_name + " client you want to build a room with ");

			return true;
		}









	}






}