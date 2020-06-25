package com.pengdst.amikomcare.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentLoginBinding;
import com.pengdst.amikomcare.datas.constants.ApiConstant;
import com.pengdst.amikomcare.datas.models.DokterModel;
import com.pengdst.amikomcare.listeners.LoginCallback;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.preferences.SessionUtil;
import com.pengdst.amikomcare.ui.viewmodels.DokterViewModel;
import com.pengdst.amikomcare.ui.viewmodels.LoginViewModel;
import com.pengdst.amikomcare.ui.viewstates.LoginViewState;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static android.util.Log.d;
import static android.util.Log.e;
import static android.view.View.GONE;
import static com.pengdst.amikomcare.ui.viewmodels.LoginViewModel.RC_SIGN_IN;

public class LoginFragment extends BaseMainFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "LoginFragment";

    private FragmentLoginBinding binding;

    private LoginViewModel viewModelLogin;

    private Fragment parentFragment;

    private SessionDokter session;

    private InputMethodManager imm;

    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;
    GoogleSignInOptions gso;

    void signInGoogle() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(ApiConstant.INSTANCE.getDefault_web_client_id())
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    void signOut() {
        viewModelLogin.logout();
    }

    public void navigate(int target) {
        NavController navController = NavHostFragment.findNavController(parentFragment);
        navController.navigate(target);
    }

    private void handleResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Google Sign In was successful, authenticate with Firebase
            viewModelLogin.firebaseAuthWithGoogle(getActivity(), account.getIdToken());

            e(TAG, account.getEmail().trim());
            viewModelLogin.signIn(account.getEmail());
        } catch (ApiException e) {
            d("handleRequest", e.toString());
        }
    }

    private void login(String email, String password) {
        viewModelLogin.login(email, password);
    }

    private void observeViewModel() {

        viewModelLogin.observeUser().observe(getViewLifecycleOwner(), new Observer<LoginViewState>() {
            @Override
            public void onChanged(LoginViewState loginViewState) {
                loading(loginViewState.getLoading());
                if (loginViewState.isSucces()){

                    FirebaseUser user = viewModelLogin.checkCurrentUser();

                    NavController navController = NavHostFragment.findNavController(parentFragment);
                    if (Objects.requireNonNull(navController.getCurrentDestination()).getId() == R.id.loginFragment) {
                        session.login(Objects.requireNonNull(loginViewState.getData()));
                        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);
                        navigate(R.id.action_loginFragment_to_homeFragment);
                    }
                    shortToast("Success Login");
                }
                else {
                    shortToast("Wrong Password or Email");
                    e(TAG, "onChanged: "+loginViewState.getError());
                }
            }
        });

    }

    private void initViewModel() {
        viewModelLogin = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void initVariable() {
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        parentFragment = getParentFragment();
        session = SessionDokter.init(getContext());
        auth = FirebaseAuth.getInstance();
    }

    private void setupBinding(View view) {
        binding = FragmentLoginBinding.bind(view);
    }

    private void setupListener() {

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(
                    binding.etEmailUser.getText().toString(),
                    binding.etPassword.getText().toString()
                );
            }
        });

        binding.btLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInGoogle();
            }
        });

    }

    private void loading(boolean active) {
        binding.progressLogin.setEnabled(active);
        if (active){
            binding.progressLogin.setVisibility(View.VISIBLE);
        }
        else {
            binding.progressLogin.setVisibility(GONE);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (session.isLogin()){
            navigate(R.id.action_loginFragment_to_homeFragment);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        e(TAG, "onActivityResult() returned: "+result.getSignInAccount().getAccount());
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleResult(task);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariable();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (session.isLogin()){
            navigate(R.id.action_loginFragment_to_homeFragment);
        }

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setupBinding(view);
        initViewModel();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        observeViewModel();
        setupListener();

    }
}