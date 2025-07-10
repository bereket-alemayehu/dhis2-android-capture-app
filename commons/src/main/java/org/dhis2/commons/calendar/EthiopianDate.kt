package com.example.ethiopiantest.calendar;

public class EthiopianDate {
    public int year;
    public int month;
    public int day;

    private static final String[] MONTH_NAMES = {
        "Meskerem", "Tikimt", "Hidar", "Tahsas", "Tir", "Yekatit",
        "Megabit", "Miyazya", "Ginbot", "Sene", "Hamle", "Nehase", "Pagumen"
    };

    public EthiopianDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getMonthName() {
        if (month >= 1 && month <= 13) {
            return MONTH_NAMES[month - 1];
        }
        return "Invalid";
    }

    @Override
    public String toString() {
        return day + " " + getMonthName() + " " + year;
    }
}
