package org.lx.mybatis.entity;

import org.apache.ibatis.annotations.Options.FlushCachePolicy;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.StatementType;

public class Options {
    private boolean useCache = true;

    private FlushCachePolicy flushCache = FlushCachePolicy.DEFAULT;

    private ResultSetType resultSetType = ResultSetType.FORWARD_ONLY;

    private StatementType statementType = StatementType.PREPARED;

    private int fetchSize = -1;

    private int timeout = -1;

    private boolean useGeneratedKeys = false;

    private String keyProperty = "id";

    private String keyColumn = "";

    private String resultSets = "";

    public boolean isUseCache() {
        return useCache;
    }

    public Options setUseCache(boolean useCache) {
        this.useCache = useCache;
        return this;
    }

    public FlushCachePolicy getFlushCache() {
        return flushCache;
    }

    public Options setFlushCache(FlushCachePolicy flushCache) {
        this.flushCache = flushCache;
        return this;
    }

    public ResultSetType getResultSetType() {
        return resultSetType;
    }

    public Options setResultSetType(ResultSetType resultSetType) {
        this.resultSetType = resultSetType;
        return this;
    }

    public StatementType getStatementType() {
        return statementType;
    }

    public Options setStatementType(StatementType statementType) {
        this.statementType = statementType;
        return this;
    }

    public int getFetchSize() {
        return fetchSize;
    }

    public Options setFetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public Options setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public boolean isUseGeneratedKeys() {
        return useGeneratedKeys;
    }

    public Options setUseGeneratedKeys(boolean useGeneratedKeys) {
        this.useGeneratedKeys = useGeneratedKeys;
        return this;
    }

    public String getKeyProperty() {
        return keyProperty;
    }

    public Options setKeyProperty(String keyProperty) {
        this.keyProperty = keyProperty;
        return this;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public Options setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
        return this;
    }

    public String getResultSets() {
        return resultSets;
    }

    public Options setResultSets(String resultSets) {
        this.resultSets = resultSets;
        return this;
    }
}
