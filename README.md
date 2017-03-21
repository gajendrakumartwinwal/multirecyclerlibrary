# multirecyclerlibrary

How to use library is:
<h2>1. initialize the recyclerview which is default intiliztion as provided in Android Recycler library</h2>
   <h3>To support multiple collumn provide total number of collumns by replacing <total number of collumn> and enable by setting it true      isGridEnable = true</h3>
   
    protected void initRecyclerView(){
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(1));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        GridLayoutManager manager = new GridLayoutManager(mContext, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.getCollumnNumber(position) == 1)
                    return <total number of collumn>;
                else if (isGridEnable)
                    return 1;
                else
                    return <total number of collumn>;
            }
        });
        mRecyclerView.setLayoutManager(manager);
    }
       
<h2>2. Setting adpter by just providing Array of RecycleAdapterParam items where RecycleAdapterParam is:</h2>
        ArrayList<RecycleAdapterParam> adapterParams = new ArrayList<>();
        RecycleAdapterParam recycleAdapterParam = new RecycleAdapterParam(mContext, items, VideoItemBinder.class);
        adapterParams.add(recycleAdapterParam);
Where items can be an object or an array and a binder claas to bind there is one more constructor available there also to take the advantage of this paas extra object that can be used in all ItemBinder and get a clickcallback while user clicks on individual item of recycler view.

    new RecycleAdapterParam(mContext, items, VideoItemBinder.class, extraObject, OnItemClickListener);
       
    And call below method that's it:
    protected void bindAdapter(ArrayList<RecycleAdapterParam> adapterParams) {
            if (adapter == null) {
                adapter = new MultiItemRecyclerView(adapterParams);
                mRecyclerView.removeAllViews();
                mRecyclerView.setAdapter(adapter);
            } else {
                adapter.notifyItemChange(adapterParams);
            }
    }
