package roiattia.com.salariestrack.ui.salarieslist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import roiattia.com.salariestrack.repositories.SalariesRepository;
import roiattia.com.salariestrack.database.AppExecutor;
import roiattia.com.salariestrack.model.SalaryEntry;
import roiattia.com.salariestrack.model.SalaryListItem;

class SalariesViewModel extends AndroidViewModel {

    private final SalariesRepository mRepository;
    private final AppExecutor mExecutors;
    private final MutableLiveData<List<SalaryListItem>> mMutableLiveDataSalaries;

    public SalariesViewModel(@NonNull Application application) {
        super(application);
        mRepository = SalariesRepository.getInstance(application.getApplicationContext());
        mExecutors = AppExecutor.getInstance();
        mMutableLiveDataSalaries = new MutableLiveData<>();
    }

    public void deleteAllSalaries(){
        mRepository.deleteAllSalaries();
    }

    public void insertSalaries(List<SalaryEntry> salaries) {
        mRepository.insertSalaries(salaries);
    }

    public void debugPrint() {
        mRepository.debugPrint();
    }

    public MutableLiveData<List<SalaryListItem>> getMutableLiveDataSalaries() {
        return mMutableLiveDataSalaries;
    }

    public void getSalariesItemsNotPaid() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<SalaryListItem> salaries = mRepository.getSalariesItemsNotPaid();
                mMutableLiveDataSalaries.postValue(salaries);
            }
        });
    }

    public void getSalariesItemsNoContract() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<SalaryListItem> salaries = mRepository.getSalariesItemsNoContract();
                mMutableLiveDataSalaries.postValue(salaries);
            }
        });
    }

    public void getSalariesItemsNoReceipt() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<SalaryListItem> salaries = mRepository.getSalariesItemsNoReceipt();
                mMutableLiveDataSalaries.postValue(salaries);
            }
        });
    }

    public void getSalariesItems() {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<SalaryListItem> salaries = mRepository.getSalariesItems();
                mMutableLiveDataSalaries.postValue(salaries);
            }
        });
    }
}
