package com.example.vitaplant;
import com.example.vitaplant.DeviceAdapter;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DeviceAdapter deviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configura el RecyclerView
        recyclerView = findViewById(R.id.recyclerViewDevices);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columnas

        // Crea una instancia del DeviceAdapter
        deviceAdapter = new DeviceAdapter();

        // Asigna el adaptador al RecyclerView
        recyclerView.setAdapter(deviceAdapter);

        // Llama al método loadData para cargar los datos iniciales
        deviceAdapter.loadData();
        Log.d("MainActivity", "loadData() llamado"); // Log de la llamada a loadData()
    }
}