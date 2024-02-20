package com.crystal.todayprice.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.crystal.todayprice.R
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.CustomDialog
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.Inquiry
import com.crystal.todayprice.databinding.ActivityInquiryBinding
import com.crystal.todayprice.repository.InquiryRepositoryImpl
import com.crystal.todayprice.util.FirebaseCallback
import com.crystal.todayprice.util.OnDialogListener
import com.crystal.todayprice.util.Result
import com.crystal.todayprice.viewmodel.InquiryViewModel

class InquiryActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityInquiryBinding

    private val inquiryViewModel: InquiryViewModel by viewModels {
        InquiryViewModel.InquiryViewModelFactory(InquiryRepositoryImpl())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInquiryBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)
        setSpinner()
    }

    override fun onResume() {
        super.onResume()
        setupEvent()
    }

    private fun setupEvent() {
        binding.submitButton.setOnClickListener {
            if (userDataManager.user == null) {
                Toast.makeText(this, getString(R.string.inquiry_info_user), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val title = binding.titleEditText.text.toString()
            val type = binding.typeSpinner.selectedItem.toString()
            val content = binding.contentEditText.text.toString()
            if (title.isEmpty()) {
                Toast.makeText(this, getString(R.string.inquiry_title_hint), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (content.isEmpty()) {
                Toast.makeText(this, getString(R.string.inquiry_content_hint), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            baseBinding.progressBar.visibility = View.VISIBLE

            val inquiry = Inquiry(
                type = type,
                content = content,
                title = title,
                userId = userDataManager.user!!.id
            )

            inquiryViewModel.addInquiry(inquiry, object : FirebaseCallback {
                override fun onResult(result: Result) {
                    if (result == Result.SUCCESS) {
                        val dialog = CustomDialog(this@InquiryActivity, object : OnDialogListener {
                            override fun onOk() {
                                finish()
                            }
                        })
                        dialog.start(null,getString(R.string.inquiry_info_submit_success), null, rightButtonText = getString(R.string.report_dialog_button), isCanceled = false )
                    } else {
                        Toast.makeText(
                            this@InquiryActivity,
                            getString(R.string.inquiry_info_submit_fail),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    baseBinding.progressBar.visibility = View.GONE
                }
            })
        }
    }

    private fun setSpinner() {
        val items = resources.getStringArray(R.array.inquiry_spinner_type)
        val adapter = ArrayAdapter(this, R.layout.item_spinner, items)
        adapter.setDropDownViewResource(R.layout.item_spinner)
        binding.typeSpinner.adapter = adapter
    }

}