package com.example.mdays;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity {

    public static final int TAB_HOME = 0;
    public static final int TAB_PROJECTS = 1;
    public static final int TAB_STUDYS = 2;
    public static final int TAB_USER_CENTER = 3;

    public ContainerViewPager viewPager;

    public RadioButton radioMain;

    public RadioButton radioProjects;

    public RadioButton radioStudys;

    public RadioButton radioUserCenter;

    FragmentMain fragmentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();
        addPageChangeListener();
    }

    private void initView() {

        List<Fragment> fragments = new ArrayList<Fragment>();

        fragmentMain = new FragmentMain();

        FragmentFenlei fragmentHuodong = new FragmentFenlei();

        FragmentShequ fragmentShequ = new FragmentShequ();

        FragmentMy fragmentMy = new FragmentMy();

        fragments.add(fragmentMain);
        fragments.add(fragmentHuodong);
        fragments.add(fragmentShequ);
        fragments.add(fragmentMy);

        viewPager =   (ContainerViewPager)findViewById(R.id.viewpager);
        this.viewPager.setOffscreenPageLimit(0);

        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(this.getSupportFragmentManager(), viewPager, fragments);

    }

    private void addPageChangeListener() {
        viewPager.setOnPageChangeListener(new MyViewPager.OnPageChangeListener() {


            @Override
            public void onPageSelected(int id) {
                radioMain=(RadioButton)findViewById(R.id.radio_main) ;
                radioProjects=(RadioButton)findViewById(R.id.radio_projects) ;
                radioStudys=(RadioButton)findViewById(R.id.radio_studys) ;
                radioUserCenter=(RadioButton)findViewById(R.id.radio_user_center) ;
                switch (id) {
                    case TAB_HOME:
                        radioMain.setChecked(true);
                        break;
                    case TAB_PROJECTS:
                        radioProjects.setChecked(true);
                        break;
                    case TAB_STUDYS:
                        radioStudys.setChecked(true);
                        break;
                    case TAB_USER_CENTER:
                        radioUserCenter.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    @OnClick({R.id.radio_main, R.id.radio_projects, R.id.radio_studys, R.id.radio_user_center})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.radio_main:
                viewPager.setCurrentItem(TAB_HOME, false);
                doubleClick(v);
                break;
            case R.id.radio_projects:
                viewPager.setCurrentItem(TAB_PROJECTS, false);
                break;
            case R.id.radio_studys:
                viewPager.setCurrentItem(TAB_STUDYS, false);
                break;
            case R.id.radio_user_center:
                viewPager.setCurrentItem(TAB_USER_CENTER, false);
                break;
        }
    }

    long firstClickTime = 0;
    long secondClickTime = 0;

    public void doubleClick(View view) {

        if (firstClickTime > 0) {
            secondClickTime = SystemClock.uptimeMillis();
            if (secondClickTime - firstClickTime < 500) {
//                LogUtils.d("***************   double click  ******************");
                fragmentMain.ScrollToTop();
            }
            firstClickTime = 0;
            return;
        }

        firstClickTime = SystemClock.uptimeMillis();

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    firstClickTime = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
