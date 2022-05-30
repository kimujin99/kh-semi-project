package kim.ujin.semipjt.config;

import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

public class RestMatcher implements RequestMatcher {

    // 매치
    private AntPathRequestMatcher matcher;

    // 생성자
    public RestMatcher(String url) {
        super();
        matcher = new AntPathRequestMatcher(url);
    }

    // URL 일치조건
    @Override
    public boolean matches(HttpServletRequest request) {
        // GET이면 CSRG를 확인안함
        if("GET".equals(request.getMethod())) {
            return false;
        }
        // 특정 URL에 해당하는 경우 CSRF확인안함
        if(matcher.matches(request)) {
            return false;
        }

        return true;
    }

}
