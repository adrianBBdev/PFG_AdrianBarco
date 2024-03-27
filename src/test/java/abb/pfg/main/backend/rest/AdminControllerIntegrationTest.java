package abb.pfg.main.backend.rest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.abb.pfg.backend.commons.Constants;
import com.abb.pfg.backend.dtos.AdministratorDto;
import com.abb.pfg.backend.entities.Administrator;
import com.abb.pfg.backend.rest.AdminController;
import com.abb.pfg.backend.service.AdminService;

import abb.pfg.main.backend.utils.TestUtils;
import abb.pfg.main.backend.utils.TestValueFactory;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = { AdminController.class })
public class AdminControllerIntegrationTest{

	@Autowired
	AdminController underTest;
	@MockBean
	AdminService adminService;
	MockMvc mockMvc;
	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
	}
	@Test
	void testGetAllAdmins_MVC() throws Exception {
		// given
		var page = 0;
		var size = 3;
		// when
		String actual = mockMvc.perform(get(Constants.Controllers.Admins.PATH)
				.param("page", String.valueOf(page))
				.param("size", String.valueOf(size))
				.accept("application/json"))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse().getContentAsString();
		// then
		assertThat(actual).isEqualTo(TestUtils.readTestFile("AdminController/getAllAdmins.json"));
	}
	@Test
	void testGetAdmin_MVC() throws Exception {
		// given
		var id = TestValueFactory.getValueForType(Long.class, "id");
		// when
		String actual = mockMvc.perform(get(Constants.Controllers.Admins.PATH + "/{id}", id)
				.accept("application/json"))
			.andExpect(status().isOk())
			.andReturn()
			.getResponse().getContentAsString();
		// then
		assertThat(actual).isEqualTo(TestUtils.readTestFile("AdminController/getAdmin.json"));
	}
	@Test
	void testCreateAdmin_MVC() throws Exception {
		// given
		AdministratorDto adminDto = TestValueFactory.getValueForType(AdministratorDto.class, "adminDto");
		// when
		mockMvc.perform(post(Constants.Controllers.Admins.PATH)
				.content(TestUtils.objectToJson(adminDto))
				.accept("application/json"))
		.andExpect(status().isOk())
		.andReturn()
		.getResponse().getContentAsString();
		// then
		// TODO check for expected side effect (i.e. service call, changed parameter or exception thrown)
		// verify(mock).methodcall();
		// assertThat(TestUtils.objectToJson(param)).isEqualTo(TestUtils.readTestFile("someMethod/ParamType_updated.json"));
		// assertThrows(SomeException.class, () -> underTest.someMethod());
	}
	@Test
	void testUpdateAdmin_MVC() throws Exception {
		// given
		AdministratorDto adminDto = TestValueFactory.getValueForType(AdministratorDto.class, "adminDto");
		// when
		mockMvc.perform(put("Constants.Controllers.Admins.PATH")
				.content(TestUtils.objectToJson(adminDto))
				.accept("application/json"))
		.andExpect(status().isOk())
		.andReturn()
		.getResponse().getContentAsString();
		// then
		// TODO check for expected side effect (i.e. service call, changed parameter or exception thrown)
		// verify(mock).methodcall();
		// assertThat(TestUtils.objectToJson(param)).isEqualTo(TestUtils.readTestFile("someMethod/ParamType_updated.json"));
		// assertThrows(SomeException.class, () -> underTest.someMethod());
	}
	@Test
	void testDeleteAdmins_MVC() throws Exception {
		// given
		var admins = Arrays.asList(TestValueFactory.getValueForType(Administrator.class, "admins"));
		// when
		mockMvc.perform(delete("Constants.Controllers.Admins.PATH")
				.content(TestUtils.objectToJson(admins))
				.accept("application/json"))
		.andExpect(status().isOk())
		.andReturn()
		.getResponse().getContentAsString();
		// then
		// TODO check for expected side effect (i.e. service call, changed parameter or exception thrown)
		// verify(mock).methodcall();
		// assertThat(TestUtils.objectToJson(param)).isEqualTo(TestUtils.readTestFile("someMethod/ParamType_updated.json"));
		// assertThrows(SomeException.class, () -> underTest.someMethod());
	}
}