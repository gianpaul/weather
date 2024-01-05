package com.example.weathercoroutines.screens.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.Address
import com.example.weathercoroutines.databinding.ItemAddressBinding

class AddressesAdapter(
    private var addresses: List<Address> = emptyList(),
    private val clickListener: (Address) -> Unit
) : RecyclerView.Adapter<AddressesAdapter.AddressViewHolder>() {

    class AddressViewHolder(
        private val binding: ItemAddressBinding,
        private val clickListener: (Address) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                clickListener(it.tag as Address)
            }
        }

        fun bind(address: Address) {
            binding.address.text = address.address.orEmpty()
            binding.city.text = address.city.orEmpty()
            binding.root.tag = address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val binding = ItemAddressBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AddressViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(addresses[position])
    }

    override fun getItemCount() = addresses.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(addresses: List<Address>) {
        this.addresses = addresses
        notifyDataSetChanged()
    }
}
