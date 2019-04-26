/**
 * Author: Mo Sagnia
 * Date: April 2019
 * Objective: Create a class to convert Dates for CRUD operations in the Database
 */


//Objective: create classes to convert date formats
package DesktopInterface;

import javafx.scene.control.DatePicker;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateConverter {

    //from DatePicker to java.util.Date
    public static Date FromDatePickerToUtilDate(DatePicker dpName)
    {
            LocalDate localDate = dpName.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instant);
            return date;


    }

    //from LocalDate to java.util.Date
    public static Date FromLocalDateToUtilDate(LocalDate ldName)
    {
            Instant instantToday = Instant.from(ldName.atStartOfDay(ZoneId.systemDefault()));
            Date date = Date.from(instantToday);
            return date;

    }
}
