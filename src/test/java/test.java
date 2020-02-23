import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;

/**
 * @author fei
 * @title: test
 * @projectName codegenerate
 * @description: TODO
 * @date 2020/2/1710:20
 */
public class test {
    private Connection conn;
    @Before
    public void init() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Properties props =new Properties();
        //设置连接属性,使得可获取到表的REMARK(备注)
        props.put("remarksReporting","true");
        props.put("user", "root");
        props.put("password", "123456");
        conn = java.sql.DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/o2o?useUnicode=true&amp;characterEncoding=UTF8", props);
    }
    @Test
    public void test() throws Exception {
        String sql = "SELECT * FROM `tb_area` where area_id=?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "1");
        //执行sql语句
        ResultSet rs = pstmt.executeQuery() ;
        //获取ResultSetMetaData对象
        ResultSetMetaData metaData = rs.getMetaData();
        //获取查询字段数量
        int columnCount = metaData.getColumnCount() ;
        for (int i=1;i<=columnCount;i++) {
            //获取表名称
            String columnName = metaData.getColumnName(i);
            //获取java类型
            String columnClassName = metaData.getColumnClassName(i);
            //获取sql类型
            String columnTypeName = metaData.getColumnTypeName(i);
            System.out.println(columnName);
            System.out.println(columnClassName);
            System.out.println(columnTypeName);
        }
        System.out.println(columnCount);
    }
}
