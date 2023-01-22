package br.com.fedablio.spn;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import br.com.fedablio.dao.NotaDAO;
import br.com.fedablio.model.Nota;

public class EditarActivity extends Activity {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.cor_barra))));
        NotaDAO notaDAO = new NotaDAO(getBaseContext());
        final Cursor cursor = notaDAO.listarTodosId(this.getIntent().getIntExtra("_ID_", 0));
        EditText titulo = (EditText) findViewById(R.id.campoTituloEditar);
        EditText conteudo = (EditText) findViewById(R.id.campoConteudoEditar);
        titulo.setText(cursor.getString(cursor.getColumnIndexOrThrow("titulo")));
        conteudo.setText(cursor.getString(cursor.getColumnIndexOrThrow("conteudo")));
    }

    public void alterar(View view) {
        NotaDAO notaDAO = new NotaDAO(getBaseContext());
        Nota nota = new Nota();
        EditText titulo = (EditText) findViewById(R.id.campoTituloEditar);
        EditText conteudo = (EditText) findViewById(R.id.campoConteudoEditar);
        try {
            nota.setId(Integer.parseInt(String.valueOf(this.getIntent().getIntExtra("_ID_", 0))));
            nota.setTitulo(titulo.getText().toString());
            nota.setConteudo(conteudo.getText().toString());
            nota.setData(simpleDateFormat.format(new Date()));
            notaDAO.alterar(nota);
            Toast.makeText(getApplicationContext(), "Successfully changed!", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Could not change!", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(EditarActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void excluir(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Simple Notes");
        builder.setMessage(Html.fromHtml("<font color='#000000'>Are you sure?</font>"));
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                NotaDAO notaDAO = new NotaDAO(getBaseContext());
                EditText titulo = (EditText) findViewById(R.id.campoTituloEditar);
                EditText conteudo = (EditText) findViewById(R.id.campoConteudoEditar);
                try {
                    notaDAO.excluir(EditarActivity.this.getIntent().getIntExtra("_ID_", 0));
                    Toast.makeText(getApplicationContext(), "Successfully deleted!", Toast.LENGTH_LONG).show();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Couldn't delete!", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(EditarActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        try {
            Resources resources = dialog.getContext().getResources();
            int alertTitleId = resources.getIdentifier("alertTitle", "id", "android");
            TextView alertTitle = (TextView) dialog.getWindow().getDecorView().findViewById(alertTitleId);
            alertTitle.setTextColor(Color.parseColor("#FFC125"));
            int titleDividerId = resources.getIdentifier("titleDivider", "id", "android");
            View titleDivider = dialog.getWindow().getDecorView().findViewById(titleDividerId);
            titleDivider.setBackgroundColor(Color.BLACK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Button nbutton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setBackgroundColor(Color.WHITE);
        nbutton.setTextColor(Color.BLACK);
        Button pbutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setBackgroundColor(Color.BLACK);
        pbutton.setTextColor(Color.WHITE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sobre, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.imMain) {
            Intent intent = new Intent(this, br.com.fedablio.spn.MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}