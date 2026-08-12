package org.will.filter;

import org.eclipse.jetty.http.pathmap.MatchedResource;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.will.Constants.Constants;
import org.will.Utils.StringUtils;
import org.will.annotation.NoAuth;
import org.will.annotation.Permission;
import org.will.auth.JwtUtils;
import org.will.context.Context;
import org.will.exception.CustomException;
import org.will.model.EnumException;
import org.will.model.entity.User;
import org.will.repository.UserRepository;
import org.will.repository.impl.UserRepositoryImplImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {

        try {
            HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

            String methodName = getMethodName(httpRequest);

            Class servletClass = getServletClass(servletRequest);

            Method method = servletClass.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

            if (method.isAnnotationPresent(NoAuth.class)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            String auth = httpRequest.getHeader(Constants.HTTP_HEADER_AUTHORIZATION);

            if (StringUtils.isEmpty(auth) || !auth.startsWith(Constants.HTTP_HEADER_BEARER)) {
                servletResponse.setContentType(Constants.HTTP_HEADER_CONTENT_TYPE);
                servletResponse.setCharacterEncoding(Constants.HTTP_HEADER_CHARACTER_ENCODING);

                servletResponse.getWriter().write(Constants.NOT_AUTHORIZED);

                return;
            }

            User contextUser = getContextUser(auth);

            if (method.isAnnotationPresent(Permission.class) && !contextUser.getRole().hasPermission(method.getDeclaredAnnotation(Permission.class).value())) {
                servletResponse.getWriter().write(Constants.NOT_ALLOWED);
                return;
            }
            Context.setUser(contextUser);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    private static User getContextUser(String auth) {

        UserRepository userRepository = new UserRepositoryImplImpl(User.class);

        String token = auth.replace("Bearer ", "");

        String usernameFromToken = JwtUtils.getUsernameFromToken(token);

        return userRepository.findByUsername(usernameFromToken).orElseThrow(() -> new CustomException(EnumException.USER_NOT_FOUND));

    }

    private static String getMethodName(HttpServletRequest httpRequest) {
        String method = httpRequest.getMethod();

        return "do" +
                method.substring(0, 1).toUpperCase() +
                method.substring(1).toLowerCase();
    }

    private Class getServletClass(ServletRequest servletRequest) throws ServletException {

        ServletContextHandler context = ServletContextHandler.getServletContextHandler(servletRequest.getServletContext());

        ServletHandler servletHandler = context.getServletHandler();

        MatchedResource<ServletHandler.MappedServlet> matchedServlet = servletHandler.getMatchedServlet(
                ((HttpServletRequest) servletRequest).getRequestURI()
        );

        ServletHandler.MappedServlet resource = matchedServlet.getResource();

        ServletHolder servletHolder = resource.getServletHolder();

        Servlet servlet = servletHolder.getServlet();

        return servlet.getClass();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
