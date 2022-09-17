package com.example.itmonster.security;

import com.example.itmonster.repository.StackOfMemberRepository;
import com.example.itmonster.security.filter.FormLoginFilter;
import com.example.itmonster.security.filter.JwtAuthFilter;
import com.example.itmonster.security.jwt.HeaderTokenExtractor;
import com.example.itmonster.security.provider.FormLoginAuthProvider;
import com.example.itmonster.security.provider.JWTAuthProvider;
import com.example.itmonster.security.provider.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
@EnableGlobalMethodSecurity(securedEnabled = true) // @Secured 어노테이션 활성화
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthProvider jwtAuthProvider;
    private final HeaderTokenExtractor headerTokenExtractor;
    private final CustomUserDetailService customUserDetailService;

    public WebSecurityConfig(
            JWTAuthProvider jwtAuthProvider,
            HeaderTokenExtractor headerTokenExtractor,
        CustomUserDetailService customUserDetailService
    ) {
        this.jwtAuthProvider = jwtAuthProvider;
        this.headerTokenExtractor = headerTokenExtractor;
        this.customUserDetailService = customUserDetailService;
    }

    @PostConstruct
    public void start() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(formLoginAuthProvider())
                .authenticationProvider(jwtAuthProvider);
    }

    @Override
    public void configure(WebSecurity web) {
        // h2-console 사용에 대한 허용 (CSRF, FrameOptions 무시)
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf()
                .disable()
                .authorizeRequests()

                .antMatchers(HttpMethod.OPTIONS).permitAll() // preflight 대응
                .antMatchers("/auth/**").permitAll(); // /auth/**에 대한 접근을 인증 절차 없이 허용(로그인 관련 url)
        // 특정 권한을 가진 사용자만 접근을 허용해야 할 경우, 하기 항목을 통해 가능

        http
                .cors()
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.NEVER);    //구글 OAuth2Use정보를 가져오려면 STATELESS가 아닌 NEVER 사용해야함
        http.headers().frameOptions().sameOrigin();
        /*
         * 1.
         * UsernamePasswordAuthenticationFilter 이전에 FormLoginFilter, JwtFilter 를 등록합니다.
         * FormLoginFilter : 로그인 인증을 실시합니다.
         * JwtFilter       : 서버에 접근시 JWT 확인 후 인증을 실시합니다.
         */
        http
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .anyRequest()
                .permitAll();
//                .and()
//                // [로그아웃 기능]
//                .logout()
//                // 로그아웃 요청 처리 URL
//                .logoutUrl("/api/logout")
//                .logoutSuccessUrl("/")
//                .permitAll();

        // google 로그인 화면 처리
        http.oauth2Login()
            .userInfoEndpoint()
            .userService(customUserDetailService)   // 유저 정보를 받아온다.
            .and()
            .defaultSuccessUrl("/oauth/google")       // 받아온 유저정보로 jwt생성
            .failureUrl("/fail");
    }

    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationManager());
        formLoginFilter.setFilterProcessesUrl("/api/members/login"); //로그인이 진행 됨
        formLoginFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler());
        formLoginFilter.afterPropertiesSet();
        return formLoginFilter;
    }

    @Bean
    public FormLoginSuccessHandler formLoginSuccessHandler() {
        return new FormLoginSuccessHandler();
    }

    @Bean
    public FormLoginAuthProvider formLoginAuthProvider() {
        return new FormLoginAuthProvider(encodePassword());
    }

    private JwtAuthFilter jwtFilter() throws Exception {
        List<String> skipPathList = new ArrayList<>();

        // Static 정보 접근 허용
        skipPathList.add("GET,/images/**");
        skipPathList.add("GET,/css/**");

        // 소셜로그인 skipPathList
        skipPathList.add("GET,/oauth/**");
        skipPathList.add("GET,/oauth/kakao/**");
        skipPathList.add("GET,/oauth/naver/**");

        //회원가입하기, 로그인 관련 skipPathList
        skipPathList.add("POST,/api/members/signup");  //회원가입
        skipPathList.add("POST,/api/members/checkId");  //username 중복 체크
        skipPathList.add("POST,/api/members/checkNickname");  //nickname 중복 체크

        //main 화면
        skipPathList.add("GET,/api/monster/month"); // main 화면

        //검색 , 필터링
        skipPathList.add("GET,/api/quests");
        skipPathList.add("GET,/api/quests/**");

        // 웹소켓 접속
        skipPathList.add("GET,/socket/**");

        // null 401 - 토큰검증안된 것도 401
        // 검증이 동작하는가 안하는가

        //무중단 배포 확인용
        skipPathList.add("GET,/health");

        skipPathList.add("GET,/auth/login");



//----------아래는 그대로----------
        skipPathList.add("GET,/basic.js");
        skipPathList.add("GET,/favicon.ico");

        FilterSkipMatcher matcher = new FilterSkipMatcher(
                skipPathList,
                "/**"
        );

        JwtAuthFilter filter = new JwtAuthFilter(
                matcher,
                headerTokenExtractor
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}