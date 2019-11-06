package com.example.vet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    TextView txt;
    Button btn,btn1,btn2;
    EditText editText;
    EditText editText2;
    EditText editText3;
    String user;
    String pass;
    boolean doubleBackToExitPressedOnce = false;
    int u=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefi = getApplicationContext().getSharedPreferences("Mye", MODE_PRIVATE);
        user= prefi.getString("username",null);
        pass=  prefi.getString("password",null);
        if(user!=null){
            login(user,pass);
        }
        setContentView(R.layout.customlogin);
        txt=findViewById(R.id.textview);
        editText=findViewById(R.id.rectangle);
        editText2=findViewById(R.id.rectangle3);
        btn1=findViewById(R.id.rectangle5);
        btn2=findViewById(R.id.rectangle6);




      //  login();

       // SharedPreferences prefi = getApplicationContext().getSharedPreferences("Mye", MODE_PRIVATE);
      //  String check=prefi.getString("username","ii");
      //  if(check.equals("ii"))
       // {share();}


        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), RegisterUser.class);
                startActivity(intent);
            }
        });
        btn=findViewById(R.id.rectangle2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                loginn();

            }
        });



    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();

            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    private void share(){
        SharedPreferences prefi = getApplicationContext().getSharedPreferences("Mye", MODE_PRIVATE);

        SharedPreferences.Editor editor = prefi.edit();
        editor.putString("username",editText.getText().toString());
        editor.putString("password",editText2.getText().toString());

        editor.putInt("user",u);

        editor.apply();
    }
    private void login(String user,String pass){


                    Call<ResponseBody> call=RetrofitClient
                            .getInstance()
                            .getApi()
                            .login(user,pass);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String s =response.body().string();
                                JSONObject jsonObject = new JSONObject(s);
                                String msg=jsonObject.getString("error");

                                if(msg=="false"){
                                    String id=jsonObject.getString("username");
                                    String type=jsonObject.getString("type");
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("Mypre", MODE_PRIVATE);

                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putString("username",id);
                                    editor.apply();
                                    if(type.equals("o")){
                                        Intent intent=new Intent(getApplicationContext(),BaseFragment.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Intent intent=new Intent(getApplicationContext(),Vetlayout.class);
                                        startActivity(intent);
                                    }

                                }
                                else
                                    Toast.makeText(getApplicationContext(),"You are Not Registered",Toast.LENGTH_SHORT).show();


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

              /*  else if(u==2){
                    Call<ResponseBody> call=RetrofitClient
                            .getInstance()
                            .getApi()
                            .vetlogin(user,pass);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                String s =response.body().string();
                                JSONObject jsonObject = new JSONObject(s);
                                String msg=jsonObject.getString("error");
                                if(msg=="false"){
                                    Intent intent=new Intent(getApplicationContext(),Vetlayout.class);
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(getApplicationContext(),"You are Not Registered",Toast.LENGTH_SHORT).show();


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
                }*/



    }
    public void Vet(View view) {
        btn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.selectback));
        btn1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangle5));
        u=2;
    }

    public void owner(View view) {
        u=1;

        btn1.setBackgroundDrawable(getResources().getDrawable(R.drawable.selectback));
        btn2.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectangle5));
    }

    private void loginn(){
        String user;
        String pass;
        user=editText.getText().toString();
        pass=editText2.getText().toString();
        SharedPreferences prefi = getApplicationContext().getSharedPreferences("Mye", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefi.edit();
        editor.putString("username",user);
        editor.putString("password",pass);
        editor.apply();

        /*SharedPreferences prefi = getApplicationContext().getSharedPreferences("Mye", MODE_PRIVATE);






        int v=prefi.getInt("user",4);
        if(v==4){
            user=editText.getText().toString();
            pass=editText2.getText().toString();
            share();
        }
        else {
            user=prefi.getString("username","ii");
            pass=prefi.getString("password","ii");
            u=prefi.getInt("user",4);
        }



        int y= u;*/


            Call<ResponseBody> call=RetrofitClient
                    .getInstance()
                    .getApi()
                    .login(user,pass);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String s =response.body().string();
                        JSONObject jsonObject = new JSONObject(s);
                        String msg=jsonObject.getString("error");

                        if(msg=="false"){
                            String id=jsonObject.getString("username");
                            String type=jsonObject.getString("type");
                            SharedPreferences pref = getApplicationContext().getSharedPreferences("Mypre", MODE_PRIVATE);

                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("username",id);
                            editor.apply();
                            if(type.equals("o")){
                                Intent intent=new Intent(getApplicationContext(),BaseFragment.class);
                                startActivity(intent);
                            }
                            else{
                                Intent intent=new Intent(getApplicationContext(),Vetlayout.class);
                                startActivity(intent);
                            }



                        }
                        else
                            Toast.makeText(getApplicationContext(),"You are Not Registered",Toast.LENGTH_SHORT).show();


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

//       else if(u==2){
//            Call<ResponseBody> call=RetrofitClient
//                    .getInstance()
//                    .getApi()
//                    .vetlogin(user,pass);
//            call.enqueue(new Callback<ResponseBody>() {
//                @Override
//                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                    try {
//                        String s =response.body().string();
//                        JSONObject jsonObject = new JSONObject(s);
//                        String msg=jsonObject.getString("error");
//                        if(msg=="false"){
//                            Intent intent=new Intent(getApplicationContext(),Vetlayout.class);
//                            startActivity(intent);
//                        }
//                        else
//                            Toast.makeText(getApplicationContext(),"You are Not Registered",Toast.LENGTH_SHORT).show();
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
//                }
//            });
//        }








    }



}
