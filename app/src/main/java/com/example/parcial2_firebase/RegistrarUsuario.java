package com.example.parcial2_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class RegistrarUsuario extends AppCompatActivity {

    Button btnRegistrar;
    EditText txtEmail,txtContraseña,txtContraseñaRepetir;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener objFirebaseListener;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        firebaseAuth=FirebaseAuth.getInstance();
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.txtCorreoru, Patterns.EMAIL_ADDRESS,R.string.correo_invalido);
        awesomeValidation.addValidation(this,R.id.txtContrasenaru,".{6,}",R.string.contraseña_invalida);
        awesomeValidation.addValidation(this,R.id.txtReContrasenaru,".{6,}",R.string.contraseña_invalida);

        txtEmail=findViewById(R.id.txtCorreoru);
        txtContraseña=findViewById(R.id.txtContrasenaru);
        txtContraseñaRepetir=findViewById(R.id.txtReContrasenaru);
        btnRegistrar=findViewById(R.id.btnRegistrarru);

        objFirebaseListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser objUsuario =firebaseAuth.getCurrentUser();
                if (objUsuario!=null){
                    Toast.makeText(RegistrarUsuario.this, "El usuario fue creado", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistrarUsuario.this, "El usuario salio de la sesion", Toast.LENGTH_SHORT).show();
                }
            }
        };

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=txtEmail.getText().toString();
                String pass =txtContraseña.getText().toString();
                if (awesomeValidation.validate()){
                    if (txtContraseña.getText().toString().equals(txtContraseñaRepetir.getText().toString())){
                        firebaseAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegistrarUsuario.this, "Usuario creado con exito. Verificalo!", Toast.LENGTH_SHORT).show();
                                    FirebaseUser objUsuario =firebaseAuth.getCurrentUser();
                                    objUsuario.sendEmailVerification();
                                    finish();
                                }else{
                                    String errorCode =((FirebaseAuthException)task.getException()).getErrorCode();
                                    dameToastdeerror(errorCode);
                                }
                            }
                        });
                    }else {
                        Toast.makeText(RegistrarUsuario.this, "Las contraseñas deben ser iguales", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void dameToastdeerror(String error) {
        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(RegistrarUsuario.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(RegistrarUsuario.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(RegistrarUsuario.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(RegistrarUsuario.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                txtEmail.setError("La dirección de correo electrónico está mal formateada.");
                txtEmail.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(RegistrarUsuario.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                txtContraseña.setError("la contraseña es incorrecta ");
                txtContraseña.requestFocus();
                txtContraseña.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(RegistrarUsuario.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(RegistrarUsuario.this, "Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(RegistrarUsuario.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(RegistrarUsuario.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                txtEmail.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                txtEmail.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(RegistrarUsuario.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(RegistrarUsuario.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(RegistrarUsuario.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(RegistrarUsuario.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(RegistrarUsuario.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(RegistrarUsuario.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(RegistrarUsuario.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                txtContraseña.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                txtContraseña.requestFocus();
                break;

        }
    }
}