package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.usach.sebastianvallejos.scap_apoderados.R;

public class LoginActivity extends AppCompatActivity {

    //Definicion de variables
    private String correo;
    private String contrasena;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        createNotificationChannel();
        mAuth = FirebaseAuth.getInstance();
        inicio();
        subscribreToTopic();

    }

    //Suscripcion necesaria para que lleguen las notificaciones push
    private void subscribreToTopic()
    {
        FirebaseMessaging.getInstance().subscribeToTopic("ses");
        FirebaseMessaging.getInstance().subscribeToTopic("tareas");
        FirebaseMessaging.getInstance().subscribeToTopic("materiales");
        FirebaseMessaging.getInstance().subscribeToTopic("pruebas");
    }

    //Verificamos si el usuario se encuentra con la sesion iniciada o no
    private void inicio()
    {
        FirebaseUser usuario = mAuth.getCurrentUser();

        //En el caso de haber iniciado sesion, se continua inmediatamente a la siguiente activity
        if(usuario != null)
        {
            correo = usuario.getEmail();

            Intent intent = new Intent(LoginActivity.this, HijosActivity.class);

            intent.putExtra("correo",correo);

            startActivity(intent);
        }
        else{//En caso contrario se le pide que inicie sesion
            crearBoton();
        }

    }

    //Funcion encargada de recuperar el correo del usuario
    private String recuperarCorreo()
    {
        EditText campoCorreo = (EditText) findViewById(R.id.correo_usuario);
        return campoCorreo.getText().toString().toLowerCase().replaceAll("\\s+","");

    }

    //Funcion encargada de recuperar la contrasena del usuario
    private String recuperarContrasena()
    {
        EditText campoContrasena = (EditText) findViewById(R.id.password_usuario);
        return campoContrasena.getText().toString();
    }

    //Dentro de esta funcion se crea el boton y se le asigna una accion
    void crearBoton()
    {
        //Llamada al boton
        Button botonLogin = (Button) findViewById(R.id.boton_login);
        //Listener para el boton, le otorga una accion al mismo
        botonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correo = recuperarCorreo();
                contrasena = recuperarContrasena();
                Log.i("HI2", "HI");
                if (correo != null && contrasena != null) {
                    Log.i("HI", "HI");
                    mAuth.signInWithEmailAndPassword(correo, contrasena)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(LoginActivity.this, HijosActivity.class);
                                        intent.putExtra("correo", correo);
                                        startActivity(intent);
                                    } else {
                                        Log.w("ER", "signInWithEmail:failure", task.getException());
                                        Toast.makeText(LoginActivity.this, "Usuario o contraseña inválidos.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }else{
                    Toast.makeText(LoginActivity.this, "Favor ingresar nombre de usuario o contraseña",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
