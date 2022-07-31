package Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.AddItemActivity;
import com.example.admin.R;

import java.util.List;

import Controller.Controller;
import ResponseModels.AvailableItemsResponseModel;
import ResponseModels.ResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemAdapter extends RecyclerView.Adapter<AddItemAdapter.AddItemViewHolder> {

    List<AvailableItemsResponseModel> data;

    public AddItemAdapter(List<AvailableItemsResponseModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public AddItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_item_row,parent,false);
        return new AddItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddItemViewHolder holder, int position) {
        holder.itemName.setText("Name: " + data.get(position).getItem_name());
        holder.itemID.setText("ID: " + data.get(position).getItem_id());
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(data.get(holder.getAdapterPosition()).getItem_id(),data.get(holder.getAdapterPosition()).getItem_name(),view);

                data.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), data.size());

                Log.d("removeBTN","Clicked");

                if (data.size() == 0) {
                    AddItemActivity.textView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void addItem(int item_id, String item_name, View view) {
        Call<ResponseModel> call = Controller.getInstance().getAPI().addItem(item_id);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel obj = response.body();
                String temp = obj.getAdded();
                if (temp.equals("true")) {
                    Toast.makeText(view.getContext(),item_name + " Added" , Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("addError","error");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("AddResError",t.getMessage());
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class AddItemViewHolder extends RecyclerView.ViewHolder{

        TextView itemName,itemID;
        Button addBtn;

        public AddItemViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemNameAdd);
            itemID = itemView.findViewById(R.id.itemIDAdd);
            addBtn = itemView.findViewById(R.id.addBtn);

        }
    }
}
