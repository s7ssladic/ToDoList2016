package eu.execom.todolistgrouptwo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import eu.execom.todolistgrouptwo.R;
import eu.execom.todolistgrouptwo.api.wrapper.UserDAOWrapper;
import eu.execom.todolistgrouptwo.model.dto.UserRegistrationDTO;
import eu.execom.todolistgrouptwo.model.dto.UserRegistrationErrorDTO;

@EActivity(R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    @Bean
    UserDAOWrapper userDAOWrapper;

    @ViewById
    EditText confirmPassword;

    @ViewById
    EditText username;

    @ViewById
    EditText password;

    @EditorAction(R.id.password)
    @Click
    void register() {
        final String username = this.username.getText().toString();
        final String password = this.password.getText().toString();
        final String confirmPassword = this.confirmPassword.getText().toString();
        if (confirmPassword.equals(password)) {
            final UserRegistrationDTO user = new UserRegistrationDTO(username, password, confirmPassword);
            registerUser(user);
        } else {
            showRegisterErrorPassword();
        }
    }

    @Background
    void registerUser(UserRegistrationDTO user) {
        final UserRegistrationErrorDTO userCreatedError = userDAOWrapper.create(user);

        if (userCreatedError == null) {
            login(user.getEmail(), user.getPassword());
        } else {
            showRegisterError(userCreatedError);
        }
    }

    @UiThread
    void login(String username, String password) {
        final Intent intent = new Intent();
        intent.putExtra("username", username);
        intent.putExtra("password", password);

        setResult(RESULT_OK, intent);
        finish();
    }

    @UiThread
    void showRegisterError(UserRegistrationErrorDTO userRegistrationErrorDTO) {
        UserRegistrationErrorDTO.ModelStateDTO ms = userRegistrationErrorDTO.getModelState();
        if (ms != null) {
            String[] errors = ms.getNizError();
            if (errors != null)
                for (String error : errors) {
                    if (error.startsWith("Email"))
                        username.setError(error);
                }
        }

        Toast.makeText(this,
                userRegistrationErrorDTO.getMessage(),
                Toast.LENGTH_SHORT)
                .show();

    }

    @UiThread
    void showRegisterErrorPassword() {
        confirmPassword.setError("Password don't match");
    }
}
