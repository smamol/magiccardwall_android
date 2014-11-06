package nz.co.trademe.fedex5.magiccardwall.api;

import nz.co.trademe.fedex5.magiccardwall.api.request.LoginRequest;
import nz.co.trademe.fedex5.magiccardwall.api.request.MoveTaskRequest;
import nz.co.trademe.fedex5.magiccardwall.api.response.LoginResponse;

import nz.co.trademe.fedex5.magiccardwall.api.response.MoveTaskResponse;
import retrofit.Callback;

/**
 * Created by pakuhata on 6/11/14.
 */
interface JiraApi {

    void login(LoginRequest request, Callback<LoginResponse> callback);

    void moveTask(MoveTaskRequest request, Callback<MoveTaskResponse> callback);

}
