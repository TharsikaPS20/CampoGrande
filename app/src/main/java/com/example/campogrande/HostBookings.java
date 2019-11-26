package com.example.campogrande;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.DefaultDayViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HostBookings extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Date today = new Date();
        Date date2 = new Date(11-01-2020);
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR,1);
        CalendarPickerView calendarPickerView = findViewById(R.id.calendar_view);
        calendarPickerView.init(today,nextYear.getTime()).inMode(CalendarPickerView.SelectionMode.RANGE).withSelectedDate(today);

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        calendarPickerView.setCustomDayView(new DefaultDayViewAdapter());
        calendarPickerView.setDecorators(Collections.<CalendarCellDecorator>emptyList());
        calendarPickerView.init(getDateWithYear(2018), getDateWithYear(2022))
                .inMode(CalendarPickerView.SelectionMode.SINGLE)
                .withSelectedDate(c.getTime());

        calendarPickerView.highlightDates(getHighlightedDaysForMonth(
                c.get(Calendar.MONTH) - 1,
                c.get(Calendar.MONTH),
                c.get(Calendar.MONTH) + 5));




        calendarPickerView.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                String chosenDate = "" + calendar.get(Calendar.DAY_OF_MONTH)
                + "/" +(calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.YEAR);

                Toast.makeText(HostBookings.this,chosenDate,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });



        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.add_new:

                        Intent i = new Intent(HostBookings.this, Listings.class);
                        startActivity(i);
                        break;


                    case R.id.listings:
                        Intent intent3 = new Intent(HostBookings.this, HostListings.class);
                        startActivity(intent3);
                        break;

                }


                return false;
            }
        });
    }

    private Collection<Date> getHighlightedDaysForMonth(int...month) {
        List<Date> dateList = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());

        for (int i = 0; i < month.length; i++) {
            for (int j = 0; j < 15; j++) {
                dateList.add(getDateWithYearAndMonthForDay(c.get(Calendar.YEAR), i, j));
            }
        }

        return dateList;
    }

        private Date getDateWithYearAndMonthForDay(int year, int month, int day) {
            final Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.DAY_OF_MONTH, day);

            return cal.getTime();
        }


    private Date getDateWithYear(int i) {
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, i);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }


}