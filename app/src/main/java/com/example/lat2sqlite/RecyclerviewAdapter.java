package com.example.lat2sqlite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.UserViewHolder> {
    List<Item> listItem;

    public RecyclerviewAdapter(List<Item> listItem){
        this.listItem = listItem;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view);

        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        final Item content = listItem.get(position);
        holder.judul.setText(content.getJudul());
        holder.desc.setText(content.getDesc());
        holder.waktu.setText(content.getWaktu());
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView waktu;
        TextView judul;
        TextView desc;
        private int id;
        private String Judul;
        private String description;
        CardView cardView;
        private List<Item> listItem;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            waktu = itemView.findViewById(R.id.tanggal);
            judul = itemView.findViewById(R.id.judul);
            desc = itemView.findViewById(R.id.desc);

            cardView = itemView.findViewById(R.id.cardview);
            cardView.setOnLongClickListener(v -> {
                alertDialogAction(itemView.getContext(), getAdapterPosition());

                return  false;
            });
        }

        public void alertDialogAction(Context context, int position){
            String[] optionDialog = { "Edit", "Delete" };
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            listItem = databaseHelper.selectContentList();

            builder.setTitle("Choose options");
            builder.setItems(optionDialog, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == 0){
                        id = listItem.get(position).getId();
                        Judul = listItem.get(position).getJudul();
                        description = listItem.get(position).getDesc();

                        Intent intent = new Intent(context, EditActivity.class);
                        intent.putExtra("id", id);
                        intent.putExtra("judul", Judul);
                        intent.putExtra("deskripsi", description);

                        context.startActivity(intent);
                    }else {
                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        databaseHelper.delete(listItem.get(position).getId());
                        listItem = databaseHelper.selectContentList();

                        NotesActivity Notes = new NotesActivity();
                        Notes.setupRecyclerView(context, listItem, NotesActivity.recyclerView);
                        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
