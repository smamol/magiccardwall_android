package nz.co.trademe.fedex5.magiccardwall.api;

import retrofit.RestAdapter;

/**
 * Created by pakuhata on 6/11/14.
 */
public class ApiWrapper {

    private static ApiWrapper instance;
    private LoginApi loginApi;

    private ApiWrapper() {
        RestAdapter.Builder builder = new RestAdapter
                .Builder();

        builder.setEndpoint("TODO add endpoint");

        RestAdapter restAdapter = builder.build();
        loginApi = restAdapter.create(LoginApi.class);
    }

    static public ApiWrapper getSingleton() {
        if (instance == null) {
            instance = new ApiWrapper();
        }

        return instance;
    }

    public LoginApi getLoginApi() {
        return loginApi;
    }
}