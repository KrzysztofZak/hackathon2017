package com.costrella.sp.sp.family;

/**
 * Created by mike on 2017-10-29.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.costrella.sp.sp.R;
import com.costrella.sp.sp.model.Family;
import com.costrella.sp.sp.notify.NotifyController;

import java.util.List;

public class FamilyAdapter extends RecyclerView.Adapter<FamilyAdapter.MyViewHolder> {

    private List<Family> moviesList;
Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;
        ImageView checkbtn;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            checkbtn = (ImageView) view.findViewById(R.id.checkbtn);

        }
    }


    public FamilyAdapter(List<Family> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.family_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Family family = moviesList.get(position);
        holder.title.setText(family.getFamilyName());
        holder.genre.setText(family.getAddress());
        if(family.getId() == NotifyController.familyId){
            holder.title.setTextColor(context.getResources().getColor(R.color.colorAccent));
            holder.checkbtn.setVisibility(View.VISIBLE);
        }else{
            holder.title.setTextColor(context.getResources().getColor(R.color.black_overlay));
            holder.checkbtn.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}