package uk.co.monotonic.json_fundamentals._5_consuming_with_streaming;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import uk.co.monotonic.json_fundamentals._4_consuming_with_binding.BasicLoanApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Warmup(iterations = 5)
@Measurement(iterations = 5)
@Fork(2)
@State(Scope.Thread)
public class JsonBenchmark
{

    String bankLoanFile;
    ObjectMapper objectMapper;
    JsonFactory jsonFactory;

    @Setup
    public void prepare() throws IOException
    {
        bankLoanFile = new String(Files.readAllBytes(Paths.get("src/main/resources/bank_loan.json")));
        objectMapper = new ObjectMapper();
        jsonFactory = objectMapper.getFactory();
    }

    @Benchmark
    public void streaming(Blackhole blackhole) throws IOException
    {
        try (final JsonParser parser = jsonFactory.createParser(bankLoanFile))
        {
            JsonToken token;
            while ((token = parser.nextToken()) != null)
            {
                if (token.isScalarValue())
                {
                    final String currentName = parser.getCurrentName();
                    if (currentName != null)
                    {
                        final String text = parser.getText();

                        blackhole.consume(text);
                    }
                }
            }
        }
    }

    @Benchmark
    public BasicLoanApplication binding() throws IOException
    {
        return objectMapper.readValue(bankLoanFile, BasicLoanApplication.class);
    }

}
