package fr.jbrunet.app.awesome;

import fr.jbrunet.app.awesome.config.AwesomeAppConfiguration;
import fr.jbrunet.app.awesome.db.FooDAO;
import fr.jbrunet.app.awesome.resources.FooResource;
import fr.jbrunet.app.awesome.resources.TestResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

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

        bootstrap.addBundle(new MigrationsBundle<AwesomeAppConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(AwesomeAppConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

    }

    @Override
    public void run(AwesomeAppConfiguration awesomeAppConfiguration, Environment environment) throws Exception {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, awesomeAppConfiguration.getDataSourceFactory(), "appDatabase");

        environment.jersey().setUrlPattern("/api/*");
        environment.jersey().register(new TestResource());
        environment.jersey().register(new FooResource(jdbi.onDemand(FooDAO.class)));

    }
}
