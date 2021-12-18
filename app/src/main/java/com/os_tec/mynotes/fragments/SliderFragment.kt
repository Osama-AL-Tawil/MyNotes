package com.os_tec.mynotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.os_tec.mynotes.R
import com.os_tec.mynotes.activities.StartingActivity
import com.os_tec.mynotes.adapters.SliderAdapter
import com.os_tec.mynotes.databinding.FragmentSliderBinding
import com.os_tec.mynotes.models.SliderModel

class SliderFragment : Fragment() {
    lateinit var binding: FragmentSliderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding= FragmentSliderBinding.inflate(inflater,container,false)

        val sliderArray=ArrayList<SliderModel>()
        sliderArray.add(SliderModel("Made it Simple","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris pellentesque erat in blandit luctus.",R.drawable.s1))
        sliderArray.add(SliderModel("Made it Simple","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris pellentesque erat in blandit luctus.",R.drawable.s2))
        sliderArray.add(SliderModel("Made it Simple","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris pellentesque erat in blandit luctus.",R.drawable.s3))

        setViewPager(sliderArray)

        return binding.root
    }
    private fun setViewPager(data:ArrayList<SliderModel>){
        val mActivity=activity as StartingActivity
        val viewPager=binding.viewPager
        viewPager.adapter= SliderAdapter(requireContext(), data)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                binding.indicatorView.setViewPager(viewPager)

                if (position==data.lastIndex){ //last index
                    binding.btnNext.visibility=View.GONE
                    binding.btnStart.visibility=View.VISIBLE
                    binding.btnStart.setOnClickListener {
                        mActivity.startFragment(mActivity.signUpFragment)
                    }
                }else{
                    binding.btnNext.visibility=View.VISIBLE
                    binding.btnStart.visibility=View.GONE
                    binding.btnStart.text=resources.getString(R.string.next)
                    binding.btnNext.setOnClickListener {
                        viewPager.setCurrentItem(position+1,true)
                    }
                }
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {
            }


        })
    }

}