package com.kunai.parkapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kunai.parkapp.AddCarActivity;
import com.kunai.parkapp.ExitCarActivity;
import com.kunai.parkapp.login.MainActivity;
import com.kunai.parkapp.R;


public class AddParkingFragment extends Fragment {

    ImageView addCar,exitCar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_parking, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addCar = view.findViewById(R.id.addCar);
        exitCar = view.findViewById(R.id.exitCar);

        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCarActivity.class);
                startActivity(intent);
            }
        });

        exitCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ExitCarActivity.class);
                startActivity(intent);
            }
        });
    }
}