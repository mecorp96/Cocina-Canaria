package com.example.mecorp.holamyfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Registro2 extends AppCompatActivity{

    EditText favFood, favIngredient, favDrink;
    RadioButton bNoPicante, bPicante;
    Button salir, registrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);



        favFood=(EditText) findViewById(R.id.ETxtFavFood);
        favIngredient=(EditText) findViewById(R.id.ETxtIngredient);
        favDrink=(EditText) findViewById(R.id.ETxtFavDrink);
        bNoPicante=(RadioButton) findViewById(R.id.bNoPicante);
        bPicante=(RadioButton) findViewById(R.id.bPicante);


        registrarse = (Button) findViewById(R.id.registrarse);

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registro();
            }
        });

        salir = (Button) findViewById(R.id.salir);

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantalla2 = new Intent(Registro2.this, MainActivity.class);
                startActivity(pantalla2);
            }
        });
    }

    public void registro(){
        boolean bFavFood;
        boolean bFavIngredient;
        boolean bFavDrink;

        if(favFood.getText().length() == 0){
            bFavFood = false;
        }else{
            bFavFood = true;
        }

        if(favIngredient.getText().length() == 0){
            bFavIngredient = false;
        }else{
            bFavIngredient = true;
        }

        if(favDrink.getText().length() == 0){
            bFavDrink = false;
        }else{
            bFavDrink = true;
        }

        if(bFavFood == true && bFavIngredient == true && bFavDrink == true) {
            OdtUsuario usu = (OdtUsuario) getIntent().getExtras().getSerializable("usuario");
            if (bNoPicante.isChecked()) {
                String tFavFood = favFood.getText().toString();
                String tFavIngredient = favIngredient.getText().toString();
                String tFavDrink = favDrink.getText().toString();
                String tFoodType = "No";
                usu.setComidafav(tFavFood);
                usu.setIngredientefav(tFavIngredient);
                usu.setBebidafav(tFavDrink);
                usu.setuFoodType(tFoodType);
                Intent pantalla2 = new Intent(Registro2.this, RegistroCompleto.class);
                pantalla2.putExtra("usuarioF", usu);
                startActivity(pantalla2);
            }
            if (bPicante.isChecked()) {
                String tFavFood = favFood.getText().toString();
                String tFavIngredient = favIngredient.getText().toString();
                String tFavDrink = favDrink.getText().toString();
                String tFoodType = "Si";
                usu.setComidafav(tFavFood);
                usu.setIngredientefav(tFavIngredient);
                usu.setBebidafav(tFavDrink);
                usu.setuFoodType(tFoodType);
                Intent pantalla2 = new Intent(Registro2.this, RegistroCompleto.class);
                pantalla2.putExtra("usuarioF", usu);
                startActivity(pantalla2);
            }
        }else{
            Toast.makeText(getApplicationContext(),"Recuerda que todos los campos deben estar rellenados", Toast.LENGTH_SHORT).show();
        }
    }
}
