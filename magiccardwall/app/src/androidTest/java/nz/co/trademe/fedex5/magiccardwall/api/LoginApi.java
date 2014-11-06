package nz.co.trademe.fedex5.magiccardwall.api;

import nz.co.trademe.fedex5.magiccardwall.api.request.LoginRequest;
import nz.co.trademe.fedex5.magiccardwall.api.response.LoginResponse;

import retrofit.Callback;

/**
 * Created by pakuhata on 6/11/14.
 */
interface LoginApi {

    void login(LoginRequest request, Callback<LoginResponse> callback);

}
