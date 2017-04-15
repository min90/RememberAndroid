package dk.iot.remember;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesper on 15/04/2017.
 */

class User {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("auth_token")
    private String authToken;


    User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", authToken='" + authToken + '\'' +
                '}';
    }
}
