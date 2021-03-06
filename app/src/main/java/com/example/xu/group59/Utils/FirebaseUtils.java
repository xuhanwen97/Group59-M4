package com.example.xu.group59.Utils;

import android.util.Log;

import com.example.xu.group59.models.Shelter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Firebase utiliy class
 */

class FirebaseUtils {
    private static final String TAG = "firebase_utils";

    private static final FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

    public static void updateShelterList(final FirebaseUtilsListener listener) {

        DatabaseReference shelterReference = mDatabase.getReference(Shelter.shelterListKey);

        shelterReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Collection<Shelter> shelterList = new ArrayList<>(10);
                for (DataSnapshot shelterSnapshot : dataSnapshot.getChildren()) {
                    Shelter tempShelter = new Shelter(shelterSnapshot);
                    shelterList.add(tempShelter);
                }

                listener.onDataLoadComplete(shelterList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                listener.onDataLoadError(databaseError.toException().getMessage());
            }
        });
    }

    public interface FirebaseUtilsListener {
        void onDataLoadComplete(Object loadedData);
        void onDataLoadError(String errorMessage);
    }
}
