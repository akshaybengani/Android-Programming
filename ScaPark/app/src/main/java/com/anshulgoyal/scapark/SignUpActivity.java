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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonRegister;
    private TextView textViewLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;

    // Here we used the imported data of com.google.firebase.auth.FirebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        // It will initialise the firebase auth object and now we can use the firebase auth to the firebase server
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            //Start Profile Activity
            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
            startActivity(intent);
        }

        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        textViewLogin = (TextView) findViewById(R.id.textviewLogin);
        editTextEmail =(EditText) findViewById(R.id.editTextEmail);
        editTextPassword =(EditText) findViewById(R.id.editTextPassword);

        //Here we created an instance of the progress Dialog
        progressDialog =new ProgressDialog(this);

        // Here we called on click listner for both the button and textview
        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

    }
    private void registerUser()
    {
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        //Here we check that the user filled the edittext or not
        if(TextUtils.isEmpty(email))
        {
            //Email is empty
            Toast.makeText(SignUpActivity.this,"Please Enter Your email Address",Toast.LENGTH_SHORT).show();
            //To stop the function execution
            return;
        }
        if (TextUtils.isEmpty(password))
        {
            //Password is empty
            Toast.makeText(SignUpActivity.this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
            //To stop the function execution
            return;
        }
        // Now at this stage user has filled the email and password and submitted the form
        // so now it takes time for the user to get to the server
        // so we will use ProgressDialog

        //Here we use a progress bar which shows like a loading function
        progressDialog.setMessage("Registering User... \n Please Wait.");
        //This is for to show the progress Dialog on the screen
        progressDialog.show();

        // This method is used to send email and password data which we stored in the string now we sent it to the server
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // this listner executes this method when the registration is completed
                // with a task object to check weather the registration is successful or not

                if(task.isSuccessful())
                {
                    // task is successful

                    // here we can display a message for successfull registration
                    Toast.makeText(SignUpActivity.this,"Congratulations You have registered Successfully",Toast.LENGTH_SHORT).show();

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
                    // task unsuccessfull

                    //here we can displaly a message for unsuccessfull registration
                    Toast.makeText(SignUpActivity.this,"Sorry... Registration Unsuccessfull please try again...",Toast.LENGTH_SHORT).show();

                    // here we stopped the dialog bar roatating screen
                    progressDialog.dismiss();

                }


            }
        });


    }
    private void loginUser(){

        Intent intent =new Intent(SignUpActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view)
    {
        if (view == buttonRegister)
        {
            // Here we call register user method
            registerUser();
        }
        if(view ==textViewLogin)
        {
            //Here we call Login User method
            loginUser();
        }



    }






}
