package uk.co.monotonic.json_fundamentals._5_consuming_with_streaming;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class JsonMemoryComparison
{
    private static final File JSON_FILE = new File("citylots.json");

    public static void main(String[] args) throws IOException
    {
        // countFeaturesInBlockTree("0022");

        countFeaturesInBlockStreaming("0022");
    }

    private static void countFeaturesInBlockStreaming(final String blockNum) throws IOException
    {
        final JsonFactory factory = new JsonFactory();

        int featureCount = 0;
        try (final JsonParser parser = factory.createParser(JSON_FILE))
        {
            JsonToken token;
            while ((token = parser.nextToken()) != null)
            {
                if (token == JsonToken.VALUE_STRING)
                {
                    final String currentName = parser.getCurrentName();
                    final String text = parser.getText();
                    if (currentName.equals("BLOCK_NUM") && text.equals(blockNum))
                    {
                        featureCount++;
                    }
                }
            }
        }
        System.out.println("featureCount = " + featureCount);

        printMemoryConsumption();
    }

    private static void countFeaturesInBlockTree(final String blockNum) throws IOException
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        final JsonNode jsonNode = objectMapper.readTree(JSON_FILE);
        final ArrayNode features = (ArrayNode) jsonNode.get("features");

        int featureCount = 0;
        for (final JsonNode feature : features)
        {
            final JsonNode properties = feature.get("properties");
            final JsonNode thisBlockNum = properties.get("BLOCK_NUM");
            if (blockNum.equals(thisBlockNum.asText()))
            {
                featureCount++;
            }
        }

        System.out.println("featureCount = " + featureCount);

        printMemoryConsumption();
    }

    private static void printMemoryConsumption()
    {
        System.gc();
        final MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
        final MemoryUsage heapMemoryUsage = bean.getHeapMemoryUsage();
        System.out.printf("Used Memory: %dK%n", heapMemoryUsage.getUsed() / 1024);
    }
}
