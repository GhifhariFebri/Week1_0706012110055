package com.example.week1

import Adapter.ListdataRVadapter
import Interface.cardListener
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week1.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import database.Globalvar

class MainActivity : AppCompatActivity(), cardListener {
    private lateinit var viewBind:ActivityMainBinding
    private var tot: Int = 0
    private val adapter = ListdataRVadapter(Globalvar.listDatahewan, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        permission()
        setupRecyclerView()
        listener()
    }

    override fun onResume() {
        super.onResume()
        tot = Globalvar.listDatahewan.size
        if(tot == 0)
        {
            viewBind.textView2.alpha = 1f
        }else
        {
            viewBind.textView2.alpha = 0f
        }
        adapter.notifyDataSetChanged()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Globalvar.STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun permission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), Globalvar.STORAGE_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Globalvar.STORAGE_PERMISSION_CODE)
        } else {
            Toast.makeText(this, "Storage Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }


    private fun listener(){
        viewBind.button.setOnClickListener {
            val myIntent = Intent(this, AddActivity::class.java)
            startActivity(myIntent)
        }


    }

    private fun setupRecyclerView(){
        val layoutManager = LinearLayoutManager(this)
        viewBind.listdata.layoutManager = layoutManager   // Set layout
        viewBind.listdata.adapter = adapter   // Set adapter
    }

    override fun onCardClick(edit:Boolean, position: Int) {
        if(edit == true){
            val myintent = Intent(this, AddActivity::class.java).apply {
                putExtra("position", position)
            }
            startActivity(myintent)
        } else{
            //remove
            Globalvar.listDatahewan.removeAt(position)
            finish();
            startActivity(getIntent());
        }
    }


}
