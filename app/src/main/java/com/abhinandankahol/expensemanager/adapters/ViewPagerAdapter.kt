package com.abhinandankahol.expensemanager.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(val fa: FragmentActivity, val frag1: Fragment, val frag2: Fragment) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> frag1
            else -> {
                frag2
            }
        }
    }
}