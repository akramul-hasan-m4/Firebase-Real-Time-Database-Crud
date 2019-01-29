package com.fcm.realtime.db;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fcm.realtime.db.config.FirbaseDBConfig;
import com.fcm.realtime.db.model.Book;

public class AddBookActivity extends AppCompatActivity implements View.OnClickListener {

    EditText book;
    EditText author;
    Button save;
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        book = findViewById(R.id.bookInput);
        author = findViewById(R.id.authorInput);
        save = findViewById(R.id.btnsave);
        back = findViewById(R.id.btnaddback);

        save.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnsave:
                String bookName = book.getText().toString();
                String authore = author.getText().toString();
                int validSignature = isInputValid(bookName, authore);
                if (validSignature == 1){
                    Book book = new Book();
                    book.setBookname(bookName);
                    book.setAuthore(authore);
                    new FirbaseDBConfig(this).addBook(book);
                }
                break;
            case R.id.btnaddback:
                finish();
                return;
        }
    }

    private int isInputValid(String bookName, String authore){
        int validSignature = 1;
        if (bookName.isEmpty()){
            book.setError("This field is required");
            validSignature ++;
        } if (authore.isEmpty()){
            author.setError("This field is required");
            validSignature ++;
        }
        if(bookName.length() < 5){
            book.setError("book name must be 5 charecter");
            validSignature ++;
        }if(authore.length() < 4){
            author.setError("author name must be 4 charecter");
            validSignature ++;
        }
        return validSignature;
    }
}
