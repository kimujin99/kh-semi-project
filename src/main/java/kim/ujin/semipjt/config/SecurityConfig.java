package kim.ujin.semipjt.config;

import kim.ujin.semipjt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private DataSource dataSource;

   private static final String USER_SQL = "SELECT"
           + " email_id,"
           + " password,"
           + " true"
           + " FROM"
           + " user"
           + " WHERE"
           + " email_id=?";

   private static final String ROLE_SQL = "SELECT"
           + " email_id,"
           + " role"
           + " FROM"
           + " user"
           + " WHERE"
           + " email_id=?";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/webjars/∗∗", "/scripts/**");
    }

    @Override
    public void configure(HttpSecurity security) throws Exception {
        security
                .csrf().disable().headers().frameOptions().disable()

                .and()
                .authorizeRequests()
                .antMatchers("/login", "/signup", "/css/**","/webjars/**").permitAll()
                .antMatchers("/rest/**").permitAll()
                .antMatchers("/user-board").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user-detail").hasAuthority("ROLE_ADMIN")
                .antMatchers("/user-detail/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .failureUrl("/login")
                .usernameParameter("email_id")
                .passwordParameter("password")
                .defaultSuccessUrl("/board", true)

                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(USER_SQL)
                .authoritiesByUsernameQuery(ROLE_SQL)
                .passwordEncoder(passwordEncoder());

    }



}
