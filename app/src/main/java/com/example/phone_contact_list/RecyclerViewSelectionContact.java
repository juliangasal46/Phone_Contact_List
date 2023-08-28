package com.example.phone_contact_list;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerViewSelectionContact extends AppCompatActivity {

    private final ActivityResultLauncher<String> launcherPeticionPermisos =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                String mensaje = isGranted ? "Permisos aceptados" : "Permisos denegados";
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_selection_contact);

        // ImageView tvContactPicture = (ImageView)findViewById(R.id.ivProfilePicture);
        TextView tvContactName = (TextView)findViewById(R.id.tvContactNameInCard);
        TextView tvContactNumber = (TextView)findViewById(R.id.tvWritePhoneNumberInCard);

        // Now we are going to get all the data sent by the intent
        Intent intent = getIntent();

        String name = intent.getStringExtra("Name");
        String number = intent.getStringExtra("Number");

        if(intent != null)
        {
            if(name != null && number != null)
            {
                tvContactName.setText(name);
                tvContactNumber.setText(number);

            } else{
                Toast.makeText(this, "You have missed data, we cannot open the window for you", Toast.LENGTH_SHORT).show();
            }
        } else{
            Toast.makeText(this, "Unable to open the contact", Toast.LENGTH_SHORT).show();
        }

        Button callPhoneNumber = (Button)findViewById(R.id.callPhoneNumber);
        Button goBack = (Button)findViewById(R.id.goBack);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        callPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // First of all, check permission then call or send permission query
                if (ContextCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                    startActivity(intent);

                } else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getApplicationContext(), "Necesitamos los permisos para que puedas llamar, por favor aceptalos", Toast.LENGTH_SHORT).show();
                    launcherPeticionPermisos.launch(Manifest.permission.CALL_PHONE);
                }
            }
        });
    }
}
