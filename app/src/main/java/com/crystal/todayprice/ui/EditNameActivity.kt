package com.crystal.todayprice.ui


import android.os.Bundle
import android.widget.Toast
import com.crystal.todayprice.R
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.databinding.ActivityEditNameBinding
import com.crystal.todayprice.util.FirebaseCallback
import com.crystal.todayprice.util.Result

class EditNameActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityEditNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditNameBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        setupEvent()
    }

    private fun setupEvent() {
        binding.submitButton.setOnClickListener {
            val newName = binding.nameEditText.text.toString()
            if (newName.isNotEmpty() && userDataManager.user != null){
                val newUser = userDataManager.user!!.copy(name = newName)
                hideKeyboard(binding.nameEditText)
                userViewModel.updateUserName(newUser, object : FirebaseCallback {
                    override fun onResult(result: Result) {
                        if (result == Result.SUCCESS) {
                            Toast.makeText(this@EditNameActivity, getString(R.string.complete_edit_nickname), Toast.LENGTH_SHORT).show()
                            updateProfile(newUser)
                            finish()
                        }
                        if (result == Result.FAIL) {
                            Toast.makeText(this@EditNameActivity, getString(R.string.incomplete_edit_nickname), Toast.LENGTH_SHORT).show()
                        }
                    }

                })
            }
        }
        binding.layout.setOnClickListener {
            hideKeyboard(binding.nameEditText, false)
        }
    }

}