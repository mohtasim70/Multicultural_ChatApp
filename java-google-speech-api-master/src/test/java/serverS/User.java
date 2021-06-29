package serverS;
//package com.messages;

import java.io.Serializable;


public class User implements Serializable {
	//protected static final long serialVersionUID = 1112122200L;

	   String picture;
	    String status;
	    String name;
	    String password;
	    String language;
	    public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}
		User(){
	    	name="";
	    	password="";
	    	//language="en";
	    	//status=online;

	    }
    public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
