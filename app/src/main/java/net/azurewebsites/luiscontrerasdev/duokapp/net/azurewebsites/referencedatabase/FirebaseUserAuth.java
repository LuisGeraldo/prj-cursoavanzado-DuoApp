package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseUserAuth {
   private FirebaseUser user;
   private FirebaseAuth auth;
   private FirebaseAuth.AuthStateListener authStateListener;


    public FirebaseUser getUser() {
        return user;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseAuth.AuthStateListener getAuthStateListener() {
        return authStateListener;
    }

    public FirebaseUserAuth() {
        auth = FirebaseAuth.getInstance();
        authStateListener = firebaseAuth -> user = firebaseAuth.getCurrentUser();
        user = auth.getCurrentUser();
    }
}
