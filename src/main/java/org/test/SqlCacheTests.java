package org.test;

import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.client.IgniteClient;
import org.jetbrains.annotations.NotNull;

public class SqlCacheTests {

    final static String SQL_CACHE_NAME = "SqlCache";
    final static String SCHEMA = "PUBLIC";
    final static int NUM_KEYS = 10_000;

    final private IgniteClient client;

    SqlCacheTests(@NotNull IgniteClient _client){
        client = _client;
    }

    public void startSqlTests() {

        SqlFieldsQuery sql_query;
        sql_query = new SqlFieldsQuery(new SqlFieldsQuery(
                String.format("CREATE TABLE IF NOT EXISTS " + SQL_CACHE_NAME +
                                " (id INT PRIMARY KEY, str_value VARCHAR) WITH \"VALUE_TYPE=%s\"",
                        TestSqlTable.class.getName())).setSchema(SCHEMA));
        sqlQueryWithConsoleEcho(sql_query);

        long time = System.currentTimeMillis();
        for (int i = 0; i < NUM_KEYS; i++) {
            client.query(new SqlFieldsQuery("INSERT INTO " + SQL_CACHE_NAME + "(id, str_value) VALUES (?,?)")
                    .setArgs(i, "Value_" + i)).getAll();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("The time spent on " + NUM_KEYS + " INSERT operations in the " + SQL_CACHE_NAME +
                " table was: " + time + " ms.");

        sql_query = new SqlFieldsQuery("SELECT COUNT(id) FROM " + SQL_CACHE_NAME);
        sqlQueryWithConsoleEcho(sql_query);

        sql_query = new SqlFieldsQuery("SELECT * from " + SQL_CACHE_NAME + " WHERE str_value LIKE 'Value_21%'");
        sqlQueryWithConsoleEcho(sql_query);

        sql_query = new SqlFieldsQuery("DROP TABLE " + SQL_CACHE_NAME);
        sqlQueryWithConsoleEcho(sql_query);
    }

    private void sqlQueryWithConsoleEcho(@NotNull SqlFieldsQuery sql_query) {
        System.out.println("Query: " + sql_query.getSql());
        client.query(sql_query).getAll().forEach((v) -> System.out.println("Result: " + v.toString()));
    }
}
