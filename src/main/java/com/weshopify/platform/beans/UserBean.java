package com.weshopify.platform.beans;
//hi
import java.io.Serializable;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

//this class use for data transefer
public class UserBean implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String id;
	@NotEmpty(message = "{firstName.notEmpty.message}")
	private String firstName;
	@NotEmpty(message = "{lastName.notEmpty.message}")
	private String lastName;
	@NotEmpty(message = "{email.notEmpty.message}")
	private String[] emails;
	@NotEmpty(message = "{password.notEmpty.message}")
	private String password;
	@NotEmpty(message = "{userName.notEmpty.message}")
	private String userId;
	@NotEmpty(message = "{mobile.notEmpty.message}")
    @Size(min = 10,message = "{mobile.number.length}")
    @Digits(integer = 10,fraction = 0,message ="{mobile.number.digits}") 
 String mobile;
	private boolean status;

}
