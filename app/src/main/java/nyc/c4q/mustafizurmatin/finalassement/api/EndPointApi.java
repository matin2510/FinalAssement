package nyc.c4q.mustafizurmatin.finalassement.api;

import nyc.c4q.mustafizurmatin.finalassement.models.RootObject;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by c4q on 2/25/18.
 */

public interface EndPointApi {

    @GET("breed/{breed}/images")
        retrofit2.Call<RootObject> getDogs(@Path("breed") String breed);
}
