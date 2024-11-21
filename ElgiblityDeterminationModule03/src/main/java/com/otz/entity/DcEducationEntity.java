package com.otz.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="DC_EDUCATION")
@Data
public class DcEducationEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer educationId;
	private Integer caseNo;
	@Column(length=40)
	private String highestQlfy;
	private Integer passOutYear;
}
