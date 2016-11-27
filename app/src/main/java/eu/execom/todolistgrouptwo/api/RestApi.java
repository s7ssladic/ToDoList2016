package eu.execom.todolistgrouptwo.api;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Header;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;

import eu.execom.todolistgrouptwo.constant.ApiConstants;
import eu.execom.todolistgrouptwo.model.dto.TokenContainerDTO;

/**
 * Created by Alex on 11/27/16.
 */
@Rest(rootUrl = ApiConstants.ROOT_URL, converters = {GsonHttpMessageConverter.class,
        FormHttpMessageConverter.class})
public interface RestApi {

    @Header(name = "Content-Type", value = "application/x-www-form-urlencoded")
    @Post(value = ApiConstants.LOGIN_PATH)
    TokenContainerDTO login(@Body LinkedMultiValueMap<String, String> accountInfo);
}
