/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.xuchao.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.example.xuchao.myapplication.ui.widget.MultiSwipeRefreshLayout;
import com.example.xuchao.myapplication.util.LogUtils;
import com.example.xuchao.myapplication.ui.widget.RefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SwipeRefreshLayoutBasicFragment extends Fragment {
    private static final String TAG = LogUtils.makeLogTag(SwipeRefreshLayoutBasicFragment.class);
    //    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MultiSwipeRefreshLayout mSwipeRefreshLayout;

    private ListView lv;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);

        mSwipeRefreshLayout = (MultiSwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        lv = (ListView) mSwipeRefreshLayout.findViewById(R.id.list);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                LogUtils.LOGD(TAG, "onRefresh called from SwipeRefreshLayout");

                initiateRefresh();
            }
        });

        lv.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, getData()));


    }

    private List<String> getData() {

        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            data.add("测试数据" + i
            );
        }


        return data;
    }

    private void initiateRefresh() {
        LogUtils.LOGD(TAG, "initiateRefresh");


        new DummyBackgroundTask().execute();
    }

    private void onRefreshComplete(String result) {
        LogUtils.LOGD(TAG, "onRefreshComplete" + result);

        mSwipeRefreshLayout.setRefreshing(false);


    }

    private class DummyBackgroundTask extends AsyncTask<Void, Void, String> {

        static final int TASK_DURATION = 3 * 1000; // 3 seconds

        @Override
        protected String doInBackground(Void... params) {
            // Sleep for a small amount of time to simulate a background-task
            try {
                Thread.sleep(TASK_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Random random = new Random();

            return random.nextInt() + "";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            onRefreshComplete(result);
        }

    }
}
