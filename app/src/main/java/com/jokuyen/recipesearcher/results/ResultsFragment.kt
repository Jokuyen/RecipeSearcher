package com.jokuyen.recipesearcher.results

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.jokuyen.recipesearcher.R
import com.jokuyen.recipesearcher.databinding.ResultsFragmentBinding

class ResultsFragment : Fragment() {

    private lateinit var viewModel: ResultsViewModel
    private lateinit var viewModelFactory: ResultsViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        val binding: ResultsFragmentBinding = DataBindingUtil
            .inflate(inflater, R.layout.results_fragment, container, false)


        // Create the viewmodel
        viewModelFactory = ResultsViewModelFactory(ResultsFragmentArgs.fromBundle(arguments!!).userRecipeInput)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ResultsViewModel::class.java)

        // Data binding
        binding.resultsViewModel = viewModel
        binding.setLifecycleOwner(this)
        binding.recyclerView.adapter = RecyclerViewAdapter(RecyclerViewAdapter.OnClickListener {
            viewModel.displayRecipeDetails(it)
        })

        // Navigation to recipe's details screen
        viewModel.navigateToSelectedRecipe.observe(this, Observer {
            if ( it != null ) {
                this.findNavController().navigate(
                    ResultsFragmentDirections.actionResultsFragmentToRecipeDetailsFragment(it))
                viewModel.displayRecipeDetailsComplete()
            }
        })

        return binding.root
    }
}
