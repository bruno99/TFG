package com.bruno.syncro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText mFullName,mEmail,mPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    //FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mFullName   = findViewById(R.id.Name);
        mEmail      = findViewById(R.id.Email);
        mPassword   = findViewById(R.id.Passw);
        mRegisterBtn= findViewById(R.id.RegisterBtn);
        mLoginBtn   = findViewById(R.id.Login);

        fAuth = FirebaseAuth.getInstance();
        //fStore = FirebaseFirestore.getInstance();

        //si el usuario ya está registrado le mandamos a la página principal
         if(fAuth.getCurrentUser() != null){
             startActivity(new Intent(getApplicationContext(),MainActivity.class));
             finish();
         }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String fullName = mFullName.getText().toString();


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

                //Registrar al usuario en Firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //Usuario creado
                            Toast.makeText(Registro.this, "User Created.", Toast.LENGTH_SHORT).show();
                            //Si se registra correctamente se le manda a la actividad principal
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        //Registro fallado
                        }else {
                            Toast.makeText(Registro.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
         //ya tienes cuenta
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}