package com.library.multirecyclerlibrary.cutomrecyclerlib;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;


public class MultiItemRecyclerView extends BaseAdapter {

    /**
     * viewType and adapterparam map here adapter params contains all data of one type
     */
    private HashMap<Integer, RecycleAdapterParam> mAdapterMap;
    private ArrayList<Integer> possitionViewTypeMap;

    /**
     * Selection variable declairations
     */

    public MultiItemRecyclerView(ArrayList<RecycleAdapterParam> adapterParams) {
        initializeAdapterParams(adapterParams);
    }

    @Override
    public BaseViewHolder getNewViewHolder(ViewGroup parent, int viewType) {
        return mAdapterMap.get(viewType).classInstance.newViewHolder(parent);
    }

    @Override
    public void onBndViewHolder(BaseViewHolder holder, int position) {
        mAdapterMap.get(holder.getItemViewType()).classInstance.bindViewHolder(holder, getViewTypeItemPosition(position, -1));
    }

    @Override
    public int getItemViewType(int position) {
        return possitionViewTypeMap.get(position);
    }

    @Override
    public int getItemCount() {
        return possitionViewTypeMap.size();
    }

    /**
     * Use targetviewtype if you are not sure viewtype exist on that position or not else paas it to some negative number
     *
     * @param position
     * @param targetviewtype
     * @return
     */
    private int getViewTypeItemPosition(int position, int targetviewtype) {
        Integer targetViewType;
        if (targetviewtype < 0)
            targetViewType = getItemViewType(position);
        else
            targetViewType = targetviewtype;

        int binderPosition = -1;
        for (int i = 0; i <= position; i++) {
            if (targetViewType == getItemViewType(i)) {
                binderPosition++;
            }
        }
        return binderPosition;
    }

    public void notifyItemChanged(int viewtype, int pos) {
        notifyItemChanged(getActualPossition(viewtype, pos));
    }

    /**
     * Notify item of className variable type
     *
     * @param className
     */
    public void notifyItemChanged(Class<? extends BaseDataBinder> className) {
        int viewtype = getViewType(className);
        for (int i = 0; i < possitionViewTypeMap.size(); i++) {
            if (possitionViewTypeMap.get(i) == viewtype) notifyItemChanged(i);
        }
    }

    public void notifyItemRemove(int viewtype, int pos) {
        boolean isRemove = mAdapterMap.get(viewtype).notifyItemRemoved(pos);
        if (!isRemove)
            mAdapterMap.remove(viewtype);

        notifyItemRemoved(getActualPossition(viewtype, pos));
        possitionViewTypeMap.remove(getActualPossition(viewtype, pos));
    }

    public void notifyItemInserted(RecycleAdapterParam param, int actualpos) {
        int viewtype = getViewType(param.className);
        //Posion in perticular viewtype
        int viewPosition;
        if (viewtype < 0) {//because no other same type view exists so it is the first object in the list
            viewPosition = 0;
            viewtype = mAdapterMap.size();
            mAdapterMap.put(viewtype, param.createClassInstance(this, viewtype));
            possitionViewTypeMap.add(actualpos, viewtype);
        } else {
            viewPosition = getViewTypeItemPosition(actualpos, viewtype) + 1;
            mAdapterMap.get(viewtype).notifyItemInserted(viewPosition, param.data);
            int count = param.data instanceof ArrayList ? ((ArrayList) param.data).size() : 1;
            for (int i = 0; i < count; i++)
                possitionViewTypeMap.add(actualpos, viewtype);
        }

        notifyItemInserted(actualpos);
    }

    public void notifyItemChange(ArrayList<RecycleAdapterParam> adapterParams) {
        initializeAdapterParams(adapterParams);
        notifyDataSetChanged();
    }

    public int getCollumnNumber(int position) {
        return mAdapterMap.get(getItemViewType(position)).collumnNumber;
    }

    public int getActualPossition(int viewtype, int pos) {
        int returnPosition = -1;
        for (int i = 0; i < getItemCount(); i++) {
            if (getItemViewType(i) == viewtype)
                returnPosition = returnPosition + 1;
            if (pos == returnPosition)
                return i;
        }
        return returnPosition;
    }

    private void initializeAdapterParams(ArrayList<RecycleAdapterParam> adapterParams) {
        mAdapterMap = new HashMap<>();
        HashMap<Class<? extends BaseDataBinder>, Integer> classTypeMap = new HashMap<>();
        possitionViewTypeMap = new ArrayList();
        int viewType = 0;
        for (int i = 0; i < adapterParams.size(); i++) {
            if (classTypeMap.get(adapterParams.get(i).className) == null) {
                classTypeMap.put(adapterParams.get(i).className, viewType);
                mAdapterMap.put(viewType, adapterParams.get(i).createClassInstance(this, viewType));
                for (int j = 0; j < adapterParams.get(i).classInstance.getItemCount(); j++)
                    possitionViewTypeMap.add(viewType);
                viewType = viewType + 1;
            } else {
                mAdapterMap.get(classTypeMap.get(adapterParams.get(i).className)).classInstance.putData(adapterParams.get(i).data, -1);
                if (adapterParams.get(i).data instanceof ArrayList) {
                    for (int j = 0; j < ((ArrayList) adapterParams.get(i).data).size(); j++) {
                        possitionViewTypeMap.add(classTypeMap.get(adapterParams.get(i).className));
                    }
                } else {
                    possitionViewTypeMap.add(classTypeMap.get(adapterParams.get(i).className));
                }
            }
        }
    }

    //Added for notify item inserted
    private int getViewType(Class<? extends BaseDataBinder> className) {
        for (int key : mAdapterMap.keySet()) {
            if (mAdapterMap.get(key).classInstance.getClass().getName().equals(className.getName())) {
                return key;
            }
        }
        return -1;
    }
}
