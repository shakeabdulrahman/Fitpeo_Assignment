package com.example.fitpeo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.fitpeo.base.BaseFragment
import com.example.fitpeo.databinding.FragmentPhotoDetailBinding
import com.example.fitpeo.viewmodel.SharedViewModel


class PhotoDetailFragment: BaseFragment<FragmentPhotoDetailBinding>() {

    private var _binding: FragmentPhotoDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoDetailBinding.inflate(inflater, container, false)

        binding.apply {
            sharedViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }
}