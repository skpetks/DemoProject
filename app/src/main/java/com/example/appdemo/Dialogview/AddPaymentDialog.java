package com.example.appdemo.Dialogview;

import android.app.Activity;
import android.app.DialogFragment;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appdemo.R;
import com.example.appdemo.ui.main.MainFragment;
import com.example.appdemo.ui.main.PaymentModel;

import java.util.ArrayList;
import java.util.List;

public class AddPaymentDialog
        extends DialogFragment implements View.OnClickListener{

    ArrayList<String> tick_option;

    ArrayList<Long> tick_scanoption;
  //  MailTrekTickAdapter trekTickAdapter;




    Spinner spn_paymentype;
    EditText ed_amount,ed_bankinfor,ed_transaction;
    Button tv_cancel,txt_ok;


    public AddPaymentDialog() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getDialog().getWindow().setAttributes(params);

        return inflater.inflate(R.layout.dialogpopup, container, false);
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
//        ll_cancel.setOnClickListener(this);
    }

    public void init() {

        ed_amount=(EditText)getView().findViewById(R.id.ed_amount);
        ed_bankinfor=(EditText)getView().findViewById(R.id.ed_bankinfor);
        ed_transaction=(EditText)getView().findViewById(R.id.ed_transaction);
        tv_cancel=(Button)getView().findViewById(R.id.tv_cancel);
        txt_ok=(Button)getView().findViewById(R.id.txt_ok);
        spn_paymentype=(Spinner) getView().findViewById(R.id.spn_paymentype);

        ed_bankinfor.setVisibility(View.GONE);
        ed_transaction.setVisibility(View.GONE);
        txt_ok.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        List<String> PaymentType = new ArrayList<String>();
        PaymentType.add("Cash");
        PaymentType.add("Bank Transfer");
        PaymentType.add("Credit Card");



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, PaymentType);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_paymentype.setAdapter(dataAdapter);


        spn_paymentype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==1)
                {
                    ed_bankinfor.setVisibility(View.VISIBLE);
                    ed_transaction.setVisibility(View.VISIBLE);
                }
                else
                {
                    ed_bankinfor.setVisibility(View.GONE);
                    ed_transaction.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });
    }




    //Method to listen Cliclk Listeners
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel: {


//                android.app.Fragment Fragment = getFragmentManager().findFragmentById(R.id.ll_fragment_container);
//                if (Fragment != null && Fragment instanceof MailTrek_Report) {
//                    ((MailTrek_Report) Fragment).syncdata();
//                }
                dismiss();
            }
            break;
            case R.id.txt_ok: {

                PaymentModel pm =new PaymentModel();


                String amount=ed_amount.getText().toString();


                if(amount==null  ||amount.length()==0)
                {
                    Toast.makeText(getActivity(),"Please enter valid amount",Toast.LENGTH_SHORT).show();
                    dismiss();
                    return;
                }

                int amt=Integer.parseInt(amount);
                if(amt==0  )
                {
                    Toast.makeText(getActivity(),"Please enter valid amount",Toast.LENGTH_SHORT).show();

                    return;
                }

                String Paymenttype=spn_paymentype.getSelectedItem().toString();
                pm.setAmount(amt);
                pm.setPaymentType(Paymenttype);

                if(Paymenttype.contains("Bank Transfer"))
                {

                    String bank=ed_bankinfor.getText().toString();
                    String inf=ed_transaction.getText().toString();
                    if(bank!=null && inf!=null)
                    {
                        if(bank.length()==0 || inf.length()==0)
                        {
                            Toast.makeText(getActivity(),"Bank or transaction info is missing",Toast.LENGTH_SHORT).show();

                            return;
                        }



                    }
                }
                android.app.Fragment Fragment =((Activity) getActivity()). getFragmentManager().findFragmentById(R.id.container);
                if (Fragment != null && Fragment instanceof MainFragment) {
                    ((MainFragment) Fragment).  updatedataView( pm);
                }

                dismiss();
            }
            break;
        }
    }
}
