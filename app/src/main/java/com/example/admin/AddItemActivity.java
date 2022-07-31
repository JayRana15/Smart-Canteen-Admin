package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Adapters.AddItemAdapter;
import Adapters.RemoveItemAdapter;
import Controller.Controller;
import ResponseModels.AvailableItemsResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemActivity extends AppCompatActivity {

    RecyclerView recyclerView;
     public static TextView textView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Toolbar toolbar = findViewById(R.id.toolbar5);
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_baseline_arrow_back_24));

        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textView = findViewById(R.id.tv2);
        progressBar = findViewById(R.id.pgbar2);

        recyclerView = findViewById(R.id.addItemRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displayUnavailableItems();

        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout2);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayUnavailableItems();
                refreshLayout.setRefreshing(false);
            }
        });
        
    }

    private void displayUnavailableItems() {

        Call<List<AvailableItemsResponseModel>> call = Controller.getInstance().getAPI().getUnavailableItems();

        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<AvailableItemsResponseModel>>() {
            @Override
            public void onResponse(Call<List<AvailableItemsResponseModel>> call, @NonNull Response<List<AvailableItemsResponseModel>> response) {
                List<AvailableItemsResponseModel> obj = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(new AddItemAdapter(obj));
//                Log.d("response","here");

            }

            @Override
            public void onFailure(Call<List<AvailableItemsResponseModel>> call, Throwable t) {
                Log.e("addItemError",t.getMessage());
//                Toast.makeText(AddItemActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            }
        });

    }

}