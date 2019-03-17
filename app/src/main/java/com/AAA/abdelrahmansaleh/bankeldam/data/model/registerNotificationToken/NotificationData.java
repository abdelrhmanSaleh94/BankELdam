
package com.AAA.abdelrahmansaleh.bankeldam.data.model.registerNotificationToken;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationData {

    @SerializedName("platform")
    @Expose
    private List<String> platform = null;

    public List<String> getPlatform() {
        return platform;
    }

    public void setPlatform(List<String> platform) {
        this.platform = platform;
    }

}
