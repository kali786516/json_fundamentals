package uk.co.monotonic.json_fundamentals._5_consuming_with_streaming;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import uk.co.monotonic.json_fundamentals.common.SimpleJettyService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StreamingBankApplicationServlet extends HttpServlet
{
    private final JsonFactory jsonFactory = new JsonFactory();

    @Override
    protected void doPost(
        final HttpServletRequest req, final HttpServletResponse resp) throws IOException
    {
        final JsonParser parser = jsonFactory.createParser(req.getInputStream());

        double totalIncome = 0;
        double amount = 0;

        JsonToken token;
        while ((token = parser.nextToken()) != null)
        {
            final String currentName = parser.getCurrentName();
            if (currentName != null && token.isNumeric())
            {
                switch (currentName)
                {
                    case "annualIncome":
                        totalIncome += parser.getDoubleValue();
                        break;

                    case "amount":
                        amount = parser.getDoubleValue();
                        break;
                }
            }
        }

        final int status;
        final String message;
        if (amount <= 3 * totalIncome) {
            status = HttpServletResponse.SC_OK;
            message = "approved";
        } else {
            status = HttpServletResponse.SC_FORBIDDEN;
            message = "denied";
        }
        resp.setStatus(status);
        resp.getWriter().println(message);
    }

    public static void main(String[] args)
    {
        SimpleJettyService.run(StreamingBankApplicationServlet.class);
    }
}
