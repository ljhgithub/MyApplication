package com.example.xuchao.myapplication.ui;

import android.app.DownloadManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xuchao.myapplication.R;
import com.example.xuchao.myapplication.util.LogUtils;
import com.example.xuchao.myapplication.ui.widget.SlidingTabLayout;

/**
 * Created by xuchao on 15-6-24.
 */
public class SlidingFragment extends Fragment {
    private static final String TAG = LogUtils.makeLogTag(SlidingFragment.class);

    private ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_slide, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());

        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, R.id.text);
        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);


        Resources res = getResources();
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.tab_selected_strip));
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);
    }


    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 10;
        }


        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return "Item " + (position + 1);
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item,
                    container, false);
            container.addView(view);
            TextView title = (TextView) view.findViewById(R.id.item_title);
            title.setText(String.valueOf(position + 1));

            LogUtils.LOGD(TAG, "instantiateItem() [position: " + position + "]");

            return view;
        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            LogUtils.LOGD(TAG, "destroyItem() [position: " + position + "]");
        }

    }

}
