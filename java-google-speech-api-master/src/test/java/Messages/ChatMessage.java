package Messages;

import java.io.*;
import java.util.ArrayList;

import serverS.Status;
import serverS.User;

//import com.messages.MessageType;
//import com.messages.Status;
//import com.messages.User;

public class ChatMessage implements Serializable {
	 protected static final long serialVersionUID = 1112122200L;
	     // The different types of message sent by the Client
	     // MESSAGE an ordinary message
	     // LOGOUT to disconnect from the Server
	    public static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2, SINGLE=3,REGISTER=4,LOGIN=5,CONNECTED=6,LANG=7,
	    		FILE=8,FILESINGLE=9;
	     private int type;
	     private User targetUser;
	     private String message;
	     private String name;
	     private String password;
	     private String language;


	     private String filename;
	     private final  int  filesize=1000000;
	    public byte[] file=new byte[filesize];


	     public String getFilename() {
			return filename;
		}
		public void setFilename(String filename) {
			this.filename = filename;
		}
		public int getFilesize() {
			return filesize;
		}

		public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}

		private int count;
	     private ArrayList<User> list;
	     private ArrayList<User> users;


	     public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}

	     private Status status;
	     // constructor
	     public ChatMessage(){
	    	 type=0;
	    //	 targetUser=" ";
	    	 message=" ";
	    	 name=" ";
	    	 password=" ";
	    	 count=0;
	    	 list=new ArrayList<User>();
	    	 users=new ArrayList<User>();
	     }
	     public ChatMessage(int type, String message) {
	         this.type = type;
	         this.message = message;
	         name=" ";
	    	 password=" ";
	    //	 targetUser=" ";
	    	 count=0;
	    	 list=new ArrayList<User>();
	    	 users=new ArrayList<User>();
	     }

	     public ChatMessage(int type, String message,User targetUser ) {
	         this.type = type;
	         this.message = message;
	         this.targetUser=targetUser;
	         name=" ";
	    	 password="  ";

	         count=0;
	    	 list=new ArrayList<User>();
	    	 users=new ArrayList<User>();
	     }
	     // getters
	     public int getType() {
	         return type;
	     }
	     public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
		public ArrayList<User> getList() {
			return list;
		}
		public void setList(ArrayList<User> list) {
			this.list = list;
		}
		public ArrayList<User> getUsers() {
			return users;
		}
		public void setUsers(ArrayList<User> users) {
			this.users = users;
		}
		public Status getStatus() {
			return status;
		}
		public void setStatus(Status status) {
			this.status = status;
		}
		public void setType(int type) {
			this.type = type;
		}
		public void setTargetUser(User targetUser) {
			this.targetUser = targetUser;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getMessage() {
	         return message;
	     }
	     public User getTargetUser() {
	         return targetUser;
	     }


}
