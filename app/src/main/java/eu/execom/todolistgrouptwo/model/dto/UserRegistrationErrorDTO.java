package eu.execom.todolistgrouptwo.model.dto;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by SSladic on 12/5/2016.
 */

public class UserRegistrationErrorDTO {

    public class ModelStateDTO {
        @SerializedName("")
        private String[] nizError;

        @Override
        public String toString() {
            return "ModelStateDTO{" +
                    "niz=" + Arrays.toString(nizError) +
                    '}';
        }

        public String[] getNizError() {
            return nizError;
        }
    }

    String message;

    ModelStateDTO modelState;

    public String getMessage() {
        return message;
    }

    public ModelStateDTO getModelState() {
        return modelState;
    }
}
