package jdy.picker;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by daeyoung on 2014-10-02.
 */
public class PickerFragment extends Fragment {
    private static final float SIZE = 0.2f; // 반드시 1에서 홀수로 나눴을때 나오는 값중 하나로 설정 ex)0.2f = 1/5 screen_width
    private static final int MAX_POS = (int)(Math.round(1 / SIZE) / 2);
    private static final int MAX_VISIBLE_CNT = ((MAX_POS * 2) + 1);

    private int screen_x;
    private String minDate, maxDate;

    public PickerFragment() {
    }

    public void init(int screen_x, String minDate, String maxDate){
        this.screen_x = screen_x;
        this.minDate = minDate;
        this.maxDate = maxDate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.picker, container, false);
        ViewPager viewPager = (ViewPager) rootView;
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(screen_x, screen_x / MAX_VISIBLE_CNT);
        viewPager.setLayoutParams(params);
        viewPager.setAdapter(new ViewAdapter());
        viewPager.setOffscreenPageLimit(MAX_VISIBLE_CNT * 3);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer(){

            @Override
            public void transformPage(View view, float v) {
                final float normalizedposition = (SIZE * MAX_POS) - Math.abs(v - (SIZE * MAX_POS));
                view.setScaleX(normalizedposition / ((SIZE * MAX_POS) * 2) + 0.5f);
                view.setScaleY(normalizedposition / ((SIZE * MAX_POS) * 2) + 0.5f);
            }
        });

        return rootView;
    }

    private class ViewAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public float getPageWidth(int position) {
            return(SIZE);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return (view == object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView textView = new TextView(getActivity());
            textView.setGravity(Gravity.CENTER);
            textView.setText("" + position);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(32);
            textView.setBackgroundColor(Color.rgb(0xab, 0xcd, 0xef));
            ((ViewPager)container).addView(textView, 0);
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((TextView) object);
        }
    }
}