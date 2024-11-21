package com.otz.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="Plane_category")
public class PlanCategory {
	@Id
	@SequenceGenerator(name="gen1",sequenceName = "category_seq",initialValue = 1,allocationSize = 1)
	@GeneratedValue(generator = "gen1",strategy =GenerationType.SEQUENCE)
	@Column(name="CATEGORY_ID")
	private Integer categoryId;
	@Column(name="CATEGORY_NAME",length = 30)
	private String categoryName;
	@Column(name="ACTIVE_SW",length = 15)
	private String activeSW="active";
	@Column(name="CREATED_DATE",updatable = false)
	private LocalDateTime careatedDate;
	@Column(name="UPDATED_DATE",updatable = true,insertable = false)
	private LocalDateTime updateDate;
	@Column(name="CREATED_BY",length = 20)
	private String createdBy;
	@Column(name="UPDATED_BY",length = 20)
	private String updatedBy;
	
}
