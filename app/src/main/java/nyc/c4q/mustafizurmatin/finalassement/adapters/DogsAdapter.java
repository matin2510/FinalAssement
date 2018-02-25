package nyc.c4q.mustafizurmatin.finalassement.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nyc.c4q.mustafizurmatin.finalassement.PhotoActivity;
import nyc.c4q.mustafizurmatin.finalassement.R;

/**
 * Created by c4q on 2/25/18.
 */

public class DogsAdapter  extends RecyclerView.Adapter<DogsAdapter.DogsViewHolder> {
    List<String> dogsList;
    Context context;

    public DogsAdapter(Context context) {
        dogsList= new ArrayList<>();
        this.context = context;
    }

    @Override
    public DogsAdapter.DogsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new DogsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DogsAdapter.DogsViewHolder holder, int position) {

        final String url=dogsList.get(position);
        Picasso.with(context).load(url).fit().into(holder.imageDogHolder);
        Log.d("ADAPTER", "onBindViewHolder: "+dogsList.get(position));
        holder.imageDogHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PhotoActivity.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dogsList.size();
    }


    public void addImages(List<String> data) {
        dogsList.addAll(data);
        notifyDataSetChanged();
    }
    public class DogsViewHolder extends RecyclerView.ViewHolder {

        ImageView imageDogHolder;

        public DogsViewHolder(View itemView) {
            super(itemView);
            imageDogHolder=(ImageView)itemView.findViewById(R.id.imageDogHolder);

        }
    }
}


