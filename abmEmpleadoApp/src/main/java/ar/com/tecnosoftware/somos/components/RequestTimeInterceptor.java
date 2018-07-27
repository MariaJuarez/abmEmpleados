package ar.com.tecnosoftware.somos.components;

import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestTimeInterceptor extends HandlerInterceptorAdapter {

    private static final org.apache.juli.logging.Log LOG = LogFactory.getLog(RequestTimeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    throws Exception {
            request.setAttribute("tiempoInicio",System.currentTimeMillis());
            return true;
        }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
        long tiempoInicio = (long) request.getAttribute("tiempoInicio");
        LOG.info("REQUEST URL: '".concat(request.getRequestURL().toString()).concat("' TIEMPO TOTAL: '")+(System.currentTimeMillis()-tiempoInicio)+"ms");
    }





    }

