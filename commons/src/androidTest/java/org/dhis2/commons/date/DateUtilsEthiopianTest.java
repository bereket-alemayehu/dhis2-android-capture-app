package org.dhis2.commons.date;

import org.junit.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateUtilsEthiopianTest {

    @Test
    public void testGregorianToEthiopian() {
        LocalDate localDate = LocalDate.of(2025, 7, 7);
        Date greg = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        String eth = DateUtils.convertGregorianToEthiopian(greg);
        assertEquals("2017-07-07", eth);
    }

    @Test
    public void testEthiopianToGregorian() {
        Date greg = DateUtils.convertEthiopianToGregorian(2017, 7, 7);
        LocalDate localDate = greg.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        assertEquals(LocalDate.of(2025, 7, 7), localDate);
    }
}
