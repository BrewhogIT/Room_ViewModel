package com.example.roomclean.fragments.update

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.roomclean.R
import com.example.roomclean.databinding.FragmentUpdateBinding
import com.example.roomclean.model.User
import com.example.roomclean.navigator
import com.example.roomclean.viewmodel.UserViewModel

private const val ARG_PARAM1 = "param1"

class UpdateFragment : Fragment() {
    private lateinit var currentUser: User
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var mViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentUser = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                it.getParcelable(ARG_PARAM1,User::class.java)!!
                            }else{
                                 it.getParcelable(ARG_PARAM1)!!
                            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val activity : AppCompatActivity = activity as AppCompatActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        activity.supportActionBar?.title = "Update"

        mViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding = FragmentUpdateBinding.inflate(inflater,container,false)

        binding.updateFirstName.setText(currentUser.firstName)
        binding.updateLastName.setText(currentUser.lastName)
        binding.updateAge.setText(currentUser.age.toString())

        binding.btnUpdate.setOnClickListener {
            updateUserInDatabase()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.delete_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.menu_delete){

                    deleteUser()

                    return true
                }
                return false
            }

        },this.viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun deleteUser(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            mViewModel.deleteUser(currentUser)
            Toast.makeText(requireContext(),"Successfully deleted!",Toast.LENGTH_SHORT).show()
            navigator().goBack()
        }
        builder.setNegativeButton("No"){_,_->}
        builder.setTitle("Delete ${currentUser.firstName} ?")
        builder.setMessage("Are you sure you want to delete ${currentUser.firstName} ?")
        builder.create().show()
    }

    private fun updateUserInDatabase() {
        val currentName = binding.updateFirstName.text.toString()
        val currentLastName = binding.updateLastName.text.toString()
        val currentAge = binding.updateAge.text.toString()
        val currentId = currentUser.id

        if (checkDataField(currentName,currentLastName,currentAge)){
            val user = User(currentId,currentName,currentLastName,Integer.parseInt(currentAge))
            mViewModel.updateUser(user)

            Toast.makeText(requireContext(),"Successfully updated!", Toast.LENGTH_SHORT).show()
            navigator().goBack()
        } else {
            Toast.makeText(requireContext(),"Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkDataField(firstName: String, lastName: String, age: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && TextUtils.isEmpty(age))
    }

    companion object {
        fun newInstance(param1: User) =
            UpdateFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}