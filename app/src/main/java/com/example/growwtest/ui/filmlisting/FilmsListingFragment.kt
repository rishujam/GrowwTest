package com.example.growwtest.ui.filmlisting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growwtest.databinding.FragmentFilmsListingBinding
import com.example.growwtest.domain.Resource
import com.example.growwtest.util.Constants
import com.example.growwtest.util.hide
import com.example.growwtest.util.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

@AndroidEntryPoint
class FilmsListingFragment : Fragment() {

    private var _binding: FragmentFilmsListingBinding? = null
    private val binding get() = _binding
    private lateinit var filmAdapter: FilmAdapter
    private val viewModel: FilmListingViewModel by viewModels()
    private var character: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilmsListingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        character = arguments?.getString(Constants.ARGS.ARG_CHARACTER)
        setupRv()
        character?.let {
            binding?.tvTitleFilmListing?.text = "Films by $it"
            viewModel.getFilms(it)
        }
        viewModel.films.onEach {
            when(it) {
                is Resource.Success -> {
                    viewModel.nextPage = it.data?.nextPage ?: -1
                    filmAdapter.differ.submitList(it.data?.films)
                    binding?.pbFilmListing?.hide()
                    if(it.data?.films?.isEmpty() == true) {
                        binding?.tvEmptyMessage?.show()
                    }
                    if(it.data?.films?.isEmpty() == true && it.data.nextPage != null) {
                        character?.let { character ->
                            viewModel.getFilms(character)
                        }
                    }
                }
                is Resource.Error -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    binding?.pbFilmListing?.hide()
                }
                is Resource.Loading -> {
                    binding?.pbFilmListing?.show()
                }
            }
        }.flowOn(Dispatchers.Main).launchIn(lifecycleScope)
    }

    private fun setupRv() {
        filmAdapter = FilmAdapter()
        binding?.rvFilmListing?.apply {
            adapter = filmAdapter
            val gridLayoutManager = GridLayoutManager(context, 2)
            layoutManager = gridLayoutManager
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = gridLayoutManager.itemCount
                    val lastVisibleItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition()
                    if (totalItemCount - lastVisibleItemPosition < 2) {
                        character?.let {
                            viewModel.getFilms(it)
                        }
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}