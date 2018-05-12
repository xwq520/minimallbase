package com.minimall.boilerplate.system.cfg;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import static org.hibernate.boot.model.naming.Identifier.toIdentifier;

/**
 * Title: 表名生成规则.
 * <p>Description: 表名生成规则</p>

 */
public class EntityNamingStrategy extends PhysicalNamingStrategyStandardImpl {

  private static final String FU_PREFIX = "mi";
//  private static final String U8_PREFIX = "u8";
//  private static final String AEMS_PREFIX = "aems";
//  private static final String INVENTORY_PREFIX = "inventory";
  private static final String UNDER_BAR = "_";

  @Override
  public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
    String tableName = appendPrefix(name);
    return super.toPhysicalTableName(toIdentifier(tableName), context);
  }

  @Override
  public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
    String sequenceName = FU_PREFIX + UNDER_BAR + name.getText().toLowerCase();
    return super.toPhysicalSequenceName(toIdentifier(sequenceName), context);
  }

  private String appendPrefix(Identifier name) {
    String prefixed = name.getText().toLowerCase();
//    if(prefixed.startsWith(U8_PREFIX))
//      prefixed = U8_PREFIX + UNDER_BAR + prefixed.substring(U8_PREFIX.length());
//    else if(prefixed.startsWith(AEMS_PREFIX))
//      prefixed = AEMS_PREFIX + UNDER_BAR + prefixed.substring(AEMS_PREFIX.length());
//    else if(prefixed.startsWith(INVENTORY_PREFIX))
//      prefixed = INVENTORY_PREFIX + UNDER_BAR + prefixed.substring(INVENTORY_PREFIX.length());
//    else
    prefixed = FU_PREFIX + UNDER_BAR + prefixed;
    return prefixed;
  }
}
