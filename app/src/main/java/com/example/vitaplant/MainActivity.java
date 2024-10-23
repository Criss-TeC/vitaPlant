package com.example.vitaplant;
import com.example.vitaplant.DeviceAdapter;
import android.os.Bundle;
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

        // lista de dispositivos (esto puede venir de una base de datos)
        List<Device> devices = new ArrayList<>();
        devices.add(new Device("Dispositivo 1", 45));
        devices.add(new Device("Dispositivo 2", 70));
        devices.add(new Device("Dispositivo 3", 85));
        devices.add(new Device("Dispositivo 4", 30));

        // adaptador con la lista de dispositivos
        deviceAdapter = new DeviceAdapter(devices);
        recyclerView.setAdapter(deviceAdapter);
    }
}
