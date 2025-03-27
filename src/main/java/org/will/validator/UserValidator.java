package org.will.validator;

import org.will.Utils.StringUtils;
import org.will.model.EnumException;
import org.will.model.entity.User;
import org.will.repository.UserRepository;
import org.will.repository.impl.UserRepositoryImplImpl;

public class UserValidator {

    public static void validateRequiredFields(User user) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            throw new RuntimeException(EnumException.USER_MANDATORY_USERNAME_PASSWORD.getValue());
        }
    }

    public static void validateInsert(User user) {
        validateRequiredFields(user);
        validateDuplicatedUsername(user);
    }

    private static void validateDuplicatedUsername(User user) {
        UserRepository userRepository = new UserRepositoryImplImpl(User.class);

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException(EnumException.USER_NAME_EXISTS.getValue());
        }
    }
}
