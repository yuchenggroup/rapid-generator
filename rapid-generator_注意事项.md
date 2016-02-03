rapid-generator_注意事项
==

1. jdbc.url 中需要指定参数才能获取到MySQL表名注释: useInformationSchema=true

2. 解决获取注释的问题

3. setter 和 getter增加

4. 运行通过配置去除列名前缀

5. 能过滤特定前缀的表()



生成单个表:

gen T_CL_DICT_COMMON


生成所有:

gen *


