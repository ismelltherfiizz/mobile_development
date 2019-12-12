package com.example.a1lab;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TerritoryAdapter extends RecyclerView.Adapter<TerritoryAdapter.TerritoryViewHolder> {
    private List<Territory> territories;
    private int rowLayout;
    private Context mContext;

    public TerritoryAdapter(Context context, List<Territory> territories, int rowLayout) {
        this.mContext = context;
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
        Picasso.with(context)
                .load(territories.get(position).getTerritoryPicture())
                .placeholder(R.drawable.fallback)
                .error(R.drawable.fallback)
                .into(holder.territoryPicture);
        holder.name.setText(territories.get(position).getName());
        holder.square.setText(context.getString(R.string.show_square, String.valueOf(territories.get(position).getSquare())));
        holder.detourTime.setText(context.getString(R.string.show_detour_time, String.valueOf(territories.get(position).getDetourTime())));
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("name", holder.name.getText().toString());
            bundle.putString("square", holder.square.getText().toString());
            bundle.putString("detour_time", holder.detourTime.getText().toString());
            bundle.putString("territory_picture", territories.get(position).getTerritoryPicture());
            Navigation.findNavController(v).navigate(R.id.navigation_territory_details, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return territories.size();
    }

    public static class TerritoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView territoryPicture;
        private TextView name;
        private TextView square;
        private TextView detourTime;

        TerritoryViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            square = v.findViewById(R.id.square);
            detourTime = v.findViewById(R.id.detour_time);
            territoryPicture = v.findViewById(R.id.territory_picture);
        }
    }
}
