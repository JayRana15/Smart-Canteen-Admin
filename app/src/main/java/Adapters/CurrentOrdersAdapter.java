package Adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.CurrentOrdersActivity;
import com.example.admin.R;

import java.util.List;

import Controller.Controller;
import ResponseModels.CurrentOrderResponseModel;
import ResponseModels.ResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CurrentOrdersAdapter extends RecyclerView.Adapter<CurrentOrdersAdapter.CurrentOrdersViewHolder> {
    List<CurrentOrderResponseModel> data;

    public CurrentOrdersAdapter(List<CurrentOrderResponseModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public CurrentOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_order_row,parent,false);

        return new CurrentOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentOrdersViewHolder holder, int position) {

        holder.orderDetails.setText("Order: "+ data.get(position).getItems_name());
        holder.orderID.setText("Order Id: "+data.get(position).getOrder_id());
        holder.userId.setText("User ID: "+data.get(position).getUid());
        holder.readyBTN.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(),data.get(holder.getAdapterPosition()).getUid(), Toast.LENGTH_SHORT).show();
                setReadyDb(data.get(holder.getAdapterPosition()).getOrder_id(),view);

                holder.readyBTN.setClickable(false);
                holder.readyBTN.setBackgroundColor(R.color.navyBlue);
                holder.readyBTN.setTextColor(R.color.white);


                Log.d("readyBTn","pressed");
            }
        });

        holder.pickedBTN.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                setPickedDb(data.get(holder.getAdapterPosition()).getOrder_id(),view);

                data.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), data.size());

                Log.d("pickedBTN","pressed");

                if (data.size() == 0 ){
                    CurrentOrdersActivity.textView.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    private void setPickedDb(String order_id, View view) {
        Call<ResponseModel> call = Controller.getInstance().getAPI().setPickedDB(order_id);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel obj = response.body();
                String temp = obj.getStatus();
                if (temp.equals("picked")){
                    Log.d("noti2",temp);
                } else {
                    Log.e("noti2",temp);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("StatusError",t.getMessage());
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setReadyDb(String order_id,View view) {

        Call<ResponseModel> call = Controller.getInstance().getAPI().setReadyDB(order_id);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel obj = response.body();
                String temp = obj.getStatus();
                if (temp.equals("sent")){
                    Log.d("noti",temp);
                } else {
                    Log.e("noti",temp);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("StatusError",t.getMessage());
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class CurrentOrdersViewHolder extends RecyclerView.ViewHolder {

        TextView orderDetails,orderID,userId;
        Button readyBTN,pickedBTN;

        public CurrentOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            orderDetails = itemView.findViewById(R.id.orderDetailsTV);
            orderID = itemView.findViewById(R.id.orderID);
            userId = itemView.findViewById(R.id.userIDTV);
            readyBTN = itemView.findViewById(R.id.readyBTN);
            pickedBTN = itemView.findViewById(R.id.pickedBtn);

        }
    }
}
