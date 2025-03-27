package org.will.servlets;

import org.will.Utils.PasswordUtils;
import org.will.Utils.Utils;
import org.will.annotation.RequestMapping;
import org.will.converter.Converter;
import org.will.model.dto.UserDTO;
import org.will.model.entity.User;
import org.will.repository.UserRepository;
import org.will.repository.impl.UserRepositoryImplImpl;
import org.will.validator.UserValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping(path = "/user")
public class UserServlet extends AbstractServlet<User, UserDTO>{

    private final UserRepository userRepository = new UserRepositoryImplImpl(User.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = Converter.toEntity(fromRequestToDTO(request), User.class);

        UserValidator.validateInsert(user);

        user.setPassword(PasswordUtils.encode(user.getPassword()));

        user = userRepository.save(user);

        write(response, user);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer id = getId(request);

        if (Utils.isNotEmpty(id)) {
            User user = userRepository.findById(id);
            write(response, user);
            return;
        }

        List<User> all = userRepository.findAll();

        write(response, all);
    }

}
