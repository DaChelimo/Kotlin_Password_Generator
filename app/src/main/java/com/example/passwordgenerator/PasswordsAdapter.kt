package com.example.passwordgenerator

import android.graphics.Color
import android.graphics.Color.rgb
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordgenerator.database.PasswordDataClass
import com.example.passwordgenerator.databinding.RecyclerItemBinding

class PasswordsAdapter: ListAdapter<PasswordDataClass, ViewHolder>(PasswordDiffUtil()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

}

class ViewHolder(private val binding: RecyclerItemBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(item: PasswordDataClass) {
        binding.websiteInput.text = item.website
        binding.passwordInput.text = item.password
        binding.strengthInput.text = if (item.password.length >= 10) {
            "Strong"
        } else {
            "Weak"
        }

        binding.strengthInput.setTextColor(
            if (binding.strengthInput.text == "Strong") {
                Color.GREEN
            } else {
                rgb(205, 92, 92)
            }
        )
    }

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = RecyclerItemBinding.inflate(layoutInflater, parent, false)

            Log.i("Adapter", "onCreateViewHolder executed")
            return ViewHolder(binding)
        }
    }
}


class PasswordDiffUtil : DiffUtil.ItemCallback<PasswordDataClass>(){
    override fun areItemsTheSame(oldItem: PasswordDataClass, newItem: PasswordDataClass): Boolean {
        return oldItem.password_id == newItem.password_id
    }

    override fun areContentsTheSame(
        oldItem: PasswordDataClass,
        newItem: PasswordDataClass
    ): Boolean {
        return oldItem == newItem
    }

}


/*
green - rgb(113, 238, 184)
red - rgb(205, 92, 92)
 */
