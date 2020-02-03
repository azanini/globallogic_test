package com.azanini.thenotelist.frontend.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.azanini.thenotelist.R;
import com.azanini.thenotelist.entities.Notebook;
import com.azanini.thenotelist.frontend.DetailActivity;
import com.azanini.thenotelist.managers.TypeFaceManager;
import com.azanini.thenotelist.utils.SquaredImageView;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotebookListAdapter extends RecyclerView.Adapter<NotebookListAdapter.MyNotebookViewHolder> {

    private List<Notebook> data;
    private Context context;

    static class MyNotebookViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView description;
        SquaredImageView image;
        View item_notebook;

        MyNotebookViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            description = v.findViewById(R.id.description);
            image = v.findViewById(R.id.image);
            item_notebook = v.findViewById(R.id.item_notebook);
        }
    }

    public NotebookListAdapter(Context context, List<Notebook> data) {
        this.context = context;
        this.data = data;
    }

    @NotNull
    @Override
    public NotebookListAdapter.MyNotebookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notebook, parent, false);
        return new MyNotebookViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MyNotebookViewHolder holder, int position) {
        Notebook notebook = data.get(position);
        holder.title.setTypeface(TypeFaceManager.getTypeFaceByName(context, TypeFaceManager.TRASANDINA_W03_MEDIUM));
        holder.title.setText(notebook.getTitle() == null || notebook.getTitle().trim().isEmpty() ? "<no title>" : notebook.getTitle());
        holder.description.setTypeface(TypeFaceManager.getTypeFaceByName(context, TypeFaceManager.TRASANDINA_W03_LIGHT));
        holder.description.setText(notebook.getDescription() == null || notebook.getDescription().trim().isEmpty() ? "<no description>" : notebook.getDescription());
        Glide.with(context)
                .load(notebook.getImage() == null || notebook.getImage().trim().isEmpty() ? R.drawable.sin_imagen : notebook.getImage())
                .placeholder(notebook.getImage() == null || notebook.getImage().trim().isEmpty() ? R.drawable.sin_imagen : R.drawable.progress)
                .into(holder.image);
        holder.item_notebook.setTag(notebook);
        holder.itemView.setOnClickListener(notebookClickListener);
        if (position % 2 == 0) {
            holder.item_notebook.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        } else {
            holder.item_notebook.setBackgroundColor(ContextCompat.getColor(context, R.color.someScaleOfGray));
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    private View.OnClickListener notebookClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Notebook notebook = (Notebook) v.getTag();
            Intent detailIntent = new Intent(context, DetailActivity.class);
            detailIntent.putExtra("notebook", notebook);
            context.startActivity(detailIntent);
        }
    };
}
