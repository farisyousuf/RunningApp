package com.faris.runningapp.ui.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.faris.runningapp.R
import com.faris.runningapp.databinding.FragmentSetupBinding
import com.faris.runningapp.ui.MainActivity
import com.faris.runningapp.ui.viewmodels.MainViewModel
import com.faris.runningapp.util.Constants.KEY_FIRST_TIME_TOGGLE
import com.faris.runningapp.util.Constants.KEY_NAME
import com.faris.runningapp.util.Constants.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment() {
    private var _binding: FragmentSetupBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var sharedPref: SharedPreferences

    @set:Inject
    var isFirstAppOpen = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isFirstAppOpen) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment, true)
                .build()
            findNavController().navigate(SetupFragmentDirections.actionSetupFragmentToRunFragment(), navOptions)
        }
        binding.tvContinue.setOnClickListener {
            if (writePersonalDataToSharedPref()) {
                findNavController().navigate(SetupFragmentDirections.actionSetupFragmentToRunFragment())
            } else {
                Snackbar.make(requireView(), "Please enter all fields", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun writePersonalDataToSharedPref(): Boolean {
        val name = binding.etName.text.toString()
        val weight = binding.etWeight.text.toString()
        if (name.trim().isEmpty() || weight.trim().isEmpty()) {
            return false
        }
        sharedPref.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .apply()

        val toolbarText = "Let's go, $name!"
        (requireActivity() as? MainActivity)?.setToolbarTitle(toolbarText)
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}