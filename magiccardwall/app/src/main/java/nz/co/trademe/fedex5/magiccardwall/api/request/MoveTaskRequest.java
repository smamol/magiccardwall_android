package nz.co.trademe.fedex5.magiccardwall.api.request;

/**
 * Created by pakuhata on 6/11/14.
 */
public class MoveTaskRequest {
    private String qrcodeData;

    public String getQrcodeData() {
        return qrcodeData;
    }

    public void setQrcodeData(String qrcodeData) {
        this.qrcodeData = qrcodeData;
    }

    public MoveTaskRequest() {
    }

    public MoveTaskRequest(String qrcodeData) {
        this.qrcodeData = qrcodeData;
    }
}
