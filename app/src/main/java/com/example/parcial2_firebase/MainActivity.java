package com.example.parcial2_firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button btnIniciar,btnRecuperar,btnRegistrar;
    EditText txtCorreo,txtContra;

    private FirebaseAuth objFirebase;
    private FirebaseAuth.AuthStateListener objFirebaseListener;
    private ProgressDialog objDialogo;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtContra=findViewById(R.id.txtContrasena);
        txtCorreo=findViewById(R.id.txtCorreoElectronico);
        btnIniciar=findViewById(R.id.btnIniciarSession);
        btnRecuperar=findViewById(R.id.btnRecuperarContrasena);
        btnRegistrar=findViewById(R.id.btnRegistrarse);

        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.txtCorreoElectronico, Patterns.EMAIL_ADDRESS,R.string.correo_invalido);
        awesomeValidation.addValidation(this,R.id.txtContrasena,".{6,}",R.string.contraseña_invalida);
        objDialogo=new ProgressDialog(this);

        objFirebase=FirebaseAuth.getInstance();
        objFirebaseListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser objUsuario =firebaseAuth.getCurrentUser();
                if(objUsuario!=null){

                }
            }
        };

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()){
                    iniciarSesion();
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IrRegistrarUsuario();
            }
        });

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IrRecuperarContrasena();
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

    private void iniciarSesion() {
        objDialogo.setMessage("Iniciando sesion...");
        objDialogo.show();
        objFirebase.signInWithEmailAndPassword(txtCorreo.getText().toString(),
                txtContra.getText().toString()).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    objDialogo.dismiss();
                    FirebaseUser objUsuario =objFirebase.getCurrentUser();
                    if (objUsuario.isEmailVerified()){
                        IrInicio();
                    }else {
                        Toast.makeText(MainActivity.this, "Debe verificar el correo electronico", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    objDialogo.dismiss();
                    Toast.makeText(MainActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void IrInicio() {
        Intent pInicio = new Intent(MainActivity.this,Inicio.class);
        startActivity(pInicio);
    }

    private void IrRecuperarContrasena() {
        Intent pRecuperar = new Intent(MainActivity.this,RecuperarContrasena.class);
        startActivity(pRecuperar);
    }

    private void IrRegistrarUsuario() {
        Intent pRgistrar = new Intent(MainActivity.this,RegistrarUsuario.class);
        startActivity(pRgistrar);
    }
}