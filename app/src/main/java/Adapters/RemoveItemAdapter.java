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

import com.example.admin.R;

import java.util.List;

import Controller.Controller;
import ResponseModels.AvailableItemsResponseModel;
import ResponseModels.ResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveItemAdapter extends RecyclerView.Adapter<RemoveItemAdapter.RemoveItemViewHolder> {

    List<AvailableItemsResponseModel> data;

    public RemoveItemAdapter(List<AvailableItemsResponseModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RemoveItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remove_item_row,parent,false);
        return new RemoveItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemoveItemViewHolder holder, int position) {

        holder.itemName.setText("Name: " + data.get(position).getItem_name());
        holder.itemID.setText("ID: " + data.get(position).getItem_id());

        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                removeItem(data.get(holder.getAdapterPosition()).getItem_id(),data.get(holder.getAdapterPosition()).getItem_name(),view);

//                holder.removeBtn.setClickable(false);
//                holder.removeBtn.setBackgroundColor(R.color.navyBlue);
//                holder.removeBtn.setTextColor(R.color.white);

                data.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), data.size());

                Log.d("removeBTN","Clicked");
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    void removeItem(int id,String name,View view){
        Call<ResponseModel> call = Controller.getInstance().getAPI().removeItem(id);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel obj = response.body();
                String temp = obj.getRemoved();
                if (temp.equals("true")) {
                    Toast.makeText(view.getContext(),name + " removed" , Toast.LENGTH_SHORT).show();

                } else {
                    Log.e("removeError","error");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("RemoveResError",t.getMessage());
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class RemoveItemViewHolder extends RecyclerView.ViewHolder{

        TextView itemName,itemID;
        Button removeBtn;

        public RemoveItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemID = itemView.findViewById(R.id.itemID);
            removeBtn = itemView.findViewById(R.id.removeBtn);

        }
    }
}
