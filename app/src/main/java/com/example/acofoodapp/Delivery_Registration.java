package com.example.acofoodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Delivery_Registration extends AppCompatActivity {
    TextInputLayout FirstName,Lastname,Email,Password,confirmpass,mobile;

    Button signup, Emaill, Phone;

    FirebaseAuth FAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String firstname,lastname,emailid,password,confpassword,mobileid;
    String role="Chef";

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_registration);
        FirstName = (TextInputLayout) findViewById(R.id.FirstNameDelevery);
        Lastname = (TextInputLayout) findViewById(R.id.LastNameDelevery);
        Email = (TextInputLayout) findViewById(R.id.emailDelevery);
        Password = (TextInputLayout) findViewById(R.id.PassworddDelevery);
        confirmpass = (TextInputLayout) findViewById(R.id.ConfPasswordDelevery);
        mobile = (TextInputLayout) findViewById(R.id.phoneDelevery);
        signup = (Button) findViewById(R.id.signupDelevery);
        Emaill = (Button) findViewById(R.id.btnEmailDelevery);
        Phone = (Button) findViewById(R.id.btnPhoneDelevery);






        databaseReference = FirebaseDatabase.getInstance().getReference("Delevery");
        FAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstname = Objects.requireNonNull(FirstName.getEditText()).getText().toString().trim();
                lastname = Objects.requireNonNull(Lastname.getEditText()).getText().toString().trim();
                emailid = Objects.requireNonNull(Email.getEditText()).getText().toString().trim();
                mobileid = Objects.requireNonNull(mobile.getEditText()).getText().toString().trim();
                password = Objects.requireNonNull(Password.getEditText()).getText().toString().trim();
                confpassword = Objects.requireNonNull(confirmpass.getEditText()).getText().toString().trim();


                if (isValid()){
                    final ProgressDialog mDialog = new ProgressDialog(Delivery_Registration.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registration in progress please wait......");
                    mDialog.show();

                    FAuth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()){
                                String useridd = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("Delevery").child(useridd);
                                final HashMap<String , String> hashMap = new HashMap<>();
                                hashMap.put("Role",role);
                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        HashMap<String , String> hashMap1 = new HashMap<>();
                                        hashMap1.put("Mobile Num",mobileid);
                                        hashMap1.put("First Name",firstname);
                                        hashMap1.put("Last Name",lastname);
                                        hashMap1.put("EmailId",emailid);
                                        hashMap1.put("Password",password);
                                        hashMap1.put("Confirm Password",confpassword);


                                        FirebaseDatabase.getInstance().getReference("Delevery")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        mDialog.dismiss();

                                                        Objects.requireNonNull(FAuth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if(task.isSuccessful()){
                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(Delivery_Registration.this);
                                                                    builder.setMessage("You Have Registered! Make Sure To Verify Your Email");
                                                                    builder.setCancelable(false);
                                                                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {

                                                                            dialog.dismiss();


                                                                        }
                                                                    });
                                                                    AlertDialog Alert = builder.create();
                                                                    Alert.show();
                                                                }else{
                                                                    mDialog.dismiss();
                                                                    ReusableCodeForAll.ShowAlert(Delivery_Registration.this,"Error",task.getException().getMessage());
                                                                }
                                                            }
                                                        });

                                                    }
                                                });

                                    }
                                });
                            }
                        }
                    });
                }
//
            }
        });

        Emaill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( Delivery_Registration. this, Cheflogin.class));
                finish();
            }
        });


    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public boolean isValid(){
        Email.setErrorEnabled(false);
        Email.setError("");
        FirstName.setErrorEnabled(false);
        FirstName.setError("");
        Lastname.setErrorEnabled(false);
        Lastname.setError("");
        Password.setErrorEnabled(false);
        Password.setError("");
        mobile.setErrorEnabled(false);
        mobile.setError("");
        confirmpass.setErrorEnabled(false);
        confirmpass.setError("");


        boolean isValid=false,isValidlname=false,isValidname=false,isValidemail=false,isValidpassword=false,isValidconfpassword=false,isValidmobilenum=false;
        if(TextUtils.isEmpty(firstname)){
            FirstName.setErrorEnabled(true);
            FirstName.setError("Enter First Name");
        }else{
            isValidname = true;
        }
        if(TextUtils.isEmpty(lastname)){
            Lastname.setErrorEnabled(true);
            Lastname.setError("Enter Last Name");
        }else{
            isValidlname = true;
        }
        if(TextUtils.isEmpty(emailid)){
            Email.setErrorEnabled(true);
            Email.setError("Email Is Required");
        }else{
            if(emailid.matches(emailpattern)){
                isValidemail = true;
            }else{
                Email.setErrorEnabled(true);
                Email.setError("Enter a Valid Email Id");
            }
        }
        if(TextUtils.isEmpty(password)){
            Password.setErrorEnabled(true);
            Password.setError("Enter Password");
        }else{
            if(password.length()<8){
                Password.setErrorEnabled(true);
                Password.setError("Password is Weak");
            }else{
                isValidpassword = true;
            }
        }
        if(TextUtils.isEmpty(confpassword)){
            confirmpass.setErrorEnabled(true);
            confirmpass.setError("Enter Password Again");
        }else{
            if(!password.equals(confpassword)){
                confirmpass.setErrorEnabled(true);
                confirmpass.setError("Password Dosen't Match");
            }else{
                isValidconfpassword = true;
            }
        }
        if(TextUtils.isEmpty(mobileid)){
            mobile.setErrorEnabled(true);
            mobile.setError("Mobile Number Is Required");
        }else{
            if(mobileid.length() < 8){
                mobile.setErrorEnabled(true);
                mobile.setError("Invalid Mobile Number");
            }else{
                isValidmobilenum = true;
            }
        }


        isValid = isValidconfpassword && isValidpassword && isValidemail && isValidmobilenum && isValidname && isValidlname;
        return isValid;




    }}

