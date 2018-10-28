package roiattia.com.salariestrack.utils;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import roiattia.com.salariestrack.R;


public class Analytics {

    public static void logEventSortClick(Context context, int sortNumber){
        String[] sortOptions = context.getResources().getStringArray(R.array.sort_selection_options);
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.CONTENT_TYPE, sortOptions[sortNumber]);
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params);
    }
}
