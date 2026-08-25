package org.will.Constants;

import java.util.ResourceBundle;

public class Constants {

    public static final String PATH = "PATH";

    public static final String JETTY_SERVLET_DEFAULT_CHARSET = "org.eclipse.jetty.servlet.Default.charset";

    public static final String HTTP_HEADER_AUTHORIZATION = "Authorization";

    public static final String HTTP_HEADER_BEARER = "Bearer ";

    public static final String HTTP_HEADER_CONTENT_TYPE = "application/json";

    public static final String HTTP_HEADER_CHARACTER_ENCODING = "UTF-8";

    public static final String NOT_AUTHORIZED = """
                        {
                            "error": "Não autorizado"
                        }
                        """;
    public static final String NOT_ALLOWED = """
                        {
                            "error": "O usuário não tem permissão para acessar a funcionalidade"
                        }
                        """;

    public static String getSecret() {
        return ResourceBundle.getBundle("variables").getString("jwt.secret_key");
    }

}
