package uk.co.monotonic.json_fundamentals.common;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import javax.servlet.Servlet;

public class SimpleJettyService
{
    public static final int PORT = 8000;

    public static void run(final Class<? extends Servlet> servlet) {
        final Server server = new Server(PORT);
        final ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(servlet, "/*");
        server.setHandler(servletHandler);
        try
        {
            server.dumpStdErr();
            server.start();
            server.join();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
