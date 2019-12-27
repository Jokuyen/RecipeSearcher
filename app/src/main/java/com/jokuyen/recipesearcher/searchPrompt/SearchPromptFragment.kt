package com.jokuyen.recipesearcher.searchPrompt

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI

import com.jokuyen.recipesearcher.R
import com.jokuyen.recipesearcher.databinding.SearchPromptFragmentBinding

class SearchPromptFragment : Fragment() {

    private lateinit var binding: SearchPromptFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil
            .inflate(inflater, R.layout.search_prompt_fragment, container, false)

        // Data binding
        binding.searchPromptFragment = this
        binding.setLifecycleOwner(this)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            view!!.findNavController()) || super.onOptionsItemSelected(item)
    }

    fun navigateToResultsFragment() {
        this.findNavController().navigate(SearchPromptFragmentDirections.actionSearchPromptFragmentToResultsFragment(binding.recipeInputEdit.text.toString()))
    }
}
