package com.weshopify.platform.advices;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.weshopify.platform.exceptions.APIException;
import com.weshopify.platform.validations.UserMessage;
import com.weshopify.platform.validations.UserValidationMessages;

@RestControllerAdvice
public class UserMgmtExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(APIException.class)
	public ResponseEntity<Object> handleAPIError(APIException apiException) {
		UserMessage userException = UserMessage.builder().message(apiException.getErrorMsg())
				.statuscode(apiException.getErrorcode()).timeStamp(new Date()).build();
		return ResponseEntity.badRequest().body(userException);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<UserMessage> errorList = new ArrayList<>();
		ex.getBindingResult().getAllErrors().stream().forEach(error -> {

			UserMessage userException = UserMessage.builder().message(error.getDefaultMessage())
					.statuscode(status.value()).timeStamp(new Date()).build();
			errorList.add(userException);
		});
		UserValidationMessages validationMessages = UserValidationMessages.builder().messages(errorList).build();
		return ResponseEntity.badRequest().body(validationMessages);
	}
}
