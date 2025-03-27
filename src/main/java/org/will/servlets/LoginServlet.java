package org.will.servlets;

import org.will.Utils.PasswordUtils;
import org.will.Utils.StringUtils;
import org.will.annotation.RequestMapping;
import org.will.converter.Converter;
import org.will.model.dto.UserDTO;
import org.will.model.entity.User;
import org.will.repository.UserRepository;
import org.will.repository.impl.UserRepositoryImplImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(path = "/login")
public class LoginServlet extends AbstractServlet<User, UserDTO>{

    private final UserRepository userRepository = new UserRepositoryImplImpl(User.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = Converter.toEntity(fromRequestToDTO(request), User.class);

        User userManaged = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("Usuario nao encontrado"));

        validatePassword(user, userManaged);
    }

    private void validatePassword(User user, User userManaged) {
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new RuntimeException("Senha é obrigatório");
        }
        if (!userManaged.getPassword().equals(PasswordUtils.encode(user.getPassword()))) {
            throw new RuntimeException("Usuario ou senha invalidos");
        }
    }
}
