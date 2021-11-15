package com.torres.edgar.bircomputehint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    public double over[][][]={{{1.00,4167.00,5000.00,6667.00,10000.00,15833.00,25000.00,45833.00},
            {1.00,6250.00,7083.00,8750.00,12083.00,17917.00,27083.00,47917.00},
            {1.00,8333.00,9167.00,10833.00,14137.00,20000.00,29167.00,50000.00},
            {1.00,10417.00,11250.00,12917.00,16250.00,22083.00,31250.00,52083.00},
            {1.00,12500.00,13333.00,15000.00,18333.00,24167.00,33333.00,54167.00}
    },
            {{1.00,2083.00,2500.00,3333.00,5000.00,7917.00,12500.00,22917.00},
                    {1.00,3125.00,3542.00,4375.00,6042.00,8958.00,13542.00,23958.00},
                    {1.00,4167.00,4583.00,5417.00,7083.00,10000.00,14583.00,25000.00},
                    {1.00,5208.00,5625.00,6458.00,8125.00,11402.00,15625.00,26042.00},
                    {1.00,6250.00,6667.00,7500.00,9167.00,12083.00,16667.00,27083.00}
            }};
    public double pagibig[]={100.00,50.00};
    public double sss[][]={{1000.00,1250.00,1750.00,2250.00,2750.00,3250.00,3750.00,4250.00,4750.00,5250.00,5750.00,6250.00,
            6750.00,7250.00,7750.00,8250.00,8750.00,9250.00,9750.00,10250.00,10750.00,11250.00,11750.00,
            12250.00,12750.00,13250.00,13750.00,14250.00,14750.00,15250.00,15750.00},
            {36.50,54.50,72.70,90.80,109.00,127.20,145.30,163.50,181.70,199.80,218.00,236.20,254.30,
                    272.50,290.70,308.80,327.00,345.20,363.30,381.50,399.70,417.80,436.00,454.20,472.30,490.50,
                    508.70,526.80,545.00,563.20,581.30}};
    public double philhealth[][]={{1000.00,9000.00,10000.00,11000.00,12000.00,13000.00,14000.00,15000.00,16000.00,17000.00,
            18000.00,19000.00,20000.00,21000.00,22000.00,23000.00,24000.00,25000.00,26000.00,27000.00,28000.00,29000.00,30000.00,31000.00,
            32000.00,33000.00,34000.00,35000.00},
            {100.00,112.50,125.00,137.50,150.00,162.50,175.00,187.50,200.00,212.50,225.00,
                    237.50,250.00,262.50,275.00,287.50,300.00,312.50,325.00,337.50,350.00,362.50,375.00
                    ,387.50,400.00,412.50,425.00,437.50}};
    public double addmoto[][]={{0.00,0.00,41.67,208.33,708.33,1875.00,4166.67,10416.67},
            {0.00,0.00,20.83,104.17,354.17,937.50,2083.33,5208.33}};
    public double persyento[]={0.00,5.00,10.00,15.00,20.00,25.00,30.00,32.00};
    public double pg, ph, taxable, SSS, withholding, First, Percent, Over;
    public Spinner spinnerstats, spinnerperiod;
    public String status []={"Single/Married","Single/Married (1)Dependent","Single/Married (2)Dependent",
            "Single/Married (3)Dependent","Single/Married (4)Dependent"};
    public String period[]={"Monthly","Semi-Monthly"};
    public int incomebracket=0, selectedstatus =0,selectedperiod=0, sssBracket =0, philhealthBracket =0;
    public EditText txtTaxableIncome,txtGrossIncome;
    public Double taxableincome,grossincome;
    public TextView txtFirst,txtPercent,txtOver,txtSSS,txtPagibig,txtPhil,txtWith;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtGrossIncome=(EditText)findViewById(R.id.textGrossIncome);
        txtSSS=(TextView)findViewById(R.id.textSSS);
        txtWith=(TextView)findViewById(R.id.textWithholding);
        txtPagibig=(TextView)findViewById(R.id.textPagibig);
        txtPhil=(TextView)findViewById(R.id.textPhilHealth);
        txtTaxableIncome=(EditText) findViewById(R.id.texttaxIncome);
        txtFirst=(TextView) findViewById(R.id.textFirst);
        txtPercent=(TextView) findViewById(R.id.textPercent);
        txtOver=(TextView) findViewById(R.id.textOver);
        populateSpinner();
        selectPeriod();
        selectStatus();
        addtextwatcherTaxableIncome();

        txtGrossIncome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String strEnteredVal = txtGrossIncome.getText().toString();
                if (!strEnteredVal.equals("")) {
                    int num = Integer.parseInt(strEnteredVal);
                    if (num < 1000) {
                        txtGrossIncome.setError("Wrong Gross Income");
                    } else {
                        computeincome();
                        grossincome=Double.parseDouble(txtGrossIncome.getText().toString());
                        pg = Double.parseDouble(txtPagibig.getText().toString());
                        ph = Double.parseDouble(txtPhil.getText().toString());
                        SSS = Double.parseDouble(txtSSS.getText().toString());
                        taxable = (grossincome)-(pg + ph + SSS);
                        txtTaxableIncome.setText(Double.toString(taxable));
                    }
                }
            }
        });
    }
    private void selectPeriod() {
        spinnerperiod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedperiod=i;
                computewtax();
            }@Override
            public void onNothingSelected(AdapterView<?> adapterView) {}});}
    private void addtextwatcherTaxableIncome() {
        txtTaxableIncome.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtTaxableIncome.getText() == null) {
                    txtTaxableIncome.setError("Wrong Taxable Income");
                } else {
                    computewtax();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                grossincome = Double.parseDouble(txtGrossIncome.getText().toString());
                First = Double.parseDouble(txtFirst.getText().toString());
                Percent = Double.parseDouble(txtPercent.getText().toString());
                Over = Double.parseDouble(txtOver.getText().toString());
                withholding = (((grossincome - Over) / Percent) * 10) + First;
                txtWith.setText(Double.toString(withholding));
            }
        });}
    private void selectStatus() {
        spinnerstats.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedstatus =i;
                computewtax();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });}
    private void computewtax(){
        try {
            taxableincome=Double.parseDouble(txtTaxableIncome.getText().toString());
        } catch (NumberFormatException e) {
            taxableincome=0.00;
        }
        incomebracket= getincomebracket(taxableincome, selectedstatus,selectedperiod);
        txtFirst.setText(""+ addmoto[selectedperiod][incomebracket]);
        txtPercent.setText(""+ persyento[incomebracket]);
        txtOver.setText(""+ over[selectedperiod][selectedstatus][incomebracket]);}
    private void computeincome(){
        try {
            grossincome=Double.parseDouble(txtGrossIncome.getText().toString());
        } catch (NumberFormatException e) {
            grossincome=0.00;
        }
        sssBracket = getSSSBracket(grossincome,selectedstatus);
        txtSSS.setText("" + sss[1][sssBracket]);
        philhealthBracket = getPhilhealthBracket(grossincome,selectedstatus);
        txtPhil.setText("" + philhealth[1][philhealthBracket]);
        txtPagibig.setText("" + pagibig[selectedperiod]  );
    }private int getPhilhealthBracket(double income, int period) {
        int klas=0;
        int x=27,cntr=0;
        do
        {if (income==0.00)
        {klas=0;break;
        }
            if (income>= philhealth[period][cntr] && income< philhealth[period][cntr+1]) {
                klas = cntr;break;}
            else
                cntr++;}
        while (cntr<x);
        if (cntr==x){
            klas=x;}
        return klas;
    };
    private int getincomebracket(double income, int status, int period) {
        int klas=0;
        int x=7,cntr=0;
        do
        {if (income==0.00)
        {klas=0;break;
        }if (income>= over[period][status][cntr] && income< over[period][status][cntr+1]) {
            klas = cntr;
            break;
        } else cntr++;
        } while (cntr<x);
        if (cntr==x){
            klas=x;
        }return klas;
    };
    private int getSSSBracket(double income, int period) {
        int klas=0;
        int x=30,cntr=0;
        do
        {if (income==0.00)
        {klas=0;break;
        }if (income>= sss[period][cntr] && income< sss[period][cntr+1]) {
            klas = cntr;
            break;
        } else
            cntr++;}
        while (cntr<x);
        if (cntr==x){
            klas=x;
        }return klas;};
    private void populateSpinner(){
        spinnerstats =(Spinner)findViewById(R.id.spinnerStatus);

        List<String> listStatus = new ArrayList<String>();
        for (int x=0;x<status.length;x++) {
            listStatus.add(status[x]);}
        ArrayAdapter<String> dataAdapterStatus = new ArrayAdapter<String>(this,
                R.layout.spinner_item, listStatus);
        dataAdapterStatus.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinnerstats.setAdapter(dataAdapterStatus);
        spinnerstats.setPrompt("Exemption Status");

        spinnerperiod = (Spinner)findViewById(R.id.spinnerPeriod);
        List<String> listPeriod = new ArrayList<String>();
        for (int x=0;x<period.length;x++) {
            listPeriod.add(period[x]);
        }
        ArrayAdapter<String> dataAdapterPeriod = new ArrayAdapter<String>(this,
                R.layout.spinner_item, listPeriod);
        dataAdapterPeriod.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinnerperiod.setAdapter(dataAdapterPeriod);
        spinnerperiod.setPrompt("Pay Period ");
    }
}
