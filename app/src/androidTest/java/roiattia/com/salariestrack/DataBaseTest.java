package roiattia.com.salariestrack;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import roiattia.com.salariestrack.database.AppDatabase;
import roiattia.com.salariestrack.database.SalaryDao;
import roiattia.com.salariestrack.utils.DummyData;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DataBaseTest {

    private static final String TAG = DataBaseTest.class.getSimpleName();
    private AppDatabase mDatabase;
    private SalaryDao mSalaryDao;

    @Before
    public void createDatabase(){
        Context context = InstrumentationRegistry.getTargetContext();
        mDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mSalaryDao = mDatabase.salaryDao();
        Log.i(TAG, "database created");
    }

    @After
    public void closeDatabase(){
        mDatabase.close();
        Log.i(TAG, "database closed");
    }

    @Test
    public void createNotes(){
        mSalaryDao.insertSalaries(DummyData.getDummySalaryList());
        int salariesCount = mSalaryDao.getSalariesCount();
        assertEquals(DummyData.getDummySalaryList().size(), salariesCount);
    }
}
