package com.weshopify.platform.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class WSO2UserPersonals implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    //firstName
	private String givenName;
	
	//LastName
	 private String familyName;
	
}
