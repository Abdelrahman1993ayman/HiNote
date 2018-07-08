package com.example.abdelrahmanayman.simplenote;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditNoteActivity extends AppCompatActivity {

    private String newDateAndTime ;
    private Calendar calander ;
    private SimpleDateFormat simpleDateFormat ;
    int IDEdite ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_note_activity);

        final EditText editText = findViewById(R.id.etEditNoteText);
        TextView textView = findViewById(R.id.tvDateedit);
        final Button buttonUpdate = findViewById(R.id.btnupdate);
        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        newDateAndTime = simpleDateFormat.format(calander.getTime());
        textView.setText(newDateAndTime);


        Intent intent = getIntent();
        editText.setText(intent.getStringExtra("NoteText"));
        IDEdite = Integer.parseInt(intent.getStringExtra("id"));

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBConnection dbConnection = new DBConnection(EditNoteActivity.this);
                dbConnection.updateNote(IDEdite , editText.getText().toString());
                Intent intent1 = new Intent(EditNoteActivity.this , MainActivity.class);
                Toast.makeText(EditNoteActivity.this, "Note Updated ", Toast.LENGTH_SHORT).show();
                startActivity(intent1);
            }
        });
    }


}
