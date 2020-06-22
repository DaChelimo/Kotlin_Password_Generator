package com.example.passwordgenerator.ui.formerPass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.passwordgenerator.PasswordsAdapter
import com.example.passwordgenerator.R
import com.example.passwordgenerator.SharedViewModel
import com.example.passwordgenerator.SharedViewModelFactory
import com.example.passwordgenerator.database.PasswordDataBase
import com.example.passwordgenerator.databinding.FragmentAllPasswordsBinding

class FormerPasswordFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var binding: FragmentAllPasswordsBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val dataSource = PasswordDataBase.getInstance(application)!!.passwordDatabaseDao

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_passwords, container, false)

        val thisView = binding.root

        viewModel = ViewModelProvider(this, SharedViewModelFactory(dataSource, thisView)).get(SharedViewModel::class.java)

        binding.lifecycleOwner = this
        binding.sharedViewModel = viewModel

        val adapter = PasswordsAdapter()
        binding.listRecycler.adapter = adapter

        viewModel.allPasswords.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        return binding.root
    }
}
