package broadbandrecomendation.payload;

import jakarta.validation.constraints.NotBlank;

public class AuthRequest {

    @NotBlank(message = "Username or email is required")
    private String identifier; // username or email

    @NotBlank(message = "Password is required")
    private String password;

    public AuthRequest() {}

    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

