package com.example.a1lab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TerritoryAdapter extends RecyclerView.Adapter<TerritoryAdapter.TerritoryViewHolder> {
    private List<Territory> territories;
    private int rowLayout;

    public TerritoryAdapter(List<Territory> territories, int rowLayout) {
        this.territories = territories;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public TerritoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new TerritoryViewHolder(view);
    }

    public void updateItems(final List<Territory> territories) {
        this.territories = territories;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(TerritoryViewHolder holder, final int position) {
        Context context = holder.itemView.getContext();

        holder.name.setText(territories.get(position).getName());
        holder.square.setText(context.getString(R.string.show_square, String.valueOf(territories.get(position).getSquare())));
        holder.detourTime.setText(context.getString(R.string.show_detour_time, String.valueOf(territories.get(position).getDetourTime())));
    }

    @Override
    public int getItemCount() {
        return territories.size();
    }

    public static class TerritoryViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView square;
        private TextView detourTime;

        TerritoryViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            square = v.findViewById(R.id.square);
            detourTime = v.findViewById(R.id.detour_time);
        }
    }
}
