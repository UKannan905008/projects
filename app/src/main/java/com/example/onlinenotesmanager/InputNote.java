package com.example.onlinenotesmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class InputNote extends AppCompatActivity{
    private TextView save;
    private EditText title,desc;
    private ProgressBar progressBar;
    private FirebaseAuth fAuth;
    private DatabaseReference databaseReference;
    private long timestamp1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_note);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        progressBar=findViewById(R.id.progressbarNOTE);
        save=findViewById(R.id.savebtn);
        title=findViewById(R.id.title);
        desc=findViewById(R.id.description);
        progressBar.setVisibility(View.INVISIBLE);
        fAuth=FirebaseAuth.getInstance();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Notes").child(fAuth.getCurrentUser().getUid());
        getIncomingIntent();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title1=title.getText().toString();
                String content=desc.getText().toString();
                if(!TextUtils.isEmpty(title1) && !TextUtils.isEmpty(content)){

                        createNotes(title1,content);
                }else{
                    Toast.makeText(InputNote.this,"Fill all fields!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void createNotes(String title, String content) {
        progressBar.setVisibility(View.VISIBLE);
        Date date=new Date();
        SimpleDateFormat format=new SimpleDateFormat("dd/MM/yy");
        String date1=format.format(date);
        long timestamp=System.currentTimeMillis();
        Notes notes=new Notes(title,date1,content,timestamp);
        if(timestamp1==0){
            home home=new home();
            home.i=1;
            FirebaseDatabase.getInstance().getReference("Notes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(timestamp)).setValue(notes).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(InputNote.this,"Note added Successfully!",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        Intent i=new Intent(InputNote.this,home.class);
                        startActivity(i);

                    }else{
                        Toast.makeText(InputNote.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else{
            FirebaseDatabase.getInstance().getReference("Notes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(timestamp1)).setValue(notes).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(InputNote.this,"Note added Successfully!",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        Intent i=new Intent(InputNote.this,home.class);
                        startActivity(i);
                        Log.e("hi","not");

                    }else{
                        Toast.makeText(InputNote.this,"Something went wrong!",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private  void getIncomingIntent(){
        if(getIntent().hasExtra("title")&&getIntent().hasExtra("desc")){
            String title0=getIntent().getStringExtra("title");
            String description=getIntent().getStringExtra("desc");
            long existingTimestamp= Long.parseLong(getIntent().getStringExtra("timestamp"));
            title.setText(title0);
            desc.setText(description);
            timestamp1=existingTimestamp;
        }
    }
}