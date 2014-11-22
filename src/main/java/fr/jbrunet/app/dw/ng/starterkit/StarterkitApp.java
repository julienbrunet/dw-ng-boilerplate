package fr.jbrunet.app.dw.ng.starterkit;

import fr.jbrunet.app.dw.ng.starterkit.db.FooDAO;
import fr.jbrunet.app.dw.ng.starterkit.resources.FooResource;
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
public class StarterkitApp extends Application<StarterkitAppConfiguration> {

    public static void main(String[] args) throws Exception {
        //Start DropWizard Server
        new StarterkitApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<StarterkitAppConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/client/", "/", "index.html"));
        //bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new MigrationsBundle<StarterkitAppConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(StarterkitAppConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });

    }

    @Override
    public void run(StarterkitAppConfiguration awesomeAppConfiguration, Environment environment) throws Exception {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, awesomeAppConfiguration.getDataSourceFactory(), "appDatabase");

        environment.jersey().register(new FooResource(jdbi.onDemand(FooDAO.class)));

    }
}
