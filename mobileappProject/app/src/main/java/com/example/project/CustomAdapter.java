package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter <CustomAdapter.MyViewHolder> implements Filterable{

    private Context context;
    private Activity activity;
    private ArrayList<String> phone_id, phone_name, phone_price, phone_description;

    //Duplicate list used for search query
    private List<String> phone_id_full, phone_name_full, phone_price_full, phone_description_full;

    int position;

    CustomAdapter(Context context,
                  ArrayList phone_id,
                  ArrayList phone_name,
                  ArrayList phone_price,
                  ArrayList phone_description){

        this.context                = context;
        this.phone_id               = phone_id;
        this.phone_name             = phone_name;
        this.phone_price            = phone_price;
        this.phone_description      = phone_description;

        this.phone_id_full          = new ArrayList(phone_id);
        this.phone_name_full        = new ArrayList(phone_name);
        this.phone_price_full       = new ArrayList(phone_price);
        this.phone_description_full = new ArrayList(phone_description);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_image, parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        this.position=position;

        holder.phone_id_txt.setText(String.valueOf(phone_id.get(position)));
        holder.phone_name_txt.setText(String.valueOf(phone_name.get(position)));
        holder.phone_price_txt.setText(String.valueOf(phone_price.get(position)));
        holder.phone_description_txt.setText(String.valueOf(phone_description.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id",String.valueOf(phone_id.get(position)));
                intent.putExtra("name",String.valueOf(phone_name.get(position)));
                intent.putExtra("price",String.valueOf(phone_price.get(position)));
                intent.putExtra("description",String.valueOf(phone_description.get(position)));
                context.startActivity(intent);
            }
        });
    }

    // Returns the total items in the array list
    @Override
    public int getItemCount() {
        return phone_id.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView phone_id_txt, phone_name_txt, phone_price_txt, phone_description_txt;
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            phone_id_txt=itemView.findViewById(R.id.Mid);
            phone_name_txt=itemView.findViewById(R.id.Mname);
            phone_price_txt = itemView.findViewById(R.id.Mprice);
            phone_description_txt = itemView.findViewById(R.id.Mdescription);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    public Filter getFilter(){
        return listFilter;
    }

    // Used to filter the products based on search query
    Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List filteredList = new ArrayList<String>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(phone_name);
                filteredList.addAll(phone_price);
                filteredList.addAll(phone_id);
                filteredList.addAll(phone_description);
            }
            else {

                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(String name: phone_name_full){
                if(name.toString().toLowerCase().contains(filterPattern)){
                    filteredList.add(name);
                }
                }
                for(String id: phone_id_full){
                    if(id.toString().toLowerCase().contains(filterPattern)){
                        filteredList.add(id);
                    }
                }
                for(String price: phone_price_full){
                    if(price.toString().toLowerCase().contains(filterPattern)){
                        filteredList.add(price);
                    }
                }
                for(String des: phone_description_full){
                    if(des.toString().toLowerCase().contains(filterPattern)){
                        filteredList.add(des);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            phone_name.clear();
            phone_description.clear();
            phone_id.clear();
            phone_price.clear();

            phone_name.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();

        }
    };
}
