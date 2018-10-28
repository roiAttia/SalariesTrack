package roiattia.com.salariestrack.sync;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class StartReminderService extends IntentService {

    public StartReminderService() {
        super(StartReminderService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ReminderUtils.scheduleSalariesReminder(this);
    }
}
