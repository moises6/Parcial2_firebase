package com.example.parcial2_firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.parcial2_firebase.Modelos.DatosRegistros;
import com.example.parcial2_firebase.Modelos.Estatus;

import java.util.ArrayList;
import java.util.UUID;

public class RegistrarDatos extends AppCompatActivity {

    EditText txtTitulo,txtDescripcion,txtFecha,txtHora;
    TextView CorreoBienvenida;
    Button btnGuardarDatos;
    Spinner sEstatus;
    String EstatusObtenido,UsuarioActual;
    private FirebaseAuth objFirebase;
    private FirebaseAuth.AuthStateListener objFirebaseListener;

    FirebaseDatabase objBaseDatos;
    DatabaseReference dbReference;

    FirebaseUser objUsuario;
    private ProgressDialog objDialogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_datos);

        CorreoBienvenida=findViewById(R.id.lblUsuarioIniciadoVD);
        txtTitulo=findViewById(R.id.txtTituloRD);
        txtDescripcion=findViewById(R.id.txtDescripcionRD);
        txtFecha=findViewById(R.id.txtFechaRD);
        txtHora=findViewById(R.id.txtHoraRD);
        sEstatus=findViewById(R.id.spinnerRD);
        btnGuardarDatos=findViewById(R.id.btnGuardarRD);
        objDialogo=new ProgressDialog(this);

        ArrayList<Estatus> estatus=new ArrayList<Estatus>();
        estatus.add(new Estatus("Pendiente"));
        estatus.add(new Estatus("En Proceso"));
        estatus.add(new Estatus("Terminada"));
        ArrayAdapter<Estatus> adapter=new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,estatus);

        sEstatus.setAdapter(adapter);

        sEstatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        objFirebase=FirebaseAuth.getInstance();

        objFirebaseListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser objUsuario = firebaseAuth.getCurrentUser();
                if(objUsuario!=null){
                    CorreoBienvenida.setText(objUsuario.getEmail());
                }
                if (objUsuario==null){
                    regresarLogin();
                }
            }
        };

        btnGuardarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Estatus estatus1=(Estatus) sEstatus.getSelectedItem();
                EstatusObtenido=estatus1.getNombreEstatus();
                Toast.makeText(RegistrarDatos.this, ""+EstatusObtenido, Toast.LENGTH_SHORT).show();
                registrarDatos();
            }
        });

        FirebaseApp.initializeApp(RegistrarDatos.this);
        objBaseDatos=FirebaseDatabase.getInstance();
        dbReference=objBaseDatos.getReference();
    }

    private void registrarDatos() {
        objDialogo.setMessage("Registrando datos...");
        objDialogo.show();
        DatosRegistros objDatos=new DatosRegistros();
        objDatos.setId_Dato(UUID.randomUUID().toString());
        objDatos.setTitulo(txtTitulo.getText().toString());
        objDatos.setDescripcion(txtDescripcion.getText().toString());
        objDatos.setFecha(txtFecha.getText().toString());
        objDatos.setHora(txtHora.getText().toString());
        objDatos.setEstatus(EstatusObtenido);
        objDatos.setUsuario(CorreoBienvenida.getText().toString());
        dbReference.child("tblDatos").child(objDatos.getId_Dato()).setValue(objDatos);
        objDialogo.dismiss();
        this.finish();
    }

    private void regresarLogin() {
        Intent objVentana=new Intent(RegistrarDatos.this,Inicio.class);
        startActivity(objVentana);
        this.finish();
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
}
