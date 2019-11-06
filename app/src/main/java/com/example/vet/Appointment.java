package com.example.vet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Appointment extends AppCompatActivity {
    EditText edittext,editText2,editText3;
    Calendar myCalendar;
    String vetname;
    String fromtime,totime,datee,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
         myCalendar = Calendar.getInstance();
        Bundle extras = getIntent().getExtras();
        vetname=extras.getString("Vetname");
        edittext= (EditText) findViewById(R.id.datepick);
        editText2= (EditText) findViewById(R.id.timepick);
        editText3= (EditText) findViewById(R.id.timepick2);
        TextView textView=findViewById(R.id.Vetname);

        textView.setText(vetname);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setFocusable(false);
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(Appointment.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                editText2.setText(hourOfDay + ":" + minute+":"+"00");
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                int seconds=c.get(Calendar.SECOND);


                TimePickerDialog timePickerDialog = new TimePickerDialog(Appointment.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                editText3.setText(hourOfDay + ":" + minute+":"+"00");
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(Appointment.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

//        DisplayMetrics dm= new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        int width=dm.widthPixels;
//        int height=dm.heightPixels;
//        getWindow().setLayout((int)(width*.8),(int)(height*.6));


    }
    private void updateLabel() {

        String myFormat = "MM-dd-yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));

    }



    public void Gomain(View view) {
        datee=edittext.getText().toString();
        fromtime=editText2.getText().toString();
        totime= editText3.getText().toString();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Mypre", MODE_PRIVATE);
        String username=pref.getString("username",null);
        status="9";


        Call<ResponseBody> call=RetrofitClient
                .getInstance()
                .getApi()
                .appointment(username,vetname,datee,fromtime,totime,status);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s =response.body().string();
                    JSONObject jsonObject = new JSONObject(s);
                    String msg=jsonObject.getString("error");

                    if(msg=="false"){


                        Intent intent=new Intent(getApplicationContext(),BaseFragment.class);
                        Toast.makeText(getApplicationContext(),"Meeting Confirmed",Toast.LENGTH_SHORT).show();
                        startActivity(intent);


                    }
                    else
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(getApplicationContext(),"Meeting not Confirmed",Toast.LENGTH_SHORT).show();
            }
        });






    }
}
