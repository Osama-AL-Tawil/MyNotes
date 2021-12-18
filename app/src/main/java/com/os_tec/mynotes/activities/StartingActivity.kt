package com.os_tec.mynotes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.os_tec.mynotes.R
import com.os_tec.mynotes.databinding.ActivityStartingBinding
import com.os_tec.mynotes.fragments.SignInFragment
import com.os_tec.mynotes.fragments.SignUpFragment
import com.os_tec.mynotes.fragments.SliderFragment
import com.os_tec.mynotes.fragments.SplashFragment

class StartingActivity : AppCompatActivity() {
    lateinit var binding:ActivityStartingBinding
    var signInFragment=SignInFragment()
    var signUpFragment=SignUpFragment()
    var sliderFragment=SliderFragment()
    var splashFragment=SplashFragment()
    var activeFragment:Fragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityStartingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val nv=intent.getStringExtra("nv").toString()
        if (nv!=""){
            when(nv){
              "signIn"->{ startFragment(signInFragment)}
                else->{startFragment(splashFragment)}
            }
        }

    }

    fun startFragment(fragment: Fragment) {
        if (activeFragment != null) {
            supportFragmentManager.beginTransaction().remove(activeFragment!!).commit()
        }
        supportFragmentManager.beginTransaction().replace(R.id.mContainer, fragment).commit()
    }
}