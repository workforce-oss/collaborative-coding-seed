package com.testbed.accountapi.persistence;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.testbed.accountapi.model.Account.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class AccountEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "customer_id")
	private Long customerId;
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "name")
	private String name;
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "number")
	private String number;
	
	@NotNull
	@Column(name = "open_date")
	private ZonedDateTime openDate;
	
	@Column(name = "close_date")
	private ZonedDateTime closeDate;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;
}
