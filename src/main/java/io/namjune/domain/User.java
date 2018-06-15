package io.namjune.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class User extends AbstractEntity {
  @Column(nullable = false, length = 20, unique = true)
  @JsonProperty
  private String userId;

  @JsonIgnore
  private String password;

  @JsonProperty
  private String name;

  @JsonProperty
  private String email;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean matchPassword(String newPassword) {
    if (newPassword == null) {
      return false;
    }

    return newPassword.equals(password);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void update(User updateUser) {
    this.password = updateUser.password;
    this.name = updateUser.name;
    this.email = updateUser.email;
  }

  public boolean matchId(Long newId) {
    if (newId == null) {
      return false;
    }

    return newId.equals(getId());
  }

  @Override
  public String toString() {
    return "User [" + super.toString() + ", userId=" + userId + ", password=" + password + "," + " name=" + name
        + ", email=" + email + "]";
  }
}
