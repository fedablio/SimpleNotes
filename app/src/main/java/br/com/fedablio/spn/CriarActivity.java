package br.com.fedablio.spn;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import br.com.fedablio.dao.NotaDAO;
import br.com.fedablio.model.Nota;

public class CriarActivity extends Activity {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.cor_barra))));
    }

    public void voltar(View view){
        Intent intent = new Intent(CriarActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void inserir(View view){
        NotaDAO notaDAO = new NotaDAO(getBaseContext());
        Nota nota = new Nota();
        EditText titulo = (EditText)findViewById(R.id.campoTituloCriar);
        EditText conteudo = (EditText)findViewById(R.id.campoConteudoCriar);
        nota.setTitulo(titulo.getText().toString());
        nota.setConteudo(conteudo.getText().toString());
        nota.setData(simpleDateFormat.format(new Date()));
        boolean resultado = notaDAO.inserir(nota);
        if(resultado){
            Toast.makeText(getApplicationContext(), "Successfully created!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "Couldn't create!", Toast.LENGTH_LONG).show();
        }
        voltar(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_criar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.imMain){
            Intent intent = new Intent(this, br.com.fedablio.spn.MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}