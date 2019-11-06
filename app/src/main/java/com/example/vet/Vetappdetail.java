package com.example.vet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Vetappdetail extends AppCompatActivity {
    Button btn,bttn2;
    Spinner spinner;
    List<appointModel> appointmentList;
    ArrayList<String>  getdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vetappdetail);
        Intent intent=getIntent();
        appointmentList=new ArrayList<>();
        final String id=intent.getStringExtra("id");
        btn=findViewById(R.id.accept);
        getdata=new ArrayList<>();
        final List<String> categories = new ArrayList<String>();
        spinner=findViewById(R.id.sp1);

        bttn2=findViewById(R.id.reject);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Call<ResponseBody> call=RetrofitClient
                       .getInstance().getApi().changestaus(id,"1");

               call.enqueue(new Callback<ResponseBody>() {
                   @Override
                   public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                       startActivity(new Intent(Vetappdetail.this,Vetlayout.class));
                   }

                   @Override
                   public void onFailure(Call<ResponseBody> call, Throwable t) {

                   }
               });



            }
        });
        bttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Appointment Rejected",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
