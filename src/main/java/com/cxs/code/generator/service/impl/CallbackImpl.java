package com.cxs.code.generator.service.impl;

import com.cxs.code.generator.model.ConfigContext;
import com.cxs.code.generator.service.Callback;
import com.cxs.code.generator.util.FileUtil;
import com.cxs.code.generator.util.VelocityUtil;
import org.apache.velocity.VelocityContext;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * description:
 *
 * @author:cxs
 * @date: 2019/3/29 11:18
 */
@Service
public class CallbackImpl implements Callback {
  @Override
  public void write(ConfigContext configContext, VelocityContext context) {
    String entityPath = configContext.getOutputPath() + File.separator + configContext.getTargetPackage() + File.separator + configContext.getTargetEntity();
    FileUtil.writeFile(entityPath,                   //输出目录
        String.format("%s.java", configContext.getTargetName()),    //文件名
        VelocityUtil.render("entity.vm", context));                 //模板生成内容

    String servicePath = configContext.getOutputPath() + File.separator + configContext.getTargetPackage() + File.separator + configContext.getTargetService();
    FileUtil.writeFile(servicePath,
        String.format("I%sService.java", configContext.getTargetName()),
        VelocityUtil.render("service.vm", context));

    String daoPath = configContext.getOutputPath() + File.separator + configContext.getTargetPackage() + File.separator + configContext.getTargetDao();
    FileUtil.writeFile(daoPath,
        String.format("I%sDao.java", configContext.getTargetName()),
        VelocityUtil.render("dao.vm", context));

    String serviceImplPath = configContext.getOutputPath() + File.separator + configContext.getTargetPackage() + File.separator + configContext.getTargetServiceImpl();
    FileUtil.writeFile(serviceImplPath,
        String.format("%sServiceImpl.java", configContext.getTargetName()),
        VelocityUtil.render("serviceImpl.vm", context));

    String xmlPath = configContext.getOutputPath() + File.separator + configContext.getTargetPackage() + File.separator + configContext.getXmlPath();
    FileUtil.writeFile(xmlPath,
        String.format("%sDao.xml", configContext.getTargetName()),
        VelocityUtil.render("baseXml.vm", context));
  }

  @Override
  public void writeMsg(ConfigContext configContext, VelocityContext context) {
    String msgPath = configContext.getOutputPath() + File.separator + configContext.getTargetPackage() + File.separator + configContext.getEntityBase();
    FileUtil.writeFile(msgPath,"Msg.java", VelocityUtil.render("msg.vm", context));
    FileUtil.writeFile(msgPath,"GlobalConstants.java", VelocityUtil.render("GlobalConstants.vm", context));
    FileUtil.writeFile(msgPath,"PageBounds.java", VelocityUtil.render("PageBounds.vm", context));
  }

  @Override
  public void writeEnum(ConfigContext configContext, VelocityContext context) {
    String enumPath = configContext.getOutputPath() + File.separator + configContext.getTargetPackage() + File.separator + configContext.getEntityEnum();
    FileUtil.writeFile(enumPath,"EnError.java",
        VelocityUtil.render("enError.vm", context));
  }

  @Override
  public void writeBase(ConfigContext configContext, VelocityContext context) {
    String baseDaoPath = configContext.getOutputPath() + File.separator + configContext.getTargetPackage() + File.separator + configContext.getDaoBase();
    String baseServicePath = configContext.getOutputPath() + File.separator + configContext.getTargetPackage() + File.separator + configContext.getServiceBase();
    String baseServiceImplPath = configContext.getOutputPath() + File.separator + configContext.getTargetPackage() + File.separator + configContext.getServiceBaseImpl();
    FileUtil.writeFile(baseDaoPath,
        "IBaseDao.java",
        VelocityUtil.render("baseDao.vm", context));

    FileUtil.writeFile(baseServicePath,
        "IBaseService.java",
        VelocityUtil.render("baseService.vm", context));

    FileUtil.writeFile(baseServiceImplPath,"BaseServiceImpl.java",VelocityUtil.render("baseServiceImpl.vm", context));

    writeMsg(configContext,context);

    writeEnum(configContext,context);
  }
}
