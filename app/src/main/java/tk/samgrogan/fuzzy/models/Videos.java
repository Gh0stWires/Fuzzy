package tk.samgrogan.fuzzy.models;

import android.net.Uri;

/**
 * Created by ghost on 10/30/2017.
 */

public class Videos {
    String fileName;
    Uri fileUri;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }
}
