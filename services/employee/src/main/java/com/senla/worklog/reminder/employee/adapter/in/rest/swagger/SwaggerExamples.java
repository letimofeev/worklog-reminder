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
     * An example JSON response for a 404 Not Found Error (Employee not found)
     */
    public static final String API_ERROR_404_EMPLOYEE_NOT_FOUND = "{\n" +
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
     * An example JSON response for a 404 Not Found Error (Region not found)
     */
    public static final String API_ERROR_404_REGION_NOT_FOUND = "{\n" +
            "    \"message\": \"Resource not found\",\n" +
            "    \"status\": 404,\n" +
            "    \"errors\": [\n" +
            "        {\n" +
            "            \"message\": \"Region with id = 'ac7bc9b7-1515-4d57-a825-5001a83f0000' not found\",\n" +
            "            \"attributeName\": \"regionId\",\n" +
            "            \"attributeValue\": \"ac7bc9b7-1515-4d57-a825-5001a83f0000\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

    /**
     * An example JSON response for a 400 Bad Request Error (Failed to parse request parameter).
     */
    public static final String API_ERROR_400_PARSE_PARAMETER = "{\n" +
            "    \"message\": \"Failed to parse request parameter with name = 'id' and value = 'abc'\",\n" +
            "    \"status\": 400,\n" +
            "    \"errors\": []\n" +
            "}";

    /**
     * An example JSON response for a 400 Bad Request Error (Validation failed).
     */
    public static final String API_ERROR_400_VALIDATION_FAILED = "{\n" +
            "    \"message\": \"Validation failed\",\n" +
            "    \"status\": 400,\n" +
            "    \"errors\": [\n" +
            "        {\n" +
            "            \"message\": \"lastName must be specified\",\n" +
            "            \"attributeName\": \"lastName\",\n" +
            "            \"attributeValue\": \"  \"\n" +
            "        },\n" +
            "        {\n" +
            "            \"message\": \"firstName must not specified\",\n" +
            "            \"attributeName\": \"firstName\",\n" +
            "            \"attributeValue\": \"null\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
