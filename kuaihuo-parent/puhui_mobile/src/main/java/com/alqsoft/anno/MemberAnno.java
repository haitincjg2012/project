package com.alqsoft.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.alqsoft.utils.SystemRole;

@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.PARAMETER)  
public @interface MemberAnno {  
   
}  