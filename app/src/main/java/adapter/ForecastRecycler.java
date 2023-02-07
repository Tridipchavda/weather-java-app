package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.ForecastStructure;
import com.example.weatherapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ForecastRecycler extends RecyclerView.Adapter<ForecastRecycler.ViewHolder>{

    private Context context;
    private ArrayList<ForecastStructure> forecastList;

    public ForecastRecycler(Context context,ArrayList<ForecastStructure> forecasts){
        this.context = context;
        this.forecastList = forecasts;
    }
    @NonNull
    @Override
    public ForecastRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_forecast,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastRecycler.ViewHolder holder, int position) {
        ForecastStructure forecastStructure = forecastList.get(position);

        holder.datetime.setText(forecastStructure.getDatetime());
        holder.temp.setText(forecastStructure.getHourTemp());
        holder.condition.setText(forecastStructure.getCondition());

        Picasso.get().load("https://"+forecastStructure.getConditionImg()).into(holder.conditionImg);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView condition,temp,datetime;
        public ImageView conditionImg;

        public ViewHolder(View itemView) {
            super(itemView);

            condition = itemView.findViewById(R.id.condition);
            conditionImg = itemView.findViewById(R.id.conditionImg);
            temp = itemView.findViewById(R.id.hourTemp);
            datetime = itemView.findViewById(R.id.datetime);

        }

    }
}