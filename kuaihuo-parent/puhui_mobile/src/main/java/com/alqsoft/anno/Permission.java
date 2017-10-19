package com.alqsoft.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.alqsoft.utils.SystemRole;

@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD)  
public @interface Permission {  
  
    /** 
     * 功能ID，该功能ID，对应数据库中的功能ID 
     * @return 
     * @version V1.0.0 
     * @date Jan 13, 2014 4:59:35 PM 
     */  
     SystemRole value();  
      
}  