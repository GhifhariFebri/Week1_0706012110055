package com.example.week1

import Model.Hewan
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.week1.databinding.ActivityAddBinding
import database.Globalvar

class AddActivity : AppCompatActivity() {
    private lateinit var viewBind: ActivityAddBinding
    private lateinit var hewan: Hewan
    var pcc = -1
    var img: String = ""

    private val GetResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK){
            val uri = it.data?.data
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(uri != null){
                    baseContext.getContentResolver().takePersistableUriPermission(uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }}
            viewBind.imageView2.setImageURI(uri)
            img = uri.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityAddBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        supportActionBar?.hide()
        getintent()
        listener()
    }
    private fun getintent(){
        pcc = intent.getIntExtra("position", -1)
        if(pcc != -1){
            val hewan = Globalvar.listDatahewan[pcc]
            viewBind.toolbar2.title = "Ganti data hewan"
            viewBind.Addmovie.text = "Edit"
            viewBind.imageView2.setImageURI(Uri.parse(Globalvar.listDatahewan[pcc].imageUri))
            viewBind.Rating.editText?.setText(hewan.umur.toString())
            viewBind.Title.editText?.setText(hewan.nama)
            viewBind.Genre.editText?.setText(hewan.jenis)
        }
    }

    private fun listener(){
        viewBind.imageView2.setOnClickListener{
            val myIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            myIntent.type = "image/*"
            GetResult.launch(myIntent)
        }

        viewBind.Addmovie.setOnClickListener{
            var nama = viewBind.Title.editText?.text.toString().trim()
            var umur = 0
            var jenis = viewBind.Genre.editText?.text.toString().trim()
            hewan = Hewan(nama, jenis, umur)
            checker()
        }

        viewBind.toolbar2.getChildAt(1).setOnClickListener {
            finish()
        }
    }

    private fun checker()
    {
        var isCompleted:Boolean = true

        if(hewan.nama!!.isEmpty()){
            viewBind.Title.error = "Nama tidak bisa kosong"
            isCompleted = false
        }else{
            viewBind.Title.error = ""
        }

        if(hewan.jenis!!.isEmpty()){
            viewBind.Genre.error = "Jenis tidak bisa kosong"
            isCompleted = false
        }else{
            viewBind.Genre.error = ""
        }
        hewan.imageUri = img.toString()


        if(viewBind.Rating.editText?.text.toString().isEmpty() || viewBind.Rating.editText?.text.toString().toInt() < 0)
        {
            viewBind.Rating.error = "Umur tidak bisa kosong atau 0"
            isCompleted = false
        }
        if(isCompleted == true)
        {
            if(pcc == -1)
            {
                hewan.umur = viewBind.Rating.editText?.text.toString().toInt()
                Globalvar.listDatahewan.add(hewan)

            }else
            {
                hewan.umur = viewBind.Rating.editText?.text.toString().toInt()
                Globalvar.listDatahewan[pcc] = hewan
            }
            finish()
        }
    }
}