package com.example.roomclean.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.roomclean.model.User
import com.example.roomclean.viewmodel.UserViewModel
import com.example.roomclean.databinding.FragmentAddBinding
import com.example.roomclean.navigator

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding
    lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val activity : AppCompatActivity = activity as AppCompatActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.title = "Add"

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding = FragmentAddBinding.inflate(inflater,container,false)
        binding.btnAdd.setOnClickListener {
            addUserToDatabase()
        }

        return binding.root
    }

    private fun addUserToDatabase() {
        val firstName = binding.edFirstName.text.toString()
        val lastName = binding.edLastName.text.toString()
        val age = binding.edAge.text.toString()


        if (checkDataField(firstName,lastName,age)){
            val user = User(0,firstName,lastName,Integer.parseInt(age))
            mUserViewModel.addUser(user)

            Toast.makeText(requireContext(),"Successfully added!",Toast.LENGTH_SHORT).show()
            navigator().goBack()
        } else {
            Toast.makeText(requireContext(),"Please fill out all fields.",Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkDataField(firstName: String, lastName: String, age: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(age))
    }

}