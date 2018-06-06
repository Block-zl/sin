package org.bottos.sin.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import org.bottos.sin.fragment.CommunityFragment;
import org.bottos.sin.fragment.HomeFragment;
import org.bottos.sin.fragment.PersonalFragment;

/**
 * Created by 星空之钥丶 on 2018/6/5.
 */

public class MainActivity extends BaseActivity {
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;
    private HomeFragment homeFragment;
    private CommunityFragment communityFragment;
    private PersonalFragment personalFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            hideFrag();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toastShort("home");

                    if (homeFragment == null) {
                        homeFragment = new HomeFragment();
                        fragmentTransaction.add(R.id.main_view, homeFragment).commit();
                    } else {
                        fragmentTransaction.show(homeFragment).commit();
                    }
                    return true;
                case R.id.navigation_community:
                    toastShort("dashboard");
                    if (communityFragment == null) {
                        communityFragment = new CommunityFragment();
                        fragmentTransaction.add(R.id.main_view, communityFragment).commit();
                    } else {
                        fragmentTransaction.show(communityFragment).commit();
                    }
                    return true;
                case R.id.navigation_personal:
                    toastShort("user");
                    if (personalFragment == null) {
                        personalFragment = new PersonalFragment();
                        fragmentTransaction.add(R.id.main_view, personalFragment).commit();
                    } else {
                        fragmentTransaction.show(personalFragment).commit();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        super.initViews();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        supportFragmentManager = getSupportFragmentManager();
        //开启事务
        fragmentTransaction = supportFragmentManager.beginTransaction();
        //碎片
        homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.main_view, homeFragment).commit();
    }

    private void hideFrag() {
        //再重新获取一个开启事务
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        //不等于空或者是否添加的时候
        if (homeFragment != null && homeFragment.isAdded()) {
            fragmentTransaction.hide(homeFragment);
        }
        if (communityFragment != null && communityFragment.isAdded()) {
            fragmentTransaction.hide(communityFragment);
        }
        if (personalFragment != null && personalFragment.isAdded()) {
            fragmentTransaction.hide(personalFragment);
        }
        fragmentTransaction.commit();
    }
}
