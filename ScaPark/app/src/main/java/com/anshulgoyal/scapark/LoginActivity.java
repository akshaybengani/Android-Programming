package com.anshulgoyal.scapark;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewRegister;

    // Here we declared a ProgressDialog variable
    private ProgressDialog progressDialog;

    // Here we Declare a FirebaseAuth variable
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Here we connect XML and java
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textViewRegister = (TextView) findViewById(R.id.textviewRegister);

        //Here we initialised the progress Dialog
        progressDialog=new ProgressDialog(this);

        // Here we initialised FirebaseAuth object
        firebaseAuth = FirebaseAuth.getInstance();

        //Check User is already logined on not
        if(firebaseAuth.getCurrentUser()!=null)
        {
            //Start Profile Activity
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }

        // Here we called on click listner for button and textview
        buttonLogin.setOnClickListener(this);
        textViewRegister.setOnClickListener(this);



    }

    private void loginUser()
    {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email))
        {
            //Email field is empty
            Toast.makeText(LoginActivity.this, "Please Enter Email address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password))
        {
            //Password Filed is empty
            Toast.makeText(LoginActivity.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        // Here User Filled the form now the data will be sent to the server to check
        // Due to the server connection we need a progressDialog used to wait for the next step

        // Here we set the logging message in the progress dialog
        progressDialog.setMessage("Logging in please wait..");
        // Here we show the progress Dialog message
        progressDialog.show();

        // Now its time to check the user login data with the server data

        //Here we used this method to send the data of the login email and password filled by the user
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful())
                {
                    // Login Successfull
                    Toast.makeText(LoginActivity.this,"Congratulations Login Successfull",Toast.LENGTH_SHORT).show();
                    // here we stopped the dialog bar roatating screen
                    progressDialog.dismiss();

                    //This is used to finish the current Activity
                    finish();

                    //Now we will start Profile Activity as we are in onCompleteListner so we cannot use the Activity name we have to use getApplicationContext
                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    //Login Unsuccessfull
                    Toast.makeText(LoginActivity.this,"Login Unsuccessfull please try again...",Toast.LENGTH_SHORT).show();
                    // here we stopped the dialog bar roatating screen
                    progressDialog.dismiss();
                }

            }
        });


    }
    private void registerUser()
    {
        //Start registerUser Activity
        Intent intent =new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view)
    {
        if (view == buttonLogin)
        {
            // Here we call loginUser method
            loginUser();
        }
        if (view == textViewRegister)
        {
            // Here we call registerUser method
            registerUser();
        }

    }




}
