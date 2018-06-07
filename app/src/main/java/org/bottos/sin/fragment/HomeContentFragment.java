package org.bottos.sin.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.bottos.sin.activity.R;
import org.bottos.sin.adapter.FargmentHomeAdapter;
import org.bottos.sin.model.Mv;
import org.bottos.sin.utils.GsonUtil;

import java.util.ArrayList;

/**
 * 圈子
 */

public class HomeContentFragment extends Fragment {
    private static final int Refresh = 1;
    private static final int More = 2;
    private static final int Banner = 3;
    private View view;
    private static final String KEY = "titleK";
    private FargmentHomeAdapter adapter;
    private RecyclerView recyclerView;
    private final String BASE_URL = "http://mvapi.yinyuetai.com/mvchannel/so?sid=&tid=&a=ML&p=&c=&s=dayFavorites&pageSize=20&page=";
    private ArrayList<Mv.ResultBean> mvs =  new ArrayList<Mv.ResultBean>();
    private Mv mv;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case Refresh:
                    mv = GsonUtil.parseJsonWithGson((String) msg.obj, Mv.class);
                    adapter.clearData();
                    mvs = (ArrayList<Mv.ResultBean>) mv.getResult();
                    adapter.setData(mvs);
                    recyclerView.scrollToPosition(0);
                    break;
                case More:
//                    mvBean = GsonUtil.parseJsonWithGson((String) msg.obj, MvBean.class);
//                    for (int i = 0; i <mvBean.getResult().size() ; i++) {
//                        adapter.addData(i, (MvBean.ResultBean) mvBean.getResult().get(i));
//                    }
//                    materialRefreshLayout.finishRefreshLoadMore();
                    break;
                case Banner:
//                    bannerBean = GsonUtil.parseJsonWithGson((String) msg.obj, BannerBean.class);
//                    Log.e("---------bannerBean----------", bannerBean.toString());
//                    images = new ArrayList<>();
//                    imagesTitle = new ArrayList<>();
//                    for (int i = 0; i <bannerBean.getBigPics().size() ; i++) {
//                        images.add(bannerBean.getBigPics().get(i).getImage());
//                        imagesTitle.add(bannerBean.getBigPics().get(i).getTitle());
//                    }
//                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
//                    //设置图片加载器
//                    banner.setImageLoader(new GlideImageLoader());
//                    //设置图片集合
//                    banner.setImages(images);
//                    banner.setBannerAnimation(Transformer.Accordion );
//                    //设置标题集合（当banner样式有显示title时）
//                    banner.setBannerTitles(imagesTitle);
//                    //设置自动轮播，默认为true
//                    banner.isAutoPlay(true);
//                    //设置轮播时间
//                    banner.setDelayTime(2500);
//                    //设置指示器位置（当banner模式中有指示器时）
//                    banner.setIndicatorGravity(BannerConfig.RIGHT);
//                    //banner设置方法全部调用完毕时最后调用
//                    banner.start();
                    break;

            }
        }

    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_content_main, container, false);
        String string = getArguments().getString(KEY);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        String url = BASE_URL+ 1;
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        Message msg = Message.obtain();
                        msg.what = Refresh;
                        msg.obj = response.body().toString();
                        handler.sendMessage(msg);
                    }
                });

    }

    private void initView() {
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new FargmentHomeAdapter(this.getActivity(), mvs);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), 1, GridLayoutManager.VERTICAL, false));

    }

    public static HomeContentFragment newInstance(String str){
        HomeContentFragment fragment = new HomeContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY, str);
        fragment.setArguments(bundle);
        return fragment;
    }
}
