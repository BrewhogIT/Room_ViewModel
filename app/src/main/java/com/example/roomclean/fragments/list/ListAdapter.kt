package com.example.roomclean.fragments.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomclean.model.User
import com.example.roomclean.databinding.UserViewBinding

class ListAdapter(val onClick:(User)->Unit) : RecyclerView.Adapter<ListAdapter.ListHolder>() {
    private var userList = emptyList<User>()

    class ListHolder(view: View) : RecyclerView.ViewHolder(view){
        lateinit var binding: UserViewBinding

        constructor(b: UserViewBinding) : this(b.root){
            binding = b
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val binding = UserViewBinding.inflate(LayoutInflater.from(parent.context))
        return ListHolder(binding)
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val currentUser = userList[position]

        holder.binding.tId.text = currentUser.id.toString()
        holder.binding.tFirstName.text = currentUser.firstName
        holder.binding.tLastName.text = currentUser.lastName
        holder.binding.tAge.text = "( ${currentUser.age.toString()} )"

        holder.binding.viewUser.setOnClickListener {
            onClick(currentUser)
        }
    }

    fun setData(list: List<User>){
        this.userList = list
        notifyDataSetChanged()
    }
}