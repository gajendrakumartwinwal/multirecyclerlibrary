package com.library.multirecyclerlibrary.cutomrecyclerlib;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private MultiItemRecyclerView adapter;

    public BaseViewHolder(View itemView, MultiItemRecyclerView adpt) {
        super(itemView);
        this.adapter = adpt;
    }
}
