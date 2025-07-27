package com.weshopify.platform.service;

import java.util.List;

import com.weshopify.platform.beans.UserBean;
import com.weshopify.platform.exceptions.APIException;

public interface UserMgmtService {
   public List<UserBean> getAllUsers();
   
   public List<UserBean> createUser(UserBean user);
}
