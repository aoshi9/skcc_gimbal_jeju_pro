package com.gimbal.android.skccJeju;

import com.gimbal.android.Gimbal;

public class PushRegistrationHelper {

    public static void registerForPush() {
        // Setup Push Communication
        String gcmSenderId = "967183592620"; // <--- SET THIS STRING TO YOUR PUSH SENDER ID HERE (Google API project #) ##

        if (gcmSenderId != null) {
            Gimbal.registerForPush(gcmSenderId);
        }
    }
}
