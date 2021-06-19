package com.pawan.netmedstask.adapters

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pawan.netmedstask.databinding.ItemMedicineBinding
import com.pawan.netmedstask.models.Medicine

class MedicineAdapter(isPrescription: Boolean) :
    RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {
    private var isFromPrescriptionPad: Boolean = isPrescription


    inner class MedicineViewHolder(private val binding: ItemMedicineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(medicine: Medicine) {
            binding.tvMedicineName.text = medicine.name
            binding.tvStrength.text = if (!isFromPrescriptionPad)
                medicine.strength
            else
                "Doses ${medicine.dose}"
            binding.tvCompanyName.text = medicine.company
            if (isFromPrescriptionPad)
                binding.ivAdd.visibility = View.GONE
            binding.ivAdd.setOnClickListener {
                onAddClickListener?.let { it(medicine) }
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Medicine>() {
        override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemMedicineBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = differ.currentList[position]
        holder.bind(medicine)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onAddClickListener: ((Medicine) -> Unit)? = null

    fun setOnAddClickListener(listener: (Medicine) -> Unit) {
        onAddClickListener = listener
    }

}