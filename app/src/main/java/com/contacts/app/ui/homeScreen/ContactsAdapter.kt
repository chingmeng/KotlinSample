package com.contacts.app.ui.homeScreen

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.contacts.app.R
import com.contacts.app.model.Contact
import com.contacts.app.util.inflate
import com.contacts.app.util.loadUrl
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.sdk25.coroutines.onClick

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    private var items = ArrayList<Contact>()
    private lateinit var listener: OnItemSelectedListener

    /*
    What if we want to send the data and listener within the constructor? how to create a constructor here?
     */
    fun setData(items: ArrayList<Contact>?) {
        if (items != null) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    fun setClickListener(listener: OnItemSelectedListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {

        /*The line that inflates the view and uses parent is too complex,and 99% of the time is usually the same on any adapters.
          Why not give ViewGroups the ability to inflate views?
         */
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        // Using Kotlin Extension
        val view = parent.inflate(R.layout.item_contact)

        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {

        holder.name.text = items[position].name
        holder.phone.text = items[position].phone

        /*
        We can replace this with an extension
        */
//        Glide.with(holder.container).load(items[position].image).into(holder.profilePic)
        holder.profilePic.loadUrl(items[position].image)


        holder.container.onClick {
            listener.onItemSelected(items[position])
        }
    }

    class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val container = itemView.findViewById<CardView>(R.id.container)
        val name = itemView.findViewById<TextView>(R.id.tv_name)
        val phone = itemView.findViewById<TextView>(R.id.tv_phone)
        val profilePic = itemView.findViewById<CircleImageView>(R.id.iv_profile)

    }

    interface OnItemSelectedListener {

        fun onItemSelected(selectedContact: Contact)
    }

}