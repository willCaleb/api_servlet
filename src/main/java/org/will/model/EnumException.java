package org.will.model;

import lombok.Getter;

@Getter
public enum EnumException {

    USER_NAME_EXISTS("Já existe um usuário cadastrado com esse username"),
    USER_MANDATORY_USERNAME_PASSWORD("Os campos usuário e senha são obrigatórios!"),
    SAVE_ERROR("Não foi possível incluir os dados."),
    LOGIN_PASSWORD_NOT_PROVIDED("Senha é obrigatório"),
    USER_NOT_FOUND("Usuario nao encontrado"),
    INVALID_CREDENTIALS("Usuario ou senha invalidos");


    final String value;

    EnumException(String value) {
        this.value = value;
    }
}
