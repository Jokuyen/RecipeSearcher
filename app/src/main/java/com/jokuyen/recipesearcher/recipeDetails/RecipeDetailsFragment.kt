package com.jokuyen.recipesearcher.recipeDetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jokuyen.recipesearcher.databinding.RecipeDetailsFragmentBinding

class RecipeDetailsFragment : Fragment() {

    private lateinit var viewModel: RecipeDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val application = requireNotNull(activity).application
        val binding = RecipeDetailsFragmentBinding.inflate(inflater)
        binding.setLifecycleOwner(this)

        val recipe = RecipeDetailsFragmentArgs.fromBundle(arguments!!).selectedRecipe

        val viewModelFactory = RecipeDetailsViewModelFactory(recipe, application)

        binding.recipeDetailsViewModel = ViewModelProviders.of(
            this, viewModelFactory).get(RecipeDetailsViewModel::class.java)

        return binding.root
    }
}
