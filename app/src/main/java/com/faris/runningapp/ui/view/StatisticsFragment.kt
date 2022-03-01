package com.faris.runningapp.ui.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.faris.runningapp.R
import com.faris.runningapp.ui.viewmodels.StatisticsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    private val viewModel: StatisticsViewModel by viewModels()
}