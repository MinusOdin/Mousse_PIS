package com.example.mousse;

import android.app.Activity;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseAdapter extends Activity {

    public static final String TAG = "DatabaseAdapter";

    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;



    public static vmInterface listener;
    public static DatabaseAdapter databaseAdapter;

    public DatabaseAdapter(vmInterface listener){
        this.listener = listener;
        databaseAdapter = this;
        FirebaseFirestore.setLoggingEnabled(true);
        initFirebase();
    }




    public interface vmInterface{
        void setCollection(ArrayList<Receta> recetas);
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
                                retrieved_recetas.add(new Receta( document.getString("nombre"), document.getString("descripcion") ) );
                            }
                            listener.setCollection(retrieved_recetas);

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
                                if(userEmail.equals(document.getString("user"))){
                                    Log.d(TAG, document.getId() + " => " + document.get("hashtags").toString());
                                    Log.d(String.valueOf(document.getData()), "oncomplete");
                                    retrieved_recetas.add(new Receta( document.getString("nombre"), document.getString("descripcion"), (ArrayList<String>) document.get("hashtags"), (ArrayList<String>) document.get("ingredients")) );
                                }
                                else Log.d(TAG, "miss");
                            }
                            listener.setCollection(retrieved_recetas);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    public void saveReceta(String nombre, String descripcion, ArrayList<String> hashtags, ArrayList<String> ingredients) {

        // Create a new user with a first and last name
        Map<String, Object> note = new HashMap<>();
        note.put("nombre", nombre);
        note.put("user", Usuario.getCurrentUserEmail()); //
        note.put("descripcion", descripcion);
        note.put("hashtags", hashtags);
        note.put("ingredients", ingredients);

        Log.d(TAG, "saveDocument");
        // Add a new document with a generated ID
        db.collection("Recetas")
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

    public void saveUser( String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            listener.setToast("Register succed.");
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
                            Usuario.setCurrentUser(email, password);
                            listener.setSuccesfull(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
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

}



