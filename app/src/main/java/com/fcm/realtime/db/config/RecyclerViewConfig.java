package com.fcm.realtime.db.config;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fcm.realtime.db.AddBookActivity;
import com.fcm.realtime.db.R;
import com.fcm.realtime.db.SignInActivity;
import com.fcm.realtime.db.dialog.EditDialog;
import com.fcm.realtime.db.model.Book;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class RecyclerViewConfig extends RecyclerView.Adapter<RecyclerViewConfig.BookView>{
    private Context context;
    private List<Book> books;
    private List<String> keys;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    public RecyclerViewConfig(List<Book> books, List<String> keys, Context context) {
        firebaseAuth = FirebaseAuth.getInstance();

        this.books = books;
        this.keys = keys;
        this.context = context;
    }

    public RecyclerViewConfig() {
    }

    @NonNull
    @Override
    public BookView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_list_item, viewGroup, false);
        return new BookView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookView bookView, final int i) {
        final Book book = books.get(i);
        final String key = keys.get(i);
        bookView.bookName.setText(book.getBookname());
        bookView.authore.setText(book.getAuthore());

        bookView.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    EditDialog dialog = new EditDialog();
                    dialog.intializeData(book, key, context);
                    FragmentManager manager = ((FragmentActivity)context).getSupportFragmentManager();
                    dialog.show(manager, "dialog test");
                }else {
                    context.startActivity(new Intent(context, SignInActivity.class));
                }
            }
        });

        bookView.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    new FirbaseDBConfig(context).deleteBook(key);
                    return true;
                }else {
                    context.startActivity(new Intent(context, SignInActivity.class));
                    return false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class BookView extends RecyclerView.ViewHolder {
        TextView bookName;
        TextView authore;
        public BookView(@NonNull View item) {
            super(item);
            bookName = item.findViewById(R.id.bookName);
            authore = item.findViewById(R.id.authore);
        }
    }

    public void logout (){
        firebaseUser = null;
    }
}
