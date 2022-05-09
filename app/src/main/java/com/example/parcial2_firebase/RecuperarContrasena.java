package com.example.parcial2_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarContrasena extends AppCompatActivity {

    Button btnReestablecer;
    EditText txtcorreo;

    AwesomeValidation awesomeValidation;
    private String email="";

    private FirebaseAuth firebaseAuth;

    private ProgressDialog Dialogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contrasena);

        firebaseAuth=FirebaseAuth.getInstance();
        Dialogo=new ProgressDialog(this);

        txtcorreo=findViewById(R.id.txtCorreorc);
        btnReestablecer=findViewById(R.id.btnReestablecerrc);

        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.txtCorreorc, Patterns.EMAIL_ADDRESS,R.string.correo_invalido);

        btnReestablecer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=txtcorreo.getText().toString();
                if (awesomeValidation.validate()){
                    if (!email.isEmpty()){
                        Dialogo.setMessage("Espere un momento...");
                        Dialogo.setCanceledOnTouchOutside(false);
                        Dialogo.show();
                        resetPassword();
                    }
                }
            }
        });
    }

    private void resetPassword() {
        firebaseAuth.setLanguageCode("es");
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RecuperarContrasena.this, "Correo enviado exitosamente", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RecuperarContrasena.this, "No se puedo enviar el correo de drestablecer contraseña", Toast.LENGTH_SHORT).show();
                }

                Dialogo.dismiss();
                finish();
            }
        });
    }
}