package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor(staticName = "of")
@Data
public class City {
	@NonNull
	private Long id;

	@NonNull
	private String name;

	@NonNull
	private Integer population;
}
