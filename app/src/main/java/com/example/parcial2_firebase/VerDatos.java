package com.example.parcial2_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.example.parcial2_firebase.ListaAdaptadorDatos;
import com.example.parcial2_firebase.Modelos.DatosRegistros;

import java.util.ArrayList;

public class VerDatos extends AppCompatActivity {

    TextView CorreoBienvenida;
    ListView lvVerDatos;
    private FirebaseAuth objFirebase;
    private FirebaseAuth.AuthStateListener objFirebaseListener;

    FirebaseDatabase objBaseDatos;
    DatabaseReference dbReferencia;
    FirebaseUser objUsuario;
    private ProgressDialog Dialogo;

    private ArrayList<DatosRegistros> listaDatos=new ArrayList<>();
    ListaAdaptadorDatos objListaAdaptadorDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_datos);

        lvVerDatos=findViewById(R.id.lvDatosVD);
        CorreoBienvenida=findViewById(R.id.lblUsuarioIniciadoVD);
        Dialogo=new ProgressDialog(this);
        objFirebase=FirebaseAuth.getInstance();

        objFirebaseListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser objUsuario = firebaseAuth.getCurrentUser();
                if(objUsuario!=null){
                    CorreoBienvenida.setText(objUsuario.getEmail());
                }
            }
        };

        FirebaseApp.initializeApp(VerDatos.this);
        objBaseDatos=FirebaseDatabase.getInstance();
        dbReferencia=objBaseDatos.getReference();

    }
    @Override
    protected void onStart() {
        super.onStart();
        objFirebase.addAuthStateListener(objFirebaseListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (objFirebaseListener!=null){
            objFirebase.removeAuthStateListener(objFirebaseListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Dialogo.setMessage("Obteniendo Datos...");
        Dialogo.show();
        cargarDatos();
    }

    private void cargarDatos() {
        dbReferencia.child("tblDatos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDatos.clear();
                for (DataSnapshot objFilaDatos : snapshot.getChildren()){
                    DatosRegistros objDatos = objFilaDatos.getValue(DatosRegistros.class);
                    listaDatos.add(objDatos);
                }
                objListaAdaptadorDatos=new ListaAdaptadorDatos(VerDatos.this,listaDatos);
                lvVerDatos.setAdapter(objListaAdaptadorDatos);
                Dialogo.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
