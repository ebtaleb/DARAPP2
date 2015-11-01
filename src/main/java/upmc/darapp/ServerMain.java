package upmc.darapp;

import java.util.ArrayList;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;

public class ServerMain {
    private static final int PORT = 8080;
    private static final String WAR_LOCATION = "src/main/webapps";
    private static final String CONTEXT_PATH = "/";
    private static final String MAPPING_URL = "/*";

    public static void main(String args[]) throws Exception {

        Server server = new Server(PORT);

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);

        WebAppContext context = new WebAppContext();
        context.setServer(server);
        context.setContextPath(CONTEXT_PATH);
        context.setWar(WAR_LOCATION);
        context.setAttribute(InstanceManager.class.getName(), new SimpleInstanceManager());

        ResourceHandler staticResourceHandler = new ResourceHandler();
        staticResourceHandler.setResourceBase("src/main/webapps/static/");
        staticResourceHandler.setDirectoriesListed(true);

        server.setConnectors(new Connector[] { connector });

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { staticResourceHandler, context });

        server.setHandler(handlers);

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
