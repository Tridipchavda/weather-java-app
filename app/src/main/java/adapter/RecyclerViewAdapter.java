package adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.Contact;
import com.example.weatherapp.DetailedForecast;
import com.example.weatherapp.MainActivity;
import com.example.weatherapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Contact> contactList;
    private String forecastAPI;

    public RecyclerViewAdapter(Context context,ArrayList<Contact> list,String api){
        this.context = context;
        this.contactList = list;
        this.forecastAPI = api;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactList.get(position);


        holder.date.setText(contact.getDate());
        holder.min.setText("Min "+contact.getMinTemp()+"\u00ba C");
        holder.max.setText("Max "+contact.getMaxTemp()+"\u00ba C");

        Picasso.get().load("https://".concat(contact.getImgURL())).into(holder.dinKaHalImg);
        holder.dinKaHal.setText(contact.getHaal());
    }

    @Override
    public int getItemCount() {

        return contactList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView dinKaHal,date,max,min;
        public ImageView dinKaHalImg;
        public ViewHolder(View itemView) {
            super(itemView);

            dinKaHalImg = itemView.findViewById(R.id.dinKaHalImg);
            dinKaHal = itemView.findViewById(R.id.dinKaHal);
            date = itemView.findViewById(R.id.date);
            max = itemView.findViewById(R.id.max);
            min = itemView.findViewById(R.id.min);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = this.getAdapterPosition();

            Toast.makeText(context,"Forecast After "+position+" days ...",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(view.getContext(), DetailedForecast.class);
            intent.putExtra("forecastAPI",forecastAPI);
            intent.putExtra("counter",position);

            intent.setClass(view.getContext(),DetailedForecast.class);

            view.getContext().startActivity(intent);
        }
    }
}
