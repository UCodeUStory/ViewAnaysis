package com.example.qiyue.viewanalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.qiyue.viewanalysis.view.ViewActivity;
import com.example.qiyue.viewanalysis.viewgroup.ViewGroupActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> datas = new ArrayList<>();
        datas.add("View 事件分析");
        datas.add("ViewGroup 事件分析");
        datas.add("ViewGroup 和 View 之间如何相互交换事件?是不是拦截判断在子View怎么交给父类容器");
        ListAdapter adapter = new ListAdapter(datas);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new ListAdapter.OnItemClick() {
            @Override
            public void onClick(View view, int position) {
                switch (position){
                    case Constants.VIEW_ANAYSIS:
                        startActivity(new Intent(MainActivity.this, ViewActivity.class));
                        break;
                    case Constants.VIEWGROUP_ANAYSIS:
                        startActivity(new Intent(MainActivity.this, ViewGroupActivity.class));
                        break;
                }
            }
        });
    }
}
