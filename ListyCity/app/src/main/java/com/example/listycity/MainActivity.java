package com.example.listycity;



import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;



public class MainActivity extends AppCompatActivity {
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;
    EditText inputCity;
    Button addButton, deleteButton, confirmButton;
    private boolean isAdding = false;
    int selectedPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        inputCity = findViewById(R.id.input_city);
        addButton = findViewById(R.id.add_button);
        deleteButton = findViewById(R.id.delete_button);
        confirmButton = findViewById(R.id.confirm_button);

        String[] cities = {"Edmonton", "Moscow", "Vancouver", "Sydney", "Berlin", "Lagos", "Stuff1", "Stuff2"};

        dataList = new ArrayList<>(Arrays.asList(cities));
        //cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityAdapter = new ArrayAdapter<String>(this, R.layout.content, dataList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                if (position == selectedPosition) {
                    view.setBackgroundColor(Color.LTGRAY); //greys out selected city
                } else {
                    view.setBackgroundColor(Color.WHITE);
                }
                return view;
            }
        };
        cityList.setAdapter(cityAdapter);

        // input city field and confirm button hidden
        inputCity.setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);


        // show input city field on clicking add city
        addButton.setOnClickListener(v -> {
            isAdding = true;
            inputCity.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.VISIBLE);
            inputCity.setHint("Enter city name to add");
        });


        // confirm the input city action
        confirmButton.setOnClickListener(v -> {
            String cityName = inputCity.getText().toString().trim();
            if (!cityName.isEmpty() && !dataList.contains(cityName)) {
                dataList.add(cityName);
                cityAdapter.notifyDataSetChanged();
            }
            inputCity.setText("");
            inputCity.setVisibility(View.GONE);
            confirmButton.setVisibility(View.GONE);
        });


        // highlighting the city when clicked
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            cityAdapter.notifyDataSetChanged();
        });


        // delete highlighted city when delete city is clicked
        deleteButton.setOnClickListener(v -> {
            if (selectedPosition != -1) {
                dataList.remove(selectedPosition);
                cityAdapter.notifyDataSetChanged();
                selectedPosition = -1; // Reset the selection
            }
        });
    }
}