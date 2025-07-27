package com.weshopify.platform.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.weshopify.platform.beans.UserBean;
import com.weshopify.platform.exceptions.APIException;
import com.weshopify.platform.model.WSO2PhoneNumbers;
import com.weshopify.platform.model.WSO2User;
import com.weshopify.platform.model.WSO2UserPersonals;
import com.weshopify.platform.outbound.UserMgmtClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserMgmtServiceImpl implements UserMgmtService {
	@Autowired
	private UserMgmtClient userMgmtClient;

	@Override
	public List<UserBean> getAllUsers() {
		List<WSO2User> wso2UsersList = userMgmtClient.findAllUsers();

		if (!org.springframework.util.CollectionUtils.isEmpty(wso2UsersList)) {

			List<UserBean> usersList = new ArrayList<>();
			wso2UsersList.stream().forEach(wso2User -> {
				usersList.add(mapWSO2UserToUserBean(wso2User));
			});

			return usersList;
		} else {
			throw new RuntimeException("No Users Found");
		}
	}

	/**
	 * To convert WSO2 User DTo to UserBean
	 * 
	 * @param wso2User
	 * @return
	 */

	private UserBean mapWSO2UserToUserBean(WSO2User wso2User) {

		/*
		 * UserBean userBean = UserBean.builder()
		 * .id(wso2User.getId()).firstName(wso2User.getName().getGivenName())
		 * .lastName(wso2User.getName().getFamilyName()).emails(wso2User.getEmails())
		 * .userId(wso2User.getUserName()).build(); return userBean;
		 */

		UserBean userBean = new UserBean();
		userBean.setId(wso2User.getId());
		if (wso2User.getName() != null) {
			userBean.setFirstName(wso2User.getName().getGivenName());
			userBean.setLastName(wso2User.getName().getFamilyName());
		}
		userBean.setEmails(wso2User.getEmails());
		userBean.setUserId(wso2User.getUserName());

		return userBean;
	}

	/**
	 * TO CONVERT the wso2 user model to User Bean
	 * 
	 * @param userBean
	 * @return
	 */
	private WSO2User mapUserBeanTOWSO2User(UserBean userBean) {
		WSO2UserPersonals personals = WSO2UserPersonals.builder().familyName(userBean.getLastName())
				.givenName(userBean.getFirstName()).build();
		WSO2PhoneNumbers userContactNum = WSO2PhoneNumbers.builder().type("work").value(userBean.getMobile()).build();

		WSO2User wso2UserModel = WSO2User.builder().emails(userBean.getEmails()).name(personals)
				.password(userBean.getPassword()).phoneNumbers(Arrays.asList(userContactNum)).schemas(new String[] {})
				.userName(userBean.getUserId()).build();
		return wso2UserModel;
	}

	@Override
	public List<UserBean> createUser(UserBean user) {
		;
		List<WSO2User> usersList = userMgmtClient.createUser(mapUserBeanTOWSO2User(user));
		if (!org.springframework.util.CollectionUtils.isEmpty(usersList)) {
			List<UserBean> userBeanList = new ArrayList<>();
			usersList.parallelStream().forEach(wso2User -> {
				userBeanList.add(mapWSO2UserToUserBean(wso2User));
			});
			return userBeanList;
		} else {
			throw new APIException("no users found",HttpStatus.NOT_FOUND.value());
		}

	}

}