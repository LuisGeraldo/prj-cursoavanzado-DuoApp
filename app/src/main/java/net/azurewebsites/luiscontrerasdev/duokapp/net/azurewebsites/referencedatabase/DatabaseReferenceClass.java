package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseReferenceClass {
    DatabaseReference reference;

    public DatabaseReference getReference() {
        return reference;
    }

    public void setReference(DatabaseReference reference) {
        this.reference = reference;
    }

    public DatabaseReferenceClass(){
        reference = FirebaseDatabase.getInstance().getReference();
    }
}
