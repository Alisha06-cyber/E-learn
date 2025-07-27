package com.weshopify.platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.weshopify.platform.outbound.RoleMgmtClient;

public class IamRoleApiTest extends WeshopifyUserManagementServiceApplicationTests {
    @Autowired
	private RoleMgmtClient roleClient;
    @Test
	public void testFindAllRoles() {
		roleClient.findAllRoles();
	}

}