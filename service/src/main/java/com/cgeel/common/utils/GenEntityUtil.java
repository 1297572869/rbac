package com.cgeel.common.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class GenEntityUtil {

    private String[] colnames; // 列名数组
    private String[] colTypes; // 列名类型数组
    private String[] originNames; // 原始列名
    private String primaryKey; //主键字段
    private int[] colSizes; // 列名大小数组
    private boolean f_util = false; // 是否需要导入包java.util.*
    private boolean f_sql = false; // 是否需要导入包java.sql.*
    private String modelPackagePath;
    private String tableNameFull;
    private String tableName;
    private String mapperPackagePath;
    private String xmlPackagePath;
    private String mapperExtends;
    private String serviceInterPackagePath;
    private String serviceImplPackagePath;

    private String controllerPackagePath;
    private String consolePagePath;
    private String consoleScriptPath;

    private String url = "jdbc:mysql://192.168.199.34:3306/rbac?characterEncoding=UTF-8&amp;autoReconnect=true";
    private String username = "root";
    private String password = "111111";

    private String conScriptDir = "D:\\rbac\\console\\src\\main\\webapp\\";
    private String conPageDir = "D:\\rbac\\console\\src\\main\\webapp\\";
    private String conDir = "D:\\rbac\\console\\src\\main\\java\\";
    private String srcDir = "D:\\rbac\\service\\src\\main\\java\\";
    private String resourceDir = "D:\\rbac\\service\\src\\main\\resources\\";


    private String prefix = "";

    public GenEntityUtil(String modelPackagePath, String tableName, String mapperPackagePath,
                         String xmlPackagePath, String mapperExtends,
                         String serviceInterPackagePath, String serviceImplPackagePath,
                         String controllerPackagePath,String consolePagePath,String consoleScriptPath) {
        this.modelPackagePath = modelPackagePath;
        this.tableNameFull = tableName;
        this.tableName = tableName.replaceAll(prefix, "");
        this.mapperPackagePath = mapperPackagePath;
        this.xmlPackagePath = xmlPackagePath;
        this.mapperExtends = mapperExtends;
        this.serviceInterPackagePath = serviceInterPackagePath;
        this.serviceImplPackagePath = serviceImplPackagePath;
        this.controllerPackagePath = controllerPackagePath;
        this.consolePagePath = consolePagePath;
        this.consoleScriptPath = consoleScriptPath;
    }

    public void genEntity(){

        try {
            Class.forName("com.mysql.jdbc.Driver") ;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null; // 得到数据库连接
        try {
            conn = DriverManager.getConnection(url, username, password);
            ResultSet rs = conn.getMetaData().getPrimaryKeys(null, null, tableNameFull);
            while( rs.next() ) {
                primaryKey = rs.getObject(4).toString();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PreparedStatement pstmt = null;
        String strsql = "select * from " + tableNameFull;
        try {
            pstmt = conn.prepareStatement(strsql);
            ResultSetMetaData rsmd = pstmt.getMetaData();
            int size = rsmd.getColumnCount(); // 共有多少列
            colnames = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            originNames = new String[size];
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                colnames[i] = this.getCamelStr(rsmd.getColumnName(i + 1));
                originNames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
                if (colTypes[i].equalsIgnoreCase("datetime")) {
                    f_util = true;
                }
                if (colTypes[i].equalsIgnoreCase("image")
                        || colTypes[i].equalsIgnoreCase("text")) {
                    f_sql = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            try {
                String content = parse(colnames, colTypes, colSizes, modelPackagePath, tableName);
                String path = System.getProperty("user.dir") + "/src/main/java/" + modelPackagePath.replaceAll("\\.", "/");
                if(srcDir != null && !srcDir.trim().equals("")){
                    path = srcDir + modelPackagePath.replaceAll("\\.", "/");
                }
                File file = new File(path);
                if(!file.exists()){
                    file.mkdirs();
                }
                String resPath = path+"/"+initcap(tableName) + ".java";
                System.out.println("resPath=" + resPath);
                File file1 = new File(resPath);
                if(file1.exists()){
                    System.err.println("=============文件已存在，请删除后再试================");
                    System.exit(-1);
                }
                FileUtils.writeStringToFile(new File(resPath), content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                assert pstmt != null;
                assert conn != null;
                pstmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 创建Mapper
     */
    public void genEntityMapper(){
        StringBuilder sb = new StringBuilder();
        sb.append("package " + mapperPackagePath + ";\r\n\r\n");
        sb.append("import " + mapperExtends + ";\r\n");
        sb.append("import "+modelPackagePath + "." + initcap(tableName) +";\r\n\r\n\r\n");
        //导入注解
        sb.append("import java.util.Map;\r\n\r\n");
        sb.append("import java.util.List;\r\n\r\n");

        sb.append("public interface " + initcap(tableName) + "Mapper extends "+mapperExtends+"<"+initcap(tableName)+"> {\r\n\r\n");
        //后台分页mapper
        sb.append("     Integer  "+tableName+"_count(Map<String, Object> map);\r\n\r\n");
        sb.append("     List<Map<String, Object>> "+tableName+"_list(Map<String, Object> map);\r\n\r\n");

        sb.append("}\r\n");

        try {
            String content = sb.toString();
            String path = System.getProperty("user.dir") + "/src/main/java/" + mapperPackagePath.replaceAll("\\.", "/");
            if(srcDir != null && !srcDir.trim().equals("")){
                path = srcDir + mapperPackagePath.replaceAll("\\.", "/");
            }
            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            String resPath = path+"/"+initcap(tableName) + "Mapper.java";
            System.out.println("resPath=" + resPath);
            File file1 = new File(resPath);
            if(file1.exists()){
                System.err.println("=============文件已存在，请删除后再试================");
                System.exit(-1);
            }
            FileUtils.writeStringToFile(new File(resPath), content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
        xml.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\r\n");
        xml.append("        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n");
        xml.append("<mapper namespace=\""+mapperPackagePath + "." + initcap(tableName) + "Mapper\">\r\n\r\n");
        //后台分页sql
        xml.append("    <select id=\""+tableName+"_count\" resultType=\"Integer\">\r\n");
        xml.append("    </select>\r\n");
        xml.append("    <select id=\""+tableName+"_list\" resultType=\"java.util.Map\">\r\n");
        xml.append("    </select>\r\n");

        xml.append("</mapper>");

        try {
            String content = xml.toString();
            String path = System.getProperty("user.dir") + "/src/main/resources/" + xmlPackagePath.replaceAll("\\.", "/");
            if(resourceDir != null && !resourceDir.trim().equals("")){
                path = resourceDir + xmlPackagePath.replaceAll("\\.", "/");
            }
            File file = new File(path);
            if(!file.exists()){
                file.mkdirs();
            }
            String resPath = path+"/"+initcap(tableName).toLowerCase() + "-mapper.xml";
            System.out.println("resPath=" + resPath);
            File file1 = new File(resPath);
            if(file1.exists()){
                System.err.println("=============文件已存在，请删除后再试================");
                System.exit(-1);
            }
            FileUtils.writeStringToFile(new File(resPath), content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建Controller
     */
    private void genController() {
        StringBuilder sb = new StringBuilder();
        //包名
        sb.append("package " + controllerPackagePath + ";\r\n\r\n");
        //导入注解
        sb.append("import com.cgeel.common.utils.BeanUtils;\r\n");
        sb.append("import com.cgeel.common.utils.DateUtils;\r\n");
        sb.append("import java.beans.IntrospectionException;\r\n");
        sb.append("import java.text.ParseException;\r\n");

        //@Controller注解
        sb.append("@Controller\r\n");
        //接口名称
        sb.append("public class " + initcap(tableName) + "Controller {\r\n\r\n");
        sb.append("     @Autowired\r\n");
        sb.append("     private " + initcap(tableName) + "Service " + tableName + "Service;\r\n\r\n");
        sb.append("     @Autowired\r\n");
        sb.append("     private " + initcap(tableName) + "Mapper " + tableName + "Mapper;\r\n\r\n");
        //后台返回页面
        sb.append("     @RequestMapping(value = \"/admin/"+tableName+".do\", method = RequestMethod.GET)\r\n");
        sb.append("     public String "+tableName+"(ModelMap modelMap) {\r\n");
        sb.append("         return \"/admin/"+tableName+"\";\r\n");
        sb.append("     }\r\n\r\n");
        //后台分页
        sb.append("     @RequestMapping(value = \"/admin/"+tableName+"_list.do\", method = RequestMethod.GET)\r\n");
        sb.append("     @ResponseBody\r\n");
        sb.append("     public DataTablePaginator "+tableName+"_list(DataTableParam param, "+initcap(tableName)+" "+tableName+", String starttime, String endtime) throws IllegalAccessException, IntrospectionException, InvocationTargetException, ParseException{\r\n");
        sb.append("         Map<String, Object> map= BeanUtils.BeanToMap("+tableName+");\r\n");
        sb.append("         map.put(\"param\",param);\r\n");
        sb.append("         Long start = DateUtils.getTimeMillisbyDate(starttime,\"yyyy/MM/dd\");\r\n");
        sb.append("         Long end =DateUtils.getTimeMillisbyDate(endtime,\"yyyy/MM/dd\");\r\n");
        sb.append("         map.put(\"start\",start);\r\n");
        sb.append("         map.put(\"end\",end);\r\n");
        sb.append("         DataTablePaginator p =  "+tableName+"Service."+tableName+"_list(map);\r\n");
        sb.append("         return p;\r\n");
        sb.append("     }\r\n\r\n");
        //后台添加编辑
        sb.append("     @RequestMapping(value = \"/admin/add_edit_"+tableName+".do\", method = RequestMethod.POST)\r\n");
        sb.append("     @ResponseBody\r\n");
        sb.append("     public RestResult add_edit_"+tableName+"("+initcap(tableName)+" "+tableName+") {\r\n");
        sb.append("         if ("+tableName+".getId()!=null){\r\n");
        sb.append("         }else{\r\n");
        sb.append("         }\r\n");
        sb.append("         return RestResult.SUCCESS();\r\n");
        sb.append("     }\r\n\r\n");
        //后台id查询
        sb.append("     @RequestMapping(value = \"/admin/get_"+tableName+".do\", method = RequestMethod.GET)\r\n");
        sb.append("     @ResponseBody\r\n");
        sb.append("     public RestResult get_"+tableName+"(Integer "+tableName+"Id) {\r\n");
        sb.append("         return RestResult.SUCCESS();\r\n");
        sb.append("     }\r\n\r\n");

        sb.append("}\r\n");
        try {
            String content = sb.toString();
            String path = System.getProperty("user.dir") + "/src/main/java/"
                    + controllerPackagePath.replaceAll("\\.", "/");
            if (conDir != null && !conDir.trim().equals("")) {
                path = conDir + controllerPackagePath.replaceAll("\\.", "/");
            }
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String resPath = path + "/" + initcap(tableName) + "Controller.java";
            System.out.println("resPath=" + resPath);
            File file1 = new File(resPath);
            if (file1.exists()) {
                System.err.println("=============文件" + resPath + "已存在 ================");
                System.exit(-1);
            }
            FileUtils.writeStringToFile(new File(resPath), content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }




    /**
     * 创建Service接口
     */
    private void genServiceInter() {
        StringBuilder sb = new StringBuilder();
        //包名
        sb.append("package " + serviceInterPackagePath + ";\r\n\r\n");
        //导入注解
        sb.append("import java.util.Map;\r\n\r\n");
        //接口名称
        sb.append("public interface " + initcap(tableName) + "Service {\r\n\r\n");
        //后台分页接口
        sb.append("     public DataTablePaginator "+tableName+"_list(Map<String, Object> map);\r\n\r\n");

        sb.append("}\r\n");

        try {
            String content = sb.toString();
            String path = System.getProperty("user.dir") + "/src/main/java/"
                    + serviceInterPackagePath.replaceAll("\\.", "/");
            if (srcDir != null && !srcDir.trim().equals("")) {
                path = srcDir + serviceInterPackagePath.replaceAll("\\.", "/");
            }
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String resPath = path + "/" + initcap(tableName) + "Service.java";
            System.out.println("resPath=" + resPath);
            File file1 = new File(resPath);
            if (file1.exists()) {
                System.err.println("=============文件" + resPath + "已存在 ================");
                System.exit(-1);
            }
            FileUtils.writeStringToFile(new File(resPath), content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建Service实现
     */
    private void genServiceImpl() {
        StringBuilder sb = new StringBuilder();
        //包名
        sb.append("package " + serviceImplPackagePath + ";\r\n\r\n");
        //导入注解
        sb.append("import java.util.Map;\r\n\r\n");

        //导入接口
        sb.append("import " + serviceInterPackagePath + "." + initcap(tableName)+ "Service;\r\n\r\n");
        //@Service注解
        sb.append("@Service\r\n");
        //实现类名称
        sb.append("public class " + initcap(tableName) + "ServiceImpl implements " + initcap(tableName) + "Service {\r\n\r\n");
        //后台分页实现
        sb.append("     @Autowired\r\n");
        sb.append("     private "+initcap(tableName)+"Mapper "+tableName+"Mapper;\r\n");
        sb.append("     @Override\r\n");
        sb.append("     public DataTablePaginator "+tableName+"_list(Map<String, Object> map) {\r\n");
        sb.append("         DataTableParam param = (DataTableParam) map.get(\"param\");\r\n");
        sb.append("         DataTablePaginator paginator = new DataTablePaginator(param);\r\n");
        sb.append("         int count = "+tableName+"Mapper."+tableName+"_count(map);\r\n");
        sb.append("         List<Map<String, Object>> list = "+tableName+"Mapper."+tableName+"_list(map);\r\n");
        sb.append("         paginator.setiTotalDisplayRecords(count);\r\n");
        sb.append("         paginator.setiTotalRecords(count);\r\n");
        sb.append("         paginator.setAaData(list);\r\n");
        sb.append("         return paginator;\r\n");
        sb.append("     }\r\n\r\n");


        sb.append("}\r\n");

        try {
            String content = sb.toString();
            String path = System.getProperty("user.dir") + "/src/main/java/"
                    + serviceImplPackagePath.replaceAll("\\.", "/");
            if (srcDir != null && !srcDir.trim().equals("")) {
                path = srcDir + serviceImplPackagePath.replaceAll("\\.", "/");
            }
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String resPath = path + "/" + initcap(tableName) + "ServiceImpl.java";
            System.out.println("resPath=" + resPath);
            File file1 = new File(resPath);
            if (file1.exists()) {
                System.err.println("=============文件" + resPath + "已存在 ================");
                System.exit(-1);
            }
            FileUtils.writeStringToFile(new File(resPath), content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 创建后台页面
     */
    private void genAdminPage() {
        StringBuilder sb = new StringBuilder();

        try {
            String content = sb.toString();
            String path = System.getProperty("user.dir") + "/src/main/webapp/"
                    + consolePagePath.replaceAll("\\.", "/");
            if (conPageDir != null && !conPageDir.trim().equals("")) {
                path = conPageDir + consolePagePath.replaceAll("\\.", "/");
            }
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String resPath = path + "/" + tableName + ".html";
            System.out.println("resPath=" + resPath);
            File file1 = new File(resPath);
            if (file1.exists()) {
                System.err.println("=============文件" + resPath + "已存在 ================");
                System.exit(-1);
            }
            FileUtils.writeStringToFile(new File(resPath), content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 创建后台js
     */
    private void genAdminScript() {
        StringBuilder sb = new StringBuilder();

        try {
            String content = sb.toString();
            String path = System.getProperty("user.dir") + "/src/main/webapp/"
                    + consoleScriptPath.replaceAll("\\.", "/");
            if (conScriptDir != null && !conScriptDir.trim().equals("")) {
                path = conScriptDir + consoleScriptPath.replaceAll("\\.", "/");
            }
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String resPath = path + "/" + tableName + "Controller.js";
            System.out.println("resPath=" + resPath);
            File file1 = new File(resPath);
            if (file1.exists()) {
                System.err.println("=============文件" + resPath + "已存在 ================");
                System.exit(-1);
            }
            FileUtils.writeStringToFile(new File(resPath), content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }





    /**
     * 解析处理(生成实体类主体代码)
     */
    private String parse(String[] colNames, String[] colTypes, int[] colSizes, String packagePath, String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + packagePath + ";\r\n\r\n");
        if (f_util) {
            sb.append("import java.util.Date;\r\n");
        }
        if (f_sql) {
            sb.append("import java.sql.*;\r\n\r\n\r\n");
        }
        sb.append("import javax.persistence.*;\r\n\r\n\r\n");
        sb.append("@Entity\r\n@Table(name = \""+tableNameFull+"\")" + "\r\n");
        sb.append("public class " + initcap(tableName) + " {\r\n\r\n");
        processAllAttrs(sb);
        sb.append("\r\n");
        processAllMethod(sb);
        sb.append("}\r\n");
        return sb.toString();

    }

    /**
     * 生成所有的方法
     *
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tpublic void set" + initcap(colnames[i]) + "("
                    + sqlType2JavaType(colTypes[i]) + " " + colnames[i]
                    + "){\r\n");
            sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
            sb.append("\t}\r\n\r\n");

            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get"
                    + initcap(colnames[i]) + "(){\r\n");
            sb.append("\t\treturn " + colnames[i] + ";\r\n");
            sb.append("\t}\r\n\r\n");
        }
    }

    /**
     * 解析输出属性
     *
     * @return
     */
    private void processAllAttrs(StringBuffer sb) {
        for (int i = 0; i < colnames.length; i++) {
            if(originNames[i].equals(primaryKey)){
                sb.append("\t@Id\r\n");
            }
            sb.append("\t@Column(name=\""+originNames[i]+"\")\r\n");
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + ";\r\n");
        }
    }

    /**
     * 把输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    private String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0]-32);
        }
        return this.getCamelStr(new String(ch));
    }

    //例：user_name --> userName
    private String getCamelStr(String s){
        while(s.indexOf("_")>0){
            int index = s.indexOf("_");
            //System.out.println(s.substring(index+1, index+2).toUpperCase());
            s = s.substring(0, index) + s.substring(index+1, index+2).toUpperCase() + s.substring(index+2);
        }
        return s;
    }

    private String sqlType2JavaType(String sqlType) {
        if (sqlType.equalsIgnoreCase("bit")) {
            return "Boolean";
        } else if (sqlType.equalsIgnoreCase("tinyint")) {
            return "Byte";
        } else if (sqlType.equalsIgnoreCase("smallint")) {
            return "Short";
        } else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("integer")) {
            return "Integer";
        } else if (sqlType.equalsIgnoreCase("bigint")) {
            return "Long";
        } else if (sqlType.equalsIgnoreCase("float")) {
            return "Float";
        } else if (sqlType.equalsIgnoreCase("decimal")
                || sqlType.equalsIgnoreCase("numeric")
                || sqlType.equalsIgnoreCase("real")
                || sqlType.equalsIgnoreCase("double")
                ) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("money")
                || sqlType.equalsIgnoreCase("smallmoney")) {
            return "Double";
        } else if (sqlType.equalsIgnoreCase("varchar")
                || sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar")
                || sqlType.equalsIgnoreCase("nchar")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("datetime")) {
            return "Date";
        }

        else if (sqlType.equalsIgnoreCase("image")) {
            return "Blob";
        } else if (sqlType.equalsIgnoreCase("text")) {
            return "Clob";
        }
        return null;
    }

    public static void main(String[] args) {
        String packagePath = "com.cgeel.model";
        String mapperPackagePath = "com.cgeel.persistence";
        String xmlPackagePath = "mybatis.mappers";
        String mapperExtends = "com.cgeel.common.mybatis.BaseMapper";
        String serviceInterPackagePath = "com.cgeel.service";
        String serviceImplPackagePath = "com.cgeel.service.impl";
        String controllerPackagePath = "com.cgeel.controller";
        String consolePagePath = "WEB-INF/html/admin";
        String consoleScriptPath = "assets/scripts/admin";

        String tableName = "auth_role_function";
        GenEntityUtil genEntityUtil = new GenEntityUtil(
                packagePath,tableName, mapperPackagePath,
                xmlPackagePath, mapperExtends, serviceInterPackagePath, serviceImplPackagePath,controllerPackagePath,consolePagePath,consoleScriptPath);
        genEntityUtil.genEntity();
        genEntityUtil.genEntityMapper();
        genEntityUtil.genServiceInter();
        genEntityUtil.genServiceImpl();
        genEntityUtil.genController();
        genEntityUtil.genAdminPage();
        genEntityUtil.genAdminScript();
    }




}
