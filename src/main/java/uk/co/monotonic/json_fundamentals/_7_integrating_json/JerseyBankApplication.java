package uk.co.monotonic.json_fundamentals._7_integrating_json;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

import static uk.co.monotonic.json_fundamentals.common.SimpleJettyService.PORT;

public class JerseyBankApplication
{
    public static void main(String[] args) throws IOException
    {
        final ResourceConfig rc = new ResourceConfig()
            .packages("uk.co.monotonic.json_fundamentals._7_integrating_json");
        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
            URI.create("http://localhost:" + PORT + "/"), rc);

        System.in.read();
        server.shutdownNow();
    }
}
