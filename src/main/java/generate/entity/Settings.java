package generate.entity;


import generate.utils.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fei
 * @title: Settings
 * @projectName codegenerate
 * @description: 代码生成配置信息
 * @date 2020/2/16 10:49
 */
public class Settings {
    /**
     * 项目名称
     */
    private String project = "example";
    /**
     * 包路径
     */
    private String pPackage = "com.example.demo";
    /**
     * 项目描述
     */
    private String projectComment;
    /**
     * 作者
     */
    private String author;
    /**
     * 项目包路径（父）
     */
    private String path1 = "com";
    /**
     * 项目包路径（子）
     */
    private String path2 = "example";
    /**
     * 项目包路径（子）
     */
    private String path3 = "demo";
    /**
     * 代码生成路径
     */
    private String pathAll;

    /**
     * 根据设置信息生成相应的代码
     *
     * @param project        项目名
     * @param pPackage       包名
     * @param projectComment 项目描述
     * @param author         作者
     */
    public Settings(String project, String pPackage, String projectComment, String author) {
        if (StringUtils.isNotBlank(project)) {
            this.project = project;
        }
        if (StringUtils.isNotBlank(pPackage)) {
            this.pPackage = pPackage;
        }
        this.projectComment = projectComment;
        this.author = author;
        String[] paths = pPackage.split("\\.");
        path1 = paths[0];
        path2 = paths.length > 1 ? paths[1] : path2;
        path3 = paths.length > 2 ? paths[2] : path3;
        pathAll = pPackage.replaceAll(".", "/");
    }

    /**
     * 获取用户设置的一些基本信息
     *
     * @return
     */
    public Map<String, Object> getSettingMap() {
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] declaredFields = Settings.class.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(this));
            } catch (Exception e) {
            }
        }
        return map;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getpPackage() {
        return pPackage;
    }

    public void setpPackage(String pPackage) {
        this.pPackage = pPackage;
    }

    public String getProjectComment() {
        return projectComment;
    }

    public void setProjectComment(String projectComment) {
        this.projectComment = projectComment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPath1() {
        return path1;
    }

    public void setPath1(String path1) {
        this.path1 = path1;
    }

    public String getPath2() {
        return path2;
    }

    public void setPath2(String path2) {
        this.path2 = path2;
    }

    public String getPath3() {
        return path3;
    }

    public void setPath3(String path3) {
        this.path3 = path3;
    }

    public String getPathAll() {
        return pathAll;
    }

    public void setPathAll(String pathAll) {
        this.pathAll = pathAll;
    }
}
