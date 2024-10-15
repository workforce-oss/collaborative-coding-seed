package com.testbed.accountapi.model;
import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.bind.annotation.PutMapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Account {
	
	@NotNull(groups = {PutMapping.class})
	private Long id;

	@NotNull
	private Long customerId;
	
	@NotNull
	@Size(min = 1, max = 50)
	private String name;
	
	@NotNull
	@Size(min = 1, max = 50)
	private String number;
	
	@NotNull
	private ZonedDateTime openDate;
	
	private ZonedDateTime closeDate;
	
	@NotNull
	private Status status;
	
	public enum Status {
		OPEN, CLOSED
	}
}
