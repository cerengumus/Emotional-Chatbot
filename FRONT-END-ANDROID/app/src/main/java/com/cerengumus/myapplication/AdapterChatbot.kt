package com.cerengumus.myapplication
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.chat_screen.view.*


class AdapterChatbot : RecyclerView.Adapter<AdapterChatbot.MyviewHolder>() {
    private val list = ArrayList<ChatModel>()
    inner class MyviewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_screen,parent,false)) {

        fun bind(chat: ChatModel) = with(itemView) {
            if(!chat.isBot) {
                chattxt.setBackgroundColor(Color.rgb(242, 242, 242))
                chattxt.setTextColor(Color.BLACK)
                chattxt.text = chat.chat.replace("[^\\p{ASCII}]", "")
            }else{
                chattxt.setBackgroundColor(Color.rgb(240, 179, 255))
                chattxt.setTextColor(Color.BLACK)
                chattxt.gravity = Gravity.RIGHT
                chattxt.text = chat.chat.replace("[^\\p{ASCII}]", "")

            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  MyviewHolder(parent)
    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        holder.bind(list[position])
    }




    override fun getItemCount(): Int =  list.size


    fun addChatToList(chat: ChatModel ){
        list.add(chat)
        notifyDataSetChanged()

    }
}



