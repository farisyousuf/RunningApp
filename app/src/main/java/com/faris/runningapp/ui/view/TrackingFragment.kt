package com.faris.runningapp.ui.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.faris.runningapp.R
import com.faris.runningapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking) {

    private val viewModel: MainViewModel by viewModels()
}