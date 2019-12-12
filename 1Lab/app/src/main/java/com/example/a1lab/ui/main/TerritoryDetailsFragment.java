package com.example.a1lab.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.a1lab.R;
import com.squareup.picasso.Picasso;


public class TerritoryDetailsFragment extends Fragment {

    private ImageView territoryPictureView;
    private TextView nameTextView;
    private TextView squareTextView;
    private TextView detourTimeTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_territory_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        territoryPictureView = getActivity().findViewById(R.id.territory_details_image);
        nameTextView = getActivity().findViewById(R.id.territory_details_name);
        squareTextView = getActivity().findViewById(R.id.territory_details_square);
        detourTimeTextView = getActivity().findViewById(R.id.territory_details_detour_time);

        Picasso.with(getContext())
                .load(getArguments().getString("territoryPicture"))
                .placeholder(R.drawable.fallback)
                .error(R.drawable.fallback)
                .into(territoryPictureView);
        nameTextView.setText(getArguments().getString("name"));
        squareTextView.setText(getArguments().getString("square"));
        detourTimeTextView.setText(getArguments().getString("detour_time"));

        getActivity().findViewById(R.id.back_to_list_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}
