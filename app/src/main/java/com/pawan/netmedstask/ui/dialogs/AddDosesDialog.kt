package com.pawan.netmedstask.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.pawan.netmedstask.databinding.DialogAddDosesBinding
import com.pawan.netmedstask.models.Medicine

class AddDosesDialog(
    context: Context,
    private val addDosesListener: AddDosesListener,
    val medicine: Medicine
) :
    AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = DialogAddDosesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvAdd.setOnClickListener {
            val med = medicine
            if (binding.etDoseQuantity.text.isBlank()) {
                Toast.makeText(context, "Please Enter Dose.....", Toast.LENGTH_LONG).show()
            } else {
                med.dose = binding.etDoseQuantity.text.toString().toInt()
                addDosesListener.onAddButtonClicked(med)
                dismiss()
            }
        }
    }


    interface AddDosesListener {
        fun onAddButtonClicked(item: Medicine)
    }
}