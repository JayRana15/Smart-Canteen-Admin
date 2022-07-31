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

import Adapters.AllOrdersAdapter;
import Adapters.CurrentOrdersAdapter;
import Controller.Controller;
import ResponseModels.AllOrdersResponseModel;
import ResponseModels.CurrentOrderResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentOrdersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    public static TextView textView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_orders);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_baseline_arrow_back_24));

        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textView = findViewById(R.id.tv3);
        progressBar = findViewById(R.id.pgbar3);

        recyclerView = findViewById(R.id.currentOrdersRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displayCurrentOrders();

        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayCurrentOrders();
                refreshLayout.setRefreshing(false);
            }
        });
        
    }

    public void displayCurrentOrders() {
        Call<List<CurrentOrderResponseModel>> call = Controller.getInstance().getAPI().getCurrentOrders();

        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<CurrentOrderResponseModel>>() {
            @Override
            public void onResponse(Call<List<CurrentOrderResponseModel>> call, Response<List<CurrentOrderResponseModel>> response) {
                List<CurrentOrderResponseModel> obj = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                CurrentOrdersAdapter adapter = new CurrentOrdersAdapter(obj);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CurrentOrderResponseModel>> call, Throwable t) {
                Log.e("CurrentOrderError",t.getMessage());
//                Toast.makeText(CurrentOrdersActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
            }
        });
    }
}