package com.katilijiwo.movieplot.ui.introduction

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.katilijiwo.movieplot.R
import com.katilijiwo.movieplot.base.BaseFragment
import com.katilijiwo.movieplot.databinding.FragmentIntroductionBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class IntroductionFragment : BaseFragment<FragmentIntroductionBinding>(
    R.layout.fragment_introduction,
), View.OnClickListener, TextWatcher {

    var counter = 0
    private val BEFORE_VALIDATION = 1
    private val AFTER_VALIDATION = 2
    private val viewModel: IntroductionViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setName("")
        binding.viewModel = viewModel
        binding.clInputName.apply {
            alpha = 0f
            animate().alpha(1f).duration = 1000
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(counter < 1){
                    Toast.makeText(requireContext(), getString(R.string.press_back_again_close_app), Toast.LENGTH_SHORT).show()
                    counter++
                } else{
                    activity?.finish()
                }
            }
        })

    }

    override fun setListener() {
        super.setListener()
        binding.etName.addTextChangedListener(this)
        binding.btnContinue.setOnClickListener(this)
        binding.ivCheckName.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_continue -> {
                if (viewModel.checkName()) {
                    findNavController().navigate(
                        IntroductionFragmentDirections
                            .actionIntroductionFragmentToFragmentDashboard(viewModel.getName().value!!)
                    )
                } else {
                    setupComponentVisibility(BEFORE_VALIDATION)
                    showValidationError()
                }
            }
            R.id.iv_check_name -> {
                if (viewModel.checkName()) {
                    hideKeyboard(requireActivity())
                    setupComponentVisibility(AFTER_VALIDATION)
                    displayContinue()
                } else {
                    setupComponentVisibility(BEFORE_VALIDATION)
                    showValidationError()
                }
            }
        }
    }

    private fun setupComponentVisibility(status: Int) {
        when(status){
            BEFORE_VALIDATION -> {
                binding.etName.visibility = View.VISIBLE
                binding.ivCheckName.visibility = View.VISIBLE
                binding.tvValidation.visibility = View.VISIBLE
                binding.tvName.visibility = View.GONE
                binding.clContinue.visibility = View.GONE
            }
            AFTER_VALIDATION -> {
                binding.etName.visibility = View.GONE
                binding.ivCheckName.visibility = View.GONE
                binding.tvValidation.visibility = View.INVISIBLE
                binding.tvName.visibility = View.VISIBLE
                binding.clContinue.visibility = View.VISIBLE
            }
        }
    }

    private fun showValidationError() {
        binding.tvValidation.setTextColor(
            ResourcesCompat.getColor(
                resources,
                R.color.red_500,
                null
            )
        )
        Handler(Looper.getMainLooper()).postDelayed({
            binding.tvValidation.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.red_200,
                    null
                )
            )
        }, 500)
    }

    private fun displayContinue() {
        binding.clContinue.alpha = 0f
        binding.clContinue.animate().alpha(1f).duration = 1000
        binding.tvContinue.text = "Nice to meet you ${viewModel.getName().value}!\nNow you can continue."
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        when(s.hashCode()){
            binding.etName.text.hashCode() -> {
                changeIcCheck()
            }
        }
    }

    private fun changeIcCheck() {
        if(viewModel.getName().value!!.isNotEmpty())
            binding.ivCheckName.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_check_circle_green_24,
                    null
                )
            )
        else
            binding.ivCheckName.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_check_circle_grey_24,
                    null
                )
            )
    }
}