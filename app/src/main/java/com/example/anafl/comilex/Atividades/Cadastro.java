package com.example.anafl.comilex.Atividades;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.anafl.comilex.Objetos.Usuario;
import com.example.anafl.comilex.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastro extends AppCompatActivity {


    private EditText edtCadNome;
    private EditText edtCadEmail;
    private EditText edtCadCep;
    private EditText edtCadContato;
    private EditText edtCadDataNasc;
    private EditText edtCadSenha;
    private EditText edtConfSenha;
    private RadioGroup radioGroupCad;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String uid;

    private boolean isFeminino = false;
    private boolean isMasculino = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        edtCadNome = (EditText) findViewById(R.id.edtCadNome);
        edtCadEmail = (EditText) findViewById(R.id.edtCadEmail);
        edtCadCep = (EditText) findViewById(R.id.edtCadCep);
        edtCadContato = (EditText) findViewById(R.id.edtCadContato);
        edtCadDataNasc = (EditText) findViewById(R.id.edtCadDataNasc);
        edtCadSenha = (EditText) findViewById(R.id.edtCadSenha);
        edtConfSenha = (EditText) findViewById(R.id.edtConfSenha);




        Button botaoFinalizar = (Button) findViewById(R.id.btnGravar);
        botaoFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCadSenha.getText().toString().equals(edtConfSenha.getText().toString())){
                    createAccount(edtCadEmail.getText().toString(), edtCadSenha.getText().toString());
                }else{
                    Toast.makeText(Cadastro.this, "Senhas diferentes!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }



    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            writeNewUser(user.getUid());
                            Toast.makeText(Cadastro.this, "Cadastro feito sucesso!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Cadastro.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public  void checkTipoCadastro(View v){
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()){
            case R.id.rbFeminino:
                if(checked){
                    isFeminino = true;
                    isMasculino = false;
                }
                break;
            case R.id.rbMasculino:
                if(checked){
                    isFeminino = false;
                    isMasculino = true;
                }
        }
    }

    private void writeNewUser(String userId) {
        Usuario usuario = new Usuario();

        usuario.setNome(edtCadNome.getText().toString());
        usuario.setEmail(edtCadEmail.getText().toString());
        usuario.setCep(edtCadCep.getText().toString());
        usuario.setContato(edtCadContato.getText().toString());
        usuario.setDataNasc(edtCadDataNasc.getText().toString());
        usuario.setSenha(edtCadSenha.getText().toString());
        if(isFeminino){
            usuario.setSexo("Feminino");
        }else if(isMasculino){
            usuario.setSexo("Masculino");
        }
        usuario.setId(userId);

        mDatabase.child("users").child(userId).setValue(usuario);
    }
}
