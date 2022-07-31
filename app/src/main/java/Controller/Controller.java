package Controller;

import API.APISet;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {

//    private static final String URL = "http://192.168.0.111/smart_canteen/admin/";
//    private static final String URL = "http://192.168.112.57/smart_canteen/admin/";
    private static final String URL = "http://192.168.72.161/smart_canteen/admin/";
    private static Controller clientObj;
    private static Retrofit retrofit;

    Controller() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized Controller getInstance() {
        if (clientObj == null)
            clientObj = new Controller();
        return clientObj;
    }

    public APISet getAPI(){
        return retrofit.create(APISet.class);
    }


}
