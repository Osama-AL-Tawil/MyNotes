package com.os_tec.mynotes.fragments

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.os_tec.mynotes.AppFunction
import com.os_tec.mynotes.R
import com.os_tec.mynotes.SharedPreferences
import com.os_tec.mynotes.activities.MainActivity
import com.os_tec.mynotes.activities.StartingActivity
import com.os_tec.mynotes.databinding.FragmentSignInBinding
import com.os_tec.mynotes.roomdb.Notes
import com.os_tec.mynotes.roomdb.RoomRepository
import com.os_tec.velanew.helper.Validation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
): View {
    // Inflate the layout for this fragment
    binding= FragmentSignInBinding.inflate(inflater,container,false)
        val mActivity=activity as StartingActivity


        binding.btnSignIn.setOnClickListener {
            val userName=binding.edUserName.text.toString()
            val password=binding.edPassword.text.toString()
            if (Validation(requireActivity())
                    .validateEditText(userName)&&Validation(requireActivity()).validateEditText(password)){
                CoroutineScope(Dispatchers.Main).launch{
                    val user=RoomRepository(requireActivity()).signIn(userName, password)

                    withContext(Dispatchers.Main){
                        if (user!=null){
                            //save user id
                            SharedPreferences(requireActivity()).addUID(user.uid)
                            //start activity
                            startActivity(Intent(requireContext(),MainActivity::class.java))
                            requireActivity().finish()
                        }else{
                            Toast.makeText(requireContext(),"SignIn Field !",Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }

        }


        var status=true
        binding.btnToggle.setOnClickListener {
           status= AppFunction(requireActivity()).passwordToggle(status,binding.edPassword,binding.btnToggle)
        }


        binding.btnSignUp.setOnClickListener {
            mActivity.startFragment(mActivity.signUpFragment)
        }


    return binding.root
}

}