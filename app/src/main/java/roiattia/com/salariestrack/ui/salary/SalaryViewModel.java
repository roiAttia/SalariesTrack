package roiattia.com.salariestrack.ui.salary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import roiattia.com.salariestrack.repositories.SalariesRepository;
import roiattia.com.salariestrack.database.AppExecutors;
import roiattia.com.salariestrack.model.SalaryEntry;

public class SalaryViewModel extends AndroidViewModel {

    private MutableLiveData<SalaryEntry> mMutableLiveDataSalary;
    private SalariesRepository mRepository;
    private AppExecutors mExecutors;

    public SalaryViewModel(@NonNull Application application) {
        super(application);
        mRepository = SalariesRepository.getInstance(application.getApplicationContext());
        mMutableLiveDataSalary = new MutableLiveData<>();
        mExecutors = AppExecutors.getInstance();
    }

    public void getSalaryById(final long salaryId) {
        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                SalaryEntry salaryEntry = mRepository.getSalaryById(salaryId);
                mMutableLiveDataSalary.postValue(salaryEntry);
            }
        });
    }

    public MutableLiveData<SalaryEntry> getMutableLiveDataSalary() {
        return mMutableLiveDataSalary;
    }

    public void insertSalary(SalaryEntry salaryEntry){
        mRepository.insertSalary(salaryEntry);
    }

    public void deleteSalary(SalaryEntry salaryEntry) {
        mRepository.deleteSalary(salaryEntry);
    }
}
