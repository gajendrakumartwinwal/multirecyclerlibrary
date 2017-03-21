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
    
    
<h2>Sample for VideoItemBinder </h2>
{
public class VideoItemBinder extends BaseDataBinder<VideoItemBinder.ViewHolder, VideoItem> {
    private VideoItem mVideoItem;

    public VideoItemBinder(Context context, ArrayList<VideoItem> data, MultiItemRecyclerView adpt, int viewtype, Object extraObject, BaseItemClickListener clickListener) {
        super(context, data, adpt, viewtype, extraObject, clickListener);
    }

    @Override
    public ViewHolder newViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.view_video_item_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder, int position) {
        mVideoItem = data.get(position);
        holder.mTitle.setText(mVideoItem.getTitle());
        holder.mLike.setTag(R.id.bookmark_status, 0);//1 For not bookmarked
        String videImageUrl = mContext.getResources().getString(R.string.youtube_image_url).replace("youtubeid", mVideoItem.getYoutubeid() + "");
        holder.mImage.bindImage(videImageUrl, ImageView.ScaleType.FIT_XY);
        holder.container.setTag(mVideoItem);
        holder.mLike.setTag(mVideoItem);
        holder.mComment.setTag(mVideoItem);
        holder.mShare.setTag(mVideoItem);
    }


    class ViewHolder extends BaseViewHolder implements View.OnClickListener {
        TextView mTitle;
        View container;
        CrossFadeImageView mImage;
        ImageView mThumb;
        View mLike, mComment, mShare;

        public ViewHolder(View view) {
            super(view, adapter);
            mThumb = (ImageView) view.findViewById(R.id.thumb);
            mTitle = (TextView) view.findViewById(R.id.title);
            mImage = (CrossFadeImageView) view.findViewById(R.id.youtubeImageView);
            mLike = view.findViewById(R.id.likeContainer);
            mComment = view.findViewById(R.id.commentContainer);
            mShare = view.findViewById(R.id.shareContainer);
            container = view;
            view.setOnClickListener(this);
            mLike.setOnClickListener(this);
            mComment.setOnClickListener(this);
            mShare.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            VideoItem clickeVideoItem = (VideoItem) v.getTag();
            //Use Click listener if required and passed in
        }
    }

}
}
