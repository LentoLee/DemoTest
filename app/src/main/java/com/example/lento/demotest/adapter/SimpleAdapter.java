package com.example.lento.demotest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lento.demotest.R;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {
    private String[] dataSource;
    private OnItemClickListener mItemClickListener;

    public SimpleAdapter(String[] dataArgs) {
        dataSource = dataArgs;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = View.inflate(parent.getContext(), R.layout.item_recyclerview, null);
        SimpleViewHolder viewHolder = new SimpleViewHolder(view);
        return viewHolder;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_fun_desc);
        }
    }

    public interface OnItemClickListener {
       void onItemClick(int pos);
    }
    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        holder.textView.setText(dataSource[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }
}
