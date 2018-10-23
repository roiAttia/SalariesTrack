package roiattia.com.salariestrack.utils;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;

public class TextFormat {

    public static String getDateStringFormat(LocalDate date){
        if(date != null) {
            DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
            return fmt.print(date);
        }
        return "";
    }

    public static String getStringFormatFromDouble(double amount){
        DecimalFormat formatter = new DecimalFormat("#,###,###.###");
        return formatter.format(amount);
    }

    public static double getDoubleFormatFromString(String amount){
        return Double.parseDouble(amount.replaceAll(",", ""));
    }
}
