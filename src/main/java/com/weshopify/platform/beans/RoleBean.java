package com.weshopify.platform.beans;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class RoleBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String displayName;
	
	private List<String> permissions;

    private String[] schemas;
	
}
