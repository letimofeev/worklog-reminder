package com.senla.worklog.reminder.employee.adapter.in.rest.swagger;

/**
 * The SwaggerExamples class provides example JSON responses for various error cases that might be encountered
 * when using the API. These examples can be used in Swagger documentation or in testing
 */
public class SwaggerExamples {

    /**
     * An example JSON response for a 500 Internal Server Error.
     */
    public static final String API_ERROR_500 = "{\n" +
            "  \"message\": \"Internal server error\",\n" +
            "  \"status\": 500,\n" +
            "  \"errors\": []\n" +
            "}";

    /**
     * An example JSON response for a 404 Not Found Error.
     */
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

    /**
     * An example JSON response for a 400 Bad Request Error (variant 1).
     */
    public static final String API_ERROR_400_1 = "{\n" +
            "    \"message\": \"Failed to parse request parameter with name id and value abc\",\n" +
            "    \"status\": 400,\n" +
            "    \"errors\": [\n" +
            "        {\n" +
            "            \"message\": \"For input string: \\\"abc\\\"\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    /**
     * An example JSON response for a 400 Bad Request Error (variant 2).
     */
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
