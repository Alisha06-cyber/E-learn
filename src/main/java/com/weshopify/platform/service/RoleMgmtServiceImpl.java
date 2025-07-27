package com.weshopify.platform.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weshopify.platform.beans.RoleBean;
import com.weshopify.platform.outbound.RoleMgmtClient;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RoleMgmtServiceImpl implements RoleMgmtService {
    @Autowired
	private RoleMgmtClient roleMgmtClient;

@Override
public List<RoleBean> getAllRoles() {
	return roleMgmtClient.findAllRoles();
	
}

@Override
public List<RoleBean> createRole(RoleBean role) {
	
		return roleMgmtClient.createRole(role);


	   
	}

}
