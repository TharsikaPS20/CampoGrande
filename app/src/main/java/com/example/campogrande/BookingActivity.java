package com.example.campogrande;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class BookingActivity extends AppCompatActivity {


    Button btn1,btn2, btn3, arrival, departure, timebtn, payment;
    TextView arrivalDateDisplay, departureDateDisplay, time;
    int currentHour;
    int currentMinute;
    TimePickerDialog tpd;
    Calendar arrivalDate, departureDate;
    ViewPager viewPager;
    static final int DATE_DIALOG_ID = 0;

    private TextView activeDateDisplay;
    private Calendar activeDate;
    ImageButton left, right;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.images);
        com.example.campogrande.ImageAdapter adapter = new com.example.campogrande.ImageAdapter(this);
        viewPager.setAdapter(adapter);

        btn1=findViewById(R.id.btn1);
        btn2=findViewById(R.id.btn2);
        btn3=findViewById(R.id.btn3);
        payment = findViewById(R.id.payment);

        arrivalDateDisplay = findViewById(R.id.arrival);
        arrival=findViewById(R.id.arrivalbtn);
        arrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(arrivalDateDisplay, arrivalDate);
            }
        });
        arrivalDate = Calendar.getInstance();

        departureDateDisplay = findViewById(R.id.departure);
        departure = findViewById(R.id.departurebtn);
        departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(departureDateDisplay, departureDate);
            }
        });
        departureDate = Calendar.getInstance();


        updateDisplay(arrivalDateDisplay, arrivalDate);
        updateDisplay(departureDateDisplay, departureDate);

        time = findViewById(R.id.time);
        timebtn = findViewById(R.id.timebtn);
        timebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                currentHour=c.get(Calendar.HOUR_OF_DAY);
                currentMinute = c.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int hourOfDay, int minutes) {
                        time.setText(String.format("%02d:%02d",hourOfDay, minutes));
                    }
                }, currentHour, currentMinute, true);
                tpd.show();
            }

        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });


        left = findViewById(R.id.left);
        right =findViewById(R.id.right);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i= viewPager.getCurrentItem();
                if (i > 0) {
                    i--;
                    viewPager.setCurrentItem(i);
                } else if (i == 0) {
                    viewPager.setCurrentItem(i);
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = viewPager.getCurrentItem();
                i++;
                viewPager.setCurrentItem(i);
            }
        });


    }

    private void updateDisplay(TextView dateDisplay, Calendar date){
        dateDisplay.setText(new StringBuilder()
        .append(date.get(Calendar.DAY_OF_MONTH)).append("-")
        .append(date.get(Calendar.MONTH)+1).append("-")
        .append(date.get(Calendar.YEAR)).append(" "));
    }
    public void showDateDialog(TextView dateDisplay, Calendar date) {
        activeDateDisplay = dateDisplay;
        activeDate = date;
        showDialog(DATE_DIALOG_ID);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            activeDate.set(Calendar.YEAR, year);
            activeDate.set(Calendar.MONTH, monthOfYear);
            activeDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDisplay(activeDateDisplay, activeDate);
            unregisterDateDisplay();
        }
    };

    private void unregisterDateDisplay() {
        activeDateDisplay = null;
        activeDate = null;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, dateSetListener, activeDate.get(Calendar.YEAR), activeDate.get(Calendar.MONTH), activeDate.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(activeDate.get(Calendar.YEAR), activeDate.get(Calendar.MONTH), activeDate.get(Calendar.DAY_OF_MONTH));
                break;
        }
    }

}