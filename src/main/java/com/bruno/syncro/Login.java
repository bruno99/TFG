package com.bruno.syncro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLoginBtn;
    TextView mRegister;
    private FirebaseAuth fAuth;
    ProgressBar progressBar;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Passw);
        fAuth = FirebaseAuth.getInstance();
        mLoginBtn = findViewById(R.id.LoginBtn);
        mRegister = findViewById(R.id.Register);
        progressBar = findViewById(R.id.progressBar);



        mLoginBtn.setOnClickListener(new View.OnClickListener() {

            /**
             * Logs in Firebase account if data is correct
             * @param v
             */
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = fAuth.getCurrentUser();
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Es necesario introducir un email");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Es necesario introducir una contraseña");
                    return;
                }


                if(password.length() < 8){
                    mPassword.setError("La contraseña debe contener 8 caracteres o más");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

               fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   /**
                    * Checks if user is logged and sends to main activity
                    * If user is not logged sends error message
                    * @param task
                    */
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //usuario logeado
                            Toast.makeText(Login.this, "Sesión iniciada", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = fAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            //algo ha fallado
                            Toast.makeText(Login.this, "Error de inico de sesión" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            /**
             * Goes to register activity if you don't already have an account
             * @param v
             */
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Registro.class));
            }
        });
    }
}