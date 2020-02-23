package generate.utils;


import generate.entity.Column;
import generate.entity.DataBase;
import generate.entity.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author fei
 * @title: DataBaseUtils
 * @projectName codegenerate
 * @description: 数据库工具类
 * @date 2020/2/16 10:49
 */
public class DataBaseUtils {
    /**
     * 获取数据库连接
     *
     * @param db 数据库信息
     * @return
     * @throws Exception
     */
    public static Connection getConnection(DataBase db) throws Exception {
        Properties props = new Properties();
        //获取数据库的备注信息
        props.put("remarksReporting", "true");
        props.put("user", db.getUserName());
        props.put("password", db.getPassWord());
        //注册驱动
        Class.forName(db.getDriver());
        return DriverManager.getConnection(db.getUrl(), props);
    }

    /**
     * 将表信息转化为实体类对象
     *
     * @param db 数据库信息对象
     * @return 元数据集合信息
     * @throws Exception
     */
    public static List<Table> getDbInfo(DataBase db) throws Exception {
        //创建连接
        Connection connection = getConnection(db);
        //获取元数据
        DatabaseMetaData metaData = connection.getMetaData();
        //获取所有的数据库表信息
        ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
        //表集合
        List<Table> tableList = new ArrayList<Table>();

        //拼装table
        while (tables.next()) {
            Table table = new Table();
            String tableName = tables.getString("TABLE_NAME");
            ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
            String keys = "";
            while (primaryKeys.next()) {
                String keyNames = primaryKeys.getString("COLUMN_NAME");
                keys += keyNames + ",";
            }
            table.setKey(keys);
            table.setName(tableName);
            table.setName2(removePrefix(tableName));
            table.setComment(tables.getString("REMARKS"));
            //处理当前表中的所有字段
            ResultSet columns = metaData.getColumns(null, null, tableName, null);
            List<Column> columnList = new ArrayList<Column>();
            while (columns.next()) {
                Column column = new Column();
                String columnName = columns.getString("COLUMN_NAME");
                column.setColumnName(columnName);
                String attName = StringUtils.toJavaVariableName(columnName);
                column.setColumnName2(attName);
                //数据库类型与java类型
                String typeName = columns.getString("TYPE_NAME");
                column.setColumnDbType(typeName);
                String javaType = PropertiesUtils.customMap.get(typeName);
                column.setColumnType(javaType);
                //备注
                String columnRemarks = columns.getString("REMARKS");
                column.setColumnComment(columnRemarks);
                //是否主键
                String pri = "";
                if (StringUtils.contains(columnName, keys.split(","))) {
                    pri = "PRI";
                }
                column.setColumnKey(pri);
                columnList.add(column);
            }
            columns.close();
            table.setColumns(columnList);
            tableList.add(table);
        }
        tables.close();
        connection.close();
        return tableList;
    }

    /**
     * 获取数据库列表
     *
     * @param db 数据库信息
     * @return
     * @throws Exception
     */
    public static List<String> getSchemas(DataBase db) throws Exception {
        //获取元数据
        Connection connection = getConnection(db);
        DatabaseMetaData metaData = connection.getMetaData();
        //获取所有数据库列表
        ResultSet rs = metaData.getCatalogs();
        List<String> list = new ArrayList<String>();
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        rs.close();
        connection.close();
        return list;
    }

    /**
     * 将表名转换为实体名称(db_user_name --> userName)
     *
     * @param tableName
     * @return
     */
    public static String removePrefix(String tableName) {
        //获取前缀
        String prefix = PropertiesUtils.customMap.get("tableRemovePrefixes");
        String replaceTableName = tableName;
        for (String pf : prefix.split(",")) {
            replaceTableName = StringUtils.removePrefix(replaceTableName, pf, true);
        }
        return StringUtils.makeAllWordFirstLetterUpperCase(replaceTableName);
    }

    public static void main(String[] args) {
        DataBase dataBase = new DataBase("MYSQL","o2o");
        dataBase.setUserName("root");
        dataBase.setPassWord("123456");
        try {
            List<Table> tableList =  DataBaseUtils.getDbInfo(dataBase);
            for(Table table : tableList){
                System.out.println(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
