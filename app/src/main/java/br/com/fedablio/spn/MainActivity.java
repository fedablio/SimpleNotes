package br.com.fedablio.spn;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import br.com.fedablio.dao.NotaDAO;

public class MainActivity extends Activity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.cor_barra))));
        NotaDAO notaDAO = new NotaDAO(getBaseContext());
        final Cursor cursor = notaDAO.listarTodos();
        String[] nomeCampos = new String[]{"_id","titulo", "data"};
        int[] codigo = new int[]{R.id.labelId, R.id.labelTitulo, R.id.labelData};
        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(), R.layout.modelo_lista, cursor, nomeCampos, codigo, 0);
        ListView lista = (ListView) findViewById(R.id.listaDeNotas);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                Intent intent = new Intent(MainActivity.this, EditarActivity.class);
                intent.putExtra("_ID_", cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
                startActivity(intent);
                finish();
            }
        });
    }

    public void adicionar(View view){
        Intent intent = new Intent(MainActivity.this, CriarActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.imSobre){
            Intent intent = new Intent(this, br.com.fedablio.spn.SobreActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}