package com.waspy.sayekti.waspy.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.waspy.sayekti.waspy.R;
import com.waspy.sayekti.waspy.db.Doo;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class LogActivity extends AppCompatActivity {

    List<String> tlist = new ArrayList<String>();
    List<String> tText = new ArrayList<String>();
    List<ContentLogFragment> fragments = new ArrayList<ContentLogFragment>();
    private Realm realm;
    //private TextView txtLog;
    private ViewPager mPager;
    private TabLayout tabLayout;
    private ConentFragmentAdapter mPageAdapter;

    /**
     * Teruske sesuk nganggo iki --> http://www.truiton.com/2015/06/android-tabs-example-fragments-viewpager/
     *
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mPager = (ViewPager) findViewById(R.id.pager);
        realm = Realm.getDefaultInstance();

        getSupportActionBar().setSubtitle("Incoming notification message");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.icon_waspy);

        loadQuery();
    }

    private void loadQuery() {

        RealmQuery realmQuery = realm.where(Doo.class);
        RealmResults<Doo> doos = realmQuery.findAll();

        for (int i = 0; i < doos.size(); i++) {
            if (!tlist.contains(doos.get(i).getTitle())) {
                tlist.add(doos.get(i).getTitle());
                tText.add(doos.get(i).getText());
                tabLayout.addTab(tabLayout.newTab().setText(doos.get(i).getTitle()));
                Bundle b = new Bundle();
                b.putInt("position", i);
                ContentLogFragment contentLogFragment = ContentLogFragment.newInstance(doos.get(i).getTitle());
                contentLogFragment.setDoos(doos);
                fragments.add(contentLogFragment);
            }
        }

        mPageAdapter = new ConentFragmentAdapter(getSupportFragmentManager(), fragments);
        mPager.setAdapter(mPageAdapter);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}
