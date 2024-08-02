package com.example.funtoscontacts.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.customview.R
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.funtoscontacts.Contacts
import com.example.funtoscontacts.MainActivity
import com.example.funtoscontacts.adapter.ContactsAdapter
import com.example.funtoscontacts.databinding.CreateContactViewBinding
import com.example.funtoscontacts.model.ContactsViewModel

class AddContactFragment : Fragment(com.example.funtoscontacts.R.layout.create_contact_view) {

    private var _binding : CreateContactViewBinding? =  null
    private val binding get() = _binding!!
    lateinit var viewModel: ContactsViewModel
    lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =CreateContactViewBinding.inflate(inflater,container,false)

        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        binding.createContact.setOnClickListener {
            var firstname = binding.etFirstname.text.toString()
            var surname = binding.etsurname.text.toString()
            var email = binding.etemail.text.toString()
            var nickname = binding.etnickname.text.toString()
            var phoneNumber = binding.etphonenumber.text.toString()

            if (firstname.isNotEmpty() && phoneNumber.isNotEmpty()){
                var contact = Contacts(firstname,surname,phoneNumber,email,nickname)
               showSaveContactConfirmationDialog(contact)
            } else{
                Toast.makeText(requireActivity(),"Please Fill in the Contacts First Name and Phone Number", Toast.LENGTH_SHORT).show()
            }
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }



    }

    private fun showSaveContactConfirmationDialog(contacts: Contacts){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Save Contact?")

        builder.setPositiveButton("YES"){dialog,_ ->
            viewModel.insertContact(contacts)
            dialog.dismiss()
            Toast.makeText(requireActivity(),"Contact Saved", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
        builder.setNegativeButton("NO"){dialog,_ ->
            dialog.dismiss()
        }
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }
}