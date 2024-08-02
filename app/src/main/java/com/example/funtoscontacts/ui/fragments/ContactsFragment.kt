package com.example.funtoscontacts.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.funtoscontacts.MainActivity
import com.example.funtoscontacts.R
import com.example.funtoscontacts.adapter.ContactsAdapter
import com.example.funtoscontacts.databinding.ContactsMainPageBinding
import com.example.funtoscontacts.model.ContactsViewModel

class ContactsFragment : Fragment(R.layout.contacts_main_page) {
    
    private var _binding : ContactsMainPageBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel : ContactsViewModel
    lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ContactsMainPageBinding.inflate(inflater,container,false)
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        setUpRecyclerView()

        contactsAdapter.setOnItemClickListener { contact ->
            val id = contact.id
            val bundle = bundleOf("id" to id)
            findNavController().navigate(R.id.action_contactsFragment_to_viewContactFragment, bundle)
        }

        viewModel.getContact().observe(viewLifecycleOwner, Observer { contact ->
            contactsAdapter.differ.submitList(contact)
        })

        binding.fabAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_contactsFragment_to_addContactFragment)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchQuery ->
                    viewModel.searchContacts(searchQuery).observe(requireActivity(), Observer {
                        it?.let { searchResults->
                            contactsAdapter.differ.submitList(searchResults)
                        }
                    })
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchQuery ->
                    viewModel.searchContacts(searchQuery).observe(requireActivity(), Observer {
                        it?.let { searchResults ->
                            contactsAdapter.differ.submitList(searchResults)
                        }
                    })
                }
                return true
            }
        })
    }

    private fun setUpRecyclerView(){
        contactsAdapter = ContactsAdapter()
        binding.recyclerViewNotes.apply {
            adapter = contactsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}