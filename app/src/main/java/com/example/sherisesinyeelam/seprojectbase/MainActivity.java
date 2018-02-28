package com.example.sherisesinyeelam.seprojectbase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    TabLayout MyTabs;
    ViewPager MyPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        MyTabs = (TabLayout) findViewById(R.id.MyTabs);
        MyPage = (ViewPager) findViewById(R.id.MyPage);

        MyTabs.setupWithViewPager(MyPage);
        SetUpViewPager(MyPage);

    }

    public void SetUpViewPager(ViewPager viewPage){
        MyViewPageAdapter adapter = new MyViewPageAdapter(getSupportFragmentManager());

        adapter.AddFragmentPage(new Page_01(), "Entry"); // Page 01
        adapter.AddFragmentPage(new Page_02(), "Home"); // Page 02
        adapter.AddFragmentPage(new Page_03(), "Setting"); // Page 03

        viewPage.setAdapter(adapter);
    }

    public class MyViewPageAdapter extends FragmentPagerAdapter{
        private List<Fragment> MyFragment = new ArrayList<>();
        private List<String> MyPageTitle = new ArrayList<>();


        public MyViewPageAdapter(FragmentManager manager){
            super(manager);
        }

        public void AddFragmentPage(Fragment frag, String title){
            MyFragment.add(frag);
            MyPageTitle.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragment.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return MyPageTitle.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
