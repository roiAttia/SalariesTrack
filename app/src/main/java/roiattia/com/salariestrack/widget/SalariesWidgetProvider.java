package roiattia.com.salariestrack.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import roiattia.com.salariestrack.R;
import roiattia.com.salariestrack.model.SalariesSum;
import roiattia.com.salariestrack.ui.salarieslist.SalariesListActivity;
import roiattia.com.salariestrack.ui.salary.SalaryActivity;
import roiattia.com.salariestrack.utils.TextFormat;

/**
 * Implementation of App Widget functionality.
 */
public class SalariesWidgetProvider extends AppWidgetProvider {

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        SalariesSum salariesSum, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.salaries_widget_provider);
        views.setTextViewText(R.id.tv_summary, context.getString(R.string.title) +
                TextFormat.getStringFormatFromDouble(salariesSum.getSum()));

        Intent salariesListIntent = new Intent(context, SalariesListActivity.class);
        PendingIntent salariesPendingIntent = PendingIntent.getActivity(context, 0, salariesListIntent, 0);
        views.setOnClickPendingIntent(R.id.tv_salaries_track, salariesPendingIntent);

        Intent salaryIntent = new Intent(context, SalaryActivity.class);
        PendingIntent salaryPendingIntent = PendingIntent.getActivity(context, 0, salaryIntent, 0);
        views.setOnClickPendingIntent(R.id.iv_new_salary, salaryPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static void updateSalariesWidgets(Context context, AppWidgetManager appWidgetManager,
                                             SalariesSum salariesSum, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, salariesSum, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        SalariesIntentService.startActionProvideReport(context);
    }

    @Override
    public void onEnabled(Context context) {
        SalariesIntentService.startActionProvideReport(context);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

