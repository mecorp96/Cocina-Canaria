package com.example.mecorp.holamyfriend.objetos;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Mecorp on 19/02/2018.
 */

public class FireBase {
    final public static String RECETASS_REFERENCE_ = "Recetas";
    final public static String USUARIO_REFERENCE_ = "Usuarios";
    final public static FirebaseDatabase database = FirebaseDatabase.getInstance();
    final public static FirebaseAuth auth = FirebaseAuth.getInstance();
    final public static StorageReference storageRef = FirebaseStorage.getInstance().getReference();


    public FireBase(){
    }
}
