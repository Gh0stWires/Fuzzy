package tk.samgrogan.fuzzy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tk.samgrogan.fuzzy.models.Videos;

/**
 * Created by ghost on 10/30/2017.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {

    private List<Videos> mVideos;
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView fileName;
        public ImageView mediaImage;
        public ViewHolder(View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.filename);
            mediaImage = itemView.findViewById(R.id.media_image);
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
