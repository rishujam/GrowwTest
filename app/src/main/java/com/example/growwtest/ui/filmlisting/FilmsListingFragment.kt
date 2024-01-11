package com.example.growwtest.ui.filmlisting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.growwtest.databinding.FragmentFilmsListingBinding
import com.example.growwtest.domain.Resource
import com.example.growwtest.util.Constants
import com.example.growwtest.util.hide
import com.example.growwtest.util.show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

class FilmsListingFragment : Fragment() {

    private var _binding: FragmentFilmsListingBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: FilmAdapter
    private val viewModel: FilmListingViewModel by viewModels()

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
        val character = arguments?.getString(Constants.ARGS.ARG_CHARACTER)
        character?.let {
            viewModel.getFilms(it)
        }
        viewModel.films.onEach {
            when(it) {
                is Resource.Success -> {
                    binding?.pbFilmListing?.hide()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}