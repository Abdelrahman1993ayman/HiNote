package com.example.abdelrahmanayman.simplenote;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NewNoteActivity extends AppCompatActivity {


    private String newDateAndTime ;
    private Calendar calander ;
    private SimpleDateFormat simpleDateFormat ;
    EditText editTextNewNoteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);

        final TextView textViewDate = findViewById(R.id.tvDate);
        editTextNewNoteText = findViewById(R.id.etNewNoteText);
        Button buttonSave = findViewById(R.id.btnSave);
        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        newDateAndTime = simpleDateFormat.format(calander.getTime());
        textViewDate.setText(newDateAndTime);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newNoteText = editTextNewNoteText.getText().toString();
                DBConnection dBconnection = new DBConnection(NewNoteActivity.this);
                dBconnection.InsertNewNote(newNoteText , newDateAndTime);
                Intent intent = new Intent(NewNoteActivity.this , MainActivity.class );
                startActivity(intent);
                Toast.makeText(NewNoteActivity.this, "Long Press On Note To Delete ", Toast.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
