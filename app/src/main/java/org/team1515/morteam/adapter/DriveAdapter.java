package org.team1515.morteam.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.team1515.morteam.R;
import org.team1515.morteam.activity.DriveActivity;
import org.team1515.morteam.entity.Folder;
import org.team1515.morteam.fragment.DriveFragment;

import java.util.List;

public class DriveAdapter extends RecyclerView.Adapter<DriveAdapter.ViewHolder> {
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView folderName;

        ViewHolder(LinearLayout layout) {
            super(layout);

            folderName = (TextView) layout.findViewById(R.id.folder_name);

            layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
//                Folder folder = drive.get(position);

//                Intent intent = new Intent(context, DriveActivity.class);
//                intent.putExtra("_id", folder.getName());
//                context.startActivity(intent);
            }
        }
    }

    private Context context;
    private List<Folder> drive;
    private DriveFragment fragment;

    public DriveAdapter(DriveFragment fragment, Context context, List<Folder> drive) {
        this.fragment = fragment;
        this.context = context;
        this.drive = drive;
    }

    public void setDrive(List<Folder> drive) {
        this.drive = drive;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout view = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_folder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Folder currentFolder = drive.get(position);

        holder.folderName.setText(currentFolder.getName());
    }

    @Override
    public int getItemCount() {
        return drive.size();
    }
}