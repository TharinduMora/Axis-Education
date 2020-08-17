package com.teamtrace.axiseducation;

import com.teamtrace.axiseducation.controller.*;
import com.teamtrace.axiseducation.health.SystemHealthCheck;
import com.teamtrace.axiseducation.util.MessageStore;
import com.teamtrace.axiseducation.util.notification.NotificationSender;
import com.teamtrace.axiseducation.util.persistence.DataSourceManager;
import com.teamtrace.axiseducation.util.persistence.PersistenceManager;
import com.teamtrace.axiseducation.util.push.PushResource;
import io.dropwizard.Application;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ServletContainer;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletException;
import javax.ws.rs.client.Client;
import java.util.EnumSet;
import java.util.Timer;
import java.util.logging.Logger;

public class AxisEducationApplication extends Application<AxisEducationConfiguration> {
    static Logger logger = Logger.getLogger(AxisEducationApplication.class.getName());
    static boolean production;
    Timer timer;
    Undertow server;

    public static void main(String[] args) throws Exception {
        logger.info("Starting Axis Education - Backend ...");
        if (args == null || args.length < 2) {
            args = new String[]{"server", "config.yml"};
        }

        String prod = System.getProperty("production");
        if (prod != null && "true".equals(prod)) {
            production = true;
        }

        new AxisEducationApplication().run(args);
    }

    @Override
    public String getName() {
        return "Axis Education - Backende";
    }

    @Override
    public void initialize(Bootstrap<AxisEducationConfiguration> bootstrap) {
        if (!production) {
            bootstrap.addBundle(new SwaggerBundle<AxisEducationConfiguration>() {
                @Override
                protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
                        AxisEducationConfiguration configuration) {
                    return configuration.swaggerBundleConfiguration;
                }
            });
        }
    }

    @Override
    public void run(AxisEducationConfiguration configuration, Environment environment) throws Exception {
        //Message Store
        MessageStore.initialize();
        //DB
        PersistenceManager.initialize(configuration.database);
        DataSourceManager.initialize(configuration.database);

        //Web socket server
        startPushServer(configuration);

        //For OAuth
        Client client = new JerseyClientBuilder(environment).using(configuration.jerseyClient).build(getName());

        //Notifications (Email, sms, push, fcm)
        NotificationSender.initialize(configuration, client);

        //Jobs
        scheduleJobs();

        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders",
                "X-Requested-With,Content-Type,Accept,Origin,sessionId");
        cors.setInitParameter("allowedMethods", "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter("exposedHeaders", "ETag");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

        environment.jersey().register(MultiPartFeature.class);

        //Jax-RS endpoints
        environment.jersey().register(new AdminUserController());
        environment.jersey().register(new AuthenticationController());
        environment.jersey().register(new FileController(configuration));
        environment.jersey().register(new PublicDataController());
        environment.jersey().register(new RoleController());
        environment.jersey().register(new RoleGroupController());
        environment.jersey().register(new SystemParameterController());
        environment.jersey().register(new SystemStatementController());

        environment.healthChecks().register("healthCheck", new SystemHealthCheck());

        logger.info("Axis Education Backend Started.");
    }

    private void startPushServer(AxisEducationConfiguration configuration) {
        PathHandler path = Handlers.path();

        server = Undertow.builder().addHttpListener(configuration.pushPort, configuration.pushHost)
                .setHandler(path).build();
        server.start();

        final ServletContainer container = ServletContainer.Factory.newInstance();

        DeploymentInfo builder =
                new DeploymentInfo().setClassLoader(AxisEducationApplication.class.getClassLoader())
                        .setContextPath(configuration.pushRoot)
                        .setResourceManager(
                                new ClassPathResourceManager(AxisEducationApplication.class.getClassLoader(),
                                        AxisEducationApplication.class.getPackage()))
                        .addServletContextAttribute(WebSocketDeploymentInfo.ATTRIBUTE_NAME,
                                new WebSocketDeploymentInfo().setBuffers(new DefaultByteBufferPool(true, 100))
                                        .addEndpoint(PushResource.class)).setDeploymentName("push.war");


        DeploymentManager manager = container.addDeployment(builder);
        manager.deploy();
        try {
            path.addPrefixPath(configuration.pushRoot, manager.start());
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }

    private void scheduleJobs() {

    }
}
