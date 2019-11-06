package com.example.vet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUser extends AppCompatActivity {
    Button btn1;
    Button btn2;
    Button btn3;
    EditText editText;
    EditText editText2;
    EditText editText3;
    int u;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        btn1=findViewById(R.id.rectangle5);
        btn2=findViewById(R.id.rectangle6);
        btn3=findViewById(R.id.rectangle2);
        editText=findViewById(R.id.rectangles);
        editText2=findViewById(R.id.rectangless);
        editText3=findViewById(R.id.rectanglesss);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u=1;

                btn1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectacolor));
                btn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangle5));


            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectacolor));
                btn1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangle5));
                u=2;

            }
        });



            btn3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(u==1){
                        String t="o";
                        Call<ResponseBody> call=RetrofitClient
                                .getInstance()
                                .getApi()
                                .registerUSER(editText.getText().toString(),editText2.getText().toString(),editText3.getText().toString(),t);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    String s =response.body().string();
                                    JSONObject jsonObject = new JSONObject(s);
                                    String msg=jsonObject.getString("error");
                                    if(msg=="false"){
                                        Toast.makeText(getApplicationContext(),"User Registered Successfully",Toast.LENGTH_SHORT).show();

                                        Intent intent=new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                    }
                                    else
                                        Toast.makeText(getApplicationContext(),"User Not  Registered Successfully",Toast.LENGTH_SHORT).show();



                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),"connection problem",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else if(u==2){
                        String t ="v";
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("Vetname",editText.getText().toString());
                        editor.putInt("method",u);
                        editor.apply();
                         JSONObject jsonObject = null;
                        Call<ResponseBody> call=RetrofitClient
                                .getInstance()
                                .getApi()
                                .registerUSER(editText.getText().toString(),editText2.getText().toString(),editText3.getText().toString(),t);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                try {
                                    String s =response.body().string();
                                    JSONObject jsonObject = new JSONObject(s);
                                    String msg=jsonObject.getString("error");
                                    if(msg=="false"){
                                        Toast.makeText(getApplicationContext(),"User Registered Successfully",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(),Vet_Clinic.class);
                                        startActivity(intent);
                                    }
                                    else
                                        Toast.makeText(getApplicationContext(),"User Not  Registered Successfully",Toast.LENGTH_SHORT).show();




                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }






                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });


                        //Intent intent=new Intent(getApplicationContext(), Login.class);
                       // startActivity(intent);



                    }
                    else
                        Toast.makeText(getApplicationContext(),"Please choose person", Toast.LENGTH_SHORT).show();
                   // Toast.makeText(getApplicationContext(),editText.getText().toString(), Toast.LENGTH_SHORT).show();




                }
            });


    }


}
