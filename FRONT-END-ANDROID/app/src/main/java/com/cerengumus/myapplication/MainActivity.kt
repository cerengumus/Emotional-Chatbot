package com.cerengumus.myapplication

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.Normalizer


class MainActivity : AppCompatActivity() {
    private val adapterChatBot = AdapterChatbot()
    val img: ImageView? = null
    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.154.1:5000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(APIService::class.java)

        recylerChatlist.layoutManager = LinearLayoutManager(this)
        recylerChatlist.adapter = adapterChatBot
        imageView.setBackgroundResource(R.drawable.normal)
        button.setOnClickListener {
            var string =
                Normalizer.normalize(editTextTextPersonName.text.toString(), Normalizer.Form.NFD);
            println(string)
            println("asdad**" + editTextTextPersonName.text.toString())
            println("asdad" +  editTextTextPersonName.text.toString().replace("[^\\p{ASCII}]", ""))
            if(editTextTextPersonName.text.isNullOrEmpty()){
                imageView.setBackgroundResource(R.drawable.uzgunn)
                Toast.makeText(this@MainActivity, "Lutfen benimle konusur musunuz?", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (editTextTextPersonName.text.toString().contains("erhaba",false) or
                editTextTextPersonName.text.toString().contains("naber",false)  or
                editTextTextPersonName.text.toString().contains("elam",false)   or
                editTextTextPersonName.text.toString().contains("ünaydın",false)){
                imageView.setBackgroundResource(R.drawable.gulumse)
            }
            if (editTextTextPersonName.text.toString().contains("kötü",false) or
                editTextTextPersonName.text.toString().contains("üzgünüm",false)  or
                editTextTextPersonName.text.toString().contains("hissetmiyorum",false)){
                imageView.setBackgroundResource(R.drawable.uzgun)
            }
            if (editTextTextPersonName.text.toString().contains("cirkin",false)){
                imageView.setBackgroundResource(R.drawable.kizginn)
            }

            adapterChatBot.addChatToList(ChatModel(editTextTextPersonName.text.toString()))
            apiService.chatWithTheBit(editTextTextPersonName.text.toString()).enqueue(callBack).toString().replace("[^\\p{ASCII}]", "")
            editTextTextPersonName.text.clear()
        }
    }

    private val callBack = object  : Callback<ChatResponse>{
        override fun onResponse(call: Call<ChatResponse>, response: Response<ChatResponse>) {
            if(response.isSuccessful &&  response.body()!= null){
                adapterChatBot.addChatToList(ChatModel(response.body()!!.chatBotReply, true))
            }else{
                Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_LONG).show()
            }
        }

        override fun onFailure(call: Call<ChatResponse>, t: Throwable) {
            Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_LONG).show()
        }

    }
}


