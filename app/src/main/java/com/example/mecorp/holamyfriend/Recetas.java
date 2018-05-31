package com.example.mecorp.holamyfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.mecorp.holamyfriend.objetos.FireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Recetas extends AppCompatActivity {

    ArrayList<OdtReceta> listaRecetas;
    RecyclerView recyclerRecetas;
    AdaptadorRecetas adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);


        recyclerRecetas = (RecyclerView) findViewById(R.id.recyclerId);
        recyclerRecetas.setLayoutManager(new LinearLayoutManager(this));

        listaRecetas = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapter = new AdaptadorRecetas(listaRecetas);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Seleccion: " + listaRecetas.get(recyclerRecetas.getChildAdapterPosition(view)).getNombreRecycler(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Recetas.this, Detalle_receta.class);

                intent.putExtra("name", listaRecetas.get(recyclerRecetas.getChildAdapterPosition(view)).getNombreRecycler().toString());
                intent.putExtra("descripcion", listaRecetas.get(recyclerRecetas.getChildAdapterPosition(view)).getDescripcionRecycler().toString());
                intent.putExtra("ingredientes", listaRecetas.get(recyclerRecetas.getChildAdapterPosition(view)).getIngredientes().toString());
                intent.putExtra("cocinar", listaRecetas.get(recyclerRecetas.getChildAdapterPosition(view)).getCocinar().toString());
                //intent.putExtra("imagen", listaRecetas.get(recyclerRecetas.getChildAdapterPosition(view)).getImageRecycler());
                startActivity(intent);
            }
        });
        recyclerRecetas.setAdapter(adapter);
        int img0 = R.drawable.papasarrugadas;
        int img1 = R.drawable.mojor;
        int img2 = R.drawable.mojoverde;
        int img3 = R.drawable.tortillacarnaval;
        int img4 = R.drawable.truchasbatata;
        database.getReference(FireBase.RECETASS_REFERENCE_).child("Receta0").child("imageRecycler").setValue(img0);
        database.getReference(FireBase.RECETASS_REFERENCE_).child("Receta1").child("imageRecycler").setValue(img1);
        database.getReference(FireBase.RECETASS_REFERENCE_).child("Receta2").child("imageRecycler").setValue(img2);
        database.getReference(FireBase.RECETASS_REFERENCE_).child("Receta3").child("imageRecycler").setValue(img3);
        database.getReference(FireBase.RECETASS_REFERENCE_).child("Receta4").child("imageRecycler").setValue(img4);
        database.getReference(FireBase.RECETASS_REFERENCE_).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaRecetas.removeAll(listaRecetas);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    OdtReceta receta = snapshot.getValue(OdtReceta.class);
                    listaRecetas.add(receta);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
