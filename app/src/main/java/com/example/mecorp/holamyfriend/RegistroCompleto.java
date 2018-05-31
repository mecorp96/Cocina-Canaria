package com.example.mecorp.holamyfriend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mecorp.holamyfriend.objetos.FireBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegistroCompleto extends AppCompatActivity {

    private static final int GALLERY_INTENT = 1;
    private OdtUsuario perfil;
    private TextView txtEmail, txtNombre, txtApellidos, txtBebida, txtComida, txtPicante, txtSoltero, txtIngrediente, txtTelefono;
    private Button btnLogin, btnGalery;
    private ImageView imgProfile;
    private FirebaseAuth au;
    private FirebaseDatabase database;
    private StorageReference storageRef;
    private String foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_completo);

        inicializarComponentes();
        inicializarEventos();

        perfil = (OdtUsuario) getIntent().getExtras().getSerializable("usuarioF");
        perfil.setFoto("");

        insAndRefFB();

        registrarEmailYContraseña();

        rellenarTxt();


    }

    private void inicializarComponentes() {
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtApellidos = (TextView) findViewById(R.id.txtApellido);
        txtTelefono = (TextView) findViewById(R.id.txtTeléfono);
        txtBebida = (TextView) findViewById(R.id.txtBebida);
        txtComida = (TextView) findViewById(R.id.txtComida);
        txtPicante = (TextView) findViewById(R.id.txtPicante);
        txtSoltero = (TextView) findViewById(R.id.txtSoltero);
        txtIngrediente = (TextView) findViewById(R.id.txtIngrediente);
        btnLogin = (Button) findViewById(R.id.btnLoginR);
        btnGalery = (Button) findViewById(R.id.btnGalery);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
    }

    private void inicializarEventos() {
        openGalery();
        Login();
    }

    private void insAndRefFB() {
        database = FireBase.database;
        au = FireBase.auth;
        storageRef = FireBase.storageRef;
    }

    private void rellenarTxt() {
        txtEmail.setText(perfil.getCorreo());
        txtNombre.setText(perfil.getNnombre());
        txtApellidos.setText(perfil.getApellidoss());
        txtBebida.setText(perfil.getBebidafav());
        txtComida.setText(perfil.getComidafav());
        txtPicante.setText(perfil.isuFoodType());
        txtSoltero.setText(perfil.isuState());
        txtIngrediente.setText(perfil.getIngredientefav());
        txtTelefono.setText(perfil.getTelefono());
    }

    private void registrarEmailYContraseña() {
        au.createUserWithEmailAndPassword(perfil.getCorreo(), perfil.getContraseña()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Te has registrado con éxito", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = au.getCurrentUser();
                    DatabaseReference usuarioRef = database.getReference(FireBase.USUARIO_REFERENCE_).child(user.getUid());
                    usuarioRef.setValue(perfil);
                } else {
                    Toast.makeText(getApplicationContext(), "Error al registrarte en nuestra base de datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void Login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantalla = new Intent(RegistroCompleto.this, Login.class);
                startActivity(pantalla);
            }
        });
    }

    private void openGalery() {
        btnGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    StorageReference filePath = storageRef.child("recetas").child(uri.getLastPathSegment());

                    filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            foto = "ERROR";
                            Uri fotoDescargada = taskSnapshot.getDownloadUrl();
                            foto = fotoDescargada.toString();
                            Log.i("URL: ", foto);
                            FirebaseUser user = au.getCurrentUser();
                            database.getReference("Usuarios").child(user.getUid()).child("foto").setValue(foto);
                            database.getReference("Usuarios").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    OdtUsuario usu = dataSnapshot.getValue(OdtUsuario.class);
                                    Glide.with(imgProfile.getContext()).load(usu.getFoto()).into(imgProfile);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            Toast.makeText(RegistroCompleto.this, "Imagen Aceptada", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
    }
}
