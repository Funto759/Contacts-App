package com.example.funtoscontacts.ui.fragments

import android.app.AlertDialog
import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.funtoscontacts.Contacts
import com.example.funtoscontacts.MainActivity
import com.example.funtoscontacts.R
import com.example.funtoscontacts.adapter.ContactsAdapter
import com.example.funtoscontacts.databinding.ViewContactsBinding
import com.example.funtoscontacts.model.ContactsViewModel

class ViewContactFragment : Fragment(R.layout.view_contacts) {

    private var _binding : ViewContactsBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: ContactsViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  ViewContactsBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val id = requireArguments().getLong("id")
        viewModel.getContactId(id).observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.etfirstname.text = Editable.Factory.getInstance().newEditable(it.firstName)
                binding.etsurname.text = Editable.Factory.getInstance().newEditable(it.surname)
                binding.etnickname.text = Editable.Factory.getInstance().newEditable(it.nickName)
                binding.etemail.text = Editable.Factory.getInstance().newEditable(it.email)
                binding.etphonenumber.text = Editable.Factory.getInstance().newEditable(it.phoneNumber)

                binding.contactName.text = Editable.Factory.getInstance().newEditable(it.firstName )

            }

        })

         binding.back.setOnClickListener {
             findNavController().popBackStack()
         }

        binding.editcontact.setOnClickListener {
            var newFirstName = binding.etfirstname.text.toString()
            var newSurname = binding.etsurname.text.toString()
            var newNickName = binding.etnickname.text.toString()
            var newEmail = binding.etemail.text.toString()
            var newPhoneNumber = binding.etphonenumber.text.toString()
            var editedContact = Contacts(newFirstName,newSurname,newPhoneNumber,newEmail,newNickName,id)
           showEditContactConfirmationDialog(editedContact)
        }

        binding.delete.setOnClickListener {
            var FirstName = binding.etfirstname.text.toString()
            var Surname = binding.etsurname.text.toString()
            var NickName = binding.etnickname.text.toString()
            var Email = binding.etemail.text.toString()
            var PhoneNumber = binding.etphonenumber.text.toString()
            var Contact = Contacts(FirstName,Surname,PhoneNumber,Email,NickName,id)
            showDeleteConfirmationDialog(Contact)


        }

    }

    private fun showDeleteConfirmationDialog(contacts: Contacts){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Delete Contact?")

        builder.setPositiveButton("YES"){dialog,_ ->
            viewModel.deleteContact(contacts)
            dialog.dismiss()
            Toast.makeText(requireActivity(),"Contact deleted", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
        builder.setNegativeButton("NO"){dialog,_ ->
            dialog.dismiss()
        }
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }

    private fun showEditContactConfirmationDialog(contacts: Contacts){
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Edit Contact?")

        builder.setPositiveButton("YES"){dialog,_ ->
            viewModel.insertContact(contacts)
            dialog.dismiss()
            Toast.makeText(requireActivity(),"Contact Edited", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
        builder.setNegativeButton("NO"){dialog,_ ->
            dialog.dismiss()
        }
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }
}