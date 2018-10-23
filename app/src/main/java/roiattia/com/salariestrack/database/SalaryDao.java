package roiattia.com.salariestrack.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.joda.time.LocalDate;

import java.util.List;

import roiattia.com.salariestrack.model.SalariesSum;
import roiattia.com.salariestrack.model.SalaryEntry;
import roiattia.com.salariestrack.model.SalaryListItem;

@Dao
public interface SalaryDao {
    // boolean -> true = 1, false = 0

    @Query("SELECT salary_id AS mSalaryId, worker_name AS mName, salary AS mSalary," +
            " work_date AS mWorkDate, payment_date AS mPaymentDate FROM salary ")
    LiveData<List<SalaryListItem>> getSalaryItems();

    @Insert
    void insertSalaries(List<SalaryEntry> dummySalaryList);

    @Query("SELECT COUNT(*) FROM salary")
    int getSalariesCount();

    @Query("DELETE FROM salary")
    void deleteAllSalaries();

    @Query("SELECT * FROM salary WHERE salary_id =:salaryId")
    SalaryEntry getSalaryById(long salaryId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSalary(SalaryEntry salaryEntry);

    @Query("SELECT * FROM salary")
    List<SalaryEntry> getAllSalaries();

    @Query("SELECT salary_id AS mSalaryId, worker_name AS mName, salary AS mSalary," +
            " work_date AS mWorkDate, payment_date AS mPaymentDate FROM salary WHERE is_paid = 0 " +
            "ORDER BY payment_date ASC")
    List<SalaryListItem> getSalariesNotPaid();

    @Query("SELECT salary_id AS mSalaryId, worker_name AS mName, salary AS mSalary," +
            " work_date AS mWorkDate, payment_date AS mPaymentDate FROM salary WHERE got_contract = 0 " +
            "ORDER BY payment_date ASC")
    List<SalaryListItem> getSalariesNoContract();

    @Query("SELECT salary_id AS mSalaryId, worker_name AS mName, salary AS mSalary," +
            " work_date AS mWorkDate, payment_date AS mPaymentDate FROM salary WHERE got_receipt = 0 " +
            "ORDER BY payment_date ASC")
    List<SalaryListItem> getSalariesNoReceipt();

    @Query("SELECT salary_id AS mSalaryId, worker_name AS mName, salary AS mSalary," +
            " work_date AS mWorkDate, payment_date AS mPaymentDate FROM salary " +
            "ORDER BY payment_date ASC")
    List<SalaryListItem> getSalariesItems();

    @Query("SELECT SUM(salary) AS mSum FROM salary WHERE payment_date BETWEEN :startMonth AND :finishMonth " +
            " AND is_paid = 0")
    SalariesSum loadSalariesSumReport(LocalDate startMonth, LocalDate finishMonth);

    @Delete
    void deleteSalary(SalaryEntry salaryEntry);
}
