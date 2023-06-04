package org.test;

import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.IgniteClient;

public class SqlCacheTests {

    final static String SQL_CACHE_NAME = "SqlCache";

    public static void startSqlTests(IgniteClient client) {
        ClientCache<Integer,String> cache = client.getOrCreateCache(SQL_CACHE_NAME);

        client.destroyCache(SQL_CACHE_NAME);
    }
}
