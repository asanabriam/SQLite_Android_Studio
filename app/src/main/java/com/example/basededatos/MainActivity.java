package com.example.basededatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etCodigo, etDescripcion, etPrecio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCodigo = (EditText)findViewById(R.id.txtCodigo);
        etDescripcion = (EditText)findViewById(R.id.txtDescrpcion);
        etPrecio = (EditText)findViewById(R.id.txtPrecio);
    }
    public void Registrar(View view){
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this,"administracion",null,1 );
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = etCodigo.getText().toString();
        String precio = etPrecio.getText().toString();
        String descripcion = etDescripcion.getText().toString();

        if(!codigo.isEmpty()&& !precio.isEmpty() && !descripcion.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            BaseDeDatos.insert("articulos",null,registro);

            BaseDeDatos.close();
            etPrecio.setText("");
            etDescripcion.setText("");
            etCodigo.setText("");

            Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show();

        }
        else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
    public void Buscar (View view) {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = etCodigo.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila = BaseDeDatos.rawQuery("Select descripcion, precio from articulos where codigo ="+codigo,null);

            if (fila.moveToFirst()){
                etDescripcion.setText(fila.getString(0));
                etPrecio.setText(fila.getString(1));


            }
            else{

                Toast.makeText(this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
            }

        }
        else{
            Toast.makeText(this, "Debes digitar un codigo", Toast.LENGTH_SHORT).show();
        }

        BaseDeDatos.close();
    }

    public void Eliminar(View view) {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = etCodigo.getText().toString();

        if (!codigo.isEmpty()) {

            int cantidad = BaseDeDatos.delete("articulos", "codigo="+codigo, null);
            BaseDeDatos.close();
            etPrecio.setText("");
            etDescripcion.setText("");
            etCodigo.setText("");

            if (cantidad==1){
                Toast.makeText(this, "Se elimino el articulo exitosamente", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes digitar un codigo", Toast.LENGTH_SHORT).show();
        }

    }
    public void Modificar(View view) {
        AdminSqliteOpenHelper admin = new AdminSqliteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = etCodigo.getText().toString();
        String precio = etPrecio.getText().toString();
        String descripcion = etDescripcion.getText().toString();

    if (!codigo.isEmpty()&& !precio.isEmpty() && !descripcion.isEmpty()) {
        ContentValues registro = new ContentValues();
        registro.put("codigo", codigo);
        registro.put("descripcion", descripcion);
        registro.put("precio", precio);

        int cantidad = BaseDeDatos.update("articulos",registro, "codigo="+codigo, null);
        BaseDeDatos.close();
        if (cantidad==1){
            Toast.makeText(this, "Se actualizo el articulo exitosamente", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "El articulo no existe", Toast.LENGTH_SHORT).show();
        }

    }
        else {
        Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
    }

    }

    public void Limpiar(View view){
        etPrecio.setText("");
        etDescripcion.setText("");
        etCodigo.setText("");
    }
}