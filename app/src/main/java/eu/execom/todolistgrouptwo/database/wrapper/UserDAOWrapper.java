package eu.execom.todolistgrouptwo.database.wrapper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.androidannotations.annotations.EBean;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.HttpClientErrorException;

import eu.execom.todolistgrouptwo.api.RestApi;
import eu.execom.todolistgrouptwo.model.dto.UserRegistrationDTO;
import eu.execom.todolistgrouptwo.model.dto.UserRegistrationErrorDTO;

@EBean
public class UserDAOWrapper {

    private static final String TAG = UserDAOWrapper.class.getSimpleName();

    @RestService
    RestApi restApi;

    public UserRegistrationErrorDTO create(UserRegistrationDTO user) {
        try {
            restApi.registerUser(user);
        } catch (HttpClientErrorException e) {
            Gson gb = new GsonBuilder().create();
            return gb.fromJson(e.getResponseBodyAsString(), UserRegistrationErrorDTO.class);
        }

        return null;
    }
}
