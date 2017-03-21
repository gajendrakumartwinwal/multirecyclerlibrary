package com.library.multirecyclerlibrary.cutomrecyclerlib;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;


public abstract class BaseDataBinder<T extends BaseViewHolder, U extends Object> {
    protected MultiItemRecyclerView adapter;
    protected Context mContext;
    protected Object extraObject;

    protected BaseDataBinder(Context context, ArrayList<U> data, MultiItemRecyclerView adpt, int viewtype, Object extraObject, BaseItemClickListener clickListener) {
        this.clickListener = clickListener;
        this.data = data;
        this.adapter = adpt;
        this.mContext = context;
        this.viewType = viewtype;
        this.extraObject = extraObject;
    }

    protected BaseItemClickListener clickListener;

    protected ArrayList<U> data;

    protected int viewType;

    abstract public T newViewHolder(ViewGroup parent);

    abstract public void bindViewHolder(T holder, int position);

    public int getItemCount() {
        return data.size();
    }

    /**
     * paas position negative if you want to append data end of the list else paas the position
     *
     * @param item
     * @param position
     */
    public void putData(U item, int position) {
        if (position < 0)
            position = data.size();
        if (item instanceof ArrayList)
            data.addAll(position, (ArrayList) item);
        else
            data.add(position, item);
    }

    public interface BaseItemClickListener {
    }


}
