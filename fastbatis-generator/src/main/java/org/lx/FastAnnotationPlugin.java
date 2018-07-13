package org.lx;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

public class FastAnnotationPlugin extends PluginAdapter {
    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn column, IntrospectedTable introspectedTable, ModelClassType modelClassType) {

        if (field.isTransient()) {
            field.addAnnotation("@Transient");
        }

        Iterator var7 = introspectedTable.getPrimaryKeyColumns().iterator();

        while (var7.hasNext()) {
            IntrospectedColumn columnTmp = (IntrospectedColumn) var7.next();
            if (column == columnTmp) {
                field.addAnnotation("@Id");
                break;
            }
        }

        String columnName = column.getActualColumnName();
        if (StringUtility.stringContainsSpace(columnName) || introspectedTable.getTableConfiguration().isAllColumnDelimitingEnabled()) {
            columnName = column.getContext().getBeginningDelimiter() + columnName + column.getContext().getEndingDelimiter();
        }

        String blobText = "";
        if (column.isBLOBColumn()) {
            blobText = ",isBlob = true";
        }
        while (var7.hasNext()) {
            IntrospectedColumn columnTmp = (IntrospectedColumn) var7.next();
            if (column == columnTmp) {
                blobText += ",id = true";
                break;
            }
        }
        String annotation = "@Column(name = \"" + columnName + "\",jdbcType = JdbcType." + column.getJdbcTypeName() + blobText + ")";
        field.addAnnotation(annotation);
        if (column.isIdentity()) {
            if (introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement().equals("JDBC")) {
                field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
            } else {
                field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
            }
        } else if (column.isSequenceColumn()) {
            String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
            String sql = MessageFormat.format(introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement(), tableName, tableName.toUpperCase());
            field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY, generator = \"" + sql + "\")");
        }

        return super.modelFieldGenerated(field, topLevelClass, column, introspectedTable, modelClassType);
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("org.lx.mybatis.annotation.Entity");
        topLevelClass.addImportedType("org.lx.mybatis.annotation.Column");
        topLevelClass.addImportedType("org.apache.ibatis.type.JdbcType");
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        if (StringUtility.stringContainsSpace(tableName)) {
            tableName = this.context.getBeginningDelimiter() + tableName + this.context.getEndingDelimiter();
        }
        topLevelClass.addAnnotation("@Entity(tableName = \"" + tableName + "\")");
        return super.modelBaseRecordClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return super.modelGetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return super.modelPrimaryKeyClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return super.modelRecordWithBLOBsClassGenerated(topLevelClass, introspectedTable);
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return super.modelSetterMethodGenerated(method, topLevelClass, introspectedColumn, introspectedTable, modelClassType);
    }

    @Override
    public boolean validate(List<String> list) {
        return true;
    }
}
