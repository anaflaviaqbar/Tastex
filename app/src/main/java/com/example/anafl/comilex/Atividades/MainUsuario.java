package com.example.anafl.comilex.Atividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anafl.comilex.R;
import com.google.firebase.auth.FirebaseAuth;

public class MainUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_usuario);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }
}
