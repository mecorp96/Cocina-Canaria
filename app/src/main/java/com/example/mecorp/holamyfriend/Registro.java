package com.example.mecorp.holamyfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mecorp.holamyfriend.objetos.FireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Registro extends AppCompatActivity {

    Button paso2;
    Button salir;
    EditText login, password, passwordRepeat, phone, name, surname;
    TextInputLayout tilLogin, tilPassword, tilPasswordRepeat;
    RadioButton bSoltero, bCasado;
    OdtUsuario usu;
    boolean emailRepeat = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        login=(EditText) findViewById(R.id.ETxtLogin);
        password=(EditText) findViewById(R.id.ETxtPassword);
        passwordRepeat=(EditText) findViewById(R.id.ETxtPasswordRepeat);
        phone=(EditText) findViewById(R.id.ETxtMovilPhone);
        name=(EditText) findViewById(R.id.ETxtName);
        surname=(EditText) findViewById(R.id.ETxtSurname);
        bSoltero=(RadioButton) findViewById(R.id.bSoltero);
        bCasado=(RadioButton) findViewById(R.id.bCasado);


        tilLogin=(TextInputLayout) findViewById(R.id.TilLogin);
        tilLogin.setErrorEnabled(true);

        tilPassword=(TextInputLayout) findViewById(R.id.TilPassword);
        tilPassword.setErrorEnabled(true);

        tilPasswordRepeat=(TextInputLayout) findViewById(R.id.TilPasswordRepeat);
        tilPasswordRepeat.setErrorEnabled(true);


        paso2 = (Button) findViewById(R.id.paso2);

        paso2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paso2();
            }
        });

        salir = (Button) findViewById(R.id.salir);

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantalla2 = new Intent(Registro.this, MainActivity.class);
                startActivity(pantalla2);
            }
        });


    }

    public void paso2() {
        boolean digitos = false;
        boolean mayusculas = false;
        boolean tamaño = false;
        boolean email = false;
        boolean repeat = false;
        boolean bPhone = false;
        boolean bName;
        boolean bSurname;
        boolean bState = false;
        String logo=login.getText().toString();
        if(isValidEmail(logo)) {
            email = true;
            tilLogin.setError(getResources().getString(R.string.noError));
        }else tilLogin.setError(getResources().getString(R.string.loginError));

        String pwd = password.getText().toString();
        for (int i = 0; i < pwd.length(); i++) {
            if (Character.isDigit(pwd.charAt(i))) {
                digitos = true;
            }
            if (Character.isUpperCase(pwd.charAt(i))){
                mayusculas = true;
            }
            if(pwd.length()>5){
                tamaño = true;
            }
        }

        String phn = phone.getText().toString();
        for (int i = 0; i < phn.length(); i++) {
            if (Character.isDigit(phn.charAt(i))) {
                bPhone = true;
            }
        }

        if(name.getText().length() == 0){
            bName = false;
        }else{
            bName = true;
        }

        if(surname.getText().length() == 0){
            bSurname = false;
        }else{
            bSurname = true;
        }

        if(bCasado.isChecked() || bSoltero.isChecked()){
            bState = true;
        }

        if (digitos == true && mayusculas == true && tamaño == true){
            tilPassword.setError(getResources().getString(R.string.noError));
        }
        if (digitos == false || tamaño == false || mayusculas == false){
            tilPassword.setError(getResources().getString(R.string.passwordError));
        }
        String pwdr = passwordRepeat.getText().toString();
            if (pwdr.equals(pwd)){
                repeat = true;
                tilPasswordRepeat.setError(getResources().getString(R.string.noError));
            }else{
                tilPasswordRepeat.setError(getResources().getString(R.string.passwordErrorR));
            }

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.getReference(FireBase.USUARIO_REFERENCE_).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<OdtUsuario> usuarios = new ArrayList<>();
                usuarios.removeAll(usuarios);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    OdtUsuario usuario = snapshot.getValue(OdtUsuario.class);
                    usuarios.add(usuario);
                }
                for (int i=0;i<usuarios.size();i++){
                    if((login.getText().toString()).equalsIgnoreCase(usuarios.get(i).getCorreo())){
                        emailRepeat = false;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (digitos == true && mayusculas == true && tamaño == true && email == true && repeat == true && bPhone == true && bName == true && bSurname == true && bState == true && emailRepeat == true) {
            String tEmail = login.getText().toString();
            String tPassword = password.getText().toString();
            String tPhone = phone.getText().toString();
            String tName = name.getText().toString();
            String tSurname = surname.getText().toString();
            String tState = "";
            if(bSoltero.isChecked()){tState = "Si";}
            if(bCasado.isChecked()){tState = "No";}
            usu = new OdtUsuario(tEmail, tPassword, tPhone, tName, tSurname, "", "", "", tState, "");
            Intent pantalla1 = new Intent(Registro.this, Registro2.class);
            pantalla1.putExtra("usuario", usu);
            startActivity(pantalla1);
        } else if(emailRepeat == false){
            Toast.makeText(getApplicationContext(),"Ese Email ya existe", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Recuerda que todos los campos deben estar rellenados", Toast.LENGTH_SHORT).show();
        }

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
