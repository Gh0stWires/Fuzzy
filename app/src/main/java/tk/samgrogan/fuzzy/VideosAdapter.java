package tk.samgrogan.fuzzy;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.cast.MediaInfo;
import com.google.android.gms.cast.MediaMetadata;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.media.RemoteMediaClient;

import java.util.List;

import tk.samgrogan.fuzzy.models.Videos;

/**
 * Created by ghost on 10/30/2017.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {

    private List<Videos> mVideos;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView fileName;
        public ImageView mediaImage;
        public Uri mItemURI;
        public String mTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.filename);
            mediaImage = itemView.findViewById(R.id.media_image);
            itemView.setOnClickListener(this);
        }

        public void setItem(Uri itemURI, String title){
            mItemURI = itemURI;
            mTitle = title;
        }


        @Override
        public void onClick(View v) {
            if( CastContext.getSharedInstance(getContext()).getSessionManager().getCurrentCastSession() != null
                    && CastContext.getSharedInstance(getContext()).getSessionManager().getCurrentCastSession().getRemoteMediaClient() != null ){
                MediaMetadata movieMetadata = new MediaMetadata(MediaMetadata.MEDIA_TYPE_MOVIE);
                movieMetadata.putString(MediaMetadata.KEY_TITLE, mTitle);
                RemoteMediaClient mediaClient = CastContext.getSharedInstance(getContext()).getSessionManager().getCurrentCastSession().getRemoteMediaClient();

                MediaInfo mediaInfo = new MediaInfo.Builder(mItemURI.toString()).setStreamType(MediaInfo.STREAM_TYPE_BUFFERED).setMetadata(movieMetadata).setContentType("video/mp4").build();
                mediaClient.load(mediaInfo, true, 0);
            }
        }
    }

    public VideosAdapter(Context context, List<Videos> videos){
        mContext = context;
        mVideos = videos;
    }

    public Context getContext(){
        return mContext;
    }

    @Override
    public VideosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View videoItem = inflater.inflate(R.layout.item_video, parent, false);
        ViewHolder viewHolder = new ViewHolder(videoItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VideosAdapter.ViewHolder holder, int position) {
        Videos video = mVideos.get(position);
        holder.setItem(mVideos.get(position).getFileUri(), mVideos.get(position).getFileName());

        TextView textView = holder.fileName;
        textView.setText(video.getFileName());
        ImageView imageView = holder.mediaImage;
        imageView.setImageResource(R.drawable.filmicon);
    }

    @Override
    public int getItemCount() {
        return mVideos.size();
    }
}
