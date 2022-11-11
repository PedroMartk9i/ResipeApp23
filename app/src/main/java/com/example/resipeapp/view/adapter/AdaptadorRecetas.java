package com.example.resipeapp.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resipeapp.R;
import com.example.resipeapp.model.entities.Recetas;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdaptadorRecetas extends RecyclerView.Adapter<AdaptadorRecetas.ViewHolder> {

    private ArrayList<Recetas> listadoRecetas;
    private ArrayList<Recetas> listadoOriginal;

    private OnItemClickListener onItemClickListener;

    public AdaptadorRecetas(ArrayList<Recetas> listadoRecetas) {
        this.listadoRecetas = listadoRecetas;
        this.onItemClickListener = null;
        listadoOriginal = new ArrayList<>();
        listadoOriginal.addAll(listadoRecetas);
    }

    public void setListadoRecetas(ArrayList<Recetas> listadoRecetas) {
        this.listadoRecetas = listadoRecetas;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycle_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cargarDatos(listadoRecetas.get(position));
    }

    public void filtrado(String txtBuscar){
        if (txtBuscar.length() == 0){
            listadoRecetas.clear();
            listadoRecetas.addAll(listadoOriginal);
        } else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Recetas> collecion = listadoRecetas.stream().filter(i -> i.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())).collect(Collectors.toList());
                listadoRecetas.clear();
                listadoRecetas.addAll(collecion);
            } else {
                for (Recetas r: listadoOriginal) {
                    if (r.getNombre().toLowerCase().contains(txtBuscar.toLowerCase())){
                        listadoRecetas.add(r);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listadoRecetas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitulo, tvDescripcion;
        ImageView ivPrincipal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitulo = itemView.findViewById(R.id.tv_item_title);
            tvDescripcion = itemView.findViewById(R.id.tv_item_descripcion);
            ivPrincipal = itemView.findViewById(R.id.img_recetas);
        }

        public void cargarDatos(Recetas recetas){
            tvTitulo.setText(recetas.getNombre());
            tvDescripcion.setText(recetas.getDescripcion());
            //picasso(imagen)
            Picasso.get().load(recetas.getUrlImagen()).resize(300,300).centerCrop().error(R.drawable.ic_launcher_background).into(ivPrincipal);

            if (onItemClickListener!=null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(recetas,getAdapterPosition());
                    }
                });
            }
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Recetas recetas, int posicion);
    }

}
