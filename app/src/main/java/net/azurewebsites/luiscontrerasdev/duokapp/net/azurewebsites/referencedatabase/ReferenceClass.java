package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ReferenceClass {
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private FirebaseDatabase firebaseDatabase;

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public StorageReference getStorageReference() {
        return storageReference;
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public ReferenceClass(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }
}
