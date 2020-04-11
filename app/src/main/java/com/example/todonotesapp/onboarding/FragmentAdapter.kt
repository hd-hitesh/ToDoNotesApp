package com.example.todonotesapp.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.lang.IllegalStateException

class FragmentAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager)
{
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return OnBoardingOneFragment()
            1 ->return OnBoardingTwoFragment()
            //else ->return null
        }
        throw IllegalStateException("position $position is invalid for this viewpager")
    }

    override fun getCount(): Int {
        return 2
    }

}