package com.senla.worklog.reminder.employee.adapter.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "Region", description = "Representation of employee's region value object")
public class RegionDto {
    @Schema(description = "Region uuid", example = "ac7bc9b7-1515-4d57-a825-5001a83f2023")
    private UUID id;

    @Schema(description = "Region name", example = "Georgia")
    private String name;

    @Override
    public String toString() {
        return "RegionDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegionDto regionDto = (RegionDto) o;

        if (!Objects.equals(id, regionDto.id)) return false;
        return Objects.equals(name, regionDto.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
