package com.cxs.code.generator.service;

import com.cxs.code.generator.model.ConfigContext;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public interface Callback {
  /**
   * 写入entity,service,dao,serviceImpl模板
   *
   * @param configContext
   * @param context
   */
  void write(ConfigContext configContext, VelocityContext context);

  /**
   * 写入Msg模板
   * @param configContext
   */
  void writeMsg(ConfigContext configContext, VelocityContext context);

  /**
   * 写入enum模板
   * @param configContext
   * @param context
   */
  void writeEnum(ConfigContext configContext, VelocityContext context);

  /**
   * 写入baseDao、baseService
   * @param configContext
   * @param context
   */
  void writeBase(ConfigContext configContext, VelocityContext context);
}
