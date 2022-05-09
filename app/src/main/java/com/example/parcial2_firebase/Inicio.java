package com.example.parcial2_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inicio extends AppCompatActivity {

    TextView CorreoBienvenida;
    Button btnRegistar,btnver,btnCambiaContra,btnCerrarSession;

    private FirebaseAuth objFirebase;
    private FirebaseAuth.AuthStateListener objFirebaseListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        CorreoBienvenida=findViewById(R.id.lblUsuarioIniciadoVD);
        btnRegistar=findViewById(R.id.btnRegistrarDatosI);
        btnver=findViewById(R.id.btnVerDatosI);
        btnCambiaContra=findViewById(R.id.btnCambiarContrasenaI);
        btnCerrarSession=findViewById(R.id.btnCerrarSessionI);

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
        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pRegistrar = new Intent(Inicio.this,RegistrarDatos.class);
                startActivity(pRegistrar);
            }
        });
        btnver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pVer = new Intent(Inicio.this,VerDatos.class);
                startActivity(pVer);
            }
        });
        btnCambiaContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pCambiarContra = new Intent(Inicio.this,CambiarContrasena.class);
                startActivity(pCambiarContra);
            }
        });
        btnCerrarSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salir();
            }
        });
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

    private void salir() {
        objFirebase.signOut();
        Intent objLogin =new Intent(Inicio.this,MainActivity.class);
        startActivity(objLogin);
        this.finish();
    }
}