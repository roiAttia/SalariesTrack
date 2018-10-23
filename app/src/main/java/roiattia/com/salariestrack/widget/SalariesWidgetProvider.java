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
import roiattia.com.salariestrack.utils.TextFormat;

/**
 * Implementation of App Widget functionality.
 */
public class SalariesWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                SalariesSum salariesSum, int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.salaries_widget_provider);
        views.setTextViewText(R.id.appwidget_text, context.getString(R.string.title) +
                TextFormat.getStringFormatFromDouble(salariesSum.getSum()));
        views.setImageViewResource(R.id.appwidget_image, R.drawable.coins_30_green);

        Intent intent = new Intent(context, SalariesListActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

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

