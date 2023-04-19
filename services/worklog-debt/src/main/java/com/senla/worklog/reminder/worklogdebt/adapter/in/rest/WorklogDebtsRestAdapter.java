package com.senla.worklog.reminder.worklogdebt.adapter.in.rest;

import com.senla.worklog.reminder.exception.handler.model.ApiError;
import com.senla.worklog.reminder.worklogdebt.adapter.in.rest.dto.EmployeeWorklogDebtsDto;
import com.senla.worklog.reminder.worklogdebt.adapter.in.rest.validator.DateRangeRequestParameters;
import com.senla.worklog.reminder.worklogdebt.adapter.in.rest.validator.ValidationSequence;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;

import static com.senla.worklog.reminder.worklogdebt.adapter.in.rest.swagger.SwaggerExamples.API_ERROR_400_BAD_DATE_FORMAT;
import static com.senla.worklog.reminder.worklogdebt.adapter.in.rest.swagger.SwaggerExamples.API_ERROR_400_DATE_FROM_AFTER_DATE_TO;
import static com.senla.worklog.reminder.worklogdebt.adapter.in.rest.swagger.SwaggerExamples.API_ERROR_400_EMPTY_PARAMETER;
import static com.senla.worklog.reminder.worklogdebt.adapter.in.rest.swagger.SwaggerExamples.API_ERROR_500;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "WorklogDebt API")
@RequestMapping(value = "/api/worklog-debts", produces = APPLICATION_JSON_VALUE)
public interface WorklogDebtsRestAdapter {
    @GetMapping
    List<EmployeeWorklogDebtsDto> getCurrentWeekEmployeesDebts();

    @GetMapping(params = {"dateFrom"})
    List<EmployeeWorklogDebtsDto> getEmployeesDebts(@PastOrPresent(message = "{dateFrom.pastOrPresent}")
                                                    @RequestParam
                                                    @DateTimeFormat(iso = DATE)
                                                    LocalDate dateFrom);

    @Operation(summary = "Retrieve a list of employees' worklog debts for a period")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved the list of employees",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmployeeWorklogDebtsDto.class)))),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(examples = @ExampleObject(API_ERROR_500),
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content(schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(name = "Empty parameter", value = API_ERROR_400_EMPTY_PARAMETER),
                                    @ExampleObject(name = "Bad date format", value = API_ERROR_400_BAD_DATE_FORMAT),
                                    @ExampleObject(name = "dateFrom after dateTo", value = API_ERROR_400_DATE_FROM_AFTER_DATE_TO)
                            }))
    })
    @GetMapping(params = {"dateFrom", "dateTo"})
    List<EmployeeWorklogDebtsDto> getEmployeesDebts(@Validated(ValidationSequence.class) DateRangeRequestParameters parameters);
}
