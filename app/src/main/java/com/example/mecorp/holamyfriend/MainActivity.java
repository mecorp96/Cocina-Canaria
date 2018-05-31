package com.example.mecorp.holamyfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button hereToStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hereToStart = (Button) findViewById(R.id.button1);

        hereToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantalla1 = new Intent(MainActivity.this, Login.class);
                startActivity(pantalla1);
            }
        });
        /*FirebaseDatabase database = FirebaseDatabase.getInstance();
        //myRef.push().setValue(7);
        ValueEventListener vel = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //int valor = dataSnapshot.getValue(Integer.class);
                //Log.i("DATOS", valor + "");
                OdtReceta receta = dataSnapshot.getValue(OdtReceta.class);
                //Log.i("Cocinar: ", receta.getCocinar());
                Log.i("RECETA", receta.toString());
                //Log.i("RECETA", dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", databaseError.getMessage());
            }
        };
        //myRef.addValueEventListener(vel);
        //Log.i("KEY", myRef.getKey());
        int imagen = R.drawable.papasarrugadas;
        Log.i("numeroImagen: ", String.valueOf(imagen));

        DatabaseReference recetasRef = database.getReference(FireBase.RECETASS_REFERENCE_);
        recetasRef.child(FireBase.RECETA0_REFERENCE_).addValueEventListener(vel);*/


    }
}
