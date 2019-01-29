package com.fcm.realtime.db.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fcm.realtime.db.R;
import com.fcm.realtime.db.config.FirbaseDBConfig;
import com.fcm.realtime.db.model.Book;

public class EditDialog extends AppCompatDialogFragment implements View.OnClickListener {
    String bookName;
    String authoreName;
    String key;
    Context context;

    EditText editBookName ;
    EditText editAuthoreName ;
    EditText editKey;
    Button btnUpdate ;

   /* if u want full screen dialog then apply this*/
 /*  @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_dailog, null);

        editBookName = view.findViewById(R.id.editBookName);
        editAuthoreName = view.findViewById(R.id.editAuthoreName);
        editKey = view.findViewById(R.id.editKey);
        btnUpdate = view.findViewById(R.id.btnupdate);
        btnUpdate.setOnClickListener(this);

        editBookName.setText(bookName);
        editAuthoreName.setText(authoreName);
        editKey.setText(key);

        TextView title = new TextView(context);
        title.setText("Update Book");
        title.setBackgroundColor(Color.BLUE);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(30);

        builder.setView(view)
                .setCustomTitle(title);
              /*  .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });*/
        return builder.create();
    }

    public void intializeData(Book book , String key, Context context) {
        this.bookName = book.getBookname();
        this.authoreName = book.getAuthore();
        this.key = key;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        String bookKey = editKey.getText().toString();
        String bookNam = editBookName.getText().toString();
        String authore = editAuthoreName.getText().toString();
        Book book = new Book();
        book.setBookname(bookNam);
        book.setAuthore(authore);
        new FirbaseDBConfig(context).updateBook(bookKey, book);
        dismiss();
    }
}
