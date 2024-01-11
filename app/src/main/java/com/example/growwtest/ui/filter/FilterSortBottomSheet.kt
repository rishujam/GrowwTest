package com.example.growwtest.ui.filter

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.view.indices
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.growwtest.R
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
import com.google.android.material.chip.Chip

/*
 * Created by Sudhanshu Kumar on 10/01/24.
 */

class FilterSortBottomSheet(
    private val listener: BottomSheetListener
) : BottomSheetDialogFragment() {

    private lateinit var binding: SheetFilterSortBinding
//    private lateinit var filterAdapter: FilterAdapter
    private lateinit var attrAdapter: FilterSortAttrAdapter
    private var argData: CharacterFeatures? = null

    private var selectedFilters = mutableMapOf<String, CharacterFeature>()
    private var selectedFeature: Pair<Int, CharacterFeature>? = null

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

        argData?.let { features ->
            for(i in features.features.indices) {
                val feature = features.features[i]
                val chip = Chip(context)
                chip.text = feature.id
                chip.chipBackgroundColor = ContextCompat.getColorStateList(requireContext(), R.color.white)
                chip.setTextColor(ContextCompat.getColor(requireContext(),R.color.dark_grey))
                chip.setOnClickListener {
                    chip.chipBackgroundColor = ContextCompat.getColorStateList(requireContext(), R.color.selected_bg)
                    chip.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                    selectedFeature?.let {
                        val oldSelectedChip = binding.chipFilters[it.first] as? Chip
                        oldSelectedChip?.chipBackgroundColor =
                            ContextCompat.getColorStateList(requireContext(), R.color.white)
                        oldSelectedChip?.setTextColor(ContextCompat.getColor(requireContext(),R.color.dark_grey))
                    }
                    val position = binding.chipFilters.indexOfChild(it as? Chip)
                    attrAdapter.submitData(argData?.features?.getOrNull(position)?.attributes)
                    selectedFeature = Pair(i, feature)
                }
                binding.chipFilters.addView(chip)
            }
        }

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
        attrAdapter = FilterSortAttrAdapter()
        binding.rvFeatureAttrSheet.apply {
            adapter = attrAdapter
            layoutManager = LinearLayoutManager(context)
        }
        attrAdapter.setOnItemClickListener { attr, position ->
            selectedFeature?.let { selectedFeature ->
                val currSelectedFeature = argData?.features?.indexOf(selectedFeature.second)
                argData?.features?.getOrNull(currSelectedFeature ?: -1)
                    ?.attributes
                    ?.getOrNull(position)?.isChecked = true
                val updatedAttrs = mutableListOf<FeatureAttr>()
                selectedFilters.getOrDefault(selectedFeature.second.id, null)?.let {
                    updatedAttrs.addAll(it.attributes)
                }
                updatedAttrs.add(attr)
                when (selectedFeature.second) {
                    is CharacterFeature.SkinColor -> {
                        selectedFilters[selectedFeature.second.id] = CharacterFeature.SkinColor(
                            selectedFeature.second.id,
                            updatedAttrs
                        )
                    }

                    is CharacterFeature.Gender -> {
                        selectedFilters[selectedFeature.second.id] = CharacterFeature.Gender(
                            selectedFeature.second.id,
                            updatedAttrs
                        )
                    }

                    is CharacterFeature.EyeColor -> {
                        selectedFilters[selectedFeature.second.id] = CharacterFeature.EyeColor(
                            selectedFeature.second.id,
                            updatedAttrs
                        )
                    }

                    is CharacterFeature.HairColor -> {
                        selectedFilters[selectedFeature.second.id] = CharacterFeature.HairColor(
                            selectedFeature.second.id,
                            updatedAttrs
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}