package com.cxs.code.generator.Helper;

import com.cxs.code.generator.model.ConfigContext;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:数据库连接工具
 *
 * @author:cxs
 * @date: 2019/3/20 10:04
 */
public class DBHelper {

  private ConfigContext context;
  private DataSource dataSource;
  private QueryRunner queryRunner;

  public DBHelper(ConfigContext context) {
    this.context = context;
    dataSource = initDataSource(this.context);
    queryRunner = new QueryRunner(dataSource);
  }

  private DataSource initDataSource(ConfigContext context) {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName(context.getDriver());
    dataSource.setUrl(context.getUrl());
    dataSource.setUsername(context.getUserName());
    dataSource.setPassword(context.getPassword());
    return dataSource;
  }

  public List<Map<String, Object>> descTable() {

    String DESC_TABLE = String.format("desc %s", context.getTargetTable());

    return queryMapList(DESC_TABLE, null);
  }

  public List<Map<String, Object>> queryMapList(String sql, Object... params) {
    List<Map<String, Object>> fieldMapList;
    try {
      fieldMapList = queryRunner.query(sql, new MapListHandler(), params);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return fieldMapList;
  }

  public Map<String, List<Map<String, Object>>> getDescTables() {
//    String sql = String.format("select table_name from information_schema.tables where table_schema= %s  and table_type = 'base table'", "'" + context.getSourceDatabase() + "'");
    String sql = String.format("select table_name from information_schema.tables where table_schema= %s  ", "'" + context.getSourceDatabase() + "'");
    List<String> tableNames;
    try {
      tableNames = queryRunner.query(sql, new ColumnListHandler<>());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    Map<String, List<Map<String, Object>>> map = new HashMap<>();
    for (String tableName : tableNames) {
      map.put(tableName, descTable(tableName));
    }
    return map;
  }

  public List<Map<String, Object>> descTable(String targetTable) {

    String DESC_TABLE = String.format("desc %s", targetTable);

    return queryMapList(DESC_TABLE, null);
  }
}
