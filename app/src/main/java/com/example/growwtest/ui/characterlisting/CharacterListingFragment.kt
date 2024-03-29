package com.example.growwtest.ui.characterlisting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growwtest.MainActivity
import com.example.growwtest.databinding.FragmentCharacterListingBinding
import com.example.growwtest.domain.Resource
import com.example.growwtest.domain.model.CharacterFeatures
import com.example.growwtest.ui.action.CharacterListingAction
import com.example.growwtest.ui.filmlisting.FilmsListingFragment
import com.example.growwtest.ui.filter.FilterSortBottomSheet
import com.example.growwtest.ui.lsitener.BottomSheetListener
import com.example.growwtest.util.Constants
import com.example.growwtest.util.hide
import com.example.growwtest.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/*
 * Created by Sudhanshu Kumar on 09/01/24.
 */

@AndroidEntryPoint
class CharacterListingFragment : Fragment() {

    private var _binding: FragmentCharacterListingBinding? = null
    private val binding get() = _binding
    private val viewModel: CharacterListingViewModel by viewModels()
    private lateinit var characterAdapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharacterListingBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRv()
        viewModel.getCharacters()
        viewModel.characters.onEach {
            when (it) {
                is Resource.Loading -> {
                    binding?.pbPeopleListing?.show()
                }

                is Resource.Success -> {
                    binding?.pbPeopleListing?.hide()
                    viewModel.nextPage = it.data?.nextPage ?: -1
                    characterAdapter.submitData(it.data?.characters)
                    if(it.data?.characters?.isEmpty() == true && it.data.nextPage != null) {
                        viewModel.selectedFilteringFeatures?.let { selectedFeatures ->
                            viewModel.getFilteredCharacters(selectedFeatures)
                        }
                    }
                }

                is Resource.Error -> {
                    Toast.makeText(context, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                    binding?.pbPeopleListing?.hide()
                }
            }
        }.flowOn(Dispatchers.Main).launchIn(lifecycleScope)

        binding?.apply {
            chFilterCharacterListing.setOnClickListener {
                val filterFeatures = viewModel.getFilterFeatures()
                val modal = FilterSortBottomSheet(object : BottomSheetListener {
                    override fun onApplyClickListener(selectedFeatures: CharacterFeatures) {
                        setupRv()
                        viewModel.nextPage = 1
                        viewModel.getFilteredCharacters(filterFeatures)
                    }

                    override fun onClearClickListener() {
                        viewModel.selectedFilteringFeatures = null
                        setupRv()
                        viewModel.nextPage = 1
                        viewModel.getCharacters()
                    }
                })
                val bundle = Bundle()
                bundle.putParcelable(Constants.ARGS.ARG_SHEET_DATA, filterFeatures)
                bundle.putParcelable(Constants.ARGS.ARG_SHEET_ACTION, CharacterListingAction.Filter)
                modal.arguments = bundle
                childFragmentManager.let { modal.show(it, "CharacterListingFragment") }
            }

            chSortCharacterListing.setOnClickListener {

            }
        }
    }

    private fun setupRv() {
        characterAdapter = CharactersAdapter()
        binding?.rvCharacterListing?.apply {
            adapter = characterAdapter
            val gridLayoutManager = GridLayoutManager(context, 2)
            layoutManager = gridLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = gridLayoutManager.itemCount
                    val lastVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition()
                    if (totalItemCount - lastVisibleItemPosition < 2) {
                        viewModel.selectedFilteringFeatures?.let {
                            viewModel.getFilteredCharacters(it)
                        } ?: run {
                            viewModel.getCharacters()
                        }
                    }
                }
            })

        }

        characterAdapter.setOnItemClickListener {
            val bundle = Bundle()
            bundle.putString(Constants.ARGS.ARG_CHARACTER, it.name)
            val fragment = FilmsListingFragment()
            fragment.arguments = bundle
            (activity as? MainActivity)?.addCurrentFragToBackStack(fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}