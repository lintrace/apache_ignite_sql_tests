package org.test;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;

public class TestSqlTable implements Serializable {
    @QuerySqlField(index = true)
    private int id;

    @QuerySqlField
    private String str_value;
}
