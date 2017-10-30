package tk.samgrogan.fuzzy.models;

import java.net.URI;

/**
 * Created by ghost on 10/30/2017.
 */

public class Videos {
    String fileName;
    URI fileUri;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public URI getFileUri() {
        return fileUri;
    }

    public void setFileUri(URI fileUri) {
        this.fileUri = fileUri;
    }
}
