package com.cxs.code.generator.controller;

import com.cxs.code.generator.Helper.ColumnHelper;
import com.cxs.code.generator.Helper.DBHelper;
import com.cxs.code.generator.model.ColumnDefinition;
import com.cxs.code.generator.model.Config;
import com.cxs.code.generator.model.ConfigContext;
import com.cxs.code.generator.service.Callback;
import com.cxs.code.generator.util.StringUtil;
import com.cxs.code.generator.util.ZipUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/3/19 16:00
 */
@Configuration
@Controller
@RequestMapping("code")
public class Hello {

  private final String JDBCDRIVER = "com.mysql.cj.jdbc.Driver";
  private final String MYSQLJDBCURLHEADER = "jdbc:mysql://";
  private final String SERVERTIMEZONE = "?serverTimezone=UTC&useSSL=false";
  @Autowired
  private Callback callback;
  @Autowired
  private Config configuration;


  private void initVelocity(){
    //配置velocity的资源加载路径
    Properties velocityPros = new Properties();
    velocityPros.setProperty("file.resource.loader.class", configuration.getClassLoader());
    velocityPros.setProperty(Velocity.ENCODING_DEFAULT,"UTF8");
    velocityPros.setProperty(Velocity.INPUT_ENCODING,"UTF8");
    velocityPros.setProperty(Velocity.OUTPUT_ENCODING,"UTF8");
    Velocity.init(velocityPros);
  }

  private void initContext(VelocityContext context,ConfigContext configContext, Object data){
    context.put("package", configContext.getTargetPackage().replaceAll("\\\\", "."));
    context.put("columns", data);
    context.put("entity", configContext.getTargetEntity());
    context.put("service", configContext.getTargetService());
    context.put("baseService", configContext.getServiceBase().replaceAll("\\\\", "."));
    context.put("baseServiceImpl", configContext.getServiceBaseImpl().replaceAll("\\\\", "."));
    context.put("entityBase",configContext.getEntityBase().replaceAll("\\\\", "."));
    context.put("serviceImpl", configContext.getTargetServiceImpl().replaceAll("\\\\", "."));
    context.put("controller", configContext.getTargetController());
    context.put("dao", configContext.getTargetDao());
    context.put("baseDao", configContext.getDaoBase().replaceAll("\\\\", "."));
    context.put("enums",configContext.getEntityEnum().replaceAll("\\\\", "."));
  }


  /**
   * 根据表名生成代码
   *
   * @param configContext
   * @return
   */
  @RequestMapping(value = "generateByTable", method = RequestMethod.GET)
  @ResponseBody
  public String codeGenerate(HttpServletResponse response, ConfigContext configContext) {
    if("prod".equals(configContext.getEnv())){
      configContext.setOutputPath(configContext.getDownloadPath());
    }
    configContext.setDriver(JDBCDRIVER);
    configContext.setUrl(MYSQLJDBCURLHEADER + configContext.getIp() + ":" + configContext.getPort() + "/" + configContext.getSourceDatabase() + SERVERTIMEZONE);

    //初始化DB工具类
    DBHelper dbHelper = new DBHelper(configContext);

    //得到数据库表的元数据
    List<Map<String, Object>> resultList = dbHelper.descTable();

    //元数据处理
    List<ColumnDefinition> columnDefinitionList = ColumnHelper.covertColumnDefinition(resultList);

    //生成代码
    doGenerator(configContext, columnDefinitionList);

    if("prod".equals(configContext.getEnv())){
      try {
        ZipUtils.toZip(configContext.getDownloadPath(), response.getOutputStream(), true);
        ZipUtils.delFolder(configContext.getDownloadPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return "OJBK";
  }

  public void doGenerator(ConfigContext configContext, Object data) {
    //配置velocity的资源加载路径
    initVelocity();

    //封装velocity数据
    VelocityContext context = new VelocityContext();
    context.put("table", configContext.getTargetTable());
    context.put("name", configContext.getTargetName());
    String lowerName = StringUtil.firstToLower(configContext.getTargetName());
    context.put("lowerName", lowerName);
    initContext(context,configContext,data);
    callback.write(configContext, context);

  }


  /**
   * 根据数据库名生成代码
   *
   * @param configContext
   * @return
   */
  @RequestMapping(value = "generateByDatabase", method = RequestMethod.GET)
  @ResponseBody
  public String codeGenerateByDatabase(HttpServletResponse response, ConfigContext configContext) {

    if("prod".equals(configContext.getEnv())){
      configContext.setOutputPath(configContext.getDownloadPath());
    }

    configContext.setDriver(JDBCDRIVER);
    configContext.setUrl(MYSQLJDBCURLHEADER + configContext.getIp() + ":" + configContext.getPort() + "/" + configContext.getSourceDatabase() + SERVERTIMEZONE);

    //初始化DB工具类
    DBHelper dbHelper = new DBHelper(configContext);
    Map<String, List<Map<String, Object>>> map = dbHelper.getDescTables();
    for (String key : map.keySet()) {
      List<Map<String, Object>> value = map.get(key);
      //元数据处理
      List<ColumnDefinition> columnDefinitionList = ColumnHelper.covertColumnDefinition(value);
      String name = StringUtil.firstToUpper(StringUtil.underlineToCamelhump(key));
      deGeneratorByDatabaseName(configContext, columnDefinitionList, key, name);
    }

    if("prod".equals(configContext.getEnv())){
      try {
        ZipUtils.toZip(configContext.getDownloadPath(), response.getOutputStream(), true);
        ZipUtils.delFolder(configContext.getDownloadPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return "ojbk";
  }

  /**
   * 生成Msg公共类
   * @param configContext
   * @return
   */
  @RequestMapping(value = "generateCommon", method = RequestMethod.POST)
  @ResponseBody
  public String generateCommon(ConfigContext configContext){
    Object data = null;
    initVelocity();
    //封装velocity数据
    VelocityContext context = new VelocityContext();
    initContext(context,configContext,data);
    callback.writeMsg(configContext, context);
    return "ojbk";
  }

  public void deGeneratorByDatabaseName(ConfigContext configContext, Object data, String table, String name) {
    //配置velocity的资源加载路径
    initVelocity();

    //封装velocity数据
    VelocityContext context = new VelocityContext();
    context.put("table", table);
    context.put("name", name);
    configContext.setTargetName(name);
      String lowerName = StringUtil.firstToLower(name);
      context.put("lowerName", lowerName);
      initContext(context,configContext,data);
    //写baseDao、baseService
    callback.writeBase(configContext, context);
    callback.write(configContext, context);
  }

  /**
   * 生成基础类-EnError
   * @param configContext
   * @return
   */
  @RequestMapping(value = "generateEnError", method = RequestMethod.POST)
  @ResponseBody
  public String generateEnError(ConfigContext configContext){
    Object data = null;
    initVelocity();
    //封装velocity数据
    VelocityContext context = new VelocityContext();
    initContext(context,configContext,data);
    callback.writeEnum(configContext, context);
    return "EnError OJBK";
  }

}
