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

import com.example.anafl.comilex.Objetos.Comprador;
import com.example.anafl.comilex.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Cadastro extends AppCompatActivity {


    private EditText nomeCad;
    private EditText emailCad;
    private EditText cepCad;
    private EditText contatoCad;
    private EditText senhaCad;
    private EditText confSenhaCad;
    private RadioGroup radioGroupCad;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String uid;

    private boolean isComprador = false;
    private boolean isVendedor = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nomeCad = (EditText) findViewById(R.id.nome);
        emailCad = (EditText) findViewById(R.id.email);
        cepCad = (EditText) findViewById(R.id.cep);
        contatoCad = (EditText) findViewById(R.id.contato);
        senhaCad = (EditText) findViewById(R.id.senha);
        confSenhaCad = (EditText) findViewById(R.id.conf_senha);
        radioGroupCad = (RadioGroup) findViewById(R.id.radio_grupo);



        Button botaoFinalizar = (Button) findViewById(R.id.botao_finalizar);
        botaoFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(senhaCad.getText().toString().equals(confSenhaCad.getText().toString())){
                    createAccount(emailCad.getText().toString(), senhaCad.getText().toString());
                }else{
                    Toast.makeText(Cadastro.this, "Senhas diferentes!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }



    public void createAccount(final String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(isComprador){
                                writeNewComprador(
                                        user.getUid(),
                                        nomeCad.getText().toString(),
                                        emailCad.getText().toString(),
                                        cepCad.getText().toString(),
                                        contatoCad.getText().toString(),
                                        senhaCad.getText().toString()
                                );
                            }else if(isVendedor){
                                writeNewVendedor(
                                        user.getUid(),
                                        nomeCad.getText().toString(),
                                        emailCad.getText().toString(),
                                        cepCad.getText().toString(),
                                        contatoCad.getText().toString(),
                                        senhaCad.getText().toString()
                                );
                            }

                            //updateUI(user);
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
            case R.id.radio_botao_comp:
                if(checked){
                    //Toast.makeText(Cadastro.this,"Comprador selecionado", Toast.LENGTH_SHORT).show();
                    isComprador = true;
                    isVendedor = false;
                }
                break;
            case R.id.radio_botao_vend:
                if(checked){
                    //Toast.makeText(Cadastro.this,"Vendedor selecionado", Toast.LENGTH_SHORT).show();
                    isComprador = false;
                    isVendedor = true;
                }
        }
    }

    private void writeNewComprador(String compradorId, String nome, String email, String cep, String contato, String senha) {
        Comprador comprador = new Comprador(nome,email, cep, contato, senha);

        mDatabase.child("Compradores").child(compradorId).setValue(comprador);
    }

    private void writeNewVendedor(String compradorId, String nome, String email, String cep, String contato, String senha) {
        Comprador comprador = new Comprador(nome,email, cep, contato, senha);

        mDatabase.child("Vendedores").child(compradorId).setValue(comprador);
    }
}
