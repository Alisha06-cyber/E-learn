package com.weshopify.platform.beans;


import java.io.File;
import java.io.Serializable;



import lombok.Builder;
import lombok.Data;
/**
 * @author Lenovo
 * @since:24-12-23
 * @apiNote:weshopifyPlatform
 * {@summary}:this beans is used the users data and will save to the ws02 server
 */
@Data
@Builder
public class WeshopifyPlatformUserBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	private int userId;
	private String email;
	private String firstName;
	private String lastName;
	
	private String password;
	private String role;
	private boolean status;
	private File photos;
}
