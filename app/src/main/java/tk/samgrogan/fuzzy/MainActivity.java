package tk.samgrogan.fuzzy;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;

import com.google.android.gms.cast.framework.CastButtonFactory;
import com.google.android.gms.cast.framework.CastContext;
import com.google.android.gms.cast.framework.CastSession;
import com.google.android.gms.cast.framework.Session;
import com.google.android.gms.cast.framework.SessionManager;
import com.google.android.gms.cast.framework.SessionManagerListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tk.samgrogan.fuzzy.models.Videos;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Videos> mVideos = new ArrayList<>();
    public RecyclerView recyclerView;
    public VideosAdapter mVideoAdapter;

    private SessionManager mSessionManager;
    private CastSession mCastSession;
    private final SessionManagerListener mSessionManagerListener =
            new SessionManagerListenerImpl();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.article_list);
        mVideoAdapter = new VideosAdapter(getApplicationContext(), mVideos);


        mSessionManager = CastContext.getSharedInstance(this).getSessionManager();
        mCastSession = mSessionManager.getCurrentCastSession();
        mSessionManager.addSessionManagerListener(mSessionManagerListener);






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        CastButtonFactory.setUpMediaRouteButton(getApplicationContext(),menu,R.id.media_route_menu_item);
        //CastButtonFactory.setUpMediaRouteButton(getApplicationContext(), menu, R.id.cast);

        return true;
    }

    public void getVideos(View view) {
        new FindVideosTask().execute();
    }

    private class SessionManagerListenerImpl implements SessionManagerListener {

        @Override
        public void onSessionStarting(Session session) {

        }

        @Override
        public void onSessionStarted(Session session, String s) {
            invalidateOptionsMenu();
        }

        @Override
        public void onSessionStartFailed(Session session, int i) {

        }

        @Override
        public void onSessionEnding(Session session) {

        }

        @Override
        public void onSessionEnded(Session session, int i) {
            finish();
        }

        @Override
        public void onSessionResuming(Session session, String s) {

        }

        @Override
        public void onSessionResumed(Session session, boolean b) {
            invalidateOptionsMenu();
        }

        @Override
        public void onSessionResumeFailed(Session session, int i) {

        }

        @Override
        public void onSessionSuspended(Session session, int i) {

        }
    }


    public class FindVideosTask extends AsyncTask{
        File folder;
        List<File> files = new ArrayList<>();

        private void checkFiles(File dir, List<File> files) {
            String extensionOne = ".mp4";
            File[] fileList = dir.listFiles();
            if (fileList != null) {
                for (int i = 0; i < fileList.length; i++) {
                    if (fileList[i].isDirectory()) {
                        //if this is a directory, loop over the files in the directory
                        checkFiles(fileList[i], files);
                    } else {
                        if (fileList[i].getName().endsWith(extensionOne) ) {
                            //this is the file you want, do whatever with it here
                            files.add(fileList[i]);
                        }

                    }
                }
            }
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            folder = new File(String.valueOf(Environment.getExternalStorageDirectory()));
            checkFiles(folder, files);

            for (int i = 0; i < files.size(); i++){
                File file = files.get(i);
                Videos video = new Videos();
                video.setFileName(file.getName());
                video.setFileUri(Uri.fromFile(file));
                mVideos.add(video);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            recyclerView.setAdapter(mVideoAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
    }
}
