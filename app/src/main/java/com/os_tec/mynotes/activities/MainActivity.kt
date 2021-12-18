package com.os_tec.mynotes.activities

import android.content.ComponentName
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.os_tec.mynotes.Alarm.BootCompleteReceiver
import com.os_tec.mynotes.BottomSheetAdd
import com.os_tec.mynotes.R
import com.os_tec.mynotes.databinding.ActivityMainBinding
import com.os_tec.mynotes.fragments.favorite.FavoriteFragment
import com.os_tec.mynotes.fragments.notes.NotesFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding
    val notesFragment= NotesFragment()
    val favoriteFragment= FavoriteFragment()
    var activeFragment:Fragment?= null
//    lateinit var adapter: NotesAdapter
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

    startFragment(NotesFragment())
    val receiver = ComponentName(applicationContext, BootCompleteReceiver::class.java)

    applicationContext.packageManager?.setComponentEnabledSetting(
        receiver,
        PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
        PackageManager.DONT_KILL_APP
    )

    binding.bottomNavigationView.background = null
    binding.bottomNavigationView.itemIconTintList=null

    binding.btnAddNote.setOnClickListener {
        BottomSheetAdd(this).showDialog()
    }

    binding.bottomNavigationView.setOnItemReselectedListener {  }
    binding.bottomNavigationView.setOnItemSelectedListener {
        when (it.itemId){
            R.id.nvNotes ->{
                startFragment(NotesFragment())
                return@setOnItemSelectedListener true
            }
            R.id.nvFavorite ->{
                startFragment(FavoriteFragment())
                return@setOnItemSelectedListener true
            }

            else->{
                return@setOnItemSelectedListener false
            }
        }
    }
    //binding.bottomNavigationView.menu.getItem(2).isEnabled = false

    }
fun startFragment(fragment:Fragment){
    supportFragmentManager.beginTransaction().replace(R.id.containerMain,fragment).commit()
//    if (activeFragment!=null){
//        supportFragmentManager.beginTransaction().remove(activeFragment!!).hide(activeFragment!!).commit()
//    }
//    if (fragment.isAdded){
//        supportFragmentManager.beginTransaction().hide(activeFragment!!).show(fragment).commit()
//
//    }else{
//        supportFragmentManager.beginTransaction().add(R.id.containerMain,fragment).commit()
//        supportFragmentManager.beginTransaction().show(fragment).commit()
//
//    }
//    activeFragment=fragment
}


}