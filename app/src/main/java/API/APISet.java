package API;

import java.util.List;

import ResponseModels.AllOrdersResponseModel;
import ResponseModels.AvailableItemsResponseModel;
import ResponseModels.CurrentOrderResponseModel;
import ResponseModels.ResponseModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APISet {

    @FormUrlEncoded
    @POST("admin_login.php")
    Call<ResponseModel> logIn(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("allOrders.php")
    Call<List<AllOrdersResponseModel>> getAllOrders();

    @GET("currentorders.php")
    Call<List<CurrentOrderResponseModel>> getCurrentOrders();

    @FormUrlEncoded
    @POST("orderReadyStatus.php")
    Call<ResponseModel> setReadyDB(
            @Field("order_id") String order_id
    );

    @FormUrlEncoded
    @POST("orderPickedStatus.php")
    Call<ResponseModel> setPickedDB(
            @Field("order_id") String order_id
    );

    @GET("availableItem.php")
    Call<List<AvailableItemsResponseModel>> getAvailableItems();

    @FormUrlEncoded
    @POST("removeItem.php")
    Call<ResponseModel> removeItem(
            @Field("itemID") int itemID
    );

    @GET("unavailableItems.php")
    Call<List<AvailableItemsResponseModel>> getUnavailableItems();

    @FormUrlEncoded
    @POST("addItem.php")
    Call<ResponseModel> addItem(
            @Field("itemID") int itemID
    );

}
