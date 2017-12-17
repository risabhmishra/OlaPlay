package risabh_mishra.olaandroidplay;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Risabh Mishra on 12/16/2017.
 */

public class ApiClient {

    public static final String BASEURL = "http://starlord.hackerearth.com/";
    public static Retrofit retrofit;

    public static Retrofit getApiClient(){
if(retrofit==null) {
    retrofit = new Retrofit.Builder().baseUrl(BASEURL).addConverterFactory(GsonConverterFactory.create()).build();

}
return retrofit;
    }

}

