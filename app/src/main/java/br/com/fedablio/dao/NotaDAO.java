package br.com.fedablio.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.fedablio.model.Nota;

public class NotaDAO {

    public SQLiteDatabase sqLiteDatabase;
    public ConnectionFactory connectionFactory;

    public NotaDAO(Context context){
        connectionFactory = new ConnectionFactory(context);
    }

    public boolean inserir(Nota nota){
        sqLiteDatabase = connectionFactory.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", nota.getTitulo());
        values.put("conteudo", nota.getConteudo());
        values.put("data", nota.getData());
        long resultado = sqLiteDatabase.insert("nota", null, values);
        sqLiteDatabase.close();
        return resultado > 0;
    }

    public void alterar(Nota nota){
        sqLiteDatabase = connectionFactory.getReadableDatabase();
        String where = "_id = "+nota.getId();
        ContentValues values = new ContentValues();
        values.put("titulo", nota.getTitulo());
        values.put("conteudo", nota.getConteudo());
        values.put("data", nota.getData());
        sqLiteDatabase.update("nota", values, where, null);
        sqLiteDatabase.close();
    }

    public void excluir(int id){
        sqLiteDatabase = connectionFactory.getReadableDatabase();
        String where = "_id = " + id;
        sqLiteDatabase.delete("nota", where, null);
        sqLiteDatabase.close();
    }

    public Cursor listarTodos() {
        String[] campos = {"_id", "titulo", "data"};
        sqLiteDatabase = connectionFactory.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("nota", campos, null, null, null, null, "_id");
        if (cursor != null) {
            cursor.moveToFirst();
        }
        sqLiteDatabase.close();
        return cursor;
    }

    public Cursor listarTodosId(int id) {
        Cursor cursor;
        String[] campos = {"_id", "titulo", "conteudo", "data"};
        String where = "_id = " + id;
        sqLiteDatabase = connectionFactory.getReadableDatabase();
        cursor = sqLiteDatabase.query("nota", campos, where, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        sqLiteDatabase.close();
        return cursor;
    }

}