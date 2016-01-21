package com.cncounter.boot;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.util.ArrayHelper;
import cn.org.rapid_framework.generator.util.SystemHelper;

import java.io.File;

public class Main {

    public static void main(String[] args) throws Exception {
        test();
    }

    public static void test() throws Exception {
        //disable freemarker logging
        freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
        //
        GeneratorFacade facade = new GeneratorFacade();
        String[] args = {"T_CL_DICT_COMMON"};
        if(args.length == 0) return;
        facade.g.setIncludes(getIncludes(args,1));
        facade.generateByTable(args[0],getTemplateRootDir());
        if(SystemHelper.isWindowsOS) {
            // renfufei 处理目录不存在的问题
            String outRoot = GeneratorProperties.getRequiredProperty("outRoot").replace('/', '\\');
            //
            File outRootDir = new File(outRoot);
            if(!outRootDir.exists()){
                outRootDir.mkdirs();
            }
            // 打开目录
            Runtime.getRuntime().exec("cmd.exe /c start "+ outRoot);
        }
    }

    private static String getIncludes(String[] args, int i) {
        String includes = ArrayHelper.getValue(args, i);
        if(includes == null) return null;
        return includes.indexOf("*") >= 0 || includes.indexOf(",") >= 0 ? includes : includes+"/**";
    }

    private static String getTemplateRootDir() {
        return System.getProperty("templateRootDir", "template");
    }
}
