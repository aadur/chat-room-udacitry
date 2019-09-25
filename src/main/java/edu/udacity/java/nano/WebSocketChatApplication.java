package edu.udacity.java.nano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
@Slf4j
public class WebSocketChatApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketChatApplication.class, args);
	}

	/**
	 * Login Page
	 */
	@GetMapping("/")
	public ModelAndView login() {
		return new ModelAndView("login");
	}

	/**
	 * Chatroom Page
	 */
	@GetMapping("/index")
	public ModelAndView index(String username, HttpServletRequest request) throws UnknownHostException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("username", username);
		modelAndView.addObject("webSocketUrl", "ws://" + InetAddress.getLocalHost().getHostAddress() + ":"
				+ request.getServerPort() + request.getContextPath() + "/chat");
		modelAndView.setViewName("chat");
		return modelAndView;
	}
}
