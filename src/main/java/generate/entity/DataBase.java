package generate.entity;


/**
 * @author fei
 * @title: DataBase
 * @projectName codegenerate
 * @description: 数据库实体类
 * @date 2020/2/16 10:49
 */
public class DataBase {
    /**
     * 用于连接的地址，用于替换的
     */
    private static String mysqlUrl = "jdbc:mysql://[ip]:[port]/[db]?useUnicode=true&amp;characterEncoding=UTF8";
    /**
     * 用于连接的地址，用于替换的
     */
    private static String oracleUrl = "jdbc:oracle:thin:@[ip]:[port]:[db]";
    /**
     * 数据库类型(匹配后缀是否以MYSQL、ORACLE 后缀结束)
     */
    private String dbType;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String passWord;
    /**
     * 驱动
     */
    private String driver;
    /**
     * 真正的连接地址
     */
    private String url;

    public DataBase() {
    }

    /**
     * @param dbType 数据库类型
     * @param ip     ip地址（127.0.0.1）
     * @param port   端口（3306）
     * @param db     数据库名称
     */
    public DataBase(String dbType, String ip, String port, String db) {
        this.dbType = dbType;
        //oUpperCase() 方法将字符串小写字符转换为大写。
        if ("MYSQL".endsWith(dbType.toUpperCase())) {
            this.driver = "com.mysql.jdbc.Driver";
            this.url = mysqlUrl.replace("[ip]", ip).replace("[port]", port).replace("[db]", db);
        } else {
            this.driver = "oracle.jdbc.driver.OracleDriver";
            this.url = oracleUrl.replace("[ip]", ip).replace("[port]", port).replace("[db]", db);
        }
    }

    public DataBase(String dbType) {
        this(dbType, "127.0.0.1", "3306", "");
    }

    public DataBase(String dbType, String db) {
        this(dbType, "127.0.0.1", "3306", db);
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
