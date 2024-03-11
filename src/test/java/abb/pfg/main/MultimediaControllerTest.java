package abb.pfg.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.abb.pfg.backend.rest.MultimediaController;

@WebMvcTest(MultimediaController.class)
public class MultimediaControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
    @WithMockUser(authorities="ADMIN")
	void getAuthenticatedUser_AdminRole_Success() throws Exception {
		 Authentication authentication = new TestingAuthenticationToken("admin@jobs4students.es", "password1234", "ADMIN");
		 SecurityContextHolder.getContext().setAuthentication(authentication);
		 String auth = "{\"authorities\":[{\"authority\":\"ADMIN\"}],\"details\":null,\"authenticated\":true,\"credentials\":\"password\",\"principal\":\"user\",\"name\":\"user\"}";
		 mockMvc.perform(MockMvcRequestBuilders.get("/test"))
         .andExpect(MockMvcResultMatchers.status().isOk())
         .andExpect(MockMvcResultMatchers.content().string(auth));
	}

}
