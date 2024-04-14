package kpi.lab_3.model.entity;

public record User(Long id, String username, String password, Role role) {

  @Override
  public String toString() {
    return String.format("User[id=%s, username=%s, password=****, role=%s]", id, username, role);
  }

}
