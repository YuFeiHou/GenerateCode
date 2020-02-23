package generate.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author fei
 * @title: FileUtils 加载配置文件项
 * @projectName codegenerate
 * @description: 需要将自定义的配置信息写入到properties文件中，配置到相对于工程的properties文件夹下
 * @date 2020/2/16 10:49
 * @return map中的形式：textKey--textValue
 */
public class PropertiesUtils {

    public static Map<String, String> customMap = new HashMap<String, String>();

    static {
        File dir = new File("properties");
        try {
            //查询某个目录下的所有文件
            List<File> files = FileUtils.searchAllFile(new File(dir.getAbsolutePath()));
            for (File file : files) {
                if (file.getName().endsWith(".properties")) {
                    Properties prop = new Properties();
                    //使用properties对象加载输入流
                    prop.load(new FileInputStream(file));
                    customMap.putAll((Map) prop);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
