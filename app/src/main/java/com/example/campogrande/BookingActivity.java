package com.example.campogrande;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import java.util.Calendar;

public class BookingActivity extends AppCompatActivity implements View.OnClickListener {


    Button arrival, departure, timebtn, bookBtn;
    TextView arrivalDateDisplay, departureDateDisplay, time;
    int currentHour;
    int currentMinute;
    TimePickerDialog tpd;
    Calendar arrivalTime, arrivalDate, departureDate;
    ViewPager viewPager;
    static final int DATE_DIALOG_ID = 0;

    private TextView activeDateDisplay;
    private Calendar activeDate;
    ImageButton left, right;
    ImageView mp, pp, visa, mc, ap, gp;
    EditText cardNo, cvvNo, greeting;
    Spinner month, year;
    Boolean aDate;
    Boolean dDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        aDate = false;
        dDate = false;


        viewPager = findViewById(R.id.images);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);

        bookBtn = findViewById(R.id.book);

        greeting = findViewById(R.id.msg);
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
                showDateDialog1(departureDateDisplay, departureDate);
            }
        });
        departureDate = Calendar.getInstance();

        updateDisplay(arrivalDateDisplay, arrivalDate);
        updateDisplay(departureDateDisplay, departureDate);

        arrivalTime = Calendar.getInstance();
        time = findViewById(R.id.time);
        timebtn = findViewById(R.id.timebtn);
        timebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHour=arrivalTime.get(Calendar.HOUR_OF_DAY);
                currentMinute = arrivalTime.get(Calendar.MINUTE);

                tpd = new TimePickerDialog(BookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int hourOfDay, int minutes) {
                        time.setText(String.format("%02d:%02d",hourOfDay, minutes));
                        time.setFocusable(true);
                    }
                }, currentHour, currentMinute, true);
                tpd.show();
            }

        });

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestBooking();
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

        month=findViewById(R.id.expirymonth);
        ArrayAdapter arrayAdapter1 = new ArrayAdapter<>(BookingActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.months));
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(arrayAdapter1);

        year=findViewById(R.id.expiryyear);
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(BookingActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.years));
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(arrayAdapter);


        mp = findViewById(R.id.mobilepay);
        mp.setOnClickListener(this);
        pp = findViewById(R.id.paypal);
        pp.setOnClickListener(this);
        visa = findViewById(R.id.visa);
        visa.setOnClickListener(this);
        mc = findViewById(R.id.master);
        mc.setOnClickListener(this);
        ap = findViewById(R.id.applepay);
        ap.setOnClickListener(this);
        gp = findViewById(R.id.googlepay);
        gp.setOnClickListener(this);
        cardNo = findViewById(R.id.ccnumber);
        cardNo.addTextChangedListener(new BookingActivity.MyTextWatcher(cardNo));
        cvvNo = findViewById(R.id.cvvnumber);
        cvvNo.addTextChangedListener(new BookingActivity.MyTextWatcher(cvvNo));
    }

    private void requestBooking() {
        if(!validDates()){
            return;
        }

        if (!validTime()){
            return;
        }

        if (!validGreeting()){
            return;
        }

        if(!validCardno()){
            return;
        }

        if(!validExpMonth()){
            return;
        }

        if(!validExpYear()){
            return;
        }

        if(!validCvv()){
            return;
        }

        Toast.makeText(BookingActivity.this, "Booking request sent!", Toast.LENGTH_LONG).show();
        Intent i = new Intent(BookingActivity.this,Reservations.class);
        startActivity(i);
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
        activeDateDisplay.setVisibility(View.VISIBLE);
        arrivalDateDisplay.setFocusable(true);

    }

    public void showDateDialog1(TextView dateDisplay1, Calendar date1) {
        activeDateDisplay = dateDisplay1;
        activeDate = date1;
        showDialog(DATE_DIALOG_ID);
        activeDateDisplay.setVisibility(View.VISIBLE);
        departureDateDisplay.setFocusable(true);
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
                Calendar c = Calendar.getInstance();
                 DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, activeDate.get(Calendar.YEAR), activeDate.get(Calendar.MONTH), activeDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                 return datePickerDialog;
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

    @Override
    public void onClick(View v) {
        Drawable chosen = getResources().getDrawable(R.drawable.highlight);
            switch (v.getId()) {
                case R.id.mobilepay:
                    mp.setBackground(chosen);
                    pp.setBackground(null);
                    visa.setBackground(null);
                    mc.setBackground(null);
                    ap.setBackground(null);
                    gp.setBackground(null);
                    break;
                case R.id.paypal:
                    pp.setBackground(chosen);
                    mp.setBackground(null);
                    visa.setBackground(null);
                    mc.setBackground(null);
                    ap.setBackground(null);
                    gp.setBackground(null);
                    break;
                case R.id.visa:
                    visa.setBackground(chosen);
                    pp.setBackground(null);
                    mp.setBackground(null);
                    mc.setBackground(null);
                    ap.setBackground(null);
                    gp.setBackground(null);
                    break;
                case R.id.master:
                    mc.setBackground(chosen);
                    pp.setBackground(null);
                    mp.setBackground(null);
                    visa.setBackground(null);
                    ap.setBackground(null);
                    gp.setBackground(null);
                    break;
                case R.id.applepay:
                    ap.setBackground(chosen);
                    pp.setBackground(null);
                    mp.setBackground(null);
                    visa.setBackground(null);
                    mc.setBackground(null);
                    gp.setBackground(null);
                    break;
                case R.id.googlepay:
                    gp.setBackground(chosen);
                    pp.setBackground(null);
                    mp.setBackground(null);
                    visa.setBackground(null);
                    mc.setBackground(null);
                    ap.setBackground(null);
                    break;

            }
        }

    private class MyTextWatcher implements TextWatcher {
        private View view;
        private  MyTextWatcher(View view){
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()){
                case R.id.ccnumber:
                    validCardno();
                    break;
                case R.id.expirymonth:
                    validExpMonth();
                    break;
                case R.id.expiryyear:
                    validExpYear();
                case R.id.cvvnumber:
                    validCvv();
                    break;

            }
        }
    }

    private boolean validExpYear() {
        if(year.getSelectedItem().equals("YYYY")){
            ((TextView)year.getSelectedView()).setError("Error message");
            year.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }

    private boolean validCvv() {
        if(cvvNo.getText().toString().trim().isEmpty()){
            cvvNo.setError(getString(R.string.error_invalid_cvc));
            cvvNo.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    private boolean validExpMonth() {
        if(month.getSelectedItem().equals("MM")){
            ((TextView)month.getSelectedView()).setError("Error message");
            month.requestFocus();
            return false;
        }
        else{
            return true;
        }

    }

    private boolean validCardno() {
        if(cardNo.getText().toString().trim().isEmpty() || !(cardNo.getText().toString().trim().length()==16)){
            cardNo.setError(getString(R.string.error_invalid_cardno));
            cardNo.requestFocus();
            return false;
        }

        else{
            return true;
        }

    }

    private boolean validDates(){
        if(!arrivalDateDisplay.isFocusable())
        {
            Toast.makeText(BookingActivity.this,getResources().getString(R.string.error_fillarr),Toast.LENGTH_LONG).show();
            arrival.requestFocus();
            return false;
        }

        else if(!departureDateDisplay.isFocusable())
        {
            Toast.makeText(BookingActivity.this,getResources().getString(R.string.error_filldep),Toast.LENGTH_LONG).show();
            departureDateDisplay.requestFocus();
            return false;
        }
        else if(departureDateDisplay.getText().equals(arrivalDateDisplay.getText())){
            Toast.makeText(BookingActivity.this,getResources().getString(R.string.error_dep),Toast.LENGTH_LONG).show();
            departureDateDisplay.requestFocus();
            return false;
        }

        else {
            return true;
        }
    }

    private boolean validTime(){
        if (!time.isFocusable())
        {
            Toast.makeText(BookingActivity.this, getResources().getString(R.string.error_time),Toast.LENGTH_LONG).show();
            time.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }

    private boolean validGreeting(){
        if(!(greeting.getText().length()>0))
        {
            Toast.makeText(BookingActivity.this,getResources().getString(R.string.error_msg),Toast.LENGTH_LONG).show();
            greeting.requestFocus();
            return false;
        }
        else{
            return true;
        }
    }
}
