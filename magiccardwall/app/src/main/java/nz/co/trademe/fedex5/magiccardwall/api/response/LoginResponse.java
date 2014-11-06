package nz.co.trademe.fedex5.magiccardwall.api.response;

/**
 * Created by pakuhata on 6/11/14.
 */
public class LoginResponse {
    private boolean success;
    private String errorMessage;
    private String token;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
