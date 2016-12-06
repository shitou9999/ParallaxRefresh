package com.yanzhenjie.parallaxrefresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * dddd
 */
public class MainActivity extends AppCompatActivity {

    private ContentAdapter mContentAdapter;
    private List<String> mDataList;

    private PtrFrameLayout mPtrFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayUtils.initScreen(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.refresh_layout);
        mPtrFrameLayout.setPtrHandler(mPtrHandler);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mContentAdapter = new ContentAdapter(this);
        recyclerView.setAdapter(mContentAdapter);

        // 由于PtrFrameLayout的自动刷新需要在onWindowFocusChanged(boolean)之后调用，所以这里延时250ms.
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh();
            }
        }, 250);
    }

    /**
     * 刷新监听。
     */
    private PtrHandler mPtrHandler = new PtrDefaultHandler() {
        @Override
        public void onRefreshBegin(PtrFrameLayout frame) {
            // 这里延时2000ms，模拟网络请求。
            mPtrFrameLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestDataList();
                }
            }, 2000);
        }
    };

    private void requestDataList() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            mDataList.add("我是第" + i + "个");
        }
        mContentAdapter.notifyDataSetChanged(mDataList);

        mPtrFrameLayout.refreshComplete();
    }
}
