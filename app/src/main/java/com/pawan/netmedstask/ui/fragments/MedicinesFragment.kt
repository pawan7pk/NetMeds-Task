package com.pawan.netmedstask.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.pawan.netmedstask.R
import com.pawan.netmedstask.adapters.MedicineAdapter
import com.pawan.netmedstask.databinding.FragmentMedicinesBinding
import com.pawan.netmedstask.models.Medicine
import com.pawan.netmedstask.models.MedicineResponse
import com.pawan.netmedstask.ui.activities.MedicineActivity
import com.pawan.netmedstask.ui.dialogs.AddDosesDialog
import com.pawan.netmedstask.util.Resource
import com.pawan.netmedstask.viewmodels.MedicineViewModel

class MedicinesFragment : Fragment(R.layout.fragment_medicines) {

    private lateinit var medicineViewModel: MedicineViewModel
    private lateinit var medicineAdapter: MedicineAdapter
    private lateinit var binding: FragmentMedicinesBinding

    private val observerMedicineResponse = Observer<Resource<MedicineResponse>> { response ->
        when (response) {
            is Resource.Success -> {
                hideProgressBar()
                response.data?.let { medicinesResponse ->

                    medicineAdapter.differ.submitList(medicinesResponse)
                }
            }
            is Resource.Error -> {
                hideProgressBar()
                response.message?.let { message ->
                    Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG)
                        .show()
                }
            }

            is Resource.Loading -> {
                showProgressBar()
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBindingAndVm(view)
        setRecyclerView()


    }

    private fun setUpBindingAndVm(view: View) {
        binding = FragmentMedicinesBinding.bind(view)
        medicineViewModel = (activity as MedicineActivity).viewModel
        medicineViewModel.medicineListLiveData.observe(viewLifecycleOwner, observerMedicineResponse)
    }

    private fun setRecyclerView() {
        medicineAdapter = MedicineAdapter(false)
        medicineAdapter.setOnAddClickListener {
            AddDosesDialog(
                requireContext(),
                object : AddDosesDialog.AddDosesListener {
                    override fun onAddButtonClicked(item: Medicine) {
                        medicineViewModel.addMedicine(item)
                        Toast.makeText(context, "Item Added Successfully", Toast.LENGTH_LONG).show()
                    }
                }, it
            ).show()
        }
        binding.rvMedicines.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            setHasFixedSize(true)
            adapter = medicineAdapter
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

}