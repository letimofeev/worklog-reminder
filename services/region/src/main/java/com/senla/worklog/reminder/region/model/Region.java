package com.senla.worklog.reminder.region.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "regions", uniqueConstraints = {
        @UniqueConstraint(name = "uc_region_name", columnNames = {"name"})
})
public class Region {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    @NotBlank(message = "Region name must not be blank")
    private String name;
}
