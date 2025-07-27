package com.weshopify.platform.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weshopify.platform.beans.RoleBean;
import com.weshopify.platform.beans.UserBean;
import com.weshopify.platform.beans.WeshopifyPlatformUserBean;
import com.weshopify.platform.outbound.UserMgmtClient;
import com.weshopify.platform.service.RoleMgmtService;
import com.weshopify.platform.service.UserMgmtService;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lenovo
 * @since {@summary}:create and manage the user resources
 */
@RestController
@Slf4j
public class UsersResource {
	@Autowired
	private RoleMgmtService roleMgmtService;
	@Autowired
	private UserMgmtService userMgmtService;
    @PostMapping("/users")
	public ResponseEntity<List<UserBean>> createUser(@Valid @RequestBody UserBean userBean){
		log.info("Weshopify Users Data is :"+userBean.toString());
		List<UserBean> userList=userMgmtService.createUser(userBean);
		return ResponseEntity.ok(userList);
	}
	
	
	@PostMapping(value = "/users/roles")
	public ResponseEntity<List<RoleBean>> createRole(@RequestBody RoleBean rolePayload) {
		List<RoleBean> rolesList = roleMgmtService.createRole(rolePayload);
		ResponseEntity<List<RoleBean>> rolesResponse = null;
		if (null != rolesList && rolesList.size() > 0) {
			rolesResponse = ResponseEntity.ok().body(rolesList);
		} else {
			rolesResponse = ResponseEntity.noContent().build();
		}
		return rolesResponse;

	}

	@GetMapping(value = "/users")
	public ResponseEntity<List<UserBean>> findAllUsers() {
		List<UserBean> userList=userMgmtService.getAllUsers();
		return ResponseEntity.ok(userList);
	}

	@PutMapping(value = "users")
	public ResponseEntity<UserBean> updateUser(@RequestBody WeshopifyPlatformUserBean userBean) {
		log.info("weshopify Users Data" + userBean.toString());
		return null;
	}

	@GetMapping(value = "/users/{userId}")
	public ResponseEntity<UserBean> findUserById(@PathVariable("userId") String userId) {

		return null;
	}

	@DeleteMapping(value = "/users/{userId}")
	public ResponseEntity<UserBean> deleteUserById(@PathVariable("userId") String userId) {

		return null;
	}

	@GetMapping(value = "/users/roles")
	public ResponseEntity<List<RoleBean>> findAllRoles() {
		List<RoleBean> rolesList = roleMgmtService.getAllRoles();
		ResponseEntity<List<RoleBean>> rolesResponse = null;
		if (null != rolesList && rolesList.size() > 0) {
			rolesResponse = ResponseEntity.ok().body(rolesList);
		} else {
			rolesResponse = ResponseEntity.noContent().build();
		}
		return rolesResponse;

	}
}
