package com.library.multirecyclerlibrary.cutomrecyclerlib;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


abstract public class BaseAdapter<T extends BaseViewHolder> extends RecyclerView.Adapter<T> {

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return getNewViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        onBndViewHolder(holder, position);
    }

    abstract public T getNewViewHolder(ViewGroup parent, int viewType);

    abstract public void onBndViewHolder(T holder, int position);

}
