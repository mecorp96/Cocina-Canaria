package com.example.mecorp.holamyfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private TextView registro;
    private EditText txtEmail, txtPassword;
    Button loginButton1;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        loginButton1 = (Button) findViewById(R.id.loginButton);
        registro = (TextView) findViewById(R.id.registro);
        loginButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFB();
            }
        });

        registro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent pantallaRegistro = new Intent(Login.this, Registro.class);
                startActivity(pantallaRegistro);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //Intent pantalla1 = new Intent(Login.this, Recetas.class);
                    //startActivity(pantalla1);
                    Intent pantalla1 = new Intent(Login.this, navigationMenu.class);
                    startActivity(pantalla1);
                    //onStart();
                }else{
                    Log.i("SESION: ", "sesion cerrada");
                }
            }
        };


    }

    public void iniciarSesion() {
        Intent pantalla1 = new Intent(Login.this, Recetas.class);
        startActivity(pantalla1);
    }
    private void loginFB(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(txtEmail.getText().toString(), txtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Sesion iniciada con éxito", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Usuario o Contraseña erróneos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //onStart();
    }

    protected void onStart(){
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
}
