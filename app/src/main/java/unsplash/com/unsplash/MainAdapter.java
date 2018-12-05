package unsplash.com.unsplash;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    ArrayList<DataClass> data = new ArrayList<>();

    public MainAdapter(ArrayList<DataClass> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.main_listitem,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.tvPicName.setText(data.get(i).getTvPicName());
        myViewHolder.tvPicDescription.setText(data.get(i).getTvDescriptionName());
        myViewHolder.imgPicture.setImageResource(data.get(i).getPicture());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvPicName;
        TextView tvPicDescription;
        ImageView imgPicture;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPicName = itemView.findViewById(R.id.tvItemName);
            tvPicDescription = itemView.findViewById(R.id.tvItemDesc);
            imgPicture = itemView.findViewById(R.id.imgImage);
        }
    }
}
