package br.com.fedablio.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConnectionFactory extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "banco_spn";
    public static final int VERSAO_BANCO = 1;

    public ConnectionFactory(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE nota (_id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, conteudo TEXT, data DATE)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS anotacoes");
        onCreate(db);
    }
}