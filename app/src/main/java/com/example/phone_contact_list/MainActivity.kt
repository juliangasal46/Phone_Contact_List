package com.example.phone_contact_list

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.TextView

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
            getContacts()
        } else{
            // For lower marshmallow builds, we are asking the permission request
            getContacts() // Coge los permisos solo ? lol
        }
    }

    private fun getContacts() {
        var contactArrayList = ArrayList<String>()

        // To pass al the contacts to the cursor
        var cursor: Cursor? = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null, null,null, null)

        // Now to fetch all the contacts from cursor into the text view
        while (cursor!!.moveToNext()) {
            val name: String = cursor.getString(cursor.getColumnIndex(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)+1)
            val mobile: String = cursor.getString(cursor.getColumnIndex(
                ContactsContract.CommonDataKinds.Phone.NUMBER)+1)

            val contactInfo = "$name: $mobile"
            contactArrayList.add(contactInfo)
            tvLoadContacts.setText("Funsiono")
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
}