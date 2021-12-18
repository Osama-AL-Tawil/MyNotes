package com.os_tec.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.os_tec.mynotes.AppFunction
import com.os_tec.mynotes.activities.StartingActivity
import com.os_tec.mynotes.databinding.FragmentSignUpBinding
import com.os_tec.mynotes.roomdb.RoomRepository
import com.os_tec.mynotes.roomdb.Users
import com.os_tec.velanew.helper.Validation
import java.util.*

class SignUpFragment : Fragment() {
lateinit var binding:FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentSignUpBinding.inflate(inflater,container,false)
        val mActivity=activity as StartingActivity

        binding.btnSignIn.setOnClickListener {
            mActivity.startFragment(mActivity.signInFragment)
        }

        var status=true
        binding.btnToggle.setOnClickListener {
            status= AppFunction(requireActivity()).passwordToggle(status,binding.edPassword,binding.btnToggle)
        }


        binding.btnSignUp.setOnClickListener {
            val randomId=UUID.randomUUID().toString()
            val userName=binding.edUserName.text.toString()
            val password=binding.edPassword.text.toString()
            val email=binding.edEmail.text.toString()
            if (Validation(requireActivity()).validateEditText(userName)&&
                Validation(requireActivity()).validateEditText(password)&&
                    Validation(requireActivity()).validateEmail(email)){

                RoomRepository(requireActivity()).signup(Users(randomId,userName, email, password))
                Toast.makeText(requireContext(),"Sign Up Success:)", Toast.LENGTH_SHORT).show()
                mActivity.startFragment(mActivity.signInFragment)

            }



        }
        return binding.root
    }

}