package com.miti.samplecovid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.Colors;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class DataEntryScreen extends AppCompatActivity {

//    String  [] SPINNERLIST ={"Lahore" , "Sahiwal" , "Okara" ,"Karachi" , "Multan" , "Rawalpindi"};

    FloatingActionButton btnSendDataVAR;
    CheckBox checkboxVAR1,checkboxVAR2,checkboxVAR3,checkboxVAR4,checkboxVAR5;
    EditText edtCityVAR,edtCountryVAR;


    FirebaseDatabase rootNode;
    DatabaseReference reference;

    DatabaseHelper myDb;

    private String  ch1,ch2,ch3,ch4,ch5;
    //private String phone_No;
    String localPhoneNO;

    @Override
    protected void onStart() {
        super.onStart();
        Cursor res = myDb.getAllData();
        if(res.getCount() == 0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext())
        {
            buffer.append(""+ res.getString(0));

            localPhoneNO = buffer.toString();
            Toast.makeText(this, localPhoneNO+"", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // checkbox
        checkboxVAR1 = findViewById(R.id.checkboxID1);
        checkboxVAR2 = findViewById(R.id.checkboxID2);
        checkboxVAR3 = findViewById(R.id.checkboxID3);
        checkboxVAR4 = findViewById(R.id.checkboxID4);
        checkboxVAR5 = findViewById(R.id.checkboxID5);
        // checkbox



        // Edit Text
        edtCountryVAR = findViewById(R.id.edtCountryID);
        edtCityVAR = findViewById(R.id.edtCityID);
        // Edit Text

        myDb=new DatabaseHelper(this);

//        Intent iin= getIntent();
//        Bundle b = iin.getExtras();
//
//        if(b!=null)
//        {
//            phone_No =(String) b.get("phNo");
//
//
//        }


        btnSendDataVAR = findViewById(R.id.btnSendDataId);
        btnSendDataVAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean valueBool = checktheFields();
                if(valueBool)
                {
                    String req = "requests";
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("users");

                    // Get All Value
                    String q1,q2,q3,q4,q5,edtCity,edtCountry;
                    q1 =ch1;
                    q2=ch2;
                    edtCity=edtCityVAR.getText().toString();
                    q3=ch3;
                    edtCountry = edtCountryVAR.getText().toString();
                    q4=ch4;
                    q5=ch5;

                    UPhoneHelperClass uPhoneHelperClass = new UPhoneHelperClass(q1,q2,edtCity,q3,edtCountry,q4,q5);

                    reference.child(localPhoneNO).child(req).setValue(uPhoneHelperClass);

                    Toast.makeText(DataEntryScreen.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                    Intent doneIntent = new Intent(DataEntryScreen.this,DoneScreen.class);
                    startActivity(doneIntent);
                    finish();

                }

            }
        });


//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,SPINNERLIST);
//        MaterialBetterSpinner betterSpinner = findViewById(R.id.android_mtSpinner);
//        betterSpinner.setTextColor(Color.WHITE);
//        betterSpinner.setBaseColor(Color.WHITE);
//        betterSpinner.setAdapter(arrayAdapter);
    }

    private Boolean checktheFields() {

        String edtCityU = edtCityVAR.getText().toString().trim();
        String edtCountryU = edtCountryVAR.getText().toString().trim();

        if(checkboxVAR1.isChecked())
        {
            ch1 = "Yes";
        }
        else {
            ch1="No";
        }

        if(checkboxVAR2.isChecked())
        {
            ch2 = "Yes";
            if(edtCityU.equals("None")){
                edtCityVAR.setError("Please Enter City !");
                edtCityVAR.requestFocus();
                return false;
            }
        }
        else {
            ch2="No";
        }

        if(checkboxVAR3.isChecked())
        {
            ch3 = "Yes";
            if(edtCountryU.equals("None")){
                edtCountryVAR.setError("Please Enter Country");
                edtCountryVAR.requestFocus();
                return false;
            }
        }
        else {
            ch3="No";
        }
        if(checkboxVAR4.isChecked())
        {
            ch4 = "Yes";
        }
        else {
            ch4="No";
        }
        if(checkboxVAR5.isChecked())
        {
            ch5 = "Yes";
        }
        else {
            ch5 = "No";
        }

        return true;
    }




}
