package com.example.ethiopiantest.calendar;


public class DateConverter {

    // Based on Bahire Hasab approximation
    public static EthiopianDate gregorianToEthiopian(int gYear, int gMonth, int gDay) {
        int[] ethMonths = {30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 5};
        int newYearDay = (gYear % 4 == 3) ? 12 : 11; // September 11 or 12

        int ethYear = gYear - 8;
        int ethMonth;
        int ethDay;

        if (gMonth < 9 || (gMonth == 9 && gDay < newYearDay)) {
            ethYear -= 1;
        }

        int totalGDays = getTotalDays(gYear, gMonth, gDay);
        int totalEthDays = totalGDays - getTotalDays(gYear, 9, newYearDay) + 1;

        if (totalEthDays <= 0) {
            totalEthDays += isLeapYear(ethYear) ? 366 : 365;
        }

        int dayCount = 0;
        for (ethMonth = 0; ethMonth < 13; ethMonth++) {
            int monthLength = (ethMonth == 12 && isLeapYear(ethYear)) ? 6 : ethMonths[ethMonth];
            if (totalEthDays <= dayCount + monthLength) break;
            dayCount += monthLength;
        }

        ethDay = totalEthDays - dayCount;

        return new EthiopianDate(ethYear, ethMonth + 1, ethDay);
    }

    private static int getTotalDays(int year, int month, int day) {
        int[] daysInMonth = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        int total = (year - 1) * 365 + (year - 1) / 4;
        total += daysInMonth[month - 1] + day;

        if (month > 2 && isLeapYear(year)) total += 1;

        return total;
    }

    private static boolean isLeapYear(int year) {
        return year % 4 == 3; // Ethiopian leap year rule (every 4 years starting from year 3)
    }
}
