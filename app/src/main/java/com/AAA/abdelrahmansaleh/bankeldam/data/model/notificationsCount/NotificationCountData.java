
package com.AAA.abdelrahmansaleh.bankeldam.data.model.notificationsCount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationCountData {

    @SerializedName("notifications_count")
    @Expose
    private Integer notificationsCount;

    public Integer getNotificationsCount() {
        return notificationsCount;
    }

    public void setNotificationsCount(Integer notificationsCount) {
        this.notificationsCount = notificationsCount;
    }

}
