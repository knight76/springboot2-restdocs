package com.example.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

import org.junit.Before;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class ParentTest {

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	@Autowired
	WebApplicationContext context;

	RestDocumentationResultHandler document;

	MockMvc mockMvc;

	@Before
	public void setUp() {
		this.document = document(
				"{class-name}/{method-name}",
				preprocessResponse(prettyPrint())
		);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
		                              .apply(documentationConfiguration(this.restDocumentation))
		                              .alwaysDo(document)
		                              .build();
	}
}
