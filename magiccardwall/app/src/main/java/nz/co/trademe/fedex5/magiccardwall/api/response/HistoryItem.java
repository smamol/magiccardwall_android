package nz.co.trademe.fedex5.magiccardwall.api.response;

import android.graphics.Color;

import nz.co.trademe.fedex5.magiccardwall.R;

/**
 * Created by pakuhata on 7/11/14.
 */
public class HistoryItem {

    private String id;
    private String title;
    private String timestamp;
    private String status;
    private String avatarUrl;
    private String username;
    private String type;

    public String getType() {
        return type;
    }

    public int getColorForType() {
        if (type != null && type.length() > 0) {
            if (type.equalsIgnoreCase("dev")) {
                return R.color.color_dev;
            }
            else if (type.equalsIgnoreCase("test")) {
                return R.color.color_test;
            }
            else {
                return R.color.color_design;
            }
        }
        else {
            return Color.BLACK;
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
