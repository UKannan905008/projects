package com.example.onlinenotesmanager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    Context context;
    private ArrayList<Notes> notes;
//    private DatabaseReference ref;

    public ListAdapter(Context context, ArrayList<Notes> notes) {
        this.context = context;
        this.notes = notes;


    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        View listitem = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ViewHolder(listitem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notes services = notes.get(position);
        holder.title.setText(services.getTitle());
        holder.date.setText(services.getDate());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, InputNote.class);
                i.putExtra("title",services.getTitle());
                i.putExtra("desc",services.getDescription());
                i.putExtra("timestamp",String.valueOf(services.getTimestamp()));
              context.startActivity(i);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FirebaseDatabase.getInstance().getReference("Notes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(String.valueOf(services.timestamp)).removeValue();

            }
        });
    }

    public int getItemCount() {
        return notes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date;
        private RelativeLayout relativeLayout;
        private TextView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title_title);
            this.date = itemView.findViewById(R.id.date_date);
            this.relativeLayout = itemView.findViewById(R.id.rv);
            this.delete=itemView.findViewById(R.id.delete);

        }
    }



}
