package com.example.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.model.City;
import com.example.service.CityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@SpringBootTest
public class CitiesControllerTest extends ParentTest {

	@MockBean
	private CityService cityService;

	@Test
	public void getCities() throws Exception {
		// given
		String response = "[{ 'id': 1, 'name': 'Bratislava', 'population': 432000}," +
		                  "{ 'id': 2, 'name': 'Budapest', 'population': 1759000}]";

		// when
		List<City> cities = new ArrayList<City>();
		cities.add(new City(1L, "Bratislava", 432000));
		cities.add(new City(2L, "Budapest", 1759000));
		when(cityService.findAll()).thenReturn(cities);

		ResultActions result =
				mockMvc.perform(get("/api/1/cities")
		                .contentType(MediaType.APPLICATION_JSON)
		                .accept(MediaType.APPLICATION_JSON))
				       .andDo(print());

		// then
		result.andExpect(status().isOk())
		      .andDo(document.document(
				      responseFields(
						      subsectionWithPath("[]").description("도시 전체 리스트"),
						      fieldWithPath("[].id").description("도시의 id"),
						      fieldWithPath("[].name").description("도시 이름"),
						      fieldWithPath("[].population").description("도시 인구")
				      )))
		      .andExpect(content().json(response));
	}

	@Test
	public void getCity() throws Exception {
		// given
		String response = "{'id': 1,'name':'Bratislava','population':432000}";

		// when
		when(cityService.get(1L)).thenReturn(Optional.of(new City(1L, "Bratislava", 432000)));

		ResultActions result =
				mockMvc.perform(get("/api/1/city/{id}", 1L)
						                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				       .andDo(print());

		// then
		result.andExpect(status().isOk())
		      .andDo(document.document(
				      pathParameters(
						      parameterWithName("id").description("도시의 id")),
				      responseFields(
						      fieldWithPath("id").description("도시의 id"),
						      fieldWithPath("name").description("도시 이름"),
						      fieldWithPath("population").description("도시 인구")
						      )))
		      .andExpect(jsonPath("id", is(notNullValue())))
		      .andExpect(content().json(response));
	}

	@Test
	public void addCity() throws Exception {
		// given
		String response = "{'added':true}";

		// when
		when(cityService.add(new City(10L, "Bratislava", 432000))).thenReturn(true);

		City city10 = new City(10L, "Bratislava", 432000);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(city10);

		ResultActions result =
				mockMvc.perform(post("/api/1/city")
						                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
						                .content(requestJson))
				       .andDo(print());

		// then
		result.andExpect(status().isOk())
		      .andExpect(jsonPath("added", is(notNullValue())))
		      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		      .andDo(document.document(
				      responseFields(
						      fieldWithPath("added").description("도시 추가에 대한 결과 값(true/false)")
				      )))
		      .andExpect(content().json(response));
	}

	@Test
	public void deleteCity() throws Exception {
		// given
		String response = "{'deleted':true}";
		cityService.add(new City(10L, "Bratislava", 432000));

		// when
		ResultActions result =
				mockMvc.perform(delete("/api/1/city/{id}", 10L)
						                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				       .andDo(print());

		// then
		result.andExpect(status().isOk())
		      .andExpect(jsonPath("deleted", is(notNullValue())))
		      .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		      .andDo(document.document(
				      responseFields(
						      fieldWithPath("deleted").description("도시 삭제에 대한 결과 값(true/false)")
				      )))
		      .andExpect(content().json(response));
	}
}