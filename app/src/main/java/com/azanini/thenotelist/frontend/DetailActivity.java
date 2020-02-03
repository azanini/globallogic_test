package com.azanini.thenotelist.frontend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.azanini.thenotelist.R;
import com.azanini.thenotelist.entities.Notebook;
import com.azanini.thenotelist.managers.TypeFaceManager;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.Nullable;

public class DetailActivity extends CustomActivity {

    Notebook notebook;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(LayoutInflater.from(this).inflate(R.layout.activity_detail, null));
        notebook = getIntent().getParcelableExtra("notebook");
        ((TextView) findViewById(R.id.fullDescription)).setText(notebook.getDescription());
        ((TextView) findViewById(R.id.fullDescription)).setTypeface(TypeFaceManager.getTypeFaceByName(this, TypeFaceManager.TRASANDINA_W03_LIGHT));
        Glide.with(this)
                .load(notebook.getImage() == null || notebook.getImage().trim().isEmpty() ? R.drawable.sin_imagen : notebook.getImage())
                .placeholder(notebook.getImage() == null || notebook.getImage().trim().isEmpty() ? R.drawable.sin_imagen : R.drawable.progress)
                .into((ImageView) findViewById(R.id.image));

        Toolbar toolbar = findViewById(R.id.toolbar_container);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public Boolean isDataNull() {
        return Boolean.FALSE;
    }

    @Override
    public void getAllData() {
        // N/A
    }

    @Override
    public void setToolbar() {
        ((Toolbar) findViewById(R.id.toolbar_container)).setTitle(notebook.getTitle());
    }
}
