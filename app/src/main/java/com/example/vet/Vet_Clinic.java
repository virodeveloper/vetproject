package com.example.vet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Vet_Clinic extends AppCompatActivity {



    private ImageView imageView;
    private  static final int IMG_REQUEST=2;
    private Bitmap  bitmap;
    Double latitude;
    Double longitude;
    Button btn;
    Button btn2;
    private String vetid=Integer.toString(1);
    private final static int LOCATION_CODE=1;
    private EditText Clinicname;
    TextView Address;
    String VetId;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vet__clinic);

        btn=findViewById(R.id.btn);
        Address=findViewById(R.id.map_address);

        Clinicname=findViewById(R.id.clinic_name);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        id=pref.getString("Vetname",null);
        getvetid();




      /*  btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });*/







    }




    public void selectImg(View view) {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMG_REQUEST);
    }
    public void getvetid(){

        Call<ResponseBody> call2=RetrofitClient
                .getInstance()
                .getApi()
                .vetid(id);
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call2, Response<ResponseBody> response) {

                try {
                    String s =response.body().string();
                    JSONObject jsonObject = new JSONObject(s);
                    String msg=jsonObject.getString("error");
                    if(msg=="false"){

                        VetId=jsonObject.getString("id");



                    }
                    else
                        Toast.makeText(getApplicationContext(),"Missed user id",Toast.LENGTH_SHORT).show();



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
    }
    public void selectLocation(View view) {


        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
        startActivityForResult(intent,LOCATION_CODE);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null
                && data.getData()!=null){


            Uri path=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                ImageView  imageView=findViewById(R.id.img1);
                imageView.setImageBitmap(bitmap);

                // imageView.setVisibility(View.VISIBLE);


            } catch (IOException e) {

                Toast.makeText(getApplicationContext(),"Cannot",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


        }
     else if(requestCode==LOCATION_CODE && resultCode==RESULT_OK && data!=null ){
         Double d=1.0;

            latitude=data.getDoubleExtra(("latitude"),d);
            longitude=data.getDoubleExtra(("longitude"),d);

            Address.setText(String.valueOf(latitude)+" ,"+String.valueOf(longitude));



        }
     else Toast.makeText(getApplicationContext(),"Data is null",Toast.LENGTH_SHORT).show();

    }

    private  String imageToString(){

        ByteArrayOutputStream byteArrayOutputStream= new ByteArrayOutputStream();
       bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imaebyte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imaebyte,Base64.DEFAULT);

    }


    public void test(View view) {

      //  String g=lo;
       // Toast.makeText(getApplicationContext(),g,Toast.LENGTH_SHORT).show();
    }

    public void uploadImage(View view) {


        String Image=imageToString();
        String clinicname=Clinicname.getText().toString();
        String Vetid=VetId;
        Call<ImageClass> calli=RetrofitClient
                .getInstance()
                .getApi()
               // .uploadData(Vetid,Image);
                .uploadData(Vetid,Image,clinicname,String.valueOf(latitude),String.valueOf(longitude));
        calli.enqueue(new Callback<ImageClass>() {
            @Override
            public void onResponse(Call<ImageClass> calli, Response<ImageClass> response) {

                ImageClass imageClass=response.body();
                Toast.makeText(getApplicationContext(),imageClass.getResponse(),Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),Login.class);
                      startActivity(intent);
                // ImageClass imageClass=response.body();
              //  Toast.makeText(getApplicationContext(),imageClass.getResponse(),Toast.LENGTH_SHORT).show();
//                try {
//                  //  String s =response.body().getResponse();
//                    ImageClass imageClass=response.body();
//                    String imm=imageClass.getResponse();
//                    Toast.makeText(getApplicationContext(),imageClass.getResponse(),Toast.LENGTH_SHORT).show();
//                    JSONObject jsonObject = new JSONObject(imm);
//                    String msg=jsonObject.getString("error");
//                    if(msg=="false"){
//                        Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_SHORT).show();
//
//                        Intent intent=new Intent(getApplicationContext(),Login.class);
//                        startActivity(intent);
//                    }
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


            }

            @Override
            public void onFailure(Call<ImageClass> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
}
