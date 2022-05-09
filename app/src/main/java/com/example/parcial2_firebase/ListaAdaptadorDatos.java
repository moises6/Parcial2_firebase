package com.example.parcial2_firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.parcial2_firebase.Modelos.DatosRegistros;
import com.example.parcial2_firebase.R;
import com.example.parcial2_firebase.RegistrarDatos;

import java.util.ArrayList;

public class ListaAdaptadorDatos extends BaseAdapter {
    Context contexto;
    ArrayList<DatosRegistros> arregloDatos;
    LayoutInflater inflaterAdapter;
    DatosRegistros objDatos;

    public ListaAdaptadorDatos(Context contexto,ArrayList<DatosRegistros> arregloDatos){
        this.contexto=contexto;
        this.arregloDatos=arregloDatos;
        inflaterAdapter=(LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ListaAdaptadorDatos(VerDatos contexto, ArrayList<DatosRegistros> listaDatos) {
    }

    @Override
    public int getCount() {
        return arregloDatos.size();
    }

    @Override
    public Object getItem(int i) {
        return arregloDatos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View filaVista=view;
        if (filaVista==null){
            filaVista=inflaterAdapter.inflate(R.layout.item_datos,null,true);
        }
        TextView titulo,descripcion,fecha,hora,estatus,usuario;
        titulo=filaVista.findViewById(R.id.txtTituloID);
        descripcion=filaVista.findViewById(R.id.txtDescripcionID);
        fecha=filaVista.findViewById(R.id.txtFechaID);
        hora=filaVista.findViewById(R.id.txtHoraID);
        estatus=filaVista.findViewById(R.id.txtEstatusID);
        usuario=filaVista.findViewById(R.id.txtUsuarioID);
        objDatos=arregloDatos.get(i);
        titulo.setText(objDatos.getTitulo());
        descripcion.setText(objDatos.getDescripcion());
        fecha.setText(objDatos.getFecha());
        hora.setText(objDatos.getHora());
        estatus.setText(objDatos.getEstatus());
        usuario.setText(objDatos.getUsuario());
        return filaVista;
    }
}
