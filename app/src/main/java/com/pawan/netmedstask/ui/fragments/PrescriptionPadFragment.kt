package com.pawan.netmedstask.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pawan.netmedstask.R
import com.pawan.netmedstask.adapters.MedicineAdapter
import com.pawan.netmedstask.databinding.FragmentPrescriptionPadBinding
import com.pawan.netmedstask.ui.activities.MedicineActivity
import com.pawan.netmedstask.viewmodels.MedicineViewModel

class PrescriptionPadFragment : Fragment(R.layout.fragment_prescription_pad) {
    private lateinit var binding: FragmentPrescriptionPadBinding
    private lateinit var medicineAdapter: MedicineAdapter
    private lateinit var medicineViewModel: MedicineViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBindingAndVm(view)
        setRecyclerView()


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val medicine = medicineAdapter.differ.currentList[position]
                medicineViewModel.deleteMedicine(medicine)
                Snackbar.make(view, "Successfully deleted Medicine", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        medicineViewModel.addMedicine(medicine)
                        medicineAdapter.notifyDataSetChanged()
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvPrescription)
        }
    }

    private fun setRecyclerView() {
        medicineAdapter = MedicineAdapter(true)
        binding.rvPrescription.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
            setHasFixedSize(true)
            adapter = medicineAdapter
        }
    }

    private fun setUpBindingAndVm(view: View) {
        binding = FragmentPrescriptionPadBinding.bind(view)
        medicineViewModel = (activity as MedicineActivity).viewModel
        medicineViewModel.getPrescriptionPadList()
            .observe(viewLifecycleOwner, { medicines ->
                if (medicines.isEmpty()) {
                    binding.tvDisclaimer.visibility = View.GONE
                    Toast.makeText(context, "No Prescriptions Found", Toast.LENGTH_LONG).show()
                } else
                    medicineAdapter.differ.submitList(medicines)
            })
    }


}