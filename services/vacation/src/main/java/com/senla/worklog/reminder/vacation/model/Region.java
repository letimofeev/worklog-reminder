package com.senla.worklog.reminder.vacation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "regions", uniqueConstraints = {
        @UniqueConstraint(name = "uc_region_name", columnNames = {"name"})
})
public class Region {
    @Id
    private UUID id;

    @NotNull
    private String name;
}
