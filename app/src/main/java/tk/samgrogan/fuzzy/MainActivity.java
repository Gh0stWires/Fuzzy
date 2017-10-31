package tk.samgrogan.fuzzy;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import tk.samgrogan.fuzzy.models.Videos;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Videos> mVideos = new ArrayList<>();
    public RecyclerView recyclerView;
    public VideosAdapter mVideoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.article_list);
        mVideoAdapter = new VideosAdapter(getApplicationContext(), mVideos);




    }



    public void getVideos(View view) {
        new FindVideosTask().execute();
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
                video.setFileUri(file.toURI());
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
