package com.example.vet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Vetlayout extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    ActionBarDrawerToggle toogle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vetlayout);
        drawer=findViewById(R.id.navga);
        navigationView=findViewById(R.id.naviagate);
        Toolbar toolbar=findViewById(R.id.toolb);

        //linear=findViewById(R.id.toolb);
        toogle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.open,R.string.close);
        drawer.addDrawerListener(toogle);

        toogle.syncState();
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        // CategoriesFragment categoriesFragment=new CategoriesFragment();
        tx.replace(R.id.frameLayout, new ProfileFragment());
        tx.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(toogle.onOptionsItemSelected(item)){


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id=menuItem.getItemId();
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Mypre", MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        if(id==R.id.it1){
            //  Toast.makeText(getApplicationContext(),"waqas",Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(intent);

            editor.putString("type","category");
            editor.apply();
        }
        else  if(id==R.id.it2){
            Toast.makeText(getApplicationContext(),"waqas",Toast.LENGTH_SHORT).show();


            Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(intent);
            editor.putString("type","changelocation");
            editor.apply();
        }
        else  if(id==R.id.it4){
            editor.putString("appostatus","9");
            editor.apply();

            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frameLayout, new VetAppointment())
                    .commit();
        }
        else  if(id==R.id.it8){
            editor.putString("appostatus","1");
            editor.apply();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frameLayout, new VetAppointment())
                    .commit();
        }
        else  if(id==R.id.it5){
            Toast.makeText(getApplicationContext(),"waqas",Toast.LENGTH_SHORT).show();

            Intent intent=new Intent(getApplicationContext(),Vet_Clinic.class);
            startActivity(intent);

        }
        else  if(id==R.id.it7){

           startActivity(new Intent(this,History.class));
        }
        else  if(id==R.id.it6){
            Toast.makeText(getApplicationContext(),"waqas",Toast.LENGTH_SHORT).show();

            SharedPreferences prefi = getApplicationContext().getSharedPreferences("Mye", MODE_PRIVATE);
            prefi.edit().clear().apply();


            Intent intent=new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
