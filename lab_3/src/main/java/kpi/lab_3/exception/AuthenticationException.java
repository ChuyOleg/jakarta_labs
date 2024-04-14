package kpi.lab_3.exception;

import jakarta.ejb.ApplicationException;

@ApplicationException
public class AuthenticationException extends RuntimeException {

  public AuthenticationException(String message) {
    super(message);
  }

  public AuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }
}
