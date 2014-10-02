package jdy.picker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (savedInstanceState == null) {
            // PickerFragment 클래스 init세팅(picker전체길이, 시작날짜, 끝날짜)
            PickerFragment picker = new PickerFragment();
            int screen_x = getResources().getDisplayMetrics().widthPixels;
            picker.init(screen_x, "20141001", "20141031");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, picker)
                    .commit();
        }
    }
}
