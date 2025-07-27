package com.weshopify.platform.utills;

import java.lang.reflect.Type;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weshopify.platform.beans.RoleBean;
import com.weshopify.platform.outbound.RoleMgmtClient;

import io.micrometer.common.util.StringUtils;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.weshopify.platform.model.WSO2User;

@Component
@Data
//@Builder
@Slf4j
public class ApplicationsUtil {

	@Value("${iamserver.base-url}")
	private String iam_server_api_base_url;// https://localhost:9443/scim2

	@Value("${iamserver.role-api}")
	private String role_api_context;// Roles
	
	@Value("${iamserver.user-api}")		
	private String user_api_context; //Users

	@Value("${iamserver.user-name}")
	private String iam_user_name;

	@Value("${iamserver.password}")
	private String iam_password;

@Value("${iamserver.role-api-schema}")
	private String roleApiSchema;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	
	
	public HttpEntity<String> prepareRequestsBody(String rolepayload) {
		HttpEntity<String> requestBody = null;
		String adminCreds = iam_user_name + ":" + iam_password;
		log.info("admin creds arre \t" + adminCreds);

		String encodedAdminCreds = Base64.getEncoder().encodeToString(adminCreds.getBytes());
		log.info("admin creds encoded are:\t" + encodedAdminCreds);

		org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
		headers.add("Authorization", "Basic " + encodedAdminCreds);

		if (StringUtils.isEmpty(rolepayload) || StringUtils.isBlank(rolepayload)) {
			requestBody = new HttpEntity<>(headers);
		} else {
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			requestBody = new HttpEntity<>(rolepayload, headers);

		}
		return requestBody;

	}
	private List<RoleBean> parseRoleResponse(Object responseBody) {
		List<RoleBean> resourceList = null;
		try {
			String response = objectMapper.writeValueAsString(responseBody);
			log.info("the response Body is:\t" + response);
			JSONObject jsonResponseObject = new JSONObject(response);

			JSONArray jsonArray = (JSONArray) Optional.ofNullable(jsonResponseObject)
					.filter(condition -> jsonResponseObject.has("Resources")).get().get("Resources");
			log.info("Resources are:\t" + jsonArray + toString());
			Gson gson = new Gson();
			Type type = new TypeToken<List<RoleBean>>() {
			}.getType();
			resourceList = gson.fromJson(jsonArray.toString(), type);
			log.info("Resources list are:\t" + resourceList.size());

		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}

		return Optional.ofNullable(resourceList).get();

	}
	public List<WSO2User> parseUserResponse(Object responseBody) {
		List<WSO2User> resourceList = null;
		try {
			String response = objectMapper.writeValueAsString(responseBody);
			log.info("the response Body is:\t" + response);
			JSONObject jsonResponseObject = new JSONObject(response);

			JSONArray jsonArray = (JSONArray) Optional.ofNullable(jsonResponseObject)
					.filter(condition -> jsonResponseObject.has("Resources")).get().get("Resources");
			log.info("Resources are:\t" + jsonArray + toString());
			Gson gson = new Gson();
			Type type = new TypeToken<List<WSO2User>>() {
			}.getType();
			resourceList = gson.fromJson(jsonArray.toString(), type);
			log.info("Resources list are:\t" + resourceList.size());

		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
		}

		return Optional.ofNullable(resourceList).get();

	}
}
