package org.will.exception;

import org.eclipse.jetty.server.handler.ErrorHandler;
import org.will.Utils.GsonBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

public class CustomErrorHandler extends ErrorHandler {

    @Override
    protected void handleErrorPage(HttpServletRequest request, Writer writer, int code, String message) throws IOException {
        writer.write(GsonBuilder.getGjon().toJson(Map.of("Bad request: ", message, "Status: ", code)));
    }
}