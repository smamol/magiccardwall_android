package nz.co.trademe.fedex5.magiccardwall.api;

import nz.co.trademe.fedex5.magiccardwall.api.request.LoginRequest;
import nz.co.trademe.fedex5.magiccardwall.api.request.MoveTaskRequest;
import nz.co.trademe.fedex5.magiccardwall.api.response.LoginResponse;

import nz.co.trademe.fedex5.magiccardwall.api.response.MoveTaskResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by pakuhata on 6/11/14.
 */
public interface JiraApi {

    @POST("/api/Login")
    void login(@Body LoginRequest request, Callback<LoginResponse> callback);

    void moveTask(MoveTaskRequest request, Callback<MoveTaskResponse> callback);

}
