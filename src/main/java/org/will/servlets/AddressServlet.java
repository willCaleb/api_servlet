package org.will.servlets;

import org.will.annotation.RequestMapping;
import org.will.context.Context;
import org.will.model.dto.AddressDTO;
import org.will.model.entity.Address;
import org.will.model.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(path = "/address")
public class AddressServlet extends AbstractServlet<Address, AddressDTO>{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = Context.getUser();

        super.doPost(req, resp);
    }
}
