package com.faris.runningapp.ui.view

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.faris.runningapp.databinding.FragmentRunBinding
import com.faris.runningapp.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RunFragment : Fragment() {
    private var _binding: FragmentRunBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private var fineLocationGranted = false
    private var coarseLocationGranted = false
    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createLocationPermissionsRequest()
        checkOrUpdatePermissions()
        binding.fab.setOnClickListener {
            findNavController().navigate(RunFragmentDirections.actionRunFragmentToTrackingFragment())
        }
    }

    private fun createLocationPermissionsRequest() {
        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            fineLocationGranted =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: fineLocationGranted
            coarseLocationGranted =
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: coarseLocationGranted

        }
    }

    private fun requestLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun checkOrUpdatePermissions() {
        fineLocationGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        coarseLocationGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!fineLocationGranted || !coarseLocationGranted) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder(requireContext()).apply {
                    setMessage("Precise location is required")
                    setPositiveButton("Ok") { _, _ ->
                        requestLocationPermission()
                    }
                    setCancelable(true)
                    show()
                }
                return
            }
            requestLocationPermission()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}