package com.senla.worklog.reminder.worklogdebt.adapter.in.rest.swagger;

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
     * An example JSON response for a 400 Bad Request Error (empty parameter value).
     */
    public static final String API_ERROR_400_EMPTY_PARAMETER = "{\n" +
            "    \"message\": \"Missing value for parameter 'dateFrom'\",\n" +
            "    \"status\": 400,\n" +
            "    \"errors\": []\n" +
            "}";

    /**
     * An example JSON response for a 400 Bad Request Error (bad date format).
     */
    public static final String API_ERROR_400_BAD_DATE_FORMAT = "{\n" +
            "    \"message\": \"Failed to parse request parameter with name = 'dateFrom' and value = '92391'\",\n" +
            "    \"status\": 400,\n" +
            "    \"errors\": []\n" +
            "}";

    /**
     * An example JSON response for a 400 Bad Request Error (dateFrom after dateTo).
     */
    public static final String API_ERROR_400_DATE_FROM_AFTER_DATE_TO = "{\n" +
            "    \"message\": \"Validation failed\",\n" +
            "    \"status\": 400,\n" +
            "    \"errors\": [\n" +
            "        {\n" +
            "            \"message\": \"'dateFrom' (2023-02-10) is after 'dateTo' (2023-01-10), which is invalid\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
