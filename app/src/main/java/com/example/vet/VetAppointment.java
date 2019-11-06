package com.example.vet;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class VetAppointment extends Fragment {
    RecyclerView recyclerView;
    List<appointModel> appointmentList;
    RecyclerView.Adapter adapter;
    LinearLayout linearLayout;
  //  ArrayList<appointModel>  getdata;
  ArrayList<String>  getdata;
    SharedPreferences prefi;
    String status;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_vet_appointment, container, false);
        recyclerView=view.findViewById(R.id.recycle);
        SharedPreferences pref = getActivity().getSharedPreferences("Mypre", MODE_PRIVATE);
        prefi = getActivity().getSharedPreferences("Mye", MODE_PRIVATE);

        status=pref.getString("appostatus","2");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getdata=new ArrayList<>();
        appointmentList=new ArrayList<>();


        gettingData();

        return view;
    }

//    private void gettingnewData() {
//        String user= prefi.getString("username",null);
//        Call<ArrayList> call=RetrofitClient
//                .getInstance()
//                .getApi()
//                .getappointment(user,status);
//        call.enqueue(new Callback<ArrayList>() {
//            @Override
//            public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
//                for(int i=0; i<response.body().size(); i=i+1)
//                {
//                    getdata= (ArrayList<String>) response.body().get(i);
//                    //  Log.e("location","response "+ getdata.get(0));
//                    appointModel list=new appointModel(
//                            String.valueOf(getdata.get(0)),
//                            String.valueOf(getdata.get(1)),
//                            String.valueOf(getdata.get(2)),
//                            String.valueOf(getdata.get(3)),
//                            String.valueOf(getdata.get(4))
//
//
//                    );
//                    appointmentList.add(list);
//
//
//
//                }
//                adapter=new appointmentAdapter(getActivity(),appointmentList);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList> call, Throwable t) {
//
//            }
//        });
//
//    }




    private void gettingData() {

        Toast.makeText(getActivity(),"camed",Toast.LENGTH_SHORT).show();
       String user= prefi.getString("username",null);
       Call<ArrayList> call=RetrofitClient
               .getInstance()
               .getApi()
               .getappointment(user,status);
       call.enqueue(new Callback<ArrayList>() {
           @Override
           public void onResponse(Call<ArrayList> call, Response<ArrayList> response) {
               for(int i=0; i<response.body().size(); i=i+1)
               {
                   getdata= (ArrayList<String>) response.body().get(i);
                 //  Log.e("location","response "+ getdata.get(0));
                   appointModel list=new appointModel(
                                String.valueOf(getdata.get(0)),
                                String.valueOf(getdata.get(1)),
                                String.valueOf(getdata.get(2)),
                                String.valueOf(getdata.get(3)),
                                String.valueOf(getdata.get(4)),
                                String.valueOf(getdata.get(5))



                        );
                   appointmentList.add(list);



               }
               adapter=new appointmentAdapter(getActivity(),appointmentList);
               recyclerView.setAdapter(adapter);
           }

           @Override
           public void onFailure(Call<ArrayList> call, Throwable t) {

           }
       });

    }

//    private void gettingData() {
//
//        SharedPreferences prefi = getActivity().getSharedPreferences("Mye", MODE_PRIVATE);
//        Toast.makeText(getActivity(),"camed",Toast.LENGTH_SHORT).show();
//       String user= prefi.getString("username",null);
//        Call<appointModel> call=RetrofitClient
//                .getInstance()
//                .getApi()
//                .getappointment(user);
//        call.enqueue(new Callback<appointModel>() {
//            @Override
//            public void onResponse(Call<appointModel> call, Response<appointModel> response) {
//                JSONObject jsonObject= null;
//               // getdata= (ArrayList<String>) response.body().get(i);
//                try {
//                    jsonObject = new JSONObject(response.body().toString());
//                    JSONArray jsonArray=jsonObject.getJSONArray("appointment");
//                    for (int i=0;i<=jsonArray.length();i++){
//                        JSONObject jsonObject1=jsonArray.getJSONObject(i);
//                        appointModel list=new appointModel(
//                                String.valueOf(jsonObject1.get("1")),
//                                String.valueOf(jsonObject1.get("2")),
//                                String.valueOf(jsonObject1.get("3")),
//                                String.valueOf(jsonObject1.get("4"))
//
//                        );
//                        appointmentList.add(list);
//                        adapter=new appointmentAdapter(getActivity(),appointmentList);
//                        recyclerView.setAdapter(adapter);
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//
//
//
//            }
//
//            @Override
//            public void onFailure(Call<appointModel> call, Throwable t) {
//                Toast.makeText(getActivity(),"failed",Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//
//
//    }


}

//  jsonObject1.getString("name"),
//          jsonObject1.getString("date"),
//          jsonObject1.getString("fromtime"),
//          jsonObject1.getString("totime")