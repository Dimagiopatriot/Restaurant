package controller.filter;

import model.entity.User;
import util.constant.Parameters;
import util.constant.Paths;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public abstract class CommonVisitorFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);

        User user = null;

        if(session != null) {
            user = ((User) req.getSession().getAttribute(Parameters.USER));
        }

        if(isNotAllowed(req, user)) {
            resp.sendRedirect(Paths.MAIN);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    abstract boolean isNotAllowed(HttpServletRequest req, User user);
}