package com.example.fitpeo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.fitpeo.R
import com.example.fitpeo.base.BaseFragment
import com.example.fitpeo.data.model.Photos
import com.example.fitpeo.databinding.FragmentMainBinding
import com.example.fitpeo.ui.adapter.PhotosAdapter
import com.example.fitpeo.ui.adapter.PhotosLoadStateAdapter
import com.example.fitpeo.viewmodel.SharedViewModel


class MainFragment: BaseFragment<FragmentMainBinding>(), PhotosAdapter.CallBack {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // we are initialising before OnCreateView, because selected item reset on back navigation
        viewModel.adapter = PhotosAdapter(this)

        viewModel.concatAdapter = viewModel.adapter?.withLoadStateFooter(
            footer = PhotosLoadStateAdapter { viewModel.adapter!!.retry() }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.apply {
            sharedViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        binding.photoListRecyclerView.adapter = viewModel.concatAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        viewModel.collectUiState()
    }

    private fun initView() {
        viewModel.adapter?.addLoadStateListener {
            showLoadingWhilePaging(it)
        }
    }

    private fun showLoadingWhilePaging(loadState: CombinedLoadStates) {
        if (loadState.source.refresh is LoadState.Loading) {
            showLoadingDialog(true)
        } else if (loadState.source.refresh is LoadState.NotLoading) {
            showLoadingDialog(false)
        }
    }

    override fun onPhotoCardClick(photo: Photos) {
        viewModel.onPhotoSelected(photo)
        findNavController().navigate(R.id.action_mainFragment_to_photoDetailFragment)
    }
}