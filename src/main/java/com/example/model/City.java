package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {
	@NonNull
	private Long id;

	@NonNull
	private String name;

	@NonNull
	private Integer population;
}
