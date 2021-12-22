package com.kunai.parkapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.kunai.parkapp.R;
import com.kunai.parkapp.recycler.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Map;


public class ParkingListFragment extends Fragment {
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    ArrayList<String> plateList;
    ArrayList<String> brandList;
    FirebaseFirestore firebaseFirestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_parking_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.recyclerView);

        firebaseFirestore = FirebaseFirestore.getInstance();
        plateList = new ArrayList<>();
        brandList = new ArrayList<>();
        recyclerViewAdapter = new RecyclerViewAdapter(plateList,brandList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

        getData();


    }

    public void getData(){
       CollectionReference collectionReference = firebaseFirestore.collection("MevcutPark");
       collectionReference.orderBy("tarihTimestamp", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
           @Override
           public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
               if (value != null){
                   for (DocumentSnapshot documentSnapshot: value.getDocuments()){
                       Map<String,Object> map = documentSnapshot.getData();

                       String brand = (String) map.get("marka");
                       String plate = (String) map.get("plaka");

                       brandList.add(brand);
                       plateList.add(plate);

                       recyclerViewAdapter.notifyDataSetChanged();
                   }
               }
           }
       });
    }
}