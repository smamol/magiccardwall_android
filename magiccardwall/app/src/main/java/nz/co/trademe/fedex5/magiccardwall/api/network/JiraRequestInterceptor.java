package nz.co.trademe.fedex5.magiccardwall.api.network;

import android.content.SharedPreferences;

import retrofit.RequestInterceptor;

/**
 * Created by pakuhata on 6/11/14.
 */
public class JiraRequestInterceptor implements RequestInterceptor {

    private String token;

    private static JiraRequestInterceptor singleton;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static JiraRequestInterceptor getSingleton() {
        if (singleton == null) {
            singleton = new JiraRequestInterceptor();
        }

        return singleton;
    }

    private JiraRequestInterceptor() {
    }

    @Override
    public void intercept(RequestFacade request) {
        if (token != null) {
            request.addHeader("Cookie", token);
        }
    }
}
