@echo off

rem --- get date ----
set yyyy=%DATE:~0,4%
set mm=%DATE:~5,2%
set dd=%DATE:~8,2%
set curdate=%yyyy%-%mm%-%dd%

set logFileName=mvn_log_%curdate%.txt

echo -----------%curdate% %time:~0,2%:%time:~3,2%:%time:~6,2%----------- > %logFileName%
echo -----------%curdate% %time:~0,2%:%time:~3,2%:%time:~6,2%-----------

@echo 默认打包 dev 版本,将执行 mvn clean package
echo 正在执行maven编译(%curdate%)...

call mvn clean package -U -X -DskipTests >> %logFileName%

echo ... 构建完成,请查看日志:  %logFileName%

pause
