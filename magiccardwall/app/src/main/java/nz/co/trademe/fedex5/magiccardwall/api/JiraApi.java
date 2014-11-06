package nz.co.trademe.fedex5.magiccardwall.api;

import nz.co.trademe.fedex5.magiccardwall.api.request.LoginRequest;
import nz.co.trademe.fedex5.magiccardwall.api.response.LoginResponse;
import retrofit.Callback;
import retrofit.ResponseCallback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by pakuhata on 6/11/14.
 */
public interface JiraApi {

    @POST("/api/Login")
    void login(@Body LoginRequest request, Callback<LoginResponse> callback);

    @POST("/api/Status")
    void status(@Query("issueId") String issueId, @Query("undo") boolean undo, ResponseCallback callback);

}
