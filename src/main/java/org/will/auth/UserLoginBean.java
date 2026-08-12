package org.will.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class UserLoginBean {

    private String username;

    private Integer id;

    private Date expiration;

    private String role;

    private String token;

    @Override
    public String toString() {
        return "{" +
                "username: \"" + username + "\"" +
                ", id: " + id +
                ", role: " + role +
                ", expiration: \"" + expiration + "\"" +
                ", token: \"" + token + "\"" +
                '}';
    }
}
