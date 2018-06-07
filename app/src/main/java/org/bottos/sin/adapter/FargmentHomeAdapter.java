package org.bottos.sin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import org.bottos.sin.activity.R;
import org.bottos.sin.model.Mv;
import org.bottos.sin.utils.GlideTopRoundTransformation;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by Administrator on 2017/10/10.
 */

public class FargmentHomeAdapter extends RecyclerView.Adapter<FargmentHomeAdapter.MyViewAdapter> {
    private final Context context;
    private ArrayList<Mv.ResultBean> datas;

    public FargmentHomeAdapter(Context context, ArrayList<Mv.ResultBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public FargmentHomeAdapter.MyViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_article, null);
        return new MyViewAdapter(itemView);
    }

    @Override
    public void onBindViewHolder(final FargmentHomeAdapter.MyViewAdapter holder, int position) {
        String title = datas.get(position).getTitle();
        holder.textView.setText(title);

        holder.totalViews.setText(datas.get(position).getTotalViews());
        holder.duration.setText(datas.get(position).getDuration());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new GlideTopRoundTransformation(5));

        Glide.with(context)
                .load(datas.get(position).getImage())
                .transition(withCrossFade())
                .apply(options)
                .into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(int position, Mv.ResultBean item) {
        datas.add(item);
        notifyItemInserted(datas.size()+position);
    }

    public void clearData() {
        datas.clear();
        notifyDataSetChanged();
    }

    public void setData(ArrayList<Mv.ResultBean> mv) {
        datas = mv;
        notifyItemRangeChanged(0, datas.size());
    }


    public class MyViewAdapter extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private TextView totalViews;
        private TextView duration;

        public MyViewAdapter(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_cover);
            textView = itemView.findViewById(R.id.tv_title);
            totalViews = itemView.findViewById(R.id.tv_category);
            duration = itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(new  View.OnClickListener(){

                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, datas.get(getLayoutPosition()), Toast.LENGTH_SHORT).show();
                    if(onItemClickListener != null){
                        onItemClickListener.onItemClick(v, getLayoutPosition(), datas.get(getLayoutPosition()));
                    }
                }
            });
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(context, "图片"+datas.get(getLayoutPosition()).getImage(), Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position, Mv.ResultBean resultBean);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener l){
        this.onItemClickListener = l;
    }
}
