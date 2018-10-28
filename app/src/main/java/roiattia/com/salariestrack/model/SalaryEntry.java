package roiattia.com.salariestrack.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import org.joda.time.LocalDate;

@Entity(tableName = "salary")
public class SalaryEntry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "salary_id")
    private long mSalaryId;
    @ColumnInfo(name = "worker_name")
    private String mWorkerName;
    @ColumnInfo(name = "salary")
    private double mSalary;
    @ColumnInfo(name = "work_date")
    private LocalDate mWorkDate;
    @ColumnInfo(name = "payment_date")
    private LocalDate mPaymentDate;
    @ColumnInfo(name = "is_paid")
    private boolean mIsPaid;
    @ColumnInfo(name = "got_receipt")
    private boolean mGotReceipt;
    @ColumnInfo(name = "got_contract")
    private boolean mGotContract;
    @ColumnInfo(name = "work_place")
    private String mWorkPlace;
    @ColumnInfo(name = "work_description")
    private String mWorkDescription;

    public SalaryEntry(long salaryId, String workerName, double salary, LocalDate workDate,
                       LocalDate paymentDate, boolean isPaid, boolean gotReceipt,
                       boolean gotContract, String workPlace, String workDescription) {
        mSalaryId = salaryId;
        mWorkerName = workerName;
        mSalary = salary;
        mWorkDate = workDate;
        mPaymentDate = paymentDate;
        mIsPaid = isPaid;
        mGotReceipt = gotReceipt;
        mGotContract = gotContract;
        mWorkPlace = workPlace;
        mWorkDescription = workDescription;
    }

    @Ignore
    public SalaryEntry(String workerName, double salary, LocalDate workDate,
                       LocalDate paymentDate, String workPlace, String workDescription,
                       boolean isPaid, boolean hasReceipt, boolean hasContract) {
        mWorkerName = workerName;
        mSalary = salary;
        mWorkDate = workDate;
        mPaymentDate = paymentDate;
        mIsPaid = isPaid;
        mGotReceipt = hasReceipt;
        mGotContract = hasContract;
        mWorkPlace = workPlace;
        mWorkDescription = workDescription;
    }

    @Ignore
    public SalaryEntry() { }

    public long getSalaryId() {
        return mSalaryId;
    }

    public void setSalaryId(long salaryId) {
        mSalaryId = salaryId;
    }

    public String getWorkerName() {
        return mWorkerName;
    }

    public void setWorkerName(String workerName) {
        mWorkerName = workerName;
    }

    public double getSalary() {
        return mSalary;
    }

    public void setSalary(double salary) {
        mSalary = salary;
    }

    public LocalDate getWorkDate() {
        return mWorkDate;
    }

    public void setWorkDate(LocalDate workDate) {
        mWorkDate = workDate;
    }

    public LocalDate getPaymentDate() {
        return mPaymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        mPaymentDate = paymentDate;
    }

    public boolean isPaid() {
        return mIsPaid;
    }

    public void setPaid(boolean paid) {
        mIsPaid = paid;
    }

    public boolean isGotReceipt() {
        return mGotReceipt;
    }

    public void setGotReceipt(boolean gotReceipt) {
        mGotReceipt = gotReceipt;
    }

    public boolean isGotContract() {
        return mGotContract;
    }

    public void setGotContract(boolean gotContract) {
        mGotContract = gotContract;
    }

    public String getWorkPlace() {
        return mWorkPlace;
    }

    public void setWorkPlace(String workPlace) {
        mWorkPlace = workPlace;
    }

    public String getWorkDescription() {
        return mWorkDescription;
    }

    public void setWorkDescription(String workDescription) {
        mWorkDescription = workDescription;
    }

    @Override
    public String toString() {
        return "SalaryEntry{" +
                "mSalaryId=" + mSalaryId +
                ", mWorkerName='" + mWorkerName + '\'' +
                ", mSalary=" + mSalary +
                ", mWorkDate=" + mWorkDate +
                ", mPaymentDate=" + mPaymentDate +
                ", mIsPaid=" + mIsPaid +
                ", mGotReceipt=" + mGotReceipt +
                ", mGotContract=" + mGotContract +
                ", mWorkPlace='" + mWorkPlace + '\'' +
                ", mWorkDescription='" + mWorkDescription + '\'' +
                '}';
    }
}
