package com.crystal.todayprice.ui

import android.content.Context
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.crystal.todayprice.R
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.CustomDialog
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.databinding.ActivityReportBinding
import com.crystal.todayprice.util.FirebaseCallback
import com.crystal.todayprice.util.OnDialogListener
import com.crystal.todayprice.util.Result

private const val TAG = "TestLog"

class ReportActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityReportBinding
    private var reportTitle: String? = null
    private lateinit var reviewId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReportBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

        reviewId = intent.getStringExtra(REVIEW_ID) ?: ""

        setupEvent()
    }

    private fun setupEvent() {

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            hideKeyboard(binding.addEditText)
            val radioButton = findViewById<RadioButton>(checkedId)
            reportTitle = radioButton.text.toString()
            binding.submitButton.setTextColor(ContextCompat.getColor(this, R.color.white) )
            binding.submitButton.setBackgroundColor(ContextCompat.getColor(this, R.color.highlight) )
        }

        binding.addEditText.setOnFocusChangeListener { _, isFocus ->
            if (isFocus) binding.radioGroup.check(R.id.button6)
        }

        binding.submitButton.setOnClickListener {
            submitReport()
        }
    }

    private fun submitReport() {
        reportTitle ?: return

        val uid = userDataManager.user!!.id
        val content = binding.addEditText.text.toString()

        userViewModel.submitReport(reviewId, uid, reportTitle!!, content, object : FirebaseCallback {
            override fun onResult(result: Result) {
                if (result == Result.SUCCESS) {
                    val dialog = CustomDialog(this@ReportActivity, object : OnDialogListener {
                        override fun onOk() { finish() }
                    })
                    dialog.start(title =  getString(R.string.report_dialog_title), message =  getString(R.string.report_dialog_message), rightButtonText = getString(R.string.report_dialog_button), leftButtonText = null, isCanceled = false )
                } else {
                    Toast.makeText(this@ReportActivity, getString(R.string.report_error_toast), Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    companion object {
        const val REVIEW_ID = "review_id"
    }
}