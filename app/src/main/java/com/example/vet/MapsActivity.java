package com.example.vet;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener

{

    GoogleMap mGoogleMap;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    MarkerOptions markerOptions;
    Marker mCurrLocationMarker;
    FusedLocationProviderClient mFusedLocationClient;
    Button btn;
    Double Latitude;
    Double Longitude;
    Spinner spinner;
    String tp;
    ArrayList<String>  getdata;
    Context c;
    String[] location;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("Mypre", MODE_PRIVATE);
        SharedPreferences prefi = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        tp=pref.getString("type","none");
        int method=prefi.getInt("method",0);





       // getSupportActionBar().setTitle("Map Location Activity");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        spinner=findViewById(R.id.spinner);
        btn=findViewById(R.id.ok);

        if(method==2){
            spinner.setVisibility(View.INVISIBLE);
            btn.setVisibility(View.VISIBLE);
        }
        else{startSpinner(tp);}

        spinner.setOnItemSelectedListener(this);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        if(!tp.equals("none") ){
            loadlocations();
        }
//        Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_SHORT).show();
    }

    private void startSpinner(String type) {


        List<String> categories = new ArrayList<String>();

        if(type=="category"){

            categories.add("Dog");
            categories.add("cat");
            categories.add("PArrot");
        }
        if(type=="changelocation"){

            categories.add("loca");
            categories.add("cat");
            categories.add("PArrot");
        }
        if(type=="newvet"){

            categories.add("new");
            categories.add("cat");
            categories.add("PArrot");
        }
        if(type=="nearhos"){

            categories.add("hos");
            categories.add("cat");
            categories.add("PArrot");
        }
        if(type=="nearvet"){

            categories.add("vet");
            categories.add("cat");
            categories.add("PArrot");
        }
        // Spinner Drop down elements



        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        spinner.setAdapter(dataAdapter);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if(tp.equals("changelocation")){
            mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    markerOptions= new MarkerOptions();
                    Longitude=latLng.longitude;
                    Latitude=latLng.latitude;
                    markerOptions.position(latLng);
                    markerOptions.title("VETT");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    mGoogleMap.clear();
                    mGoogleMap.addMarker(markerOptions);

                }
            });
        }


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Place current location marker
                Latitude=location.getLatitude();
                Longitude=location.getLongitude();

                LatLng latLng = new LatLng(Latitude, Longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

                //move map camera
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            }
        }
    };

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

   /* public void getlocation(View view) {

        Intent intent=new Intent(getApplicationContext(),BaseFragment.class);
        startActivity(intent);
    }*/



    public void getlocation(View view) {

        Intent intent = new Intent();
        intent.putExtra("latitude", Latitude);
        intent.putExtra("longitude", Longitude);
       /// String s=Latitude.toString();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void loadlocations(){

      Call<ArrayList> call=RetrofitClient
              .getInstance()
              .getApi()
              .location();
      call.enqueue(new Callback<ArrayList>() {
          @Override
          public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
              assert response.body() !=null;

              for(int i=0; i<response.body().size(); i=i+1)
              {
                  getdata= (ArrayList<String>) response.body().get(i);
                  Log.e("location","response"+ getdata);

                 // GoogleMap mgoogle ;


                  final String Vetid=String.valueOf(getdata.get(0));
                  final String Vetname=String.valueOf(getdata.get(1));
                  String lati=String.valueOf(getdata.get(2));
                  String longi=String.valueOf(getdata.get(3));

                  if(lati.isEmpty()){
                      lati="1";
                      longi="1";
                  }

                  double latitude=Double.parseDouble(lati);
                  double longitude=Double.parseDouble(longi);

                  LatLng latlong=new LatLng(latitude,longitude);
                  final MarkerOptions markerOptions= new MarkerOptions();
                  markerOptions.position(latlong);
                  markerOptions.snippet(getdata.get(1));
                  markerOptions.title(getdata.get(1));
                  markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                  mGoogleMap.addMarker(markerOptions);
                  mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                      @Override
                      public void onInfoWindowClick(Marker marker) {

                     //  String vetname=markerOptions.getTitle();


                          Intent intent=new Intent(MapsActivity.this,Appointment.class);
                         // intent.putExtra("Vetid",Vetid);
                          intent.putExtra("Vetname",marker.getTitle());
                          Log.e("markerposition", "" + marker.getTitle());
                        //  Toast.makeText(getApplicationContext(),vetname,Toast.LENGTH_SHORT).show();
                          startActivity(intent);
                      }
                  });


//                  mGoogleMap.addMarker(markerOptions);
//                  mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                      @Override
//                      public void onMapClick(LatLng latLng) {
//
//                          Toast.makeText(getApplicationContext(),"here",Toast.LENGTH_SHORT).show();
//
//                      }
//                  });





                 // location=getdata.toArray(new String[getdata.size()]);

               //   Log.e("location","response"+ getdata.get(i));
              }


              //Log.e("location","response"+ location[1]);


          }

          @Override
          public void onFailure(Call<ArrayList> call, Throwable t) {

              Log.e("location","responseerror");

          }
      });



    }
}
