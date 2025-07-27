package com.weshopify.platform.service;

import java.util.List;

import com.weshopify.platform.beans.RoleBean;

public interface RoleMgmtService {
 
	public List<RoleBean> getAllRoles();
	
	public List<RoleBean> createRole(RoleBean role);
	
}
