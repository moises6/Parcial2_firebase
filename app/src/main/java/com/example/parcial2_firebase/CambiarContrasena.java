package com.example.parcial2_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CambiarContrasena extends AppCompatActivity {

    EditText txtContraNueva,txtRepetirContra;
    Button btnCambiarContra;
    TextView CorreoBienvenida;

    private FirebaseAuth objFirebase;
    private FirebaseAuth.AuthStateListener objFirebaseListener;
    AwesomeValidation awesomeValidation;
    private ProgressDialog Dialogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);

        txtContraNueva=findViewById(R.id.txtContrasenaNuevaCC);
        txtRepetirContra=findViewById(R.id.txtRepetirContrasenaCC);
        CorreoBienvenida=findViewById(R.id.lblUsuarioIniciadoVD);
        btnCambiarContra=findViewById(R.id.btnCambiarContrasenaCC);

        Dialogo=new ProgressDialog(this);
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.txtContrasenaNuevaCC,".{6,}",R.string.contraseña_invalida);
        awesomeValidation.addValidation(this,R.id.txtRepetirContrasenaCC,".{6,}",R.string.contraseña_invalida);
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

        btnCambiarContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()){
                    CambiarContra();
                }
            }
        });
    }

    private void CambiarContra() {
        if (txtContraNueva.getText().toString().equals(txtRepetirContra.getText().toString())){
            FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
            String newPassword=txtContraNueva.getText().toString();
            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(CambiarContrasena.this, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            this.finish();
        }else {
            Toast.makeText(CambiarContrasena.this, "Ambas contraseñas deben coinsidir", Toast.LENGTH_SHORT).show();
        }
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