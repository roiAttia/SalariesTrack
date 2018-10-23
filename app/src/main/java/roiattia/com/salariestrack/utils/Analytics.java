package roiattia.com.salariestrack.utils;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics {

    public static void logEventSortClick(Context context, int sortNumber){
        Bundle params = new Bundle();
        params.putInt("sort number", sortNumber);
        FirebaseAnalytics.getInstance(context).logEvent("check", params);
    }
}
