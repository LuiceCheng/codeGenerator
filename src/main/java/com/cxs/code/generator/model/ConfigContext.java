package com.cxs.code.generator.model;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/3/19 16:44
 */
public class ConfigContext {

    //  private static String sourcePath = "%s/src/main/resources/";
    private static String sourcePath = "%s/src/main/java/com/cxs/code/generator/templates/";
    private String outputPath;

    private String ip; //数据库ip
    private String port; //数据库端口
    private String driver;
    private String url;
    private String userName;
    private String password;

    private String sourceDatabase;//源数据库名
    private String targetTable;
    private String targetName;
    private String targetPackage = "com\\example\\code";
    private String targetEntity = "entity";
    private String entityBase = "entity\\base";
    private String entityEnum = "entity\\enums";
    private String targetService = "service";
    private String serviceBase = "service\\base";
    private String serviceBaseImpl = "service\\base\\impl";
    private String targetServiceImpl = "service\\impl";
    private String targetController = "controller";
    private String targetDao = "dao";
    private String daoBase = "dao\\base";
    private String xmlPath = "resource";

    private String env;

    private String downloadPath = "/opt/generator-code/outpath/";

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSourceDatabase() {
        return sourceDatabase;
    }

    public void setSourceDatabase(String sourceDatabase) {
        this.sourceDatabase = sourceDatabase;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable = targetTable;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public String getTargetEntity() {
        return targetEntity;
    }

    public void setTargetEntity(String targetEntity) {
        this.targetEntity = targetEntity;
    }

    public String getEntityBase() {
        return entityBase;
    }

    public void setEntityBase(String entityBase) {
        this.entityBase = entityBase;
    }

    public String getTargetService() {
        return targetService;
    }

    public void setTargetService(String targetService) {
        this.targetService = targetService;
    }

    public String getTargetServiceImpl() {
        return targetServiceImpl;
    }

    public void setTargetServiceImpl(String targetServiceImpl) {
        this.targetServiceImpl = targetServiceImpl;
    }

    public String getTargetController() {
        return targetController;
    }

    public void setTargetController(String targetController) {
        this.targetController = targetController;
    }

    public String getTargetDao() {
        return targetDao;
    }

    public void setTargetDao(String targetDao) {
        this.targetDao = targetDao;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getEntityEnum() {
        return entityEnum;
    }

    public void setEntityEnum(String entityEnum) {
        this.entityEnum = entityEnum;
    }

    public String getServiceBase() {
        return serviceBase;
    }

    public void setServiceBase(String serviceBase) {
        this.serviceBase = serviceBase;
    }

    public String getServiceBaseImpl() {
        return serviceBaseImpl;
    }

    public void setServiceBaseImpl(String serviceBaseImpl) {
        this.serviceBaseImpl = serviceBaseImpl;
    }

    public String getDaoBase() {
        return daoBase;
    }

    public void setDaoBase(String daoBase) {
        this.daoBase = daoBase;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getDownloadPath() {
        return downloadPath;
    }
}
