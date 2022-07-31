package com.example.admin;

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

import Adapters.RemoveItemAdapter;
import Controller.Controller;
import ResponseModels.AvailableItemsResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveItemActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_item);

        Toolbar toolbar = findViewById(R.id.toolbar4);
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_baseline_arrow_back_24));

        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textView = findViewById(R.id.tv1);
        progressBar = findViewById(R.id.pgbar1);

        recyclerView = findViewById(R.id.removeItemRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        displayAvailableItems();

        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout3);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayAvailableItems();
                refreshLayout.setRefreshing(false);
            }
        });

    }

    private void displayAvailableItems() {
        Call<List<AvailableItemsResponseModel>> call = Controller.getInstance().getAPI().getAvailableItems();

        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<AvailableItemsResponseModel>>() {
            @Override
            public void onResponse(Call<List<AvailableItemsResponseModel>> call, Response<List<AvailableItemsResponseModel>> response) {
                List<AvailableItemsResponseModel> obj = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setAdapter(new RemoveItemAdapter(obj));
            }

            @Override
            public void onFailure(Call<List<AvailableItemsResponseModel>> call, Throwable t) {
                Log.e("RemoveItemError",t.getMessage());
//
                progressBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
            }
        });
    }
}