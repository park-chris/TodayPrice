package com.crystal.todayprice.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.crystal.todayprice.R
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.CustomDialog
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.ktx.functions
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginActivity: BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var googleSignInClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        baseBinding.contentLayout.addView(binding.root)
        auth = Firebase.auth

    }

    override fun onResume() {
        super.onResume()

        setupEvent()
    }

    private fun setupEvent() {
        binding.googleSignInButton.setOnClickListener {
            googleLogin()
        }
        binding.kakaoSignInButton.setOnClickListener {
            kakaoLogin()
        }
    }

    private fun googleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInClient = googleSignInClient?.signInIntent

        binding.progressBar.visibility = View.VISIBLE
        childForResult.launch(signInClient)
    }

    private val childForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                RESULT_OK -> {
                    val resultData = Auth.GoogleSignInApi.getSignInResultFromIntent(result.data!!)
                    val account = resultData?.signInAccount
                    firebaseAuthWithGoogle(account)
                }
                RESULT_CANCELED -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {

        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateUI(auth.currentUser)
//                    updateUserInfo()
                    binding.progressBar.visibility = View.GONE
                } else {
                    binding.progressBar.visibility = View.GONE
                    val alert = CustomDialog(this, null)
                    alert.start(getString(R.string.login_fail_info_title_dialog), getString(R.string.login_fail_info_message_dialog) + task.exception, getString(R.string.ok), null, true)
                }
            }
    }


    private fun kakaoLogin() {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    loginWithKaKaoAccount(this)
                } else if (token != null) {
                    getCustomToken(token.accessToken)
                }
            }
        } else {
            loginWithKaKaoAccount(this)
        }
    }


    private fun loginWithKaKaoAccount(context: Context) {
        UserApiClient.instance.loginWithKakaoAccount(context) { token: OAuthToken?, error: Throwable? ->
            if (token != null) {
                getCustomToken(token.accessToken)
            }
        }
    }

    private fun getCustomToken(accessToken: String) {

        binding.progressBar.visibility = View.VISIBLE

        val functions: FirebaseFunctions = Firebase.functions("asia-northeast3")

        val data = hashMapOf(
            "token" to accessToken
        )

        functions
            .getHttpsCallable("kakaoCustomAuth")
            .call(data)
            .addOnCompleteListener { task ->
                try {
                    val result = task.result?.data as HashMap<*, *>
                    var mKey: String? = null
                    for (key in result.keys) {
                        mKey = key.toString()
                    }
                    val customToken = result[mKey!!].toString()

                    firebaseAuthWithKakao(customToken)
                } catch (e: RuntimeExecutionException) {
                    val alert = CustomDialog(this, null)
                    alert.start(getString(R.string.login_fail_info_title_dialog), getString(R.string.login_fail_info_message_dialog) + e.message, getString(R.string.ok), null, true)
                }
            }
    }

    private fun firebaseAuthWithKakao(customToken: String) {
        auth.signInWithCustomToken(customToken).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                updateUI(auth.currentUser)
//                updateUserInfo()
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
    }


    private fun updateUI(user: FirebaseUser?) {}

}