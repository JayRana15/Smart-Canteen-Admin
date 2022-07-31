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
import Controller.Controller;
import ResponseModels.AllOrdersResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllOrdersActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders);

        Toolbar toolbar = findViewById(R.id.toolbar3);
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_baseline_arrow_back_24));

        setSupportActionBar(toolbar);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textView = findViewById(R.id.tv);
        progressBar = findViewById(R.id.pgbar);

        recyclerView = findViewById(R.id.allOrdersRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        displayData();

        SwipeRefreshLayout refreshLayout = findViewById(R.id.refreshLayout1);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                displayData();
                refreshLayout.setRefreshing(false);
            }
        });



    }

    private void displayData() {
        Call<List<AllOrdersResponseModel>> call = Controller.getInstance().getAPI().getAllOrders();

        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<AllOrdersResponseModel>>() {
            @Override
            public void onResponse(Call<List<AllOrdersResponseModel>> call, Response<List<AllOrdersResponseModel>> response) {
                List<AllOrdersResponseModel> obj = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                AllOrdersAdapter adapter = new AllOrdersAdapter(obj);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<AllOrdersResponseModel>> call, Throwable t) {
//                Toast.makeText(AllOrdersActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
                textView.setVisibility(View.VISIBLE);
                Log.d("error",t.getMessage());
            }
        });
    }
}