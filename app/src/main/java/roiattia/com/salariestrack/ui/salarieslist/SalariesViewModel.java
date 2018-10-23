package roiattia.com.salariestrack.ui.salarieslist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import roiattia.com.salariestrack.repositories.SalariesRepository;
import roiattia.com.salariestrack.database.AppExecutors;
import roiattia.com.salariestrack.model.SalaryEntry;
import roiattia.com.salariestrack.model.SalaryListItem;

public class SalariesViewModel extends AndroidViewModel {

    SalariesRepository mRepository;
//    LiveData<List<SalaryListItem>> mSalaries;
    AppExecutors mExecutors;
    private MutableLiveData<List<SalaryListItem>> mMutableLiveDataSalaries;

    public SalariesViewModel(@NonNull Application application) {
        super(application);
        mRepository = SalariesRepository.getInstance(application.getApplicationContext());
        mExecutors = AppExecutors.getInstance();
//        mSalaries = mRepository.getSalaries();
        mMutableLiveDataSalaries = new MutableLiveData<>();
    }

//    public LiveData<List<SalaryListItem>> getSalaries() {
//        return mSalaries;
//    }

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

    public void loadSalariesBySort(final int whichSelected) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<SalaryListItem> salaries = new ArrayList<>();
                switch (whichSelected){
                    case 0: // not paid
                        salaries = mRepository.getSalariesItemsNotPaid();
                        break;
                    case 1: // no contract
                        salaries = mRepository.getSalariesItemsNoContract();
                        break;
                    case 2: // no receipt
                        salaries = mRepository.getSalariesItemsNoReceipt();
                        break;
                    case 3: // all salaries
                        salaries = mRepository.getSalariesItems();
                        break;
                }
                mMutableLiveDataSalaries.postValue(salaries);
            }
        });

    }
}
