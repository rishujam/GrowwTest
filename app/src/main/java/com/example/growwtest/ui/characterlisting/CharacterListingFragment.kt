package com.example.growwtest.ui.characterlisting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growwtest.MainActivity
import com.example.growwtest.databinding.FragmentCharacterListingBinding
import com.example.growwtest.domain.Resource
import com.example.growwtest.domain.model.Character
import com.example.growwtest.ui.filmlisting.FilmsListingFragment
import com.example.growwtest.ui.action.CharacterListingAction
import com.example.growwtest.ui.filter.FilterSortBottomSheet
import com.example.growwtest.util.Constants
import com.example.growwtest.util.hide
import com.example.growwtest.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        viewModel.getPeople()
        viewModel.characters.onEach {
            when(it) {
                is Resource.Loading -> {
                    binding?.pbPeopleListing?.show()
                }
                is Resource.Success -> {
                    binding?.pbPeopleListing?.hide()
                    viewModel.nextPage = it.data?.nextPage ?: -1
                    Log.d("RishuTest", "nextPage: ${viewModel.nextPage}")
                    characterAdapter.setData(it.data?.characters)
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
                val modal = FilterSortBottomSheet()
                val bundle = Bundle()
                bundle.putParcelable(Constants.ARGS.ARG_SHEET_DATA, filterFeatures)
                bundle.putParcelable(Constants.ARGS.ARG_SHEET_ACTION, CharacterListingAction.Filter)
                modal.arguments = bundle
                childFragmentManager.let { modal.show(it, "CharacterListingFragment") }
            }

            chSortCharacterListing.setOnClickListener {
//                val sortingFeatures = viewModel.getSortFeatures()
                val modal = FilterSortBottomSheet()
                val bundle = Bundle()
                bundle.putParcelable(Constants.ARGS.ARG_SHEET_ACTION, CharacterListingAction.Sort)
//                bundle.putParcelable(Constants.ARGS.ARG_SHEET_DATA, sortingFeatures)
                modal.arguments = bundle
                childFragmentManager.let { modal.show(it, "CharacterListingFragment") }
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
                    val lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition()
                    if(totalItemCount - lastVisibleItemPosition < 2) {
                        viewModel.getPeople()
                    }
                }
            })
        }

        characterAdapter.setOnItemClickListener {
            val fragment = FilmsListingFragment()
            (activity as? MainActivity)?.addCurrentFragToBackStack(fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}