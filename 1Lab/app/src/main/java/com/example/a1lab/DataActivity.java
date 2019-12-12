package com.example.a1lab;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

public class DataActivity extends AppCompatActivity {

    private RelativeLayout main;
    private RecyclerView recyclerView;
    private Button logoutButton;
    private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        initFields();
        initOnRefresh();
        startNetworkMonitoring();
        getAPIData();
        logoutButton.setOnClickListener(v -> onClick());
    }

    public void getAPIData() {
        final TerritoryService service = ((AppEx) getApplication()).getAPIService();
        Call<List<Territory>> call = service.getTerritories();
        loadingSpinner.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<Territory>>() {
            @Override
            public void onResponse(Call<List<Territory>> call, Response<List<Territory>> response) {
                TerritoryAdapter adapter = (TerritoryAdapter) recyclerView.getAdapter();
                loadingSpinner.setVisibility(View.INVISIBLE);
                if (response.isSuccessful() && adapter != null) {
                    List<Territory> dataArrayList = response.body();
                    adapter.updateItems(dataArrayList);
                }
            }

            @Override
            public void onFailure(Call<List<Territory>> call, Throwable throwable) {
                Snackbar.make(main, R.string.loading_failed, Snackbar.LENGTH_LONG).show();
                loadingSpinner.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void onClick() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void startNetworkMonitoring() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        NetworkWatcher receiver = new NetworkWatcher(main);
        this.registerReceiver(receiver, filter);
    }

    public void initOnRefresh() {
        final SwipeRefreshLayout refresh = findViewById(R.id.refresh);
        refresh.setOnRefreshListener(() -> {
            getAPIData();
            refresh.setRefreshing(false);
        });
    }

    private void initFields() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        recyclerView.setAdapter(new TerritoryAdapter(new ArrayList<>(), R.layout.list_item_territory));
        logoutButton = findViewById(R.id.logout_button);
        main = findViewById(R.id.main_layout);
        loadingSpinner = findViewById(R.id.loading_spinner);
    }

}