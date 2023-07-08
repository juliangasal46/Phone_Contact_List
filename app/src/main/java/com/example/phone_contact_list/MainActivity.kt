package com.example.phone_contact_list

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    lateinit var tvLoadContacts: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvLoadContacts = findViewById(R.id.tvCargarContactos)

        // ArrayList where the contacts are gonna be
        // Check build of the system, cause with the permission stuff some builds have problems
        // aka marshmallow or greater
        // We also check for the permission if it is or not granted
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && checkSelfPermission(Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED){
            // We are gonna ask for permission cause or not granted or didnt accept
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),1)
            Toast.makeText(this, "PERMISOS QUE FUERON PEDIDOS PORQUE NO HABÍA", Toast.LENGTH_SHORT).show()
            getContacts()
        } else{
            Toast.makeText(this, "PERMISOS YA CONCENDIDOS", Toast.LENGTH_SHORT).show()
            // For lower marshmallow builds, we are asking the permission request
            getContacts() // Coge los permisos solo ? lol
        }
    }

    fun getContacts(): ArrayList<String> {

        var contactArrayList = ArrayList<String>()

        // To pass al the contacts to the cursor
        var cursor: Cursor? = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null, null,null, null)

        // Now to fetch all the contacts from cursor into the text view
        // getColumIndex = -1 si no encuentra algo

                val testName = cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val testNumber = cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                if(testName >= 0 && testNumber >=0)
                {
                    while (cursor!!.moveToNext()) {

                        val name: String = cursor.getString(testName)
                        val mobile: String = cursor.getString(testNumber)

                        val contactInfo = "$name: $mobile"
                        contactArrayList.add(contactInfo)
                    }

                    for(i in contactArrayList){
                        tvLoadContacts.text = "${tvLoadContacts.text}$i\n"
                    }

                } else {
                    tvLoadContacts.setText("No se encontraron las columnas necesarias en el cursor")
                }
            return contactArrayList
        }
    }


    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
        { // Accepted
            getContacts()
        }
    }*/
