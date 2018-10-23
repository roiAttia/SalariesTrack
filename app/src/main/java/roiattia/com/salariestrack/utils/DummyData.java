package roiattia.com.salariestrack.utils;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import roiattia.com.salariestrack.model.SalaryEntry;

public class DummyData {

    //  long salaryId, String workerName, double salary, LocalDate workDate,
    //  LocalDate paymentDate, boolean isPaid, boolean gotReceipt,
    //  boolean gotContract, String workPlace, String workDescription

    public static List<SalaryEntry> getDummySalaryList(){
        List<SalaryEntry> salaries = new ArrayList<>();
        LocalDate workDate = new LocalDate();
        LocalDate paymentDate = new LocalDate();
        SalaryEntry salaryEntry_1 = new SalaryEntry("Roi attia", 350, workDate,
                paymentDate, "Home", "App", false, false, false);
        workDate.withDayOfMonth(1);
        paymentDate.withDayOfMonth(1);
        SalaryEntry salaryEntry_2 = new SalaryEntry("Orit azryav", 1000,
                workDate, paymentDate, "C-Hotel", "Entertainment staff",
                false, false, true);
        salaries.add(salaryEntry_1);
        salaries.add(salaryEntry_2);
        return salaries;
    }
}
