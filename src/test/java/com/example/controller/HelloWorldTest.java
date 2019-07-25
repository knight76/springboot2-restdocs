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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@SpringBootTest
public class HelloWorldTest extends ParentTest {

	@Test
	public void helloWorld() throws Exception {
		// given
		String response = "Welcome, Hello World, Samuel";

		// when
		ResultActions result = mockMvc.perform(get("/helloworld").param("name", "Samuel"))
		            .andDo(print());

		// then
		result.andExpect(status().isOk())
		            .andExpect(content().string(response));
	}

	@Test
	public void helloWorldJson() throws Exception {
		// given
		String response = "{\"result\":\"Welcome, Hello World, Samuel\"}";

		// when
		ResultActions result =
				mockMvc.perform(get("/helloworld/json")
								.contentType(MediaType.APPLICATION_JSON)
								.accept(MediaType.APPLICATION_JSON)
								.param("name", "Samuel"))
				            .andDo(print());

		// then
		result.andExpect(status().isOk())
		      .andDo(document.document(
		                      requestParameters(
		                      		parameterWithName("name").description("Name")),
							  responseFields(
							  		fieldWithPath("result").description("결과"))))
		      .andExpect(jsonPath("result", is(notNullValue())))
		      .andExpect(content().string(response));
	}
}
