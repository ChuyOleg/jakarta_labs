package kpi.lab_3.service;

import kpi.lab_3.exception.AuthenticationException;
import kpi.lab_3.model.entity.User;

public interface UserService {

  User doAuthentication(String username, String password) throws AuthenticationException;

}
