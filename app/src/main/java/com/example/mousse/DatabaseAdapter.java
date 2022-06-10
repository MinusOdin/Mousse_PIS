package com.example.mousse;

import android.app.Activity;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseAdapter extends Activity {

    public static final String TAG = "DatabaseAdapter";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();



    public static vmInterface listener;
    public static DatabaseAdapter databaseAdapter;

    public DatabaseAdapter(vmInterface listener){
        this.listener = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        initFirebase();
    }




    public interface vmInterface{
        void setCollectionPublicadas(ArrayList<Receta> recetas);
        void setCollectionHechas(ArrayList<Receta> recetas);
        void setCollectionLikes(ArrayList<Receta> recetas);
        void setCollectionFavs(ArrayList<Receta> recetas);
        void setToast(String t);
        void setSuccesfull(boolean succesfull );
    }

    public void initFirebase(){
        user = mAuth.getCurrentUser();
    }

    public void getCollection10() {
        Log.d(TAG,"Recomenacions updateRecetas: ");
        DatabaseAdapter.db.collection("Recetas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Receta> retrieved_recetas = new ArrayList<Receta>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                retrieved_recetas.add(new Receta( document.getString("nombre"), document.getString("descripcion"), document.getString("user"), (ArrayList<String>) document.get("hashtags"),
                                                (ArrayList<String>) document.get("ingredients"), (ArrayList<String>) document.get("pasos"), document.getId()));
                            }
                            listener.setCollectionPublicadas(retrieved_recetas);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getCollectionByUser(){
        Log.d(TAG,"updateRecetas: " + user.getUid());
        Log.d(TAG, user.getUid());
        String userEmail = Usuario.getCurrentUserEmail();
        DatabaseAdapter.db.collection("Recetas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//where usuario es email
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Receta> retrieved_recetas = new ArrayList<Receta>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(userEmail.equals(document.getString("user"))) {
                                    Log.d(String.valueOf(document.getData()), "oncomplete");
                                    retrieved_recetas.add(new Receta(document.getString("nombre"), document.getString("descripcion"), userEmail, (ArrayList<String>) document.get("hashtags"),
                                                    (ArrayList<String>) document.get("ingredients"), (ArrayList<String>) document.get("pasos"), document.getId()));
                                }
                                else Log.d(TAG, "miss");
                            }
                            listener.setCollectionPublicadas(retrieved_recetas);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getCollectionByUser(String email){
        Log.d(TAG,"updateRecetas: " + user.getUid());
        Log.d(TAG, user.getUid());
        DatabaseAdapter.db.collection("Recetas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//where usuario es email
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Receta> retrieved_recetas = new ArrayList<Receta>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(email.equals(document.getString("user"))){
                                    Log.d(TAG, document.getId() + " => " + document.get("hashtags").toString());
                                    Log.d(String.valueOf(document.getData()), "oncomplete");
                                    retrieved_recetas.add(new Receta( document.getString("nombre"), document.getString("descripcion"), email, (ArrayList<String>) document.get("hashtags"),
                                                    (ArrayList<String>) document.get("ingredients"), (ArrayList<String>) document.get("pasos"), document.getId() ));
                                }
                                else Log.d(TAG, "miss");
                            }
                            listener.setCollectionPublicadas(retrieved_recetas);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void saveReceta(String nombre, String descripcion, ArrayList<String> hashtags, ArrayList<String> ingredients, ArrayList<String> pasos, Uri foto) {

        // Create a new user with a first and last name
        Map<String, Object> note = new HashMap<>();
        note.put("nombre", nombre);
        note.put("user", Usuario.getCurrentUserEmail()); //
        note.put("descripcion", descripcion);
        note.put("hashtags", hashtags);
        note.put("ingredients", ingredients);
        note.put("pasos", pasos);



        Log.d(TAG, "saveDocument");
        // Add a new document with a generated ID
        db.collection("Recetas")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        if (foto != null) {
                            StorageReference fotoRef = storageRef.child("recetas/" + documentReference.getId() + "/image.jpg");
                            fotoRef.putFile(foto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    listener.setToast("Pubication succed.");
                                    listener.setSuccesfull(true);
                                }

                                ;
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    listener.setToast("Failed");
                                    listener.setSuccesfull(false);
                                }
                            });
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        listener.setToast("Pubication failed.");
                    }
                });
    }

    public void saveUser( String email, String password, Uri foto) {
        Map<String, Object> note = new HashMap<>();
        //note.put("nombre", nombre);
        note.put("email", email);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            listener.setToast("Register succed.");
                            db.collection("Usuarios").add(note);
                            if (foto != null) {
                                StorageReference fotoRef = storageRef.child("usuario/" + email + "/image.jpg");
                                fotoRef.putFile(foto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        listener.setToast("Pubication succed.");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        listener.setToast("Failed");
                                    }
                                });
                            }
                            Usuario.setCurrentUser(email, password);
                            listener.setSuccesfull(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            listener.setToast("Register failed.");
                            listener.setSuccesfull(false);
                        }
                    }
                });
    }

    public void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            listener.setToast("Authentication succes.");
                            StorageReference profileRef = storageRef.child("usuario/" + email + "/image.jpg" );
                            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Usuario.setCurrentUser(email, password);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Usuario.setCurrentUser(email, password);
                                }
                            });
                            listener.setSuccesfull(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure " + email, task.getException());
                            listener.setToast("Authentication failed.");
                            listener.setSuccesfull(false);
                        }
                    }
                });
    }


    /*
    public void saveDocumentWithFile (String id, String description, String userid, String path, Date data) {

        Uri file = Uri.fromFile(new File(path));
        StorageReference storageRef = storage.getReference();
        StorageReference audioRef = storageRef.child("audio"+File.separator+file.getLastPathSegment());
        UploadTask uploadTask = audioRef.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return audioRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    saveDocument(id, description, userid, downloadUri.toString(), data);
                } else {
                    // Handle failures
                    // ...
                }
            }
        });


        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                Log.d(TAG, "Upload is " + progress + "% done");
            }
        });
    }

    */

    public HashMap<String, String> getDocuments () {
        db.collection("Recetas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        return new HashMap<>();
    }

    public void getCollectionSearch(String busqueda){
        Log.d(TAG,"updateRecetas: " + user.getUid());
        Log.d(TAG, user.getUid());
        final boolean[] afegida = {false};
        DatabaseAdapter.db.collection("Recetas")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//where usuario es email
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Receta> retrieved_recetas = new ArrayList<Receta>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                afegida[0] = false;
                                for (String palabra : (List<String>) document.get("ingredients")){
                                    if (busqueda.equalsIgnoreCase(palabra) && !afegida[0]) {           //userEmail.equals(document.getString("user"))
                                        //Log.d(TAG, document.getId() + " => " + document.get("hashtags").toString());
                                        //Log.d(String.valueOf(document.getData()), "oncomplete");
                                        retrieved_recetas.add(new Receta(document.getString("nombre"), document.getString("descripcion"), document.getString("user"), (ArrayList<String>) document.get("hashtags"), (ArrayList<String>) document.get("ingredients"), (ArrayList<String>) document.get("pasos"), (String) document.getId() ));
                                        afegida[0] = true;

                                    } else Log.d(TAG, "miss");
                                }
                                for (String palabra : (List<String>) document.get("hashtags")){
                                    if (busqueda.equalsIgnoreCase(palabra) && !afegida[0]) {           //userEmail.equals(document.getString("user"))
                                        //Log.d(TAG, document.getId() + " => " + document.get("hashtags").toString());
                                        //Log.d(String.valueOf(document.getData()), "oncomplete");
                                        retrieved_recetas.add(new Receta(document.getString("nombre"), document.getString("descripcion"), document.getString("user"), (ArrayList<String>) document.get("hashtags"), (ArrayList<String>) document.get("ingredients"), (ArrayList<String>) document.get("pasos"), (String) document.getId()));
                                        afegida[0] = true;
                                    } else Log.d(TAG, "miss");
                                }
                            }
                            if (retrieved_recetas.isEmpty()){
                                listener.setSuccesfull(false);
                            }
                            else{
                                listener.setCollectionPublicadas(retrieved_recetas);
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void isfav() {
        Log.d(TAG, "updateRecetas: " + user.getUid());
        Log.d(TAG, user.getUid());
        final boolean[] is_fav = {false};
        String usuario = Usuario.getCurrentUserEmail();
        DatabaseAdapter.db.collection("Recetas_Fav")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//where usuario es email
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Receta> retrieved_recetas = new ArrayList<Receta>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("user").equals(usuario)){
                                    retrieved_recetas.add(new Receta( document.getString("nombre"), document.getString("descripcion"), document.getString("user"), (ArrayList<String>) document.get("hashtags"), (ArrayList<String>) document.get("ingredients"), (ArrayList<String>) document.get("pasos"), document.getString("Receta")));
                                    is_fav[0] = true;
                                }
                            }
                            listener.setCollectionFavs(retrieved_recetas);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void guardar_receta_fav(String id){
        Map<String, Object> note = new HashMap<>();
        note.put("Receta", id);
        note.put("user", Usuario.getCurrentUserEmail());

        Log.d(TAG, "saveDocument");
        // Add a new document with a generated ID
        db.collection("Recetas_Fav")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        listener.setToast("Pubication succed.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        listener.setToast("Pubication failed.");
                    }
                });
    }

    public void eliminar_fav(String id){
        DatabaseAdapter.db.collection("Recetas_Fav")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//where usuario es email
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("Receta").equals(id) && document.getString("user").equals(Usuario.getCurrentUserEmail())){
                                    db.collection("Recetas_Fav").document(document.getId()).delete();
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void guardar_receta_like(String id) {
        Map<String, Object> note = new HashMap<>();
        note.put("Receta", id);
        note.put("user", Usuario.getCurrentUserEmail());

        Log.d(TAG, "saveDocument");
        // Add a new document with a generated ID
        db.collection("Recetas_Like")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        listener.setToast("Pubication succed.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        listener.setToast("Pubication failed.");
                    }
                });
    }

    public void isLike() {
        Log.d(TAG, "updateRecetas: " + user.getUid());
        Log.d(TAG, user.getUid());
        final boolean[] is_like = {false};
        String usuario = Usuario.getCurrentUserEmail();
        DatabaseAdapter.db.collection("Recetas_Like")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//where usuario es email
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Receta> retrieved_recetas = new ArrayList<Receta>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("user").equals(usuario)){
                                    retrieved_recetas.add(new Receta(document.getString("nombre"), document.getString("descripcion"), document.getString("user"), (ArrayList<String>) document.get("hashtags"), (ArrayList<String>) document.get("ingredients"), (ArrayList<String>) document.get("pasos"), document.getString("Receta")));
                                    is_like[0] = true;
                                }
                            }
                            listener.setCollectionLikes(retrieved_recetas);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void eliminar_like(String id){
        DatabaseAdapter.db.collection("Recetas_Like")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//where usuario es email
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("Receta").equals(id) && document.getString("user").equals(Usuario.getCurrentUserEmail())){
                                    db.collection("Recetas_Like").document(document.getId()).delete();
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void guardar_receta_hecho(String id) {
        Map<String, Object> note = new HashMap<>();
        note.put("Receta", id);
        note.put("user", Usuario.getCurrentUserEmail());

        Log.d(TAG, "saveDocument");
        // Add a new document with a generated ID
        db.collection("Recetas_Hecho")
                .add(note)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        listener.setToast("Pubication succed.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        listener.setToast("Pubication failed.");
                    }
                });
    }

    public void isHecho() {
        Log.d(TAG, "updateRecetas: " + user.getUid());
        Log.d(TAG, user.getUid());
        final boolean[] is_Hecho = {false};
        String usuario = Usuario.getCurrentUserEmail();
        DatabaseAdapter.db.collection("Recetas_Hecho")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//where usuario es email
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<Receta> retrieved_recetas = new ArrayList<Receta>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("user").equals(usuario)){
                                    retrieved_recetas.add(new Receta( document.getString("nombre"),  document.getString("descripcion"), document.getString("user"), (ArrayList<String>) document.get("hashtags"), (ArrayList<String>) document.get("ingredients"), (ArrayList<String>) document.get("pasos"), document.getString("Receta")));
                                    is_Hecho[0] = true;
                                }
                            }
                            listener.setCollectionHechas(retrieved_recetas);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void eliminar_hecho(String id){
        DatabaseAdapter.db.collection("Recetas_Hecho")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//where usuario es email
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.getString("Receta").equals(id) && document.getString("user").equals(Usuario.getCurrentUserEmail())){
                                    db.collection("Recetas_Hecho").document(document.getId()).delete();
                                }
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getCollectionByUserHecho(String email){
        Log.d(TAG,"updateRecetas: " + user.getUid());
        Log.d(TAG, user.getUid());
        DatabaseAdapter.db.collection("Recetas_Hecho")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//where usuario es email
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> idRecetasHechas = new ArrayList<String>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(email.equals(document.getString("user"))){
                                    Log.d(String.valueOf(document.getData()), "oncomplete");
                                    idRecetasHechas.add(document.getString("Receta"));
                                }
                                else Log.d(TAG, "miss");
                            }
                            DatabaseAdapter.db.collection("Recetas")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                ArrayList<Receta> retrieved_recetas = new ArrayList<Receta>() ;
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if(idRecetasHechas.contains(document.getId()) ){
                                                        Log.d(String.valueOf(document.getData()), "oncomplete");
                                                        retrieved_recetas.add(new Receta( document.getString("nombre"),  document.getString("descripcion"), document.getString("user"), (ArrayList<String>) document.get("hashtags"), (ArrayList<String>) document.get("ingredients"), (ArrayList<String>) document.get("pasos"), document.getId()));
                                                    }
                                                    else Log.d(TAG, "miss");
                                                }
                                                listener.setCollectionHechas(retrieved_recetas);
                                            } else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getCollectionByUserLikes(String email){
        Log.d(TAG,"updateRecetas: " + user.getUid());
        Log.d(TAG, user.getUid());
        DatabaseAdapter.db.collection("Recetas_Like")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//where usuario es email
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> idRecetasLikes = new ArrayList<String>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(email.equals(document.getString("user"))){
                                    Log.d(String.valueOf(document.getData()), "oncomplete");
                                    idRecetasLikes.add(document.getString("Receta"));
                                }
                                else Log.d(TAG, "miss");
                            }
                            DatabaseAdapter.db.collection("Recetas")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                ArrayList<Receta> retrieved_recetas = new ArrayList<Receta>() ;
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if(idRecetasLikes.contains(document.getId()) ){
                                                        Log.d(String.valueOf(document.getData()), "oncomplete");
                                                        retrieved_recetas.add(new Receta( document.getString("nombre"),  document.getString("descripcion"), document.getString("user"), (ArrayList<String>) document.get("hashtags"), (ArrayList<String>) document.get("ingredients"), (ArrayList<String>) document.get("pasos"), document.getId()));
                                                    }
                                                    else Log.d(TAG, "miss");
                                                }
                                                listener.setCollectionLikes(retrieved_recetas);
                                            } else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void getCollectionByUserFav(String email){
        Log.d(TAG,"updateRecetas: " + user.getUid());
        Log.d(TAG, user.getUid());
        DatabaseAdapter.db.collection("Recetas_Fav")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {//where usuario es email
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> idRecetasFavs = new ArrayList<String>() ;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(email.equals(document.getString("user"))){
                                    Log.d(String.valueOf(document.getData()), "oncomplete");
                                    idRecetasFavs.add(document.getString("Receta"));
                                }
                                else Log.d(TAG, "miss");
                            }
                            DatabaseAdapter.db.collection("Recetas")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                ArrayList<Receta> retrieved_recetas = new ArrayList<Receta>() ;
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    if(idRecetasFavs.contains(document.getId()) ){
                                                        Log.d(String.valueOf(document.getData()), "oncomplete");
                                                        retrieved_recetas.add(new Receta( document.getString("nombre"),  document.getString("descripcion"), document.getString("user"), (ArrayList<String>) document.get("hashtags"), (ArrayList<String>) document.get("ingredients"), (ArrayList<String>) document.get("pasos"), document.getId()));
                                                    }
                                                    else Log.d(TAG, "miss");
                                                }
                                                listener.setCollectionFavs(retrieved_recetas);
                                            } else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}