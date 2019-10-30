package uk.co.monotonic.json_fundamentals._3_consuming_with_dom;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;

public class ConsumingWithDomApi
{
    private static final File BANK_LOAN_FILE = new File("src/main/resources/bank_loan.json");

    // Parse it
    // Pretty Print
    // Errors - edit the file and break the formatting
    // JsonNode API: Recursive descent - scan for dates and validate them, looking up information

    public static void main(String[] args) throws IOException
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(BANK_LOAN_FILE);

        ObjectWriter prettyPrinter = objectMapper.writerWithDefaultPrettyPrinter();
        System.out.println(prettyPrinter.writeValueAsString(jsonNode));

        validateDates(jsonNode);
    }

    private static void validateDates(final JsonNode jsonNode)
    {
        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            String fieldName = field.getKey();
            JsonNode childNode = field.getValue();
            if (childNode.isTextual() && fieldName.endsWith("Date")) {
                System.out.println("Found date field: " + fieldName);
                String fieldValue = childNode.asText();

                try
                {
                    DateTimeFormatter.ISO_LOCAL_DATE.parse(fieldValue);
                }
                catch (DateTimeException e)
                {
                    System.out.println("Invalid value: " + fieldValue);
                }
            } else {
                validateDates(childNode);
            }
        }
    }
}
