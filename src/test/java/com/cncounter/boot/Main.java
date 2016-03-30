package com.cncounter.boot;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.util.ArrayHelper;
import cn.org.rapid_framework.generator.util.SystemHelper;

import java.io.File;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) throws Exception {
        test();
        Thread.currentThread().getId();
        HashMap<?,?> hashMap = null;
    }

    public static void test() throws Exception {
        //disable freemarker logging
        freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
        //
        GeneratorFacade facade = new GeneratorFacade();
        String[] args = {"dict_common"};
        if(args.length == 0) return;
        facade.g.setIncludes(getIncludes(args,1));
        facade.generateByTable(args[0],getTemplateRootDir());
        //
        checkAndMakeOutRootDirs();
        if(SystemHelper.isWindowsOS) {
            // renfufei 处理目录不存在的问题
            String outRoot = GeneratorProperties.getRequiredProperty("outRoot").replace('/', File.separatorChar);
            // 打开目录
            Runtime.getRuntime().exec("cmd.exe /c start "+ outRoot);
        }
    }

    private static void checkAndMakeOutRootDirs(){
            // renfufei 处理目录不存在的问题
            String outRoot = GeneratorProperties.getRequiredProperty("outRoot").replace('/', File.separatorChar);
            //
            File outRootDir = new File(outRoot);
            if(!outRootDir.exists()){
                outRootDir.mkdirs();
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
