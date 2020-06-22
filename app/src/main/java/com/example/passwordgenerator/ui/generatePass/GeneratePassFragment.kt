package com.example.passwordgenerator.ui.generatePass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.passwordgenerator.R
import com.example.passwordgenerator.SharedViewModel
import com.example.passwordgenerator.SharedViewModelFactory
import com.example.passwordgenerator.database.PasswordDataBase
import com.example.passwordgenerator.databinding.FragmentGenerateBinding
import com.google.android.material.snackbar.Snackbar

class GeneratePassFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var binding: FragmentGenerateBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_generate, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = PasswordDataBase.getInstance(application)!!.passwordDatabaseDao

        val thisView = binding.root

        viewModel = ViewModelProvider(this, SharedViewModelFactory(dataSource, thisView)).get(
            SharedViewModel::class.java)


        binding.addBtn.setOnClickListener {
            if (binding.enterWebsiteEdit.text.toString().trim().isNotEmpty() && binding.enterPasswordEdit.text.toString().trim().isNotEmpty()) {
                viewModel.website = binding.enterWebsiteEdit.text.toString().trim()
                viewModel.password = binding.enterPasswordEdit.text.toString().trim()
                viewModel.insertPassword()
                Toast.makeText(this.context, "Website and password have been added", Toast.LENGTH_SHORT).apply {
                    this.show()
                }
            }
            else{
                Snackbar.make(this.requireView(), "Please enter valid input", Snackbar.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}
