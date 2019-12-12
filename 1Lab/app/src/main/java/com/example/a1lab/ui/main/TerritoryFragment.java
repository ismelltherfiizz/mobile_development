package com.example.a1lab.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.a1lab.AddTerritoryActivity;
import com.example.a1lab.AppEx;
import com.example.a1lab.NetworkWatcher;
import com.example.a1lab.R;
import com.example.a1lab.Territory;
import com.example.a1lab.TerritoryAdapter;
import com.example.a1lab.TerritoryService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;


public class TerritoryFragment extends Fragment {

    private ConstraintLayout tabbedLayout;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_territory, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity parentActivity = getActivity();
        if (parentActivity != null) {
            initFields(parentActivity);
            initOnRefresh(parentActivity);
            registerNetworkMonitoring(parentActivity);
            getAPIData();
            floatingActionButton.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), AddTerritoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        }
    }

    public void getAPIData() {
        final TerritoryService service = getAppEx().getAPIService();
        Call<List<Territory>> call = service.getTerritories();
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<Territory>>() {
            @Override
            public void onResponse(Call<List<Territory>> call, Response<List<Territory>> response) {
                TerritoryAdapter adapter = (TerritoryAdapter) recyclerView.getAdapter();
                progressBar.setVisibility(View.INVISIBLE);
                if (response.isSuccessful() && adapter != null) {
                    List<Territory> dataArrayList = response.body();
                    adapter.updateItems(dataArrayList);
                }
            }

            @Override
            public void onFailure(Call<List<Territory>> call, Throwable throwable) {
                Snackbar.make(tabbedLayout, R.string.loading_failed, Snackbar.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private AppEx getAppEx() {
        return ((AppEx) Objects.requireNonNull(getActivity()).getApplication());
    }

    private void initFields(Activity activity) {
        floatingActionButton = activity.findViewById(R.id.floating_action_button);
        recyclerView = activity.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        DividerItemDecoration itemDecor = new DividerItemDecoration(activity, VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        recyclerView.setAdapter(new TerritoryAdapter(getActivity(), new ArrayList<>(), R.layout.list_territory));
        tabbedLayout = activity.findViewById(R.id.tabbed_layout);
        progressBar = activity.findViewById(R.id.loading_spinner);
    }

    private void registerNetworkMonitoring(Activity activity) {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        NetworkWatcher receiver = new NetworkWatcher(tabbedLayout);
        activity.registerReceiver(receiver, filter);
    }

    private void initOnRefresh(Activity activity) {
        final SwipeRefreshLayout pullToRefresh = activity.findViewById(R.id.pull_to_refresh);
        pullToRefresh.setOnRefreshListener(() -> {
            getAPIData();
            pullToRefresh.setRefreshing(false);
        });
    }
}
