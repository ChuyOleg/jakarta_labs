package kpi.lab_2.model;

public record User(Long id, String username, String password, Role role) {

  @Override
  public String toString() {
    return String.format("User[id=%s, username=%s, password=****, role=%s]", id, username, role);
  }

}
