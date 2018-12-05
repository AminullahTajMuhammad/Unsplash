package unsplash.com.unsplash;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
