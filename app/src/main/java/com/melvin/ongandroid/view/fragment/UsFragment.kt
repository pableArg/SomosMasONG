package com.melvin.ongandroid.view.fragment

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.melvin.ongandroid.R
import com.melvin.ongandroid.databinding.FragmentUsBinding
import com.melvin.ongandroid.model.entities.us.Member
import com.melvin.ongandroid.view.adapters.UsAdapter
import com.melvin.ongandroid.viewmodel.ApiStatus
import com.melvin.ongandroid.viewmodel.UsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsFragment : Fragment() {

    private var _binding: FragmentUsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsBinding.inflate(inflater, container, false)
        setUpMembers()
        handleProgressBar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title="Nosotros"
    }

    private fun initRecyclerView(usData: List<Member>) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val manager = GridLayoutManager(binding.root.context, 2, GridLayoutManager.VERTICAL, false)
            binding.rvUs.layoutManager = manager
        } else {
            binding.rvUs.layoutManager = LinearLayoutManager(context)
            binding.rvUs.layoutManager = LinearLayoutManager(binding.root.context)
        }
        binding.rvUs.adapter = UsAdapter(usData, false)
    }

    private fun setUpObserver() {
        viewModel.membersList.observe(viewLifecycleOwner) { currentList ->
            if (currentList.isNullOrEmpty()) {
                dialogMembers()
            } else {
                initRecyclerView(currentList)
            }
        }
    }

    private fun setUpMembers() {
        viewModel.getMembers()
        setUpObserver()
    }

    private fun dialogMembers() {
        context?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(R.string.menu_about_us)
                .setMessage(R.string.dialog_news_error_message)
                .setPositiveButton(R.string.dialog_news_error_positive_btn) { _, _ ->
                    setUpMembers()
                }
                .show()
        }
    }

    private fun handleProgressBar() {
        viewModel.status.observe(viewLifecycleOwner) { currentStatus ->
            when (currentStatus) {
                ApiStatus.SUCCESS, ApiStatus.FAILURE -> binding.pbUs.hideProgressBar()
                ApiStatus.LOADING -> binding.pbUs.showProgressBar()
            }
        }
    }
}