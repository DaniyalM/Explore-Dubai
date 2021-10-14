package com.app.dubaiculture.ui.postLogin.plantrip.steps.step4

import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import com.app.dubaiculture.R
import com.app.dubaiculture.databinding.FragmentAddDurationBinding
import com.app.dubaiculture.ui.base.BaseBottomSheetFragment

class AddDurationFragment : BaseBottomSheetFragment<FragmentAddDurationBinding>() {
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddDurationBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.view = this
    }

    fun onDropDownClicked(view: View) {

        showMenu(view, R.menu.duration_menu)

    }

    fun onDaySelected() {

        binding.btnDay.icon=ContextCompat.getDrawable(requireContext(),R.drawable.ic_day_selected)
//        binding.btnDay.iconTint =
//            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white_900))
        binding.btnDay.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.purple_650)
        )
//        binding.btnNight.iconTint =
//            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray_800))
//        binding.btnNight.setIconResource(R.drawable.ic_night)
        binding.btnNight.icon=ContextCompat.getDrawable(requireContext(),R.drawable.ic_night)

        binding.btnNight.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.white_900)
        )
    }

    fun onNightSelected() {

//        binding.btnNight.setIconResource(R.drawable.ic_night_selected)
//        binding.btnNight.iconTint =
//            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white_900))
        binding.btnNight.icon=ContextCompat.getDrawable(requireContext(),R.drawable.ic_night_selected)

        binding.btnNight.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.purple_650)
        )
//        binding.btnDay.setIconResource(R.drawable.ic_day)
//        binding.btnDay.iconTint =
//            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.gray_800))
        binding.btnDay.icon=ContextCompat.getDrawable(requireContext(),R.drawable.ic_day)

        binding.btnDay.setBackgroundColor(
            ContextCompat.getColor(requireContext(), R.color.white_900)
        )
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(context, v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.option_1 -> {
                    Toast.makeText(context, menuItem.title, Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

}