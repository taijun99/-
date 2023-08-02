package com.example.soleproject.config.auth.dto;

import com.example.soleproject.domain.user.Users;
import lombok.Getter;

@Getter
public class SessionUser {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Users users) {
        this.name = users.getName();
        this.email = users.getEmail();
        this.picture = users.getPicture();
    }
}
