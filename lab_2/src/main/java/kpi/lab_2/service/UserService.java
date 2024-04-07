package kpi.lab_2.service;

import kpi.lab_2.exception.AuthenticationException;
import kpi.lab_2.model.User;

public interface UserService {

  User doAuthentication(String username, String password) throws AuthenticationException;

}
