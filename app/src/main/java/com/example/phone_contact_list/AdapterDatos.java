package com.example.phone_contact_list;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDatos extends RecyclerView.Adapter<AdapterDatos.ViewHolderDatos> implements View.OnClickListener {

    private ArrayList<String> listDatosNombre, listDatosNumber, listDatosFoto;
    private View.OnClickListener listener;

    public AdapterDatos(ArrayList<String> listDatosNombre,
                        ArrayList<String> listDatosNumber,
                        ArrayList<String> listDatosFoto){
        this.listDatosNombre = listDatosNombre;
        this.listDatosNumber = listDatosNumber;
        this.listDatosFoto = listDatosFoto;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder( ViewGroup parent, int viewType) {
        // Enlaza nuestro adaptador con el contactPaint.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contactpaint, null , false);

        view.setOnClickListener(this);

        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(AdapterDatos.ViewHolderDatos holder, int position) {
        // Se encarga de la comunicación entre el ViewHolder y el adaptador, usaremos el objeto Holder

        holder.asignarDatos(listDatosNombre.get(position),
                listDatosNumber.get(position),
                listDatosFoto.get(position));
    }

    @Override
    public int getItemCount() {
        return listDatosNombre.size(); // Mismo tamaño , porque todos los contacts tienen nombre
    }

    // To detect if clicked
    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v); // Check if listener is not null
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder{

        TextView name, number;
        ImageView foto;

        public ViewHolderDatos(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvName);
            number = itemView.findViewById(R.id.tvNumber);
            foto = itemView.findViewById(R.id.iv_image_contactpaint);
        }


        public void asignarDatos(String incomingName, String incomingNumber, String incomingFoto){
            name.setText(incomingName); // Metemos lo que le llegue
            number.setText(incomingNumber);

            if(incomingFoto == null){
                foto.setImageResource(R.drawable.vector_person);
            } else{
                foto.setImageURI(Uri.parse(incomingFoto));
            }
        }
    }
}