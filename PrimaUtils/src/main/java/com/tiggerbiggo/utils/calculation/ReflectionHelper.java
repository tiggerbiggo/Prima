package com.tiggerbiggo.utils.calculation;

import java.lang.reflect.Field;

public class ReflectionHelper {

  public static Field getFieldFromClass(Class clazz, String name){
    try {
      return clazz.getDeclaredField(name);
    } catch (NoSuchFieldException e) {}
    return null;
  }

}
