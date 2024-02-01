package com.crystal.todayprice.component

import android.app.Dialog
import android.content.Context
import android.view.Window
import androidx.core.view.isVisible
import com.crystal.todayprice.databinding.CustomDialogBinding
import com.crystal.todayprice.util.OnDialogListener

class CustomDialog(context: Context, private val onDialogListener: OnDialogListener?) {
    private val dialog = Dialog(context)
    private lateinit var binding: CustomDialogBinding

    fun start(title: String?, message: String, leftButtonText: String, rightButtonText: String?, isCanceled: Boolean) {
        dialog.setTitle(title)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding =  CustomDialogBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(isCanceled)

        if (title != null) {
            binding.titleTextView.text = title
        } else {
            binding.titleTextView.isVisible = false
        }

        if (rightButtonText == null) {
            binding.rightButton.isVisible = false
        } else {
            binding.rightButton.text = rightButtonText
        }

        binding.messageTextView.text = message
        binding.leftButton.text = leftButtonText

        binding.leftButton.setOnClickListener {
            dialog.dismiss()
        }

        binding.rightButton.setOnClickListener {
            onDialogListener?.onOk()
            dialog.dismiss()
        }

        dialog.show()
    }

}