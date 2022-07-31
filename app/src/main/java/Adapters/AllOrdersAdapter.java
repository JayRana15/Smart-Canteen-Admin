package Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import ResponseModels.AllOrdersResponseModel;

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.AllOrdersViewHolder> {
    List<AllOrdersResponseModel> data;

    public AllOrdersAdapter(List<AllOrdersResponseModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public AllOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_order_row,parent,false);
        return new AllOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllOrdersViewHolder holder, int position) {
        holder.orderDetails.setText(data.get(position).getItems_name());
        holder.transactionID.setText("Transaction ID: "+data.get(position).getTxn_id());
        holder.amountTV.setText(data.get(position).getAmount()+"₹");
        holder.date.setText(formatter(data.get(position).getDate()));
        holder.orderId.setText( "Order ID: "+ data.get(position).getOrder_id());
        holder.uid.setText("User Id: " + data.get(position).getUid());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    String formatter(String date){
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yy hh:mm");
        String newDate = "";

        try {
            newDate = myFormat.format(fromUser.parse(date));
        } catch (ParseException e) {
            Log.e("dateError",e.getMessage());
        }

        return newDate;
    }

    class AllOrdersViewHolder extends RecyclerView.ViewHolder {

        TextView orderDetails,transactionID,amountTV,date,uid,orderId;

        public AllOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            orderDetails = itemView.findViewById(R.id.orderDetails);
            transactionID = itemView.findViewById(R.id.transactionID);
            amountTV = itemView.findViewById(R.id.amountTV);
            date = itemView.findViewById(R.id.date);
            uid = itemView.findViewById(R.id.uid);
            orderId = itemView.findViewById(R.id.orderIdTV);

        }
    }

}
