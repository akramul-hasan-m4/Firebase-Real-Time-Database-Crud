package com.fcm.realtime.db;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.fcm.realtime.db.config.FirbaseDBConfig;
import com.fcm.realtime.db.config.RecyclerViewConfig;
import com.fcm.realtime.db.model.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.fabric.sdk.android.Fabric;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        setUpRecyclerView();

        new FirbaseDBConfig(this).readBooks(new FirbaseDBConfig.DataStatus() {
            @Override
            public void dataIsLoaded(List<Book> book, List<String> keys) {
                findViewById(R.id.progressBar).setVisibility(View.GONE);
                recyclerView.setAdapter(new RecyclerViewConfig(book, keys, MainActivity.this));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);

        if (firebaseUser != null){
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(false);
            menu.getItem(3).setVisible(true);
        }else {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
            menu.getItem(2).setVisible(true);
            menu.getItem(3).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.newBook){
            startActivity(new Intent(this, AddBookActivity.class));
            return true;
        } else if (item.getItemId() == R.id.mReg){
            Intent intent = new Intent(this, SignInActivity.class);
            intent.putExtra("regMenu", "isRegMenu");
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.mlogin){
            startActivity(new Intent(this, SignInActivity.class));
            return true;
        }else if (item.getItemId() == R.id.logout){
            firebaseAuth.signOut();
            invalidateOptionsMenu();
            new RecyclerViewConfig().logout();
            return true;
        }
        return false;
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recylerId);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    /*private void testCrushReport(){
        throw new RuntimeException("throw fabric exception");
    }*/
}
