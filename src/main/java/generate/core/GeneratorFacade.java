package generate.core;

import generate.entity.DataBase;
import generate.entity.Settings;
import generate.entity.Table;
import generate.utils.DataBaseUtils;
import generate.utils.PropertiesUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fei
 * @title: GeneratorFacade
 * @projectName codegenerate
 * @description: 采集数据, 根据模板完成代码生成
 * @date 2020/2/18 13:51
 */
public class GeneratorFacade {
    /**
     * 模板所在路径
     */
    private String templatePath;
    /**
     * 代码生成路径
     */
    private String outPath;
    /**
     * 工程配置对象
     */
    private Settings settings;
    /**
     * 数据库对象
     */
    private DataBase dataBase;
    /**
     * 核心代码生成器
     */
    private Generator generator;

    public GeneratorFacade(String templatePath, String outPath, Settings settings, DataBase dataBase) throws Exception {
        this.templatePath = templatePath;
        this.outPath = outPath;
        this.settings = settings;
        this.dataBase = dataBase;
        this.generator = new Generator(templatePath, outPath);
    }

    /**
     * 根据数据库生成代码
     */
    public void generatorByDataBase() throws Exception {
        List<Table> tableList = DataBaseUtils.getDbInfo(dataBase);
        for (Table table : tableList) {
            //准备数据模型
            Map<String, Object> dataModel = getDataModel(table);
            //调用代码生成核心处理类
            generator.scanTemplatesAndProcess(dataModel);
        }
    }

    /**
     * 根据表对象，获取数据模型
     *
     * @param table
     * @return
     */
    private Map<String, Object> getDataModel(Table table) {
        Map<String, Object> dataModel = new HashMap<String, Object>();
        //自定义配置
        dataModel.putAll(PropertiesUtils.customMap);
        //元数据
        dataModel.put("table", table);
        //setting
        dataModel.putAll(this.settings.getSettingMap());
        //实体名称
        dataModel.put("className", table.getName2());
        return dataModel;
    }


}
