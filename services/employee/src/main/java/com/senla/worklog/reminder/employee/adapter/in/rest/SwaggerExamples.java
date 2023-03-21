package com.senla.worklog.reminder.employee.adapter.in.rest;

public class SwaggerExamples {
    public static final String API_ERROR_500 = "{\n" +
            "  \"message\": \"Internal server error\",\n" +
            "  \"status\": 500,\n" +
            "  \"errors\": []\n" +
            "}";

    public static final String API_ERROR_404 = "{\n" +
            "    \"message\": \"Resource not found\",\n" +
            "    \"status\": 404,\n" +
            "    \"errors\": [\n" +
            "        {\n" +
            "            \"message\": \"Employee with id = '100' not found\",\n" +
            "            \"attributeName\": \"id\",\n" +
            "            \"attributeValue\": \"100\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    public static final String API_ERROR_400_1 = "{\n" +
            "    \"message\": \"Failed to parse request parameter with name id and value abc\",\n" +
            "    \"status\": 400,\n" +
            "    \"errors\": [\n" +
            "        {\n" +
            "            \"message\": \"For input string: \\\"abc\\\"\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    public static final String API_ERROR_400_2 = "{\n" +
            "    \"message\": \"Failed to parse request parameter with name id and value abc\",\n" +
            "    \"status\": 400,\n" +
            "    \"errors\": [\n" +
            "        {\n" +
            "            \"message\": \"For input string: \\\"abc\\\"\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
