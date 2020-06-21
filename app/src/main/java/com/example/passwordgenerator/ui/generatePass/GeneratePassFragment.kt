package com.example.passwordgenerator.ui.generatePass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.passwordgenerator.R
import com.example.passwordgenerator.SharedViewModel
import com.example.passwordgenerator.SharedViewModelFactory
import com.example.passwordgenerator.database.PasswordDataBase
import com.example.passwordgenerator.databinding.FragmentGenerateBinding
import java.util.*

class GeneratePassFragment : Fragment() {

    private lateinit var viewModel: SharedViewModel
    private lateinit var binding: FragmentGenerateBinding
    private lateinit var option: Spinner
    var charLong: Int = 0

    @ExperimentalStdlibApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_generate, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = PasswordDataBase.getInstance(application)!!.passwordDatabaseDao

        viewModel = ViewModelProvider(this, SharedViewModelFactory(dataSource, application)).get(
            SharedViewModel::class.java)

        option = binding.spinnerChar

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this.requireActivity().application,
            R.array.optionsList,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            option.adapter = adapter
        }

        val optionsList = arrayOf(6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24)
        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@GeneratePassFragment.context, "Enter valid gender", Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                charLong = optionsList[position]
            }

        }

        val upperCase = mutableListOf<String>()
        // Will use random to generate random number then append letter to textview
        //val lowerCase = arrayOf("a", "b", "c", "d", "e", "f", "g", "h","i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "y", "z")

        val lowerCase = listOf("a", "b", "c", "d", "e", "f", "g", "h","i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "y", "z")
        lowerCase.forEach { eachSmallLetter -> upperCase.add(eachSmallLetter.toUpperCase(Locale.ROOT))}
        val number = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

        val alphabet = lowerCase + upperCase + number
        val result = binding.resultText

        binding.generateBtn.setOnClickListener {
            if (binding.enterWebsiteEdit.text.toString().trim().isNotEmpty()){

                viewModel.website = binding.enterWebsiteEdit.text.toString().trim().capitalize(Locale.ROOT)

                viewModel.newPassword = ""

                for (eachNumber in 1..charLong)  {
                    viewModel.newPassword += alphabet.random()
                }

                binding.addBtn.visibility = View.VISIBLE
                result.text = viewModel.newPassword

               Toast.makeText(this.context, "A $charLong character password for ${binding.enterWebsiteEdit.text.toString().capitalize(Locale.ROOT)}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addBtn.setOnClickListener {
            if (binding.enterWebsiteEdit.text.toString().trim().isNotEmpty()) {
                viewModel.insertPassword()
            }
            else{
                Toast.makeText(this.context, "Cannot add a password for an empty website", Toast.LENGTH_SHORT).show()
            }
            binding.addBtn.visibility = View.GONE
        }

        return binding.root
    }
}
