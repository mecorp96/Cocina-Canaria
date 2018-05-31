package com.example.mecorp.holamyfriend;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Meciro on 07/12/2017.
 */

public class AdaptadorRecetas extends RecyclerView.Adapter<AdaptadorRecetas.ViewHolderRecetas> implements View.OnClickListener{


    ArrayList<OdtReceta> listaRecetas;
    private View.OnClickListener listener;

    public AdaptadorRecetas(ArrayList<OdtReceta> listaRecetas) {
        this.listaRecetas = listaRecetas;
    }

    @Override
    public ViewHolderRecetas onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_recetas, null, false);
        view.setOnClickListener(this);
        return new ViewHolderRecetas(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderRecetas holder, int position) {
            holder.txtNombre.setText(listaRecetas.get(position).getNombreRecycler());
            holder.txtDescripcion.setText(listaRecetas.get(position).getDescripcionRecycler());
            Glide.with(holder.imagen.getContext()).load(listaRecetas.get(position).getImageRecycler()).into(holder.imagen);
    }

    @Override
    public int getItemCount() {
        return listaRecetas.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderRecetas extends RecyclerView.ViewHolder {

        TextView txtNombre, txtDescripcion;
        ImageView imagen;


        public ViewHolderRecetas(View itemView) {
            super(itemView);
            txtNombre = (TextView) itemView.findViewById(R.id.idNombreRecycler);
            txtDescripcion = (TextView) itemView.findViewById(R.id.idDescripcionRecycler);
            imagen = (ImageView) itemView.findViewById(R.id.idImageRecycler);
        }
    }


}
