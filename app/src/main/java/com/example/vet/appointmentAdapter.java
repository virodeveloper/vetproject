package com.example.vet;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class appointmentAdapter  extends  RecyclerView.Adapter<appointmentAdapter.appointmentViewHolder>{

    public appointmentAdapter(Context ctx, List<appointModel> appointModelList) {
        this.ctx = ctx;
        this.appointModelList = appointModelList;
    }

    private Context ctx;
        private List<appointModel> appointModelList;



    @NonNull
    @Override
    public appointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(ctx);
        View view=layoutInflater.inflate(R.layout.cardview,null);
        return new appointmentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull appointmentViewHolder holder, int position) {

        final appointModel appointmodel= appointModelList.get(position);
        holder.tx1.setText(appointmodel.getName().toUpperCase());
        holder.tx2.setText("Date: "+appointmodel.getDate());
        holder.tx3.setText("From Time: "+appointmodel.getFrmTime());
        holder.tx4.setText("ToTime: "+appointmodel.getToTime());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(appointmodel.getStatus().equals("9")){
                    Intent intent=new Intent(ctx,Vetappdetail.class);
                    intent.putExtra("id",appointmodel.getId());
                    ctx.startActivity(intent);
                }
                else{
                    Intent intent=new Intent(ctx,History.class);
                    intent.putExtra("id",appointmodel.getId());
                    ctx.startActivity(intent);
                }





            }
        });

}

    @Override
    public int getItemCount() {
        return appointModelList.size();
    }

    class appointmentViewHolder extends RecyclerView.ViewHolder{

        TextView tx1,tx2,tx3,tx4;
        CardView cardView;


        public appointmentViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.card);
            tx1=itemView.findViewById(R.id.tx1);
            tx2=itemView.findViewById(R.id.tx2);
            tx3=itemView.findViewById(R.id.tx3);
            tx4=itemView.findViewById(R.id.tx4);
        }
    }
}
