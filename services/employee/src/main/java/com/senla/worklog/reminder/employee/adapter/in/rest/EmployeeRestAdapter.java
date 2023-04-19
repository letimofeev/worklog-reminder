package com.senla.worklog.reminder.employee.adapter.in.rest;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.CreateEmployeeRequestDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.EmployeeDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.UpdateEmployeeRequestDto;
import com.senla.worklog.reminder.exception.handler.model.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

import static com.senla.worklog.reminder.employee.adapter.in.rest.swagger.SwaggerExamples.API_ERROR_400_PARSE_PARAMETER;
import static com.senla.worklog.reminder.employee.adapter.in.rest.swagger.SwaggerExamples.API_ERROR_400_VALIDATION_FAILED;
import static com.senla.worklog.reminder.employee.adapter.in.rest.swagger.SwaggerExamples.API_ERROR_404_EMPLOYEE_NOT_FOUND;
import static com.senla.worklog.reminder.employee.adapter.in.rest.swagger.SwaggerExamples.API_ERROR_404_REGION_NOT_FOUND;
import static com.senla.worklog.reminder.employee.adapter.in.rest.swagger.SwaggerExamples.API_ERROR_500;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

/**
 * This interface defines the REST API for managing employees. It is annotated with Spring MVC annotations
 * to define the base request mapping for the API and to tag it with a descriptive name.
 */
@Tag(name = "Employee API")
@RequestMapping(value = "/api/employees", produces = APPLICATION_JSON_VALUE)
public interface EmployeeRestAdapter {
    @Operation(summary = "Add a new employee to the system with the provided employee data")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content(examples = @ExampleObject(API_ERROR_400_VALIDATION_FAILED),
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = @Content(examples = @ExampleObject(API_ERROR_404_REGION_NOT_FOUND),
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(examples = @ExampleObject(API_ERROR_500),
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    ResponseEntity<EmployeeDto> addEmployee(@Valid @RequestBody CreateEmployeeRequestDto employeeDto);

    @Operation(summary = "Get the employee with the specified ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Ok",
                    content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content(examples = @ExampleObject(API_ERROR_400_PARSE_PARAMETER),
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = @Content(examples = @ExampleObject(API_ERROR_404_EMPLOYEE_NOT_FOUND),
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(examples = @ExampleObject(API_ERROR_500),
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    EmployeeDto getEmployeeById(@PathVariable Long id);

    @Operation(summary = "Get a list of employees")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Ok",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmployeeDto.class)))),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(examples = @ExampleObject(API_ERROR_500),
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping
    List<EmployeeDto> getEmployees(@Parameter(description = "Employee jiraKey filter")
                                   @RequestParam(required = false)
                                   String jiraKey);

    @Operation(summary = "Updates an existing employee in the system with the provided employee data")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content(examples = @ExampleObject(API_ERROR_400_VALIDATION_FAILED),
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = @Content(schema = @Schema(implementation = ApiError.class),
                            examples = {
                                    @ExampleObject(name = "Employee not found", value = API_ERROR_404_EMPLOYEE_NOT_FOUND),
                                    @ExampleObject(name = "Region not found", value = API_ERROR_404_REGION_NOT_FOUND)
                            })),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(examples = @ExampleObject(API_ERROR_500),
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping
    EmployeeDto updateEmployee(@Valid @RequestBody UpdateEmployeeRequestDto employeeDto);

    @Operation(summary = "Deletes the employee with the specified ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204",
                    description = "No Content",
                    content = @Content(mediaType = TEXT_PLAIN_VALUE)),
            @ApiResponse(responseCode = "404",
                    description = "Not found",
                    content = @Content(examples = @ExampleObject(API_ERROR_404_EMPLOYEE_NOT_FOUND),
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(examples = @ExampleObject(API_ERROR_500),
                            schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteEmployeeById(@PathVariable Long id);
}
