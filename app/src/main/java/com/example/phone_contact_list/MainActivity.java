package com.example.phone_contact_list;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView tvName;
    private ArrayList<String> listDatosNombre, listDatosNumber, listDatosFoto;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvName = findViewById(R.id.tvName);

        // Creamos lo necesario para el recycler view
        recycler = (RecyclerView) findViewById(R.id.rvContacts);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // También podemos hacer la lista Horizontal, o usar un grid layout para que lo enseñe a modo tabla
        // (new GridLayoutManager(this, 2)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1);
            Toast.makeText(this, "PERMISOS QUE FUERON PEDIDOS PORQUE NO HABÍA", Toast.LENGTH_SHORT).show();
            getContacts();
        } else {
            Toast.makeText(this, "PERMISOS YA CONCENDIDOS", Toast.LENGTH_SHORT).show();
            getContacts();
        }
    }

    private void getContacts() {
        listDatosNombre = new ArrayList<String>();
        listDatosNumber = new ArrayList<String>();
        listDatosFoto = new ArrayList<String>();

        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null
        );

        if (cursor != null) {
            int testName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            int testNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int foto_uri = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI);

            if (testName >= 0 && testNumber >= 0) {

                while (cursor.moveToNext()) {
                    String name = cursor.getString(testName);
                    String mobile = cursor.getString(testNumber);
                    String foto = cursor.getString(foto_uri);

                    listDatosNombre.add(name);
                    listDatosNumber.add(mobile);

                    if(foto!=null){
                        listDatosFoto.add(foto);
                        // Send data to the adapter
                        AdapterDatos datos = new AdapterDatos (listDatosNombre, listDatosNumber, listDatosFoto);
                        Toast.makeText(getApplicationContext(), "Rabo", Toast.LENGTH_SHORT).show();
                        recycler.setAdapter(datos);

                    } else {
                        //Load default picture
                        listDatosFoto.add("file:///android_res/drawable/vector_person.png");

                        AdapterDatos datos = new AdapterDatos (listDatosNombre, listDatosNumber, listDatosFoto);

                        datos.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int clickedPosition = recycler.getChildAdapterPosition(v);
                                // We use the child adapter to get the data where we clicked
                                openContactInfo(listDatosNombre.get(clickedPosition), listDatosNumber.get(clickedPosition));
                            }
                        });

                        recycler.setAdapter(datos);
                    }
                }
            } else {
                tvName.setText("No se encontraron las columnas necesarias en el cursor");
            }

            cursor.close();
        }
    }


    public void openContactInfo(String name, String number){
        Intent intent = new Intent(this, RecyclerViewSelectionContact.class);
        intent.putExtra("Name", name);
        intent.putExtra("Number", number);
        startActivity(intent);
    }
}