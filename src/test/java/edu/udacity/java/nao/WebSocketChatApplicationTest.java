package edu.udacity.java.nao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.udacity.java.nano.WebSocketChatApplication;
import edu.udacity.java.nano.chat.WebSocketChatServer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSocketChatApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebSocketChatApplicationTest {

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@MockBean
	private WebSocketChatServer webSocketChatServer;

	private MockMvc mockMvc;

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	
	/**
	 * Application context test case.
	 */
	@Test
	public void contextLoads() {
	}

	/**
	 * Controller test for login controller
	 * @throws Exception
	 */
	@Test
	public void loginTest() throws Exception {

		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("login"));

	}

	/**
	 * Controller test for failure/exception in login controller.
	 * @throws Exception
	 */
	@Test
	public void loginFailTest() throws Exception {

		mockMvc.perform(get("/abcd")).andExpect(status().isNotFound());

	}

	/**
	 * Controller test for index controller
	 * @throws Exception
	 */
	@Test
	public void indexTest() throws Exception {

		mockMvc.perform(get("/index")).andExpect(status().isOk()).andExpect(view().name("chat"));
	}

	/**
	 * Controller test for failure/exception in index controller.
	 * @throws Exception
	 */
	@Test
	public void indexFailTest() throws Exception {

		mockMvc.perform(get("/indexes")).andExpect(status().isNotFound());
	}
	
	/**
	 * login controller second test.
	 */
	@Test
	public void loginSecondTest() {
		 assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
	                String.class)).contains("login");
		
	}
	
	/**
	 * chat controller second test.
	 */
	@Test
	public void chatSecondTest() {
		 assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/index",
	                String.class)).contains("chat");
		
	}	
}