package com.brightslearning.webblog.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class RegisterUser {

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "UserName is allowed to contain letters, digits and underscores")
    private String username;
    @Size(min = 8, message = "Password length has to be at least 8 characters long")
    private String password1;
    private String password2;

    public RegisterUser(String username, String password1, String password2) {
        this.username = username;
        this.password1 = password1;
        this.password2 = password2;
    }
}
