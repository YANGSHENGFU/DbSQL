package com.dbsql.db;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by LY on 2018/5/3.
 */

public class VersionFactory {

    static Set<String> list = new LinkedHashSet<>();

    static {  // 以后没添加一个版本，就在这个集合中添加类的全路径（必须是全路径）
        list.add("com.dbsql.db.VersionSecond");
        list.add("com.dbsql.db.VersionThird");
    }

    /**
     * 根据数据库版本号获取对应的对象
     * @param i
     * @return
     */
    public static Upgrade getUpgrade(int i) {
        Upgrade upgrade = null ;
        if (null != list && list.size() > 0) {
            try {
                for (String className : list) {
                    Class<?> cls = null;
                    cls = Class.forName(className);
                    if (Upgrade.class == cls.getSuperclass()) { // 首先父类是Upgrade类
                        VersionCode versionCode = cls.getAnnotation(VersionCode.class);
                        if (null == versionCode) {
                            throw new IllegalStateException(cls.getName() + "类必须使用VersionCode类注解");
                        } else {
                            if (i == versionCode.value()) { // 等于当前要操作的版本。
                                upgrade = (Upgrade) cls.newInstance(); // 根据class获取实例，并向上转为父类型。
                                break;
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new IllegalStateException("没有找到类名,请检查list里面添加的类名是否正确！");
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return upgrade;
    }

    /**
     * 得到当前数据库版本
     * @return
     */
    public static int getCurrentDBVersion() {
        return list.size() + 1;
    }
}
