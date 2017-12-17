package risabh_mishra.olaandroidplay;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Risabh Mishra on 12/16/2017.
 */

public class Songs {

    @SerializedName("song")
    private String Song;

    @SerializedName("url")
    private String URL;

    @SerializedName("artists")
    private String Artist;

    @SerializedName("cover_image")
    private String CoverImage;

    public String getSong() {
        return Song;
    }

    public String getURL() {
        return URL;
    }

    public String getArtist() {
        return Artist;
    }

    public String getCoverImage() {
        return CoverImage;
    }
}

