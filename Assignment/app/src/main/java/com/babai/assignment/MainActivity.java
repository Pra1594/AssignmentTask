package com.babai.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerList;
    ProgressBar progress;
    SwipeRefreshLayout containers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerList = findViewById(R.id.recyclerList);
        progress = findViewById(R.id.progress);
        containers = findViewById(R.id.containers);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        recyclerList.setLayoutManager(manager);

        containers.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                containers.setRefreshing(false);



                recyclerList.setVisibility(View.INVISIBLE);

                if(Utility.internet_check(MainActivity.this)) {

                    progress.setVisibility(View.VISIBLE);
                    loadJSON();

                }
                else {

                    progress.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,"No Network!",Toast.LENGTH_SHORT).show();

                }

            }
        });

        if(Utility.internet_check(MainActivity.this)) {

            progress.setVisibility(View.VISIBLE);
            loadJSON();

        }
        else {

            progress.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this,"No Network!",Toast.LENGTH_SHORT).show();

        }


    }

    private void loadJSON(){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<EmployeesModel> call = apiService.getEmployeeData();

        call.enqueue(new Callback<EmployeesModel>() {
            @Override
            public void onResponse(Call<EmployeesModel> call, Response<EmployeesModel> response) {
                Log.e("ResponseCode",String.valueOf(response.code()));
                if(response.isSuccessful()){
                    progress.setVisibility(View.GONE);
                    recyclerList.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                    recyclerList.setAdapter(new EmployeesAdapter(MainActivity.this,response.body().getData()));
                }
            }

            @Override
            public void onFailure(Call<EmployeesModel> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,t.getMessage().toString(),Toast.LENGTH_SHORT).show();
            }
        });


    }

}