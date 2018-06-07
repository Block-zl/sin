package org.bottos.sin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.bottos.sin.activity.BaseActivity;
import org.bottos.sin.activity.MainActivity;
import org.bottos.sin.activity.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * 圈子
 */

public class HomeFragment extends Fragment {
    private View view;
    private Toolbar toolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private String[] tabTitleK = new String[]{"top", "shehui", "guonei", "guoji", "yule", "tiyu", "junshi", "keji", "caijing", "shishang"};
    private String[] tabTitle = new String[]{"默认", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        initView();

        return view;
    }

    private void initView(){
        toolbar = view.findViewById(R.id.toolbar);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add:
                        Toast.makeText(getActivity(), "收藏", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.search:
                        Toast.makeText(getActivity(), "分享", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        //设置TabLayout
        mTabLayout = view.findViewById(R.id.tab);
        mViewPager = view.findViewById(R.id.viewPager);
        for (int i = 0; i < tabTitle.length; i++) {
            fragments.add(HomeContentFragment.newInstance(tabTitleK[i]));
        }
        for (int i = 0; i < tabTitle.length; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[i]));
        }
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(this.getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
            //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitle[position];
            }
        };
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toobar_home, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((MainActivity)getActivity()).menuControl();
                break;
            case R.id.add:
                Toast.makeText(getActivity(), "add", Toast.LENGTH_LONG);
                break;
            case R.id.search:
                Toast.makeText(getActivity(), "search", Toast.LENGTH_LONG);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
