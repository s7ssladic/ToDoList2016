package eu.execom.todolistgrouptwo.model.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SSladic on 12/5/2016.
 */

public class UserRegistrationDTO {

    @SerializedName("email")
    private String email;

    private String password;

    private String confirmPassword;

    public UserRegistrationDTO(String username, String password, String confirmPassword) {
        this.email = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                ", username='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
