package org.lx.mybatis.builder;

import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.session.Configuration;
import org.lx.mybatis.annotation.StatementProvider;

import java.lang.reflect.Method;

public class ProviderMethodInvoker {
    private final Class<?> providerType;
    private Method providerMethod;
    private Integer providerContextIndex;
    private StatementProviderContext providerContext;
    private int parameterCount;


    public ProviderMethodInvoker(Configuration configuration, StatementProvider provider, Class<?> mapperType, Class<?> entityClass, Method mapperMethod) {
        String providerMethodName;
        Class<?>[] providerMethodParameterTypes = null;
        try {

            this.providerType = provider.value();
            providerMethodName = provider.method();

            for (Method m : this.providerType.getMethods()) {
                if (providerMethodName.equals(m.getName())) {
                    if (m.getReturnType() == String.class) {
                        if (providerMethod != null) {
                            throw new BuilderException("Error creating SqlSource for SqlProvider. Method '"
                                    + providerMethodName + "' is found multiple in SqlProvider '" + this.providerType.getName()
                                    + "'. Sql provider method can not overload.");
                        }
                        this.providerMethod = m;
                        providerMethodParameterTypes = m.getParameterTypes();
                        parameterCount = providerMethodParameterTypes.length;
                    }
                }
            }
        } catch (BuilderException e) {
            throw e;
        } catch (Exception e) {
            throw new BuilderException("Error creating SqlSource for SqlProvider.  Cause: " + e, e);
        }
        if (this.providerMethod == null) {
            throw new BuilderException("Error creating SqlSource for SqlProvider. Method '"
                    + providerMethodName + "' not found in SqlProvider '" + this.providerType.getName() + "'.");
        }
        for (int i = 0; i < providerMethodParameterTypes.length; i++) {
            Class<?> parameterType = providerMethodParameterTypes[i];
            if (parameterType == ProviderContext.class) {
                if (this.providerContext != null) {
                    throw new BuilderException("Error creating SqlSource for SqlProvider. ProviderContext found multiple in SqlProvider method ("
                            + this.providerType.getName() + "." + providerMethod.getName()
                            + "). ProviderContext can not define multiple in SqlProvider method argument.");
                }
                this.providerContext = new StatementProviderContext().setConfiguration(configuration).setEntityClass(entityClass).setMapperType(mapperType).setMapperMethod(mapperMethod);
                this.providerContextIndex = i;
            }
        }
    }

    public String createSqlSource() {
        try {
            Object[] args = new Object[parameterCount];
            if (this.providerContext != null && this.providerContextIndex != null) {
                args[providerContextIndex] = providerContext;
            }
            return (String) providerMethod.invoke(providerType.newInstance(), args);
        } catch (Exception e) {
            throw new BuilderException("Error invoking StatementProvider method ("
                    + providerType.getName() + "." + providerMethod.getName()
                    + ").  Cause: " + e, e);
        }
    }
}
