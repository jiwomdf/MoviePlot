package com.katilijiwo.movieplot.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.katilijiwo.movieplot.R
import com.katilijiwo.movieplot.databinding.LayoutErrorBottomsheetBinding

abstract class BaseFragment<DB: ViewDataBinding>(
        private val layout: Int
) : Fragment() {

    protected lateinit var binding: DB

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding.lifecycleOwner = this
        setListener()
        return binding.root
    }

    protected open fun setListener() {

    }

    open fun hideKeyboard(activity: Activity) {
        val inputManager: InputMethodManager = activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = activity.currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    protected fun showError(
            title: String = resources.getString(R.string.text_error_title),
            description: String = resources.getString(R.string.text_error_dsc),
            isCancelable: Boolean = true,
            isFinish: Boolean = false,
            callback: (() -> Unit)? = null) {

        val dialog = BottomSheetDialog(requireContext())
        val dialogBinding: LayoutErrorBottomsheetBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.layout_error_bottomsheet, null, true
        )
        dialog.apply {
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setCancelable(isCancelable)
            setContentView(dialogBinding.root)
            show()
        }
        dialogBinding.apply {
            tvTitle.text = title
            tvDesc.text = description
        }
        dialogBinding.btnOk.setOnClickListener {
            dialog.hide()
            callback?.invoke()
            if (isFinish)
                findNavController().popBackStack()
        }
    }

}