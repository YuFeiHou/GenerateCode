package generate.entity;

import java.util.List;

/**
 * @author fei
 * @title: Table
 * @projectName codegenerate
 * @description: 表实体
 * @date 2020/2/16 10:49
 */
public class Table {
    /**
     * 表名称
     */
    private String name;
    /**
     * 实体名称
     */
    private String name2;
    /**
     * 表介绍
     */
    private String comment;
    /**
     * 主键列
     */
    private String key;
    /**
     * 列集合
     */
    private List<Column> columns;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", name2='" + name2 + '\'' +
                ", comment='" + comment + '\'' +
                ", key='" + key + '\'' +
                ", columns=" + columns +
                '}';
    }
}
