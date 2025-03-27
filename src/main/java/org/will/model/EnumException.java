package org.will.model;

import lombok.Getter;

@Getter
public enum EnumException {

    USER_NAME_EXISTS("Já existe um usuário cadastrado com esse username"),
    USER_MANDATORY_USERNAME_PASSWORD("Os campos usuário e senha são obrigatórios!")
    ;


    final String value;

    EnumException(String value) {
        this.value = value;
    }
}
