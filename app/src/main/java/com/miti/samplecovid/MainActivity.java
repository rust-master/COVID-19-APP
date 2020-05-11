package com.miti.samplecovid;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    FloatingActionButton btnFloatingSubmitVAR,btnFloatingLogOutVAR;
    TextView tvEmailVAR,tvNameVAR, DynamicTextViewVAR;
    EditText edtDepVAR,edtEmpVAR,edtPhoneVAR;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    PreferencesManager prefs;

    DatabaseHelper myDb;
    int req = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        myDb=new DatabaseHelper(this);

        prefs = new PreferencesManager(this);

        if (prefs.isPrivacyAccepted()) {

            Intent intent = new Intent(MainActivity.this, DataEntryScreen.class);
            startActivity(intent);
            finish();

        }


        //finding View
        edtDepVAR = findViewById(R.id.edtDepID);
        edtEmpVAR = findViewById(R.id.edtEmpID);
        edtPhoneVAR = findViewById(R.id.edtPhoneID);


        tvEmailVAR = findViewById(R.id.tvEmailID);
        tvNameVAR = findViewById(R.id.tvNameID);
        DynamicTextViewVAR = findViewById(R.id.DynamicTextViewID);


         GoogleSignInAccount signInAccount =  GoogleSignIn.getLastSignedInAccount(this);
         if(signInAccount != null)
         {
             tvNameVAR.setText(signInAccount.getDisplayName());
             tvEmailVAR.setText(signInAccount.getEmail());
         }

        btnFloatingSubmitVAR = findViewById(R.id.btnFloatingSubmitId);
        btnFloatingLogOutVAR = findViewById(R.id.btnFloatingLogOutdId);
        btnFloatingLogOutVAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent =new Intent(MainActivity.this,GSignInScreen.class);
                startActivity(intent);
            }
        });
        btnFloatingSubmitVAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean valueBool = checktheFields();
                if(valueBool) {

                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("users");

                    // Get All Value
                    final String name,email,depName,empID,phoneno;
                    name =tvNameVAR.getText().toString();
                    email=tvEmailVAR.getText().toString();
                    depName=edtDepVAR.getText().toString();
                    empID=edtEmpVAR.getText().toString();
                    phoneno=edtPhoneVAR.getText().toString();


                    UserHelperClass helperClass = new UserHelperClass(name,email,depName,empID,phoneno);

                    reference.child(phoneno).setValue(helperClass);

                    DynamicTextViewVAR.setText("Please Go Next");

                    boolean isInserted = myDb.insertData(phoneno,req);

                    if(isInserted) {
                        edtDepVAR.setEnabled(false);
                        edtEmpVAR.setEnabled(false);
                        edtPhoneVAR.setEnabled(false);
                        prefs.setIsPrivacyAccepted(true);
                        Intent sendDataIntent = new Intent(MainActivity.this, DataEntryScreen.class);
//                        sendDataIntent.putExtra("phNo", phoneno);
                        startActivity(sendDataIntent);
                        finish();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Data not inserted.Storage is Full", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
    }

    private void retriveData(final String phone) {

    }

    private Boolean checktheFields() {


        String UserDepName = edtDepVAR.getText().toString().trim();
        String UserEmpID = edtEmpVAR.getText().toString().trim();
        String UserPhoneNo = edtPhoneVAR.getText().toString().trim();

        if(UserDepName.isEmpty()){
            edtDepVAR.setError("Department Name is Required");
            edtDepVAR.requestFocus();
            return false;
        }
        if(UserEmpID.isEmpty()){
            edtEmpVAR.setError("Employee ID is Required");
            edtEmpVAR.requestFocus();
            return false;
        }
        if(UserPhoneNo.isEmpty()){
            edtPhoneVAR.setError("Phone No is Required");
            edtPhoneVAR.requestFocus();
            return false;
        }
        if(UserPhoneNo.length()<11)
        {
            edtPhoneVAR.setError("Minimum length of phone no should be 11");
            edtPhoneVAR.requestFocus();
            return false;
        }

        return true;

    }
}
