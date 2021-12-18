package com.os_tec.mynotes.fragments.notes

import android.content.Intent
import android.os.Bundle

import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.os_tec.mynotes.AppFunction
import com.os_tec.mynotes.R
import com.os_tec.mynotes.SharedPreferences
import com.os_tec.mynotes.activities.StartingActivity
import com.os_tec.mynotes.adapters.NotesAdapter
import com.os_tec.mynotes.adapters.SwipeGesture
import com.os_tec.mynotes.databinding.FragmentNotesBinding
import com.os_tec.mynotes.roomdb.Notes
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class NotesFragment : Fragment() {
    private lateinit var binding:FragmentNotesBinding
    private lateinit var viewModel:NotesViewModel
    private lateinit var adapter: NotesAdapter
    lateinit var data:ArrayList<Notes>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentNotesBinding.inflate(inflater,container,false)

        EventBus.getDefault().register(this)//register EventBus

        //setRcLayoutManager
        binding.recyclerView.layoutManager=
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)

        //initializedVariable
        viewModel= NotesViewModel()
        data=ArrayList()

        observe()//get and set data


        binding.profileImage.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), binding.profileImage)
            popupMenu.menuInflater.inflate(R.menu.action_bar_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.mLogout -> {
                        SharedPreferences(requireActivity()).logout()
                        val i = Intent(requireContext(), StartingActivity::class.java)
                        i.putExtra("nv", "signIn")
                        requireContext().startActivity(i)
                        requireActivity().finish()
                    }
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()

        }


        binding.edSearch.addTextChangedListener { text ->
            searchInArray(text.toString().lowercase())

        }

        binding.edSearch.setOnEditorActionListener { textView, i, keyEvent ->

            when(textView.imeActionId){
                1000->{
                    binding.edSearch.clearFocus()
                   AppFunction(requireActivity()).hideKeyboard()

                }
            }
            return@setOnEditorActionListener true
        }



        return binding.root
    }



    private fun observe(){
        viewModel.getData(requireActivity()).observe(viewLifecycleOwner,{
            data.addAll(it)
            setAdapter(it)
        })

    }


    private fun setAdapter(mData: ArrayList<Notes>) {
        adapter = NotesAdapter(requireActivity(), mData)
        val itemTouchHelper = ItemTouchHelper(SwipeGesture(requireActivity(), adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.adapter = adapter

        if (mData.isNotEmpty()) {
            showLayout(true)
        } else {
            showLayout(false)
        }

       // Log.e("dataNotesFragment", "dataNotesFragment$mdata")
    }



    private fun searchInArray(text: String) {
        val newArray = ArrayList<Notes>()

        for (i in data) {
            if (i.title.lowercase().contains(text)) {
                newArray.add(i)
            }
        }

        if (newArray.isNotEmpty()) {//if found result
            setAdapterData(newArray)
            binding.searchMessage.visibility=View.GONE

        } else {
            if (text!=""){//if contain the text but no result found
                newArray.clear()
                setAdapterData(newArray)
                binding.searchMessage.text="The result not found \"$text\""
                binding.searchMessage.visibility=View.VISIBLE

            }else{//if not contain the text->return original data
                newArray.clear()
                setAdapterData(data)
                binding.searchMessage.visibility=View.GONE

            }


        }
    }



    private fun setAdapterData(mData:ArrayList<Notes>){//to set data in adapter
        adapter.data.clear()
        adapter.data.addAll(mData)
        adapter.notifyDataSetChanged()
    }


    fun showLayout(status: Boolean) {
        if (status) {
            binding.imageView.visibility = View.VISIBLE
            binding.edSearch.visibility = View.VISIBLE
            binding.profileImage.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.searchMessage.visibility=View.INVISIBLE
            binding.emptyNotes.visibility = View.GONE
        } else {
            binding.imageView.visibility = View.GONE
            binding.edSearch.visibility = View.GONE
            binding.profileImage.visibility = View.GONE
            binding.linearLayout.visibility = View.GONE
            binding.searchMessage.visibility=View.INVISIBLE
            binding.emptyNotes.visibility = View.VISIBLE
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(obj: Notes) {
        data.clear()
        data.addAll(adapter.data)
        data.add(obj)
        adapter.data.clear()
        adapter.data.addAll(data)
        adapter.notifyDataSetChanged()
        showLayout(true)
        Log.e("EventBus", "refresh")

    }


    override fun onDestroy() {
        EventBus.getDefault().unregister(this)//unregister EventBus
        super.onDestroy()
    }
}