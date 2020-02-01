package com.azanini.thenotelist.frontend;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.azanini.thenotelist.R;
import com.azanini.thenotelist.entities.Notebook;
import com.azanini.thenotelist.frontend.adapters.NotebookListAdapter;
import com.azanini.thenotelist.interfaces.WebServiceCallbackInterface;
import com.azanini.thenotelist.repo.ws.GetNotebooksResponse;
import com.azanini.thenotelist.repo.ws.WebServices;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.azanini.thenotelist.repo.ws.WebServices.NO_CONNECTION_ERROR;
import static com.azanini.thenotelist.repo.ws.WebServices.NO_ERROR;

public class MainActivity extends CustomActivity {

    private ArrayList<Notebook> notebooks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainContentView(LayoutInflater.from(this).inflate(R.layout.activity_main, null));
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("notebooks", notebooks);
    }

    @Override
    protected void onRestoreInstanceState(@NotNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        notebooks = savedInstanceState.getParcelable("notebooks");
    }

    @Override
    public Boolean isDataNull() {
        return notebooks == null;
    }

    @Override
    public void getAllData() {
        getNotebooks();
    }

    @Override
    public void setToolbar() {
        // ((Toolbar) findViewById(R.id.my_toolbar)).setTitle(getString(R.string.app_name));
    }

    private void getNotebooks() {
        WebServices.getNotebooks(getBaseContext(), new WebServiceCallbackInterface<GetNotebooksResponse>() {
            @Override
            public void onResponse(GetNotebooksResponse response) {
                if (response != null) {
                    if (response.getErrorCode().intValue() == NO_ERROR) {
                        loadNotebooksDataOnList(response.getNotebooks());
                    } else {
                        if (response.getErrorCode().intValue() == NO_CONNECTION_ERROR) {
                            toggleNoConnectionScreen(Boolean.TRUE, Boolean.FALSE, null);
                        } else {
                            toggleNoConnectionScreen(Boolean.TRUE, Boolean.TRUE, response.getErrorMessage());
                        }
                    }
                } else {
                    toggleNoConnectionScreen(Boolean.TRUE, Boolean.FALSE, null);
                }
            }
        });
    }

    private void loadNotebooksDataOnList(ArrayList<Notebook> notebooks) {
        this.notebooks = notebooks;
        RecyclerView recyclerView = findViewById(R.id.myNotebookListView);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter mAdapter = new NotebookListAdapter(getBaseContext(), notebooks);
        recyclerView.setAdapter(mAdapter);
    }
}
