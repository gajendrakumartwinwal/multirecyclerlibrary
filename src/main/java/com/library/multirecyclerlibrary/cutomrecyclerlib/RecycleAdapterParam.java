package com.library.multirecyclerlibrary.cutomrecyclerlib;

import android.content.Context;

import java.util.ArrayList;


public class RecycleAdapterParam {
    //Here data is made only fore temp use as it does not change for single object use class instance data instead of this data
    Object data, extraObject;
    Class<? extends BaseDataBinder> className;
    BaseDataBinder.BaseItemClickListener clickListener;
    BaseDataBinder classInstance;
    MultiItemRecyclerView adapter;
    Context mContext;
    int collumnNumber = 1;

    public RecycleAdapterParam(Context context, Object data, Class<? extends BaseDataBinder> className) {
        this(context, data, className, null);
    }

    public void setCollumnNumber(int collumnNumber) {
        this.collumnNumber = collumnNumber;
    }

    public RecycleAdapterParam(Context context, Object data, Class<? extends BaseDataBinder> className, BaseDataBinder.BaseItemClickListener clickListener) {
        this(context, data, className, null, clickListener);
    }

    public RecycleAdapterParam(Context context, Object data, Class<? extends BaseDataBinder> className, Object extraObject, BaseDataBinder.BaseItemClickListener clickListener) {
        if (data instanceof ArrayList) {
            this.data = ((ArrayList) data).clone();
        } else {
            try {
                this.data = ((BaseObject) data).clone();
            } catch (Exception e) {
                e.printStackTrace();
                this.data = data;
            }
        }
        this.className = className;
        this.clickListener = clickListener;
        this.mContext = context;
        this.extraObject = extraObject;
    }

    public RecycleAdapterParam createClassInstance(MultiItemRecyclerView adpt, int viewtype) {
        try {
            this.adapter = adpt;
            ArrayList inputDataArray;
            if (data instanceof ArrayList)
                inputDataArray = (ArrayList) data;
            else {
                inputDataArray = new ArrayList();
                inputDataArray.add(data);
            }
            this.classInstance = className.getConstructor(Context.class, ArrayList.class, MultiItemRecyclerView.class, int.class, Object.class, BaseDataBinder.BaseItemClickListener.class).
                    newInstance(mContext, inputDataArray, adapter, viewtype, extraObject, clickListener);
            return this;
        } catch (Exception e) {
            return this;
        }
    }

    /**
     * In case of single item whole adapter parama should be removed
     *
     * @param position
     */
    public boolean notifyItemRemoved(int position) {
        if (((ArrayList) data).size() > 0) {
            ((ArrayList) data).remove(position);
            return true;
        } else
            return false;

    }

    public void notifyItemInserted(int position, Object dataitem) {
        if (dataitem instanceof ArrayList) {
            ArrayList itmes = ((ArrayList) dataitem);
            for (int i = 0; i < itmes.size(); i++) {
                ((ArrayList) data).add(position + i, itmes.get(i));
            }
        } else {
            ((ArrayList) data).add(position, dataitem);
        }
    }

    //Added for notifyitem inserted

    public Class<? extends BaseDataBinder> getClassName() {
        return className;
    }
}
