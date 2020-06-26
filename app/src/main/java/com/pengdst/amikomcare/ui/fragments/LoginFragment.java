package com.pengdst.amikomcare.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.pengdst.amikomcare.R;
import com.pengdst.amikomcare.databinding.FragmentLoginBinding;
import com.pengdst.amikomcare.datas.models.DokterModel;
import com.pengdst.amikomcare.preferences.SessionDokter;
import com.pengdst.amikomcare.ui.viewmodels.LoginViewModel;
import com.pengdst.amikomcare.datas.viewstates.LoginViewState;
import com.pengdst.amikomcare.utils.GoogleSignInUtil;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.util.Log.e;
import static android.view.View.GONE;
import static com.pengdst.amikomcare.utils.GoogleSignInUtil.RC_SIGN_IN;

public class LoginFragment extends BaseMainFragment {

    private static final String TAG = "LoginFragment";

    private FragmentLoginBinding binding;

    private LoginViewModel viewModelLogin;

    private SessionDokter session;

    private GoogleSignInUtil signInUtil;

    private Fragment parentFragment;
    private InputMethodManager imm;

    void signInGoogle() {

        Intent signInIntent = signInUtil.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    public void navigate(int target) {
        NavController navController = NavHostFragment.findNavController(parentFragment);
        navController.navigate(target);
    }

    private void handleResult(Task<GoogleSignInAccount> completedTask) {
        signInUtil.handleSignInResult(completedTask);
        try {
            GoogleSignInAccount account = signInUtil.getAccount();

            assert account != null;
            firebaseAuthWithGoogle(account.getIdToken());
            viewModelLogin.signIn(Objects.requireNonNull(account.getEmail()));
        } catch (Exception e) {
            e(TAG, "handleResult() called with: ApiException = [" + e + "]");
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        signInUtil.signInWithGoogle(idToken)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:success " + task.toString());
                        if (task.isSuccessful()) {
                            FirebaseUser user = signInUtil.getCurrentUser();
                            Log.d(TAG, "signInWithCredential:success " + user);
                        } else {
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e(TAG, "onFailure() called with: Exception = [" + e + "]");
                    }
                });
    }

    private void login(String email, String password) {
        viewModelLogin.signIn(email, password);
    }

    private void observeViewModel() {

        viewModelLogin.observeUser().observe(getViewLifecycleOwner(), new Observer<LoginViewState>() {
            @Override
            public void onChanged(LoginViewState loginViewState) {
                loading(loginViewState.getLoading());
                if (loginViewState.isSucces()) {

                    @SuppressWarnings("unused")
                    FirebaseUser user = signInUtil.getCurrentUser();

                    DokterModel dokterModel = (DokterModel) loginViewState.getData();

                    session.set(Objects.requireNonNull(dokterModel));
                    imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0);

                    shortToast(loginViewState.getMessage());

                    navigate(R.id.action_loginFragment_to_homeFragment);
                } else {
                    signInUtil.signOut();
                    shortToast("Wrong Password or Email");
                }
            }
        });

    }

    private void initViewModel() {
        viewModelLogin = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void initVariable() {
        imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        parentFragment = getParentFragment();
        session = SessionDokter.init(getContext());
        signInUtil = GoogleSignInUtil.Companion.init(requireActivity());
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
        if (active) {
            binding.progressLogin.setVisibility(View.VISIBLE);
        } else {
            binding.progressLogin.setVisibility(GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == RC_SIGN_IN) {
                signInUtil.prepareHandleResult(data);
                @SuppressWarnings("unused") GoogleSignInResult
                        result = signInUtil.getResult();
                Task<GoogleSignInAccount> task = signInUtil.task;
                handleResult(task);
            }
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

        if (session.checkIsLogin()) {
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