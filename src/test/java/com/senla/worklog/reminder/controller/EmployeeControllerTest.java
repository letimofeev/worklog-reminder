package com.senla.worklog.reminder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.worklog.reminder.dto.EmployeeDto;
import com.senla.worklog.reminder.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void addEmployee_shouldReturn204StatusAndCreatedDto_whenRequestBodyIsValid() throws Exception {
        EmployeeDto employeeDto = new EmployeeDto()
                .setFirstName("Lexa")
                .setLastName("Alex")
                .setJiraKey("jira_key_1");

        given(employeeService.addEmployee(employeeDto)).willReturn(employeeDto.setId(10L));

        String requestBody = objectMapper.writeValueAsString(employeeDto);
        String response = mvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        EmployeeDto actual = objectMapper.readValue(response, EmployeeDto.class);

        assertEquals(employeeDto, actual);
    }
}