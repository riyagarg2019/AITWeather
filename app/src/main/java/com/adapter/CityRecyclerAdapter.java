package com.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.data.AppDatabase;
import com.data.City;
import com.example.riyagarg.aitweather.DetailsActivity;
import com.example.riyagarg.aitweather.MainActivity;
import com.example.riyagarg.aitweather.R;
import com.touch.CityTouchHelperAdapter;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.support.v4.content.ContextCompat.startActivity;


public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.ViewHolder> implements CityTouchHelperAdapter{
    private List<City> cityList;
    private Context context;
    public final static String KEY_CITY = "KEY_CITY";

    public CityRecyclerAdapter(List<City> cities, Context con){
        cityList = cities;
        this.context = con;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType){
        View viewRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_row, parent, false);
        return new ViewHolder(viewRow);

    }

    @Override
    public void onBindViewHolder(final CityRecyclerAdapter.ViewHolder holder, int position) {

        holder.tvCityName.setText(cityList.get(holder.getAdapterPosition()).getCityName());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCityDismiss(holder.getAdapterPosition());
            }
        });

        holder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(context.getApplicationContext(), DetailsActivity.class);
                i.addFlags(FLAG_ACTIVITY_NEW_TASK);
                i.putExtra(KEY_CITY, holder.tvCityName.getText());
                context.getApplicationContext().startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public void addCity(City city) {
        cityList.add(city);
        notifyDataSetChanged();

    }

    @Override
    public void onCityDismiss(int position) {
        final City Remove = cityList.remove(position);
        notifyItemRemoved(position);
        new Thread(){
            @Override
            public void run() {
                AppDatabase.getAppDatabase(context).cityDao().delete(Remove);
            }
        }.start();

    }

    public void updateCity(City city) {
        int editPos = findCityIndexByCityId(city.getCityId());
        cityList.set(editPos,city);
        notifyItemChanged(editPos);

    }

    private int findCityIndexByCityId(long cityId){
        for(int i = 0; i < cityList.size(); i++){
            if(cityList.get(i).getCityId() == cityId){
                return i;
            }
        }
        return -1;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCityName;
        private Button btnDetails;
        private Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            tvCityName = itemView.findViewById(R.id.tvCityName);
            btnDetails = itemView.findViewById(R.id.btnDetails);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

    }
}
