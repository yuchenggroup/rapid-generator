
#export CLASSPATH=.:./lib/*

java -classpath '.:./lib/*' -Xms128m -Xmx384m cn.org.rapid_framework.generator.ext.CommandLine -DtemplateRootDir=template
