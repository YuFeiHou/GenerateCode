package generate.core;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import generate.utils.FileUtils;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fei
 * @title: Generator
 * @projectName codegenerate
 * @description: 代码生成器核心处理类
 * @date 2020/2/18 14:01
 */
public class Generator {
    /**
     * 模板所在路径
     */
    private String templatePath;
    /**
     * 代码生成路径
     */
    private String outPath;
    /**
     * freemarker解析器
     */
    private Configuration conf;

    public Generator(String templatePath, String outPath) throws Exception {
        this.templatePath = templatePath;
        this.outPath = outPath;
        //创建freemarker的核心配置类
        conf = new Configuration();
        //指定模板加载器
        conf.setTemplateLoader(new FileTemplateLoader(new File(templatePath)));
    }

    /**
     * 扫描所有模板并进行代码生成
     *
     * @param dataMap 数据模型
     * @throws Exception
     */
    public void scanTemplatesAndProcess(Map dataMap) throws Exception {
        //加载文件夹下的所有模板文件
        List<File> srcFiles = FileUtils.searchAllFile(new File(templatePath));
        //针对每一个模板文件进行代码生成
        for (File srcFile : srcFiles) {
            executeGenerate(dataMap, srcFile);
        }
    }

    /**
     * 对某个模板生成代码
     *
     * @param dataMap 数据模型
     * @param srcFile 模板文件
     * @throws Exception
     */
    private void executeGenerate(Map dataMap, File srcFile) throws Exception {
        //获取文件路径（完整路径）
        String templateFile = srcFile.getAbsolutePath().replace(this.templatePath, "");
        //对文件名称进行处理（字符串替换）
        String outputFilepath = processTemplateString(templateFile, dataMap);
        //读取模板
        Template template = conf.getTemplate(templateFile);
        //设置字符集
        template.setOutputEncoding("encode");
        //创建文件
        File outFile = FileUtils.mkdir(outPath, outputFilepath);
        FileWriter fileWriter = new FileWriter(outFile);
        //模板生成
        template.process(dataMap, fileWriter);
        fileWriter.close();
    }

    /**
     * 处理字符串模板
     *
     * @param templateString 替换的包名
     * @param dataMap        数据模型
     * @return
     * @throws Exception
     */
    private String processTemplateString(String templateString, Map dataMap) throws Exception {
        //替换内容输出
        StringWriter out = new StringWriter();
        Template template = new Template("ts", new StringReader(templateString), conf);
        template.process(dataMap, out);
        return out.toString();
    }

    public static void main(String[] args) {
        String templatePath = "F:\\代码生成\\模板";
        String outPath = "F:\\代码生成\\生成\\dar";
        try {
            Generator generator = new Generator(templatePath, outPath);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("username", "侯宇飞");
            generator.scanTemplatesAndProcess(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
