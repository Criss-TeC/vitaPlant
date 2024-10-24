package com.example.vitaplant;  // Asegúrate de que este sea el paquete correcto

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vitaplant.model.Device;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder> {

    private final List<Device> deviceList = new ArrayList<>();

    public DeviceAdapter() {
    }
    public void loadData() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            String uid = user.getUid();
            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userDevicesRef = database.child("users").child(uid).child("devices");

            userDevicesRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    deviceList.clear();
                    for (DataSnapshot deviceSnapshot : snapshot.getChildren()) {
                        String deviceName = deviceSnapshot.child("name").getValue(String.class);
                        int humidity = deviceSnapshot.child("humidity").getValue(Integer.class);
                        Device device = new Device(deviceName, humidity);
                        deviceList.add(device);
                    }
                    notifyDataSetChanged();
                }@Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Maneja el error, por ejemplo, mostrando un mensaje al usuario
                }
            });
        }
    }


    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        Device device = deviceList.get(position);
        holder.deviceName.setText(device.getName());

        holder.progressHumidity.setProgress(device.getHumidity());
        holder.textHumidityPercentage.setText(device.getHumidity() + "%");
    }

    @Override
    public int getItemCount() {
        return deviceList.size();
    }



    static class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName;
        CircularProgressBar progressHumidity;
        TextView textHumidityPercentage;

        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            deviceName = itemView.findViewById(R.id.textDeviceName);
            progressHumidity = itemView.findViewById(R.id.progressHumidity);
            textHumidityPercentage = itemView.findViewById(R.id.textHumidityPercentage);
        }
    }
}
