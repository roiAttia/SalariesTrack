package roiattia.com.salariestrack.repositories;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import org.joda.time.LocalDate;

import java.util.List;

import roiattia.com.salariestrack.database.AppDatabase;
import roiattia.com.salariestrack.database.AppExecutors;
import roiattia.com.salariestrack.model.SalariesSum;
import roiattia.com.salariestrack.model.SalaryEntry;
import roiattia.com.salariestrack.model.SalaryListItem;

public class SalariesRepository {

    private static final String TAG = SalariesRepository.class.getSimpleName();
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static SalariesRepository sInstance;
    private AppDatabase mDb;
    private AppExecutors mExecutors;

    private SalariesRepository(Context context){
        mDb = AppDatabase.getsInstance(context);
        mExecutors = AppExecutors.getInstance();
    }

    public synchronized static SalariesRepository getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new SalariesRepository(context);
            }
        }
        return sInstance;
    }

    public LiveData<List<SalaryListItem>> getSalaries() {
        return mDb.salaryDao().getSalaryItems();
    }

    public void deleteAllSalaries(){
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.salaryDao().deleteAllSalaries();
            }
        });
    }

    public void insertSalaries(final List<SalaryEntry> salaries) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                int salariesCount = mDb.salaryDao().getSalariesCount();
                Log.i(TAG, "salaries count before: " + salariesCount);
                mDb.salaryDao().insertSalaries(salaries);
                salariesCount = mDb.salaryDao().getSalariesCount();
                Log.i(TAG, "salaries count after: " + salariesCount);
            }
        });
    }

    public SalaryEntry getSalaryById(long salaryId) {
        return mDb.salaryDao().getSalaryById(salaryId);
    }

    public void insertSalary(final SalaryEntry salaryEntry) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.salaryDao().insertSalary(salaryEntry);
            }
        });
    }

    public void debugPrint() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<SalaryEntry> salaryEntries = mDb.salaryDao().getAllSalaries();
                for(SalaryEntry salaryEntry : salaryEntries)
                    Log.i(TAG, salaryEntry.toString());
            }
        });
    }

    public List<SalaryListItem> getSalariesItemsNotPaid() {
        return mDb.salaryDao().getSalariesNotPaid();
    }

    public List<SalaryListItem> getSalariesItemsNoContract() {
        return mDb.salaryDao().getSalariesNoContract();
    }

    public List<SalaryListItem> getSalariesItemsNoReceipt() {
        return mDb.salaryDao().getSalariesNoReceipt();
    }

    public List<SalaryListItem> getSalariesItems() {
        return mDb.salaryDao().getSalariesItems();
    }

    public SalariesSum loadSalariesSumReport(LocalDate startMonth, LocalDate finishMonth) {
        return mDb.salaryDao().loadSalariesSumReport(startMonth, finishMonth);
    }

    public void deleteSalary(final SalaryEntry salaryEntry) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.salaryDao().deleteSalary(salaryEntry);
            }
        });
    }
}
