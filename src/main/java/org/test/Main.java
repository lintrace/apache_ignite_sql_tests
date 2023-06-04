package org.test;

import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;

import java.util.Collection;


public class Main {
    public static void main(String[] args) {
        ClientConfiguration cfg = new ClientConfiguration();
        cfg.setPartitionAwarenessEnabled(true);
        cfg.setAddresses("localhost:10800");

        IgniteClient client = Ignition.startClient(cfg);

        // Server node OS info and Ignite version
        System.out.println("=======================================================");
        System.out.println("OS: " + client.cluster().node().attributes().get("os.name") +
                " \t\t Ignite ver: " + client.cluster().node().attributes().get("org.apache.ignite.build.ver"));
        System.out.println("=======================================================");

        System.out.println("The cluster state is "  + client.cluster().state());

        // Cache list
        if (client.cacheNames().size() > 0) {
            System.out.println("\nCache found in the cluster: ");
            client.cacheNames().forEach(System.out::println);
        }
        else System.out.println("No cache found in the cluster");

        new SqlCacheTests(client).startSqlTests();

        client.close();
    }
}