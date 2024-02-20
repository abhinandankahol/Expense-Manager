package com.abhinandankahol.expensemanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.abhinandankahol.expensemanager.adapters.ViewPagerAdapter
import com.abhinandankahol.expensemanager.databinding.FragmentStatsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class StatsFragment : Fragment() {
    private val binding by lazy {
        FragmentStatsBinding.inflate(layoutInflater)
    }

    private lateinit var viewpager: ViewPager2
    private lateinit var tablayout: TabLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewpager = binding.viewpager
        tablayout = binding.tabLayout

        viewpager.adapter = ViewPagerAdapter(requireActivity(), IncomeFragment(), ExpenseFragment())

        TabLayoutMediator(tablayout, viewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "Income"
                1 -> tab.text = "Expense"
            }
        }.attach()


    }


}