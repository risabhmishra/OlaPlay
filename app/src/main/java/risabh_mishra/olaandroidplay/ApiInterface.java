package risabh_mishra.olaandroidplay;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

/**
 * Created by Risabh Mishra on 12/16/2017.
 */

public interface ApiInterface {

    @GET("studio")
    Call<List<Songs>> getSongsInfo();

    /*
    @Streaming
    @GET("studio")
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Query("url") String fileUrl);
*/
}
