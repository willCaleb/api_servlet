package org.example.exception;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.example.Utils.Utils;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomErrorHandler extends ErrorHandler {

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) {

        try {
            Throwable throwable = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
            if (Utils.isNotEmpty(throwable)) {
                throwable.printStackTrace();  // Exibe o erro no console
            }
            super.handle(target, baseRequest, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}