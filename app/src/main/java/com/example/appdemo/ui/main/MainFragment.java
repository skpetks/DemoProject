package com.example.appdemo.ui.main;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdemo.Dialogview.AddPaymentDialog;
import com.example.appdemo.Paymentadpater;
import com.example.appdemo.R;
import com.example.appdemo.utils.FileWrite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    TextView tv_addpayment,txt_TotalAmount;

    List<PaymentModel> lst;
    private RecyclerView paymentrecycle;
    Button btn_save;




    public MainFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
                              Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lst=new ArrayList<PaymentModel>();
        init();
        // TODO: Use the ViewModel
    }

    private void init()  {

        txt_TotalAmount=(TextView)getView().findViewById(R.id.txt_TotalAmount);
        tv_addpayment=(TextView)getView().findViewById(R.id.tv_addpayment);
        btn_save=(Button) getView().findViewById(R.id.btn_save);
        paymentrecycle=(RecyclerView) getView().findViewById(R.id.paymentrecycle);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileWrite.Deletefile();
                JSONArray JObject=new JSONArray();
                for(int i=0;i<lst.size();i++)
                {

                    JSONObject js=new JSONObject();
                    try {
                        js.put("amt", lst.get(i).getAmount());
                        js.put("type", lst.get(i).getPaymentType());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JObject.put(js);

                }


                String ob=JObject.toString();

                FileWrite.writedata(ob);

                Toast.makeText(getActivity(),"File is updated",Toast.LENGTH_SHORT).show();
            }
        });
        tv_addpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     Dialog vieda=popupDialogMessage(getActivity(),"");
             //   vieda.show();

                FragmentManager fm = getActivity().getFragmentManager();

                AddPaymentDialog customTickDialog = new AddPaymentDialog();
                Bundle args = new Bundle();
                customTickDialog.setArguments(args);
                customTickDialog.show(fm, "Dialog Fragment");

            }
        });

        String filejson= FileWrite.readUsingFileInputStream();

        try {
            JSONArray jObj1 = new JSONArray(filejson);
            for (int i = 0; i < jObj1.length(); i++) {
                JSONObject Jsobject = jObj1.getJSONObject(i);

                PaymentModel obj = new PaymentModel();
                obj.setPaymentType(Jsobject.getString("type"));
                obj.setAmount(Jsobject.getInt("amt"));

                lst.add(obj);

            }
        }
        catch (Exception e)
        {

        }
        int amount=0;
        for(int i=0;i<lst.size();i++)
        {

            amount=amount+lst.get(i).getAmount();

        }
        txt_TotalAmount.setText(""+amount);
        ViewUpdate();



    }



    public  void updatedataView(PaymentModel obj)
    {

        if(obj.getAmount()>0)
        {

            boolean status=false;
            for(int i=0;i<lst.size();i++)
            {
                if(lst.get(i).getPaymentType().contains(obj.getPaymentType()))
                {
                    status=true;
                }
            }

            if(status==false)
            {

                lst.add(obj);


                int amount=0;
                for(int i=0;i<lst.size();i++)
                {

                    amount=amount+lst.get(i).getAmount();

                }
                txt_TotalAmount.setText(""+amount);
            }
            else
            {
                Toast.makeText(getActivity(),"Duplicate payment added",Toast.LENGTH_SHORT).show();
            }

            ViewUpdate();

        }


    }




    public  void positionupdate(int pos)
    {
        for(int i=0;i<lst.size();i++)
        {
            if(i==pos)
            {
              lst.remove(i);
            }
        }

        int amount=0;
        for(int i=0;i<lst.size();i++)
        {

            amount=amount+lst.get(i).getAmount();

        }
        txt_TotalAmount.setText(""+amount);
        ViewUpdate();
    }

    public void ViewUpdate()
    {
        Paymentadpater adapter = new Paymentadpater(getActivity(), lst);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        paymentrecycle.setLayoutManager(mLayoutManager);
        paymentrecycle.setItemAnimator(new DefaultItemAnimator());
        paymentrecycle.setAdapter(adapter);
    }

}
