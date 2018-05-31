package com.example.mecorp.holamyfriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TextView;

public class Detalle_receta extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_receta);

        TabHost tabs = (TabHost) findViewById(R.id.tabhost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Info");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Ingredientes");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Cocinar");
        tabs.addTab(spec);

        tabs.setCurrentTab(0);

        TextView recetaNombre = (TextView) findViewById(R.id.recetaNombre);
        TextView recetaDescripcion = (TextView) findViewById(R.id.recetaDescripcion);
        TextView recetaIngredientes = (TextView) findViewById(R.id.recetaIngredientes);
        TextView recetaCocinar = (TextView) findViewById(R.id.recetaCocinar);



        Bundle extras = getIntent().getExtras();

        String name = extras.getString("name");
        String descripcion = extras.getString("descripcion");
        String ingredientes = extras.getString("ingredientes");
        String cocinar = extras.getString("cocinar");

        recetaNombre.setText(String.format("%s", name));
        recetaDescripcion.setText(String.format("%s", descripcion));
        recetaIngredientes.setText(String.format("%s", ingredientes));
        recetaCocinar.setText(String.format("%s", cocinar));

    }
}
