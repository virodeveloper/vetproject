package com.example.vet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class History extends AppCompatActivity {
    String id;
    EditText editText,editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_history);
        editText=findViewById(R.id.diag);
        editText2=findViewById(R.id.medicine);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");

    }

    public void submit(View view) {
        String diao=editText.getText().toString();
        String medicine=editText2.getText().toString();

        if(diao!="" && medicine!=""){

        }
    }
}
