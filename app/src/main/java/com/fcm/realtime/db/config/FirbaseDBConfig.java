package com.fcm.realtime.db.config;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.fcm.realtime.db.model.Book;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirbaseDBConfig {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private List<Book> booklist = new ArrayList<>();
    private Context context;

    public interface DataStatus{
        void dataIsLoaded(List<Book> book, List<String> keys);
    }

    public FirbaseDBConfig(Context context) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("books");
        this.context = context;
    }

    public void readBooks(final DataStatus status){
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                booklist.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Book book = keyNode.getValue(Book.class);
                    booklist.add(book);
                }
                status.dataIsLoaded(booklist, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addBook(Book book){
       String key = reference.push().getKey();
       reference.child(key).setValue(book)
               .addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       Log.d("success", "successfully saved");
                   }
               });
    }

    public void updateBook(String key, Book book){
        reference.child(key).setValue(book)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("success", "successfully update");
                        Toast.makeText(context, "successfully update", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void deleteBook(String key){
        try {
            reference.child(key).setValue(null)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(context, "successfully Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
        }catch (Exception e){
            Log.d("DeleteException", e.getMessage());
        }

    }
}
