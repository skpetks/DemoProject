package com.example.appdemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.ui.main.MainFragment;
import com.example.appdemo.ui.main.PaymentModel;
import com.example.appdemo.R;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class Paymentadpater extends RecyclerView.Adapter<Paymentadpater.PaymentViewHolder>  {


    List<PaymentModel> lst;
    Activity context;
    public Paymentadpater(Activity context, List<PaymentModel> objdata) {
        this.context = context;

        this.lst=objdata;
    }

    @Override
    public PaymentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView ;


        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapterviewitem, parent, false);

        return new PaymentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PaymentViewHolder holder, final int position) {
       String rs= context.getString(R.string.Rs);
        holder.tv_Amount.setText(lst.get(position).getPaymentType()+"  "+rs+"   "+lst.get(position).getAmount());


        holder.img_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                android.app.Fragment Fragment =((Activity) context). getFragmentManager().findFragmentById(R.id.container);
                if (Fragment != null && Fragment instanceof MainFragment) {
                    ((MainFragment) Fragment).  positionupdate( position);
                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return lst.size();
    }



    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Amount;
        ImageView img_Delete;

        PaymentViewHolder(View view) {
            super(view);
            tv_Amount = (TextView) view.findViewById(R.id.tv_Amount);

            img_Delete = (ImageView) view.findViewById(R.id.img_Delete);
        }
    }

}
