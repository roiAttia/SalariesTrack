package roiattia.com.salariestrack.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import org.joda.time.LocalDate;

import roiattia.com.salariestrack.database.AppDatabase;
import roiattia.com.salariestrack.database.AppExecutors;
import roiattia.com.salariestrack.model.SalariesSum;
import roiattia.com.salariestrack.repositories.SalariesRepository;

public class SalariesIntentService extends IntentService {

    public static final String ACTION_GET_REPORT =
            "roiattia.com.salariestrack.action.get_report";
    private AppExecutors mExecutors;
    private SalariesRepository mRepository;
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public SalariesIntentService() {
        super("SalariesIntentService");
        mExecutors = AppExecutors.getInstance();
        mRepository = SalariesRepository.getInstance(this);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_REPORT.equals(action)) {
                handleActionProvideReport();
            }
        }
    }

    private void handleActionProvideReport() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                LocalDate startMonth = new LocalDate();
                startMonth = startMonth.withDayOfMonth(1);
                LocalDate finishMonth = new LocalDate();
                finishMonth = finishMonth.plusMonths(1).withDayOfMonth(1);
                SalariesSum salariesSum =
                        mRepository.loadSalariesSumReport(startMonth, finishMonth);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(SalariesIntentService.this);
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                        new ComponentName(SalariesIntentService.this, SalariesWidgetProvider.class));
                //Now update all widgets
                SalariesWidgetProvider.updateSalariesWidgets(
                        SalariesIntentService.this, appWidgetManager, salariesSum, appWidgetIds);
            }
        });
    }

    public static void startActionProvideReport(Context context) {
        Intent intent = new Intent(context, SalariesIntentService.class);
        intent.setAction(ACTION_GET_REPORT);
        context.startService(intent);
    }
}
