package upmc.darapp;

import java.util.List;
import java.util.ArrayList;

import org.apache.jasper.servlet.JspServlet;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.SimpleInstanceManager;

import org.eclipse.jetty.annotations.ServletContainerInitializersStarter;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.plus.annotation.ContainerInitializer;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public class ServerMain {

    private static final int PORT = 8080;
    private static final String WAR_LOCATION = "src/main/webapp";
    private static final String CONTEXT_PATH = "/";
    private static final String MAPPING_URL = "/*";

    private static List<ContainerInitializer> jspInitializers()
    {
        JettyJasperInitializer sci = new JettyJasperInitializer();
        ContainerInitializer initializer = new ContainerInitializer(sci, null);
        List<ContainerInitializer> initializers = new ArrayList<ContainerInitializer>();
        initializers.add(initializer);
        return initializers;
    }

    private static ServletHolder jspServletHolder()
    {
        ServletHolder holderJsp = new ServletHolder("jsp", JspServlet.class);
        holderJsp.setInitOrder(0);
        holderJsp.setInitParameter("logVerbosityLevel", "DEBUG");
        holderJsp.setInitParameter("fork", "false");
        holderJsp.setInitParameter("xpoweredBy", "false");
        holderJsp.setInitParameter("compilerTargetVM", "1.7");
        holderJsp.setInitParameter("compilerSourceVM", "1.7");
        holderJsp.setInitParameter("keepgenerated", "true");
        return holderJsp;
    }

    public static void main(String args[]) throws Exception {

        Server server = new Server(PORT);

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);

        WebAppContext context = new WebAppContext();
        context.setServer(server);
        context.setContextPath(CONTEXT_PATH);
        context.setWar(WAR_LOCATION);

        context.setInitParameter("dirAllowed", "false");
        context.setAttribute("org.eclipse.jetty.containerInitializers", jspInitializers());
        context.addBean(new ServletContainerInitializersStarter(context), true);

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
