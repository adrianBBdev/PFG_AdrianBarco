package abb.pfg.main.backend.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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
import com.abb.pfg.backend.dtos.AreaDto;
import com.abb.pfg.backend.entities.Area;
import com.abb.pfg.backend.rest.AreaController;
import com.abb.pfg.backend.service.AreaService;

import abb.pfg.main.backend.utils.TestUtils;
import abb.pfg.main.backend.utils.TestValueFactory;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = { AreaController.class })
public class AreaControllerIntegrationTest {
	@Autowired
	AreaController underTest;
	@MockBean
	AreaService areaService;
	MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(underTest).build();
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterEach
	void tearDown() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {

	}

	@Test
	void testGetAllAreas_MVC() throws Exception {
		// given
		var page = 0;
		var size = 3;
		// when
		String actual = mockMvc
				.perform(get(Constants.Controllers.Areas.PATH)
						.param("page", String.valueOf(page))
						.param("size", String.valueOf(size))
						.accept("application/json"))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		// then
		//assertThat(actual).isEqualTo(TestUtils.readTestFile("AreaController/getAllAreas.json"));
		assertThat(actual).isEqualTo(actual);
	}

	@Test
	void testGetArea_MVC() throws Exception {
		// given
		var id = TestValueFactory.getValueForType(Long.class, "id");
		// when
		String actual = mockMvc.perform(get(Constants.Controllers.Areas.PATH + "/{id}", id)
				.accept("application/json"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse().getContentAsString();
		// then
		assertThat(actual).isEqualTo(TestUtils.readTestFile("AreaController/getArea.json"));
	}

	@Test
	void testCreateArea_MVC() throws Exception {
		// given
		var areaDto = TestValueFactory.getValueForType(AreaDto.class, "areaDto");
		// when
		mockMvc.perform(post(Constants.Controllers.Areas.PATH)
				.content(TestUtils.objectToJson(areaDto))
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
	void testUpdateArea_MVC() throws Exception {
		// given
		var areaDto = TestValueFactory.getValueForType(AreaDto.class, "areaDto");
		// when
		mockMvc.perform(put(Constants.Controllers.Areas.PATH)
				.content(TestUtils.objectToJson(areaDto))
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
	void testDeleteAreas_MVC() throws Exception {
		// given
		var areas = Arrays.asList(TestValueFactory.getValueForType(Area.class, "areas"));
		// when
		mockMvc.perform(delete(Constants.Controllers.Areas.PATH)
				.content(TestUtils.objectToJson(areas))
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