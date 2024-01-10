package com.example.growwtest.ui.filter

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.growwtest.databinding.SheetFilterSortBinding
import com.example.growwtest.domain.model.CharacterFeatures
import com.example.growwtest.ui.action.CharacterListingAction
import com.example.growwtest.util.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

class FilterSortBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding : SheetFilterSortBinding
    private lateinit var filterAdapter: FilterAdapter
    private lateinit var attrAdapter: FilterSortAttrAdapter
    private var argData: CharacterFeatures? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = SheetFilterSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRv()
        val argsAction = arguments?.getParcelable<CharacterListingAction>(Constants.ARGS.ARG_SHEET_ACTION)
        argData = arguments?.getParcelable<CharacterFeatures>(Constants.ARGS.ARG_SHEET_DATA)
        when(argsAction) {
            is CharacterListingAction.Sort -> {
                binding.tvTitleSheet.text = "Sort"
                binding.tvDesSheet.text = "Select feature to sort"
            }
            is CharacterListingAction.Filter -> {
                binding.tvTitleSheet.text = "Filter"
                binding.tvDesSheet.text = "Select feature to filter"
            }
            else -> {}
        }
        argData?.let { filterAdapter.differ.submitList(it.features) }

        binding.btnApply.setOnClickListener {

        }
    }

    private fun setUpRv() {
        filterAdapter = FilterAdapter()
        binding.rvFilterFeatureSheet.apply {
            adapter = filterAdapter
            layoutManager = LinearLayoutManager(context)
        }
        attrAdapter = FilterSortAttrAdapter()
        binding.rvFeatureAttrSheet.apply {
            adapter = attrAdapter
            layoutManager = LinearLayoutManager(context)
        }
        filterAdapter.setOnItemClickListener {
            argData?.let { nArgData ->
                filterAdapter.differ.submitList(nArgData.features)
                attrAdapter.differ.submitList(it.attributes)
            }
        }
        attrAdapter.setOnItemClickListener {

        }
    }
}