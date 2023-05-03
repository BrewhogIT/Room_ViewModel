package com.example.roomclean

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.roomclean.databinding.ActivityMainBinding
import com.example.roomclean.fragments.add.AddFragment
import com.example.roomclean.fragments.list.ListFragment
import com.example.roomclean.fragments.update.UpdateFragment
import com.example.roomclean.model.User

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container,ListFragment())
                .commit()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        goBack()
        return true
    }

    override fun goBack() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun openSecondFragment() {
        val fragment = AddFragment()

        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container,fragment)
            .commit()
    }

    override fun openThirdFragment(user: User) {
        val fragment = UpdateFragment.newInstance(user)

        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_container,fragment)
            .commit()
    }
}
