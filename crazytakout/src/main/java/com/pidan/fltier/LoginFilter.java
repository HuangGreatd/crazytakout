package com.pidan.fltier;

import com.alibaba.fastjson.JSON;
import com.pidan.common.Ressult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

/**
 * @author 黄大头
 * @date 2023年04月22日 21:03
 */
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginFilter implements Filter {

    /**
     * 路径匹配器，支持通配符
     */
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //过滤器具体的处理逻辑如下:
        // 1、获取本次请求的URI
        String requestURI = request.getRequestURI();

        //定义不需要处理的路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };//2、判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        // 3、如果不需要处理，则直接放行'
        if (check){
            filterChain.doFilter(request, response);
            return;
        }
        //4、判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("employee") != null){
            filterChain.doFilter(request, response);
            return;
        }
        // 5、如果未登录则返回未登录结果
        response.getWriter().write(JSON.toJSONString(Ressult.error("NOTLGIN")));
        return;
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     *
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = ANT_PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
