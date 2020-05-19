package com.chuchujie.core.xrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chuchujie.core.widget.recyclerview.BaseRVMultiAdapter;
import com.chuchujie.core.widget.recyclerview.HeaderFooterRVAdapter;
import com.chuchujie.core.widget.recyclerview.footerview.FooterLoadingView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        List<ItemData> data = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            data.add(new ItemData("positon:" + i));
        }


        BaseRVMultiAdapter adapter = new BaseRVMultiAdapter(this, data);
        adapter.setCallBackHolder(this);

        HeaderFooterRVAdapter headerFooterRVAdapter = new HeaderFooterRVAdapter(this, adapter);

        FooterLoadingView footerLoadingView = new FooterLoadingView(this);
        footerLoadingView.setLastPageText("我是Footer");
        footerLoadingView.showFooterView(false, true);
        headerFooterRVAdapter.addFooterView(footerLoadingView);

        FooterLoadingView footerLoadingView1 = new FooterLoadingView(this);
        footerLoadingView1.setLastPageText("我是Header");
        footerLoadingView1.showFooterView(false, true);
        headerFooterRVAdapter.addHeaderView(footerLoadingView1);

        recyclerView.setAdapter(headerFooterRVAdapter);
    }
}
