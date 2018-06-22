package org.bottos.sin.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.LayoutRes;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static org.bottos.sin.widget.NavigationMenuStyle.setNavigationMenuLineStyle;

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected DrawerLayout mRootView;
    protected FrameLayout flActivityContainer;
    protected BottomNavigationView bottomNav;
    private Toast mToast;
    private List<TurnBackListener> mTurnBackListeners = new ArrayList<>();
    public Boolean navIsShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initDatas();
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @LayoutRes
    protected abstract int setLayoutId();

    /**
     * 初始化 View， 调用位置在 initDatas 之后
     */
    protected void initViews() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        bottomNav = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
        setNavigationMenuLineStyle(navigationView, Color.parseColor("#F3F3F3"), 20);
        flActivityContainer = findViewById(R.id.activity_container);
        flActivityContainer.addView(LayoutInflater.from(this).inflate(setLayoutId(), flActivityContainer, false));
        mRootView = findViewById(R.id.drawer_layout);
    }

    protected void initDatas() {
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    Intent intent=new Intent(BaseActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, 250);
        } else if (id == R.id.nav_gallery) {
            showOrHideNavAnim();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        menuControl();
        return true;
    }

    public void menuControl() {
        if(mRootView.isDrawerOpen(Gravity.LEFT)){
            mRootView.closeDrawer(Gravity.LEFT);
        }else{
            mRootView.openDrawer(Gravity.LEFT);
        }
    }

    private void showOrHideNavAnim() {
        if(navIsShow) {
            hideBottomNav();
        } else {
            showBottomNav();
        }

    }


    private void showBottomNav() {
        ValueAnimator va = ValueAnimator.ofFloat(bottomNav.getY(), bottomNav.getY()-bottomNav.getHeight());
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                bottomNav.setY((Float) valueAnimator.getAnimatedValue());
            }
        });
        va.start();
        navIsShow = true;
    }

    private void hideBottomNav() {
        ValueAnimator va = ValueAnimator.ofFloat(bottomNav.getY(), bottomNav.getY() + bottomNav.getHeight());
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                bottomNav.setY((Float) valueAnimator.getAnimatedValue());
            }
        });

        va.start();
        navIsShow = false;
    }


    public void toastShort(String text) {
        toast(text, Toast.LENGTH_SHORT);
    }

    public void toastLong(String text) {
        toast(text, Toast.LENGTH_LONG);
    }

    private void toast(final String text, final int duration) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), text, duration);
                    } else {
                        mToast.setText(text);
                        mToast.setDuration(duration);
                    }
                    mToast.show();
                }
            });
        }
    }

    public interface TurnBackListener {
        boolean onTurnBack();
    }

    public void addOnTurnBackListener(TurnBackListener l) {
        this.mTurnBackListeners.add(l);
    }

    private long mBackPressedTime;

    @Override
    public void onBackPressed() {
        for (TurnBackListener l : mTurnBackListeners) {
            if (l.onTurnBack()) return;
        }

        if (this instanceof MainActivity) {
            long curTime = SystemClock.uptimeMillis();
            if ((curTime - mBackPressedTime) < (3 * 1000)) {
                finish();
            } else {
                mBackPressedTime = curTime;
                toastLong("再按一次退出运用!");
            }
        } else {
            super.onBackPressed();
        }
    }
}
