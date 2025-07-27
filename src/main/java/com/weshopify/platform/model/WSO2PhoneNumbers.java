package com.weshopify.platform.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WSO2PhoneNumbers {
		private String type;
		
		private String value;
}
