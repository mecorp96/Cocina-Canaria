package com.example.mecorp.holamyfriend;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mecorp.holamyfriend.objetos.FireBase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class navigationMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Perfil.OnFragmentInteractionListener, BlankFragment.OnFragmentInteractionListener, settings.OnFragmentInteractionListener {
    ArrayList<OdtReceta> listaRecetas;
    private static final int GALLERY_INTENT = 1;
    FirebaseDatabase database;
    RecyclerView recyclerRecetas;
    AdaptadorRecetas adapter;
    StorageReference storageRef;
    FirebaseAuth au;
    FloatingActionButton fab;
    private String foto="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pantallaAdd = new Intent(navigationMenu.this, aniadirReceta.class);
                startActivity(pantallaAdd);
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);




        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_recetas);


        recyclerRecetas = (RecyclerView) findViewById(R.id.recyclerId);
        recyclerRecetas.setLayoutManager(new LinearLayoutManager(this));

        listaRecetas = new ArrayList<>();

        database = FirebaseDatabase.getInstance();

        adapter = new AdaptadorRecetas(listaRecetas);
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Seleccion: " + listaRecetas.get(recyclerRecetas.getChildAdapterPosition(view)).getNombreRecycler(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(navigationMenu.this, Detalle_receta.class);

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

        au = FirebaseAuth.getInstance();
        final TextView name = (TextView) hView.findViewById(R.id.nameHead);

        TextView email = (TextView) hView.findViewById(R.id.emailHead);
        email.setText(au.getCurrentUser().getEmail());
        final ImageView profileImg = (ImageView) hView.findViewById(R.id.profilePhoto);
        database.getReference("Usuarios").child(au.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                OdtUsuario usu = dataSnapshot.getValue(OdtUsuario.class);
                name.setText(usu.getNnombre());
                if (!(usu.getFoto()).equalsIgnoreCase("")) {
                    Log.i("URL: ", usu.getFoto());
                    Glide.with(profileImg.getContext()).load(usu.getFoto()).into(profileImg);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Fragment fragment = null;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            fragment = new settings();
            fab.setVisibility(View.GONE);
            getFragmentManager().beginTransaction().replace(R.id.contenedorPrin, fragment).commit();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Boolean seleccionado = false;

        if (id == R.id.nav_perfil) {
            fragment = new Perfil();
            fab.setVisibility(View.GONE);
            seleccionado = true;
        } else if (id == R.id.nav_recetas) {
            fragment = new BlankFragment();
            fab.setVisibility(View.VISIBLE);
            seleccionado = true;

        } else if (id == R.id.nav_sesion) {
            cerrarSesion();
        }
        if (seleccionado){
            getFragmentManager().beginTransaction().replace(R.id.contenedorPrin, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cerrarSesion(){
         FirebaseAuth.getInstance().signOut();
         finish();
         startActivity(new Intent(navigationMenu.this, Login.class));
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            storageRef = FirebaseStorage.getInstance().getReference();
            Uri uri = data.getData();
            StorageReference filePath =  storageRef.child("recetas").child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    foto = "ERROR";
                    Uri fotoDescargada = taskSnapshot.getDownloadUrl();
                    foto = fotoDescargada.toString();
                    Log.i("URL: ", foto);
                    FirebaseUser user = au.getCurrentUser();
                    database.getReference("Usuarios").child(user.getUid()).child("foto").setValue(foto);
                    Toast.makeText(navigationMenu.this, "Imagen Aceptada", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
