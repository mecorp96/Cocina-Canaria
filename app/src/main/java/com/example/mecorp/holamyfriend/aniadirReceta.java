package com.example.mecorp.holamyfriend;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mecorp.holamyfriend.objetos.FireBase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class aniadirReceta extends AppCompatActivity {
    EditText txtNombre, txtDescripcion, txtIngredientes, txtCocinar;
    Button btnAñadir, btnExaminar;
    StorageReference storageRef;
    FirebaseDatabase database;
    static final int GALLERY_INTENT = 1;
    String foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadir_receta);

        txtNombre=(EditText) findViewById(R.id.idNombre);
        txtDescripcion=(EditText) findViewById(R.id.idDescripcion);
        txtIngredientes=(EditText) findViewById(R.id.idIngredientes);
        txtCocinar=(EditText) findViewById(R.id.idCocinar);
        btnAñadir = (Button) findViewById(R.id.idAñadir);
        btnExaminar = (Button) findViewById(R.id.idExaminar);

        database = FirebaseDatabase.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();

        btnExaminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtNombre.getText().toString();
                String descripcion = txtDescripcion.getText().toString();
                String ingredientes = txtIngredientes.getText().toString();
                String cocinar = txtCocinar.getText().toString();
                if(foto.equalsIgnoreCase("ERROR")){
                    Toast.makeText(aniadirReceta.this, "Posiblemente se esté subiendo la imagen... aún", Toast.LENGTH_SHORT).show();

                }else{
                    OdtReceta receta = new OdtReceta(nombre, descripcion, foto, ingredientes, cocinar);
                    database.getReference(FireBase.RECETASS_REFERENCE_).push().setValue(receta).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent pantalla = new Intent(aniadirReceta.this, navigationMenu.class);
                                startActivity(pantalla);
                            }
                        }
                    });
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            StorageReference filePath =  storageRef.child("recetas").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    foto = "ERROR";
                    Uri fotoDescargada = taskSnapshot.getDownloadUrl();
                    foto = fotoDescargada.toString();
                    Log.i("URL: ", foto);
                    Toast.makeText(aniadirReceta.this, "Imagen Aceptada", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
