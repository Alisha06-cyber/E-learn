package com.weshopify.platform.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
@Getter
//@Builder
@AllArgsConstructor
public class APIException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
	private String errorMsg;
	
	private int errorcode;
	
	
	
}
