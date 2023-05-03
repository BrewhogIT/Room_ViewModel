package com.example.roomclean

import androidx.fragment.app.Fragment
import com.example.roomclean.model.User

fun Fragment.navigator(): Navigator{
    return requireActivity() as Navigator
}

interface Navigator {
    fun goBack()
    fun openSecondFragment()
    fun openThirdFragment(user: User)
}