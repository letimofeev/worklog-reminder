package com.senla.worklog.reminder.employee.adapter.in.rest;

import com.senla.worklog.reminder.employee.adapter.in.rest.dto.ApiError;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.CreateEmployeeRequestDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.EmployeeDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.dto.UpdateEmployeeRequestDto;
import com.senla.worklog.reminder.employee.adapter.in.rest.mapper.EmployeeRestMapper;
import com.senla.worklog.reminder.employee.domain.model.Employee;
import com.senla.worklog.reminder.employee.domain.port.in.EmployeeServicePort;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;

@CrossOrigin
@Tag(name = "Employee API")
@OpenAPIDefinition()
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeServicePort employeeServicePort;
    private final EmployeeRestMapper employeeMapper;

    @Operation(summary = "Add a new employee to the system with the provided employee data")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully added employee",
                    content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content(examples = @ExampleObject("{\n" +
                            "    \"message\": \"Validation failed\",\n" +
                            "    \"status\": 400,\n" +
                            "    \"errors\": [\n" +
                            "        {\n" +
                            "            \"message\": \"lastName must be specified\",\n" +
                            "            \"attributeName\": \"lastName\",\n" +
                            "            \"attributeValue\": \"null\"\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}"),
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(examples = @ExampleObject("{\n" +
                            "  \"message\": \"Internal server error\",\n" +
                            "  \"status\": 500,\n" +
                            "  \"errors\": []\n" +
                            "}"),
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<EmployeeDto> addEmployee(@Valid @RequestBody CreateEmployeeRequestDto employeeDto) {
        var employee = employeeMapper.mapToDomain(employeeDto);
        var createdEmployee = employeeServicePort.addEmployee(employee);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(createdEmployee.getId())
                .toUri();
        var employeeResponse = employeeMapper.mapToDto(employee);
        return created(location).body(employeeResponse);
    }

    @Operation(summary = "Get the employee with the specified ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved employee",
                    content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content(examples = @ExampleObject("{\n" +
                            "    \"message\": \"Failed to parse request parameter with name id and value abc\",\n" +
                            "    \"status\": 400,\n" +
                            "    \"errors\": [\n" +
                            "        {\n" +
                            "            \"message\": \"For input string: \\\"abc\\\"\"\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}"),
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404",
                    description = "Employee not found",
                    content = @Content(examples = @ExampleObject("{\n" +
                            "    \"message\": \"Resource not found\",\n" +
                            "    \"status\": 404,\n" +
                            "    \"errors\": [\n" +
                            "        {\n" +
                            "            \"message\": \"Employee with id = '10' not found\",\n" +
                            "            \"attributeName\": \"id\",\n" +
                            "            \"attributeValue\": \"10\"\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}"),
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(examples = @ExampleObject("{\n" +
                            "  \"message\": \"Internal server error\",\n" +
                            "  \"status\": 500,\n" +
                            "  \"errors\": []\n" +
                            "}"),
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public EmployeeDto getEmployeeById(@PathVariable Long id) {
        var employee = employeeServicePort.getEmployeeById(id);
        return employeeMapper.mapToDto(employee);
    }

    @Operation(summary = "Get a list of employees")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Successfully retrieved the list of employees",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmployeeDto.class)))),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(examples = @ExampleObject("{\n" +
                            "  \"message\": \"Internal server error\",\n" +
                            "  \"status\": 500,\n" +
                            "  \"errors\": []\n" +
                            "}"),
                            schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping
    public List<EmployeeDto> getEmployees(@Parameter(description = "Employee jiraKey filter")
                                          @RequestParam(required = false)
                                          String jiraKey) {
        List<Employee> employees;
        if (jiraKey != null) {
            employees = employeeServicePort.getEmployeesByJiraKey(jiraKey);
        } else {
            employees = employeeServicePort.getAllEmployees();
        }
        return employees.stream()
                .map(employeeMapper::mapToDto)
                .collect(toList());
    }

    @Operation(summary = "Updates an existing employee in the system with the provided employee data")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully updated employee",
                    content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "400",
                    description = "Bad request",
                    content = @Content(examples = @ExampleObject("{\n" +
                            "    \"message\": \"Validation failed\",\n" +
                            "    \"status\": 400,\n" +
                            "    \"errors\": [\n" +
                            "        {\n" +
                            "            \"message\": \"id must be specified\",\n" +
                            "            \"attributeName\": \"id\",\n" +
                            "            \"attributeValue\": \"null\"\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}"),
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "404",
                    description = "Employee with specified id not found",
                    content = @Content(examples = @ExampleObject("{\n" +
                            "    \"message\": \"Resource not found\",\n" +
                            "    \"status\": 404,\n" +
                            "    \"errors\": [\n" +
                            "        {\n" +
                            "            \"message\": \"Employee with id = '100' not found\",\n" +
                            "            \"attributeName\": \"id\",\n" +
                            "            \"attributeValue\": \"100\"\n" +
                            "        }\n" +
                            "    ]\n" +
                            "}"),
                            schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(examples = @ExampleObject("{\n" +
                            "  \"message\": \"Internal server error\",\n" +
                            "  \"status\": 500,\n" +
                            "  \"errors\": []\n" +
                            "}"),
                            schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping
    public EmployeeDto updateEmployee(@Valid @RequestBody UpdateEmployeeRequestDto employeeDto) {
        var employee = employeeMapper.mapToDomain(employeeDto);
        var updatedEmployee = employeeServicePort.updateEmployee(employee);
        return employeeMapper.mapToDto(updatedEmployee);
    }

    @Operation(summary = "Deletes the employee with the specified ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Successfully deleted employee"),
            @ApiResponse(responseCode = "500",
                    description = "Internal server error",
                    content = @Content(examples = @ExampleObject("{\n" +
                            "  \"message\": \"Internal server error\",\n" +
                            "  \"status\": 500,\n" +
                            "  \"errors\": []\n" +
                            "}"),
                            schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployeeById(@PathVariable Long id) {
        employeeServicePort.deleteEmployeeById(id);
        return noContent().build();
    }
}
