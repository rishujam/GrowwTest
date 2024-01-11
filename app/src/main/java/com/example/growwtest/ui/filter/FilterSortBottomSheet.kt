package com.example.growwtest.ui.filter

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.growwtest.databinding.SheetFilterSortBinding
import com.example.growwtest.domain.model.CharacterFeature
import com.example.growwtest.domain.model.CharacterFeatures
import com.example.growwtest.ui.action.CharacterListingAction
import com.example.growwtest.ui.filter.model.FeatureAttr
import com.example.growwtest.ui.lsitener.BottomSheetListener
import com.example.growwtest.util.Constants
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

class FilterSortBottomSheet(
    private val listener: BottomSheetListener
) : BottomSheetDialogFragment() {

    private lateinit var binding: SheetFilterSortBinding
    private lateinit var filterAdapter: FilterAdapter
    private lateinit var attrAdapter: FilterSortAttrAdapter
    private var argData: CharacterFeatures? = null

    private var selectedFilters = mutableMapOf<String, CharacterFeature>()
    private var selectedFeature: CharacterFeature? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SheetFilterSortBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener { it ->
            val d = it as BottomSheetDialog
            val bottomSheet =
                d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
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
        val argsAction =
            arguments?.getParcelable<CharacterListingAction>(Constants.ARGS.ARG_SHEET_ACTION)
        argData = arguments?.getParcelable(Constants.ARGS.ARG_SHEET_DATA)
        when (argsAction) {
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
            listener.onApplyClickListener(
                CharacterFeatures(
                    selectedFilters.values.toList()
                )
            )
            dismiss()
        }

        binding.btnClear.setOnClickListener {
            listener.onClearClickListener()
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
        filterAdapter.setOnItemClickListener { character, position ->
            selectedFeature = character
            filterAdapter.differ.submitList(null)
            filterAdapter.differ.submitList(argData?.features)
            attrAdapter.submitData(argData?.features?.getOrNull(position)?.attributes)
        }
        attrAdapter.setOnItemClickListener { attr, position ->
            selectedFeature?.let { selectedFeature ->
                val currSelectedFeature = argData?.features?.indexOf(selectedFeature)
                argData?.features?.getOrNull(currSelectedFeature ?: -1)
                    ?.attributes
                    ?.getOrNull(position)?.isChecked = true
                val updatedAttrs = mutableListOf<FeatureAttr>()
                selectedFilters.getOrDefault(selectedFeature.id, null)?.let {
                    updatedAttrs.addAll(it.attributes)
                }
                updatedAttrs.add(attr)
                when (selectedFeature) {
                    is CharacterFeature.SkinColor -> {
                        selectedFilters[selectedFeature.id] = CharacterFeature.SkinColor(
                            selectedFeature.id,
                            updatedAttrs
                        )
                    }

                    is CharacterFeature.Gender -> {
                        selectedFilters[selectedFeature.id] = CharacterFeature.Gender(
                            selectedFeature.id,
                            updatedAttrs
                        )
                    }

                    is CharacterFeature.EyeColor -> {
                        selectedFilters[selectedFeature.id] = CharacterFeature.EyeColor(
                            selectedFeature.id,
                            updatedAttrs
                        )
                    }

                    is CharacterFeature.HairColor -> {
                        selectedFilters[selectedFeature.id] = CharacterFeature.HairColor(
                            selectedFeature.id,
                            updatedAttrs
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}