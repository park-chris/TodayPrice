package com.crystal.todayprice.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.crystal.todayprice.R
import com.crystal.todayprice.component.BaseActivity
import com.crystal.todayprice.component.CustomDialog
import com.crystal.todayprice.component.ToolbarType
import com.crystal.todayprice.util.Result
import com.crystal.todayprice.component.TransitionMode
import com.crystal.todayprice.data.User
import com.crystal.todayprice.data.UserProvider
import com.crystal.todayprice.databinding.ActivityLoginBinding
import com.crystal.todayprice.util.FirebaseCallback
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity(ToolbarType.ONLY_BACK, TransitionMode.HORIZON) {

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
        binding.usePolicyTextView.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java).apply {
                putExtra(WebViewActivity.WEB_VIEW_URL, "https://park-chris.github.io/pages/use_policy_market.html")
            }
            startActivity(intent)
        }
        binding.policyTextView.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java).apply {
                putExtra(WebViewActivity.WEB_VIEW_URL, "https://park-chris.github.io/pages/data_policy_market.html")
            }
            startActivity(intent)
        }
    }

    private fun googleLogin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val signInClient = googleSignInClient?.signInIntent

        baseBinding.progressBar.visibility = View.VISIBLE
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
                    baseBinding.progressBar.visibility = View.GONE
                }
            }
        }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {

        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.let {
                        updateUser(it, UserProvider.GOOGLE)
                    }
                } else {
                    baseBinding.progressBar.visibility = View.GONE
                    val alert = CustomDialog(this, null)
                    alert.start(
                        getString(R.string.login_fail_info_title_dialog),
                        getString(R.string.login_fail_info_message_dialog) + task.exception,
                        getString(R.string.ok),
                        null,
                        true
                    )
                }
            }
    }

    private fun kakaoLogin() {
        baseBinding.progressBar.visibility = View.VISIBLE
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    baseBinding.progressBar.visibility = View.GONE
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
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
            } else {
                baseBinding.progressBar.visibility = View.GONE
                return@loginWithKakaoAccount
            }
        }
    }

    private fun getCustomToken(accessToken: String) {
        baseBinding.progressBar.visibility = View.VISIBLE
        val functions: FirebaseFunctions = Firebase.functions("asia-northeast3")
        val data = hashMapOf(
            "token" to accessToken
        )

        functions
            .getHttpsCallable("getKakaoCustomAuth")
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
                    baseBinding.progressBar.visibility = View.GONE
                    val alert = CustomDialog(this, null)
                    alert.start(
                        getString(R.string.login_fail_info_title_dialog),
                        getString(R.string.login_fail_info_message_dialog) + e.message,
                        null,
                        getString(R.string.ok),
                        true
                    )
                }
            }
    }

    private fun firebaseAuthWithKakao(customToken: String) {
        auth.signInWithCustomToken(customToken).addOnCompleteListener { result ->
            if (result.isSuccessful) {
                auth.currentUser?.let {
                    updateUser(it, UserProvider.KAKAO)
                }
            } else {
                baseBinding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun updateUser(user: FirebaseUser, provider: UserProvider) = CoroutineScope(Dispatchers.Main).launch {

        val dbUser = userViewModel.getUser(user.uid)

        if (dbUser != null) {
            userDataManager.user = dbUser
            baseBinding.progressBar.visibility = View.GONE
            finish()
        } else {
            val newUser = User(
                user.uid,
                user.displayName ?: "",
                user.email ?: "",
                provider = provider,

            )
            userViewModel.createUser(newUser, object : FirebaseCallback {
                override fun onResult(result: Result) {
                    when (result) {
                        Result.FAIL
                        -> {
                            baseBinding.progressBar.visibility = View.GONE
                        }
                        Result.SUCCESS -> {
                            userDataManager.user = newUser
                            baseBinding.progressBar.visibility = View.GONE
                            finish()
                        }
                    }
                }
            })
        }
    }
}