package roiattia.com.salariestrack.model;

import org.joda.time.LocalDate;

public class SalaryListItem {

    private long mSalaryId;
    private String mName;
    private double mSalary;
    private LocalDate mWorkDate;
    private LocalDate mPaymentDate;

    public SalaryListItem(long salaryId, String name, double salary, LocalDate workDate,
                          LocalDate paymentDate) {
        mSalaryId = salaryId;
        mName = name;
        mSalary = salary;
        mWorkDate = workDate;
        mPaymentDate = paymentDate;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public long getSalaryId() {
        return mSalaryId;
    }

    public void setSalaryId(long salaryId) {
        mSalaryId = salaryId;
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
}
