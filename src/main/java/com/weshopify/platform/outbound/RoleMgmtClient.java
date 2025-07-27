package com.weshopify.platform.outbound;

import java.lang.reflect.Type;

import java.net.http.HttpHeaders;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.weshopify.platform.beans.RoleBean;
import com.weshopify.platform.utills.ApplicationsUtil;

import io.micrometer.common.util.StringUtils;
import lombok.var;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleMgmtClient {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${iamserver.base-url}")
	private String iam_server_api_base_url;// https://localhost:9443/scim2

	@Value("${iamserver.role-api}")
	private String role_api_context;// Roles

	@Value("${iamserver.user-name}")
	private String iam_user_name;

	@Value("${iamserver.password}")
	private String iam_password;

//	@Value("${iamserver.role-api-schema}")
	//private String roleApiSchema;

	public List<RoleBean> findAllRoles() {
		List<RoleBean> resourceList = null;
		String role_api_url = iam_server_api_base_url + role_api_context;
		// https://localhost:9443/scim2/Roles
		log.info("constructed role api url is :\t" + role_api_url);

		HttpEntity<String> requestBody = prepareRequestsBody(null);

		ResponseEntity<Object> apiResponse = restTemplate.exchange(role_api_url, HttpMethod.GET, requestBody,
				Object.class);

		log.info("response code of the api is :\t" + apiResponse.getStatusCode().value());
		if (HttpStatus.OK.value() == apiResponse.getStatusCode().value()) {
			Object responseBody = apiResponse.getBody();
			resourceList = parseResponse(responseBody);

		}
		return Optional.ofNullable(resourceList).get();
	}

	public List<RoleBean> createRole(RoleBean roleBean) {
		List<RoleBean> resourceList = null;
		String role_api_url = iam_server_api_base_url + role_api_context;
		// https://localhost:9443/scim2/Roles
		log.info("constructed role api url is :\t" + role_api_url);

		// roleBean.setSchemas(new String[]););

		String rolePayload = null;
		try {
			rolePayload = objectMapper.writeValueAsString(roleBean);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// String rolePayload=objectMapper.convertValue(roleBean, String.class);
		HttpEntity<String> requestBody = prepareRequestsBody(rolePayload);

		ResponseEntity<Object> apiResponse = restTemplate.exchange(role_api_url, HttpMethod.POST, requestBody,
				Object.class);
		log.info("response code of the api is :\t" + apiResponse.getStatusCode().value());
		if (HttpStatus.CREATED.value() == apiResponse.getStatusCode().value()) {
			Object responseBody = apiResponse.getBody();

			resourceList = findAllRoles();
		}
		return Optional.ofNullable(resourceList).orElse(null);
	}

	private HttpEntity<String> prepareRequestsBody(String rolepayload) {
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

	private List<RoleBean> parseResponse(Object responseBody) {
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
}
