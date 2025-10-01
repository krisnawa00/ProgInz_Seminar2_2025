
package lv.venta.config;

import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lv.venta.service.impl.MyUserDetailsManagerServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	//piemērs ar kodā iekodētiem lietotājiem
	/*@Bean
	public UserDetailsManager createSomeUsers() {
		
		PasswordEncoder passEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		String encodedPassword = passEncoder.encode("1234");
		UserDetails user1 = User.builder().username("karina").password(encodedPassword).authorities("USER_PROF").build();
		
		
		UserDetails user2 = User.builder().username("admin").
				password(passEncoder.encode("admin")).authorities("ADMIN").build();
		
		UserDetails user3 = User.builder().username("janis").
				password(passEncoder.encode("4321")).authorities("USER_STUD").build();
		
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(user1, user2, user3);
		return manager;
	
	}
	*/
	
	//piemērs ar lietotājiem no datubāzes
	@Bean
	public MyUserDetailsManagerServiceImpl loadMyUserDetailsManager() {
		return new MyUserDetailsManagerServiceImpl();
	}
	
	@Bean
	public DaoAuthenticationProvider loadDaoAuthProvider() {
		PasswordEncoder passEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		MyUserDetailsManagerServiceImpl service =  loadMyUserDetailsManager();
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passEncoder);
		provider.setUserDetailsService(service);
		
		return provider;	
		
	}
	
	
	
	@Bean
	public SecurityFilterChain configureUrlsSecurity(HttpSecurity http) throws Exception {
	
		http.authorizeHttpRequests(auth -> auth
		.requestMatchers("/h2-console").permitAll()
		.requestMatchers("/h2-console/**").permitAll()
		.requestMatchers("/crud/professor/all").permitAll()//visiem piekļuve, arī neregistrētiem
		.requestMatchers("/crud/professor/all/**").permitAll()//katra infdividuālā apsniedzeja lapu vara redzēt visi, arī nereģistreie
		.requestMatchers("/crud/professor/create").hasAuthority("ADMIN")//izveidot professoru lapai var piekļut tikai admin
		.requestMatchers("/crud/professor/update/**").hasAuthority("ADMIN")//rediģēt profesoru varēs tikai admins
		.requestMatchers("/crud/professor/delete/**").hasAuthority("ADMIN")//arī dzēst profesoru var tikai admins
		.requestMatchers("/filter/grades/student/**").hasAnyAuthority("USER_PROF", "ADMIN")//redzet studentu atzīmes var jeblkurš profesors un arī admins
		.requestMatchers("/filter/courses/student/**").hasAnyAuthority("USER_STUD", "ADMIN")//redzet, kādi kursi ir studentam, var redzet studenti un arī admins
		.requestMatchers("/filter/courses/professor/**").hasAnyAuthority("USER_PROF", "USER_STUD", "ADMIN")//visi registrētie lietotaji avr redzet, kadus kursus pasniedz konkrētais pasniedzējs
		.requestMatchers("/filter/grades/avg/course/**").hasAnyAuthority("USER_PROF", "ADMIN")//vidējās atzīmes vajadzetu rerdzēt tikai pasniedzejiem un adminam
		.requestMatchers("/filter/students/failed").hasAnyAuthority("USER_PROF", "ADMIN")//tikai apsniedzeji un admins redz nesekmīgos studentus
		.requestMatchers("/filter/professors/degree/phd").hasAnyAuthority("USER_PROF", "USER_STUD", "ADMIN"));//visi reģistrētie lietotaji varēs redzēt pasniedzejus ar doktora grādu
		
		http.formLogin(auth -> auth.permitAll());
		
		http.csrf(auth -> auth.disable());
		
		http.headers(frame->frame.frameOptions(option->option.disable()));
		return http.build();
	
	
	}
	
	

}
