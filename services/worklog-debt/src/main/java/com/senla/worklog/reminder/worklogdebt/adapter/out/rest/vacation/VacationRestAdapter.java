package com.senla.worklog.reminder.worklogdebt.adapter.out.rest.vacation;

import com.senla.worklog.reminder.annotation.DrivenAdapter;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.vacation.dto.EmployeeVacationsDto;
import com.senla.worklog.reminder.worklogdebt.adapter.out.rest.vacation.mapper.VacationsMapper;
import com.senla.worklog.reminder.worklogdebt.domain.model.EmployeeWorklogDebts;
import com.senla.worklog.reminder.worklogdebt.domain.model.ExcludedDay;
import com.senla.worklog.reminder.worklogdebt.domain.port.out.VacationRestPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

@DrivenAdapter
@RequiredArgsConstructor
public class VacationRestAdapter implements VacationRestPort {
    private final RestTemplate restTemplate;
    private final VacationRestProperties restProperties;
    private final VacationsMapper mapper;

    @Override
    public EmployeeWorklogDebts getVacationDays(EmployeeWorklogDebts worklogDebts, LocalDate dateFrom, LocalDate dateTo) {
        var vacationsDto  = getVacationsDto(worklogDebts, dateFrom, dateTo).getBody();
        var excludedDays = mapToExcludedDays(vacationsDto);
        return worklogDebts
                .setDateFrom(dateFrom)
                .setDateTo(dateTo)
                .setExcludedDays(excludedDays);
    }

    private List<ExcludedDay> mapToExcludedDays(EmployeeVacationsDto vacationsDto) {
        if (vacationsDto != null) {
            return vacationsDto.getVacations().stream()
                    .map(mapper::mapToExcludedDay)
                    .collect(toList());
        }
        return List.of();
    }

    private ResponseEntity<EmployeeVacationsDto> getVacationsDto(EmployeeWorklogDebts worklogDebts, LocalDate dateFrom, LocalDate dateTo) {
        var regionId = worklogDebts.getRegion().getId();
        var employeeId = worklogDebts.getId();
        var uri = fromUriString(restProperties.getGetVacationsUri())
                .buildAndExpand(regionId, dateFrom, dateTo, employeeId)
                .toUri();
         return restTemplate.getForEntity(uri, EmployeeVacationsDto.class);
    }
}
