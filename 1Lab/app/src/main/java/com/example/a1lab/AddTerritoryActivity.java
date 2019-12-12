package com.example.a1lab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTerritoryActivity extends AppCompatActivity {

    private TextInputLayout territoryNameLayout;
    private TextInputLayout territorySquareLayout;
    private TextInputLayout territoryDetourTimeLayout;
    private TextInputEditText territoryNameField;
    private TextInputEditText territorySquareField;
    private TextInputEditText territoryDetourTimeField;
    private Button addTerritoryButton;
    private ProgressBar progressBar;
    private final String PICTURE_URL = "https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Wikipedia_Logo_1.0.png/600px-Wikipedia_Logo_1.0.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_territory);
        initFields();
        initOnFocusChangeListeners();
        addTerritoryButton.setOnClickListener(v -> {

                if (!(territoryNameLayout.isErrorEnabled()||territorySquareLayout.isErrorEnabled()
                        ||territoryDetourTimeLayout.isErrorEnabled())){
                    String name = Objects.requireNonNull(territoryNameField.getText()).toString().trim();
                    Integer square = Integer.parseInt(Objects.requireNonNull(territorySquareField.getText().toString().trim()));
                    Integer detourTime = Integer.parseInt(Objects.requireNonNull(territoryDetourTimeField.getText()).toString().trim());
                    Territory territory = new Territory(name, square, detourTime, PICTURE_URL);
                    addTerritory(territory);
                }
        });
    }

    private void initOnFocusChangeListeners() {
        territoryNameField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (DataValidator.getInstance().isName(territoryNameField)) {
                        territoryNameLayout.setErrorEnabled(false);
                    } else {
                        territoryNameLayout.setError("Name is invalid");
                    }
                }
            }
        });
        territorySquareField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (DataValidator.getInstance().isNumber(territorySquareField)) {
                        territorySquareLayout.setErrorEnabled(false);
                    } else {
                        territorySquareLayout.setError("Number is invalid");
                    }
                }
            }
        });
        territoryDetourTimeField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (DataValidator.getInstance().isNumber(territoryDetourTimeField)) {
                        territoryDetourTimeLayout.setErrorEnabled(false);
                    } else {
                        territoryDetourTimeLayout.setError("Number is invalid");
                    }
                }
            }
        });
    }


        private void initFields() {
        territoryNameLayout = findViewById(R.id.add_territory_name);
        territorySquareLayout = findViewById(R.id.add_territory_square);
        territoryDetourTimeLayout = findViewById(R.id.add_territory_detour_time);
        territoryNameField = findViewById(R.id.add_territory_name_text);
        territorySquareField = findViewById(R.id.add_territory_square_text);
        territoryDetourTimeField = findViewById(R.id.add_territory_detour_time_text);
        addTerritoryButton = findViewById(R.id.add_territory_button);
        progressBar = findViewById(R.id.progress_bar);
    }

    private void addTerritory(Territory territory){
        final TerritoryService service = getAppEx().getAPIService();
        Call<Territory> call = service.createTerritory(territory);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<Territory>() {
            @Override
            public void onResponse(Call<Territory> call, Response<Territory> response) {
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(AddTerritoryActivity.this, TabbedActivity.class));
            }

            @Override
            public void onFailure(Call<Territory> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(new Intent(AddTerritoryActivity.this, TabbedActivity.class));
            }
        });
    }

    private AppEx getAppEx() {
        return ((AppEx) Objects.requireNonNull(getApplication()));
    }

}
