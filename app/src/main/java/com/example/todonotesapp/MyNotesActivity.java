package com.example.todonotesapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todonotesapp.Adapter.NotesAdapter;
import com.example.todonotesapp.Model.Notes;
import com.example.todonotesapp.clickListeners.ItemClickListeners;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyNotesActivity extends AppCompatActivity {

    String fullName;
    FloatingActionButton fabAddNotes;
    SharedPreferences sharedPreferences;
    String TAG = "MyNotesActivity";
    RecyclerView recyclerViewNotes;
    ArrayList<Notes> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_notes);

        bindView();
        setUpSharedPreference();
        getIntentData();

        fabAddNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.d("MyNotesActivity","On click performed");
                setUpDialogBox();
            }
        });

       // Log.d("MyNotesActivity",fullName);
        getSupportActionBar().setTitle(fullName);

    }

    private void setUpSharedPreference() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCE_NAME,MODE_PRIVATE);
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        fullName = intent.getStringExtra(AppConstant.FULL_NAME);
        if(TextUtils.isEmpty(fullName)){
            fullName = sharedPreferences.getString(PrefConstant.FULL_NAME,"");
            Log.d(TAG,fullName);
        }
    }

    private void bindView() {
        fabAddNotes = findViewById(R.id.fabAddNotes);


    }

    private void setUpDialogBox() {
        View view = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.add_notes_dialog_layout,null);

        final EditText editTextTitle = view.findViewById(R.id.editTextTitle);
        final EditText editTextDescription = view.findViewById(R.id.editTextDesciption);
        Button buttonSubmit = view.findViewById(R.id.buttonSubmit);
        //Dialog
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();

                if (!TextUtils.isEmpty() && !TextUtils.isEmpty(description)){
                    Notes notes = new Notes();
                    notes.setTille(title);
                    notes.setDescription(description);
                    notesList.add(notes);
                }
                else
                {
                    Toast.makeText(MyNotesActivity.this,"Empty",Toast.LENGTH_SHORT).show();
                }
                dialog.hide();
                setUpRecyclerView();
            }
        });
        dialog.show();
    }

    private void setUpRecyclerView() {
        ItemClickListeners itemClickListeners = new ItemClickListeners() {
            @Override
            public void onClick(Notes notes) {
                Intent intent = new Intent(MyNotesActivity.this,DetailActivity.class);
                intent.putExtra(AppConstant.TITLE,notes.getTille());
                intent.putExtra(AppConstant.DESCRIPTION,notes.getTille());
                startActivity(intent);
            }
        };

        NotesAdapter notesAdapter = new NotesAdapter(notesList,itemClickListeners);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyNotesActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewNotes.setLayoutManager(linearLayoutManager);
        recyclerViewNotes.setAdapter(notesAdapter);
    }
}
