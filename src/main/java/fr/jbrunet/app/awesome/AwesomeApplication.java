package fr.jbrunet.app.awesome;

import fr.jbrunet.app.awesome.config.AwesomeAppConfiguration;
import fr.jbrunet.app.awesome.resources.TestResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by Julien BRUNET on 23/09/2014.
 */
public class AwesomeApplication extends Application<AwesomeAppConfiguration> {


    public static void main(String[] args) throws Exception {
        //Start DropWizard Server
        new AwesomeApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<AwesomeAppConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/client/", "/", "index.html"));

    }

    @Override
    public void run(AwesomeAppConfiguration awesomeAppConfiguration, Environment environment) throws Exception {
        environment.jersey().setUrlPattern("/api/*");
        environment.jersey().register(new TestResource());

    }
}
