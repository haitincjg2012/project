package com.ph.shopping.common.util.container;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName:  ContainerUtil   
 * @Description:容器工具类   
 * @author: 李杰
 * @date:   2017年4月25日 上午11:32:56     
 * @Copyright: 2017
 */
public final class ContainerUtil {

	private static final Logger log = LoggerFactory.getLogger(ContainerUtil.class);
	
	/**
	 * 创建hashmap集合
	 * @return
	 */
	public static <K,V> Map<K, V> map(){
		
		return new HashMap<K,V>();
	}
	/**
	 * 得到县城同步的map
	 * @return
	 */
	public static <K,V> Map<K, V> synchronizedMap(){
		
		return Collections.synchronizedMap(new HashMap<K,V>());
	}
	/**
	 * 创建 ArrayList
	 * @return
	 */
	public static <T> List<T> aList(){
		
		return new ArrayList<T>();
	}
	/**
	 * 创建指定长度的ArrayList
	 * @param num
	 * @return
	 */
	public static <T> ArrayList<T> aList(int num){
		ArrayList<T> arrays = new ArrayList<T>();
		if(num > 10){
			arrays.ensureCapacity(num);
		}
		return arrays;
	}
	/**
	 * 构造一个包含指定 collection 中的元素的新 ArrayList
	 * @return
	 */
	public static <T> List<T> aList(Collection<? extends T> c){
		if(c != null && c.size() > 0)
			return new ArrayList<T>(c);
		return new ArrayList<T>();
	}
	/**
	 * 创建LinkedList
	 * @return
	 */
	public static <T> LinkedList<T> lList(){
		
		return new LinkedList<T>();
	}
	/**
	 * 创建 hashset
	 * @return
	 */
	public static <T> Set<T> set(){
		
		return new HashSet<T>();
	}
	/**
	 * 构造一个包含指定 collection 中的元素的新 set。
	 * @param c
	 * @return
	 */
    public static <T> Set<T> set(Collection<? extends T> c){
		if(c != null && c.size() > 0)
			return new HashSet<T>(c);
		
		return new HashSet<T>();
	}
	/**
	 * 创建基于 LinkedList 的 Queue
	 * @return
	 */
	public static <T> Queue<T> queue(){
		
		return new LinkedList<T>();
	}
	
	/**
	 * 将obj 转换成指定容器
	 * @param clas
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getListByObj(Class<T> clas,List<?> list){
		if(ParamVerifyUtil.objIsNotNull(list)){
			List<T> retList = aList(list.size());
			for(Object obj : list){
				if(clas.isInstance(obj))
					retList.add((T)obj);
			}
			return retList;
		}
		return null;
	}
	/**
	 * 将obj 数组转换成指定类型的arraylist
	 * @param clas
	 * @param objs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getListByArrayObjs(Class<T> clas,Object[] objs){
		if(ParamVerifyUtil.objIsNotNull(objs)){
			List<T> retList = aList(objs.length);
			for(Object obj : objs){
				if(clas.isInstance(obj))
					retList.add((T)obj);
			}
			return retList;
		}
		return null;
	}
	/**
	 * 将集合转换成数组
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] callBackArrayByList(List<T> list,Class<T> clas){
		T[] ts = null;
		if(ParamVerifyUtil.objIsNotNull(list)){
			ts = (T[]) Array.newInstance(clas, list.size());
			int num = 0;
			for(T t : list){
				ts[num++] = t;
			}
		}
		return ts;
	} 
	/**
	 * 
	* @Title: containerConvert  
	* @Description: 将一个容器的值转换到另外一个指定容器 ，
	* 备注：两个容器的实体字段名称必须要一样才会去得到值，同样字段的类型也要一样 才会赋值成功
	* @param @param clas
	* @param @param list
	* @param @return 
	* @return List<T>  
	* @throws
	 */
	public static <T> List<T> containerConvert(Class<T> clas,List<?> list){
		List<T> retList = null;
		if(ParamVerifyUtil.objIsNotNull(list)){
			String[] fieldNames = ClassUtil.getFiledStrByClassBySuper(clas);
			if(ParamVerifyUtil.objIsNotNull(fieldNames)){
				retList = aList(list.size());
				for(Object obj : list){
					if(obj == null){
						continue;
					}
					T result = objSetValue(obj, clas, fieldNames);
					if(result != null){
						retList.add(result);
					}
				}
			}
		}
		return retList;
	}
	/**
	 * 
	* @Title: objSetValue  
	* @Description: 根据字段名称得到指定的值，然后将值赋值给指定对象  
	* @param @param obj
	* @param @param clas
	* @param @param targetFieldNames
	* @param @return    参数  
	* @return T    返回类型  
	* @throws
	 */
	private static <T> T objSetValue(Object obj,Class<T> clas,String[] targetFieldNames){
		T result = null;
		try {
			result = clas.newInstance();
			for(String target : targetFieldNames){
				Object objValue = ClassUtil.getPropertiesValue(target, obj);
				if(objValue != null){
					ClassUtil.setPropertiesValue(target, result, objValue);
				}
			}
		} catch (Exception e) {
			log.error("实体对象赋值错误",e);
		}
		return result;
	}
 }
