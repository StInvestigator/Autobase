package org.example.autobase.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String userName;

    @Column(name = "encryted_password")
    private String encryptedPassword;

    @Column(name = "enabled")
    private boolean enabled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return enabled == appUser.enabled
                && Objects.equals(id, appUser.id)
                && Objects.equals(userName, appUser.userName)
                && Objects.equals(encryptedPassword, appUser.encryptedPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, encryptedPassword, enabled);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + id +
                ", userName='" + userName + '\'' +
                ", encryptedPassword='" + encryptedPassword + '\'' +
                ", enabled=" + enabled +
                '}';
    }

}


