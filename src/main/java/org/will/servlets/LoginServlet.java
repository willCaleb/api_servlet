package org.will.servlets;

import org.will.Utils.PasswordUtils;
import org.will.Utils.StringUtils;
import org.will.annotation.NoAuth;
import org.will.annotation.RequestMapping;
import org.will.auth.JwtUtils;
import org.will.context.Context;
import org.will.converter.Converter;
import org.will.exception.CustomException;
import org.will.model.EnumException;
import org.will.model.dto.UserDTO;
import org.will.model.entity.User;
import org.will.repository.UserRepository;
import org.will.repository.impl.UserRepositoryImplImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(path = "/login")
public class LoginServlet extends AbstractServlet<User, UserDTO>{

    private final UserRepository userRepository = new UserRepositoryImplImpl(User.class);

    @NoAuth
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = Converter.toEntity(fromRequestToDTO(request), User.class);

        User userManaged = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new CustomException(EnumException.USER_NOT_FOUND));

        validatePassword(user, userManaged);

        Context.setUser(userManaged);

        writeBean(response, JwtUtils.getUserLoginBean(userManaged));
    }

    private void validatePassword(User user, User userManaged) {
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new CustomException(EnumException.LOGIN_PASSWORD_NOT_PROVIDED);
        }
        if (!userManaged.getPassword().equals(PasswordUtils.encode(user.getPassword()))) {
            throw new CustomException(EnumException.INVALID_CREDENTIALS);
        }
    }
}
