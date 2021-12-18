package com.os_tec.mynotes.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.os_tec.mynotes.SharedPreferences
import com.os_tec.mynotes.activities.MainActivity
import com.os_tec.mynotes.activities.StartingActivity
import com.os_tec.mynotes.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentSplashBinding.inflate(inflater,container,false)

        val thread=object :Thread(){
            override fun run() {
                sleep(400)
                if (SharedPreferences(requireActivity()).getUID()!=""){
                    requireContext().startActivity(Intent(requireContext(),MainActivity::class.java))
                    requireActivity().finish()
                }else{
                    val mActivity=activity as StartingActivity
                    mActivity.startFragment(mActivity.sliderFragment)
                }

                super.run()
            }
        }
        thread.start()


        return binding.root
    }

}