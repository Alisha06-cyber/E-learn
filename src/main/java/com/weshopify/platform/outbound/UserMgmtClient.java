package com.weshopify.platform.outbound;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.weshopify.platform.exceptions.APIException;
import com.weshopify.platform.model.WSO2User;
import com.weshopify.platform.utills.ApplicationsUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserMgmtClient {
	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ApplicationsUtil propsUtil;

	public List<WSO2User> findAllUsers() {
		try {
			String user_api_url = propsUtil.getIam_server_api_base_url() + propsUtil.getUser_api_context();
			log.info("user api is", user_api_url);
			List<WSO2User> WSO2usersList = null;
			HttpEntity<String> requestBody = propsUtil.prepareRequestsBody(null);
			ResponseEntity<Object> apiResponse = restTemplate.exchange(user_api_url, HttpMethod.GET, requestBody,
					Object.class);

			log.info("response code of the api is :\t" + apiResponse.getStatusCode().value());
			if (HttpStatus.OK.value() == apiResponse.getStatusCode().value()) {
				Object responseBody = apiResponse.getBody();
				WSO2usersList = propsUtil.parseUserResponse(responseBody);

			}else {
				throw new APIException(apiResponse.getBody().toString(), apiResponse.getStatusCode().value());
			}
			
			
			
			return Optional.ofNullable(WSO2usersList).get();

		} catch (Exception e) {
			throw new APIException(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		}

	}

	public List<WSO2User> createUser(WSO2User wso2User) {

		try {
			String user_api_url = propsUtil.getIam_server_api_base_url() + propsUtil.getUser_api_context();
			log.info("user api is", user_api_url);
			List<WSO2User> WSO2UsersList = null;
			String payload = null;
			try {
				payload = objectMapper.writeValueAsString(wso2User);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			HttpEntity<String> requestBody = propsUtil.prepareRequestsBody(payload);
			ResponseEntity<Object> apiresponse = restTemplate.exchange(user_api_url, HttpMethod.POST, requestBody,
					Object.class);
			log.info("reponse code of the role api is :\t" + apiresponse.getStatusCode().value());
			if (HttpStatus.CREATED.value() == apiresponse.getStatusCode().value()) {
				WSO2UsersList = findAllUsers();
			} else {
				throw new APIException(apiresponse.getBody().toString(), apiresponse.getStatusCode().value());
			}
			return Optional.ofNullable(WSO2UsersList).get();

		} catch (Exception e) {
			throw new APIException(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST.value());
		}

	}

}
