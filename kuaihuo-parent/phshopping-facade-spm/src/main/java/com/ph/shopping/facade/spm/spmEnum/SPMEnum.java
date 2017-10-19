package com.ph.shopping.facade.spm.spmEnum;

import com.ph.shopping.common.core.exception.BizException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @项目：phshopping-facade-spm
 * @描述：代理商、供应商、商户枚举
 * @作者  何文浪
 * @时间：2017-5-17
 * @version: 2.1
 */
public class SPMEnum extends BizException{
	
		//TODO******************************枚举类定义构造方法处理异常start*************************************************************
		private static final long serialVersionUID = -7636485364781086959L;
		//无参构造方法
		public  SPMEnum(){
			
		}
	    public SPMEnum(String name, String index) {
	        super(name, index);
	    }
	    public SPMEnum(String name) {
	        super(name);
	    }
	    
		//TODO******************************供应商枚举定义start*************************************************************
	    /**
		 * 供应商supplierType ALL("全国",1),LOCAL("本地",2);
		 * @author 何文浪
		 * @时间 2017-5-17
		 */
        public enum supplierType {
            NATIONAL("全国", (byte) 1), LOCAL("本地", (byte) 2);
            private String name;
            private byte index;

            supplierType(String name, byte index) {
                this.name = name;
                this.index = index;
            }
			 // 普通方法  
		    public static String getName(byte index) {  
		        for (supplierType c : supplierType.values()) {  
		            if (c.getIndex() == index) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    }  
		    // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (supplierType c : supplierType.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    }
		    public static List<Object> getEnumBySupplierTypeList(){
				List<Object> list = new ArrayList<Object>();
				 for (supplierType c : supplierType.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public byte getIndex() {
				return index;
			}

			public void setIndex(byte index) {
				this.index = index;
			}
		}
		
		
		/**
		 * 供应商status 供应商审核状态：0待审核，1审核通过，2未通过，;
		 * @author 何文浪
		 * @时间 2017-5-17
		 */
        public enum supplierStatus {
            ZERO("待审核", (byte) 0), ONE("审核通过", (byte) 1), TWO("未通过", (byte) 2);
            private String name;
            private byte index;

            supplierStatus(String name, byte index) {
                this.name = name;
                this.index = index;
            }
		    // 普通方法  
		    public static String getName(byte index) {  
		        for (supplierStatus c : supplierStatus.values()) {  
		            if (c.getIndex() == index) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    }  
		    // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (supplierStatus c : supplierStatus.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    } 
		    public static List<Object> getEnumBySupplierStatusList(){
				List<Object> list = new ArrayList<Object>();
				 for (supplierStatus c : supplierStatus.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		
		/**
		 * 供应商isDelete 是否删除 0:否 1：是;
		 * @author 何文浪
		 * @时间 2017-5-17
		 */
        public enum supplierIsDelete {
            ZERO("否 ", (byte) 0), ONE("是", (byte) 1);
            private String name;
            private byte index;

            supplierIsDelete(String name, byte index) {
                this.name = name;
                this.index = index;
            }
		    // 普通方法  
		    public static String getName(byte index) {  
		        for (supplierIsDelete c : supplierIsDelete.values()) {  
		            if (c.getIndex() == index) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    }  
		    // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (supplierIsDelete c : supplierIsDelete.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    }  
		    public static List<Object> getEnumBySupplierIsDeleteList(){
				List<Object> list = new ArrayList<Object>();
				 for (supplierIsDelete c : supplierIsDelete.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		/**
		 * 供应商isFrozen 是否冻结 0:否 1:是 2.暂冻;
		 * @author 何文浪
		 * @时间 2017-5-17
		 */
        public enum supplierIsFrozen {
            ZERO("正常 ", (byte) 0), ONE("冻结", (byte) 1), TWO("暂冻", (byte) 2);
            private String name;
            private byte index;

            supplierIsFrozen(String name, byte index) {
                this.name = name;
                this.index = index;
            }
		 // 普通方法  
		    public static String getName(byte index) {  
		        for (supplierIsFrozen c : supplierIsFrozen.values()) {  
		            if (c.getIndex() == index) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    }  
		 // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (supplierIsFrozen c : supplierIsFrozen.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    }  
		    public static List<Object> getEnumBySupplierIsFrozenList(){
				List<Object> list = new ArrayList<Object>();
				 for (supplierIsFrozen c : supplierIsFrozen.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		
		/**
		 * 供应商图片 sort 序列 1营业执照  2身份证第一张  3身份第二张 ;
		 * @author 何文浪
		 * @时间 2017-5-17
		 */
        public enum supplierImageSort {
            ONE("营业执照", (byte) 1), TWO("身份证第一张", (byte) 2), THREE("身份第二张", (byte) 3);
            private String name;
            private byte index;

            supplierImageSort(String name, byte index) {
                this.name = name;
                this.index = index;
            }
		    // 普通方法  
		    public static String getName(byte index) {  
		        for (supplierImageSort c : supplierImageSort.values()) {  
		            if (c.getIndex() == index) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    }  
		    // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (supplierImageSort c : supplierImageSort.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    }  
		    public static List<Object> getEnumBySupplierImageSortList(){
				List<Object> list = new ArrayList<Object>();
				 for (supplierImageSort c : supplierImageSort.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		
		/**
		 * 供应商图片type 图片类型 1营业执照类型  2身份证类型 ;
		 * @author 何文浪
		 * @时间 2017-5-17
		 */
        public enum supplierImageType {
            ONE("营业执照类型", (byte) 1), TWO("身份证类型", (byte) 2);
            private String name;
            private byte index;

            supplierImageType(String name, byte index) {
                this.name = name;
                this.index = index;
            }
		    // 普通方法  
		    public static String getName(byte index) {  
		        for (supplierImageType c : supplierImageType.values()) {  
		            if (c.getIndex() == index) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    }  
		    // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (supplierImageType c : supplierImageType.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    }  
		    public static List<Object> getEnumBySupplierImageTypeList(){
				List<Object> list = new ArrayList<Object>();
				 for (supplierImageType c : supplierImageType.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		
		/**
		 * 供应商异常枚举码 ;
		 * @author 何文浪
		 * @时间 2017-5-19
		 */
        public enum supplierExtion {
            //供应商
            ADD( "供应商新增异常","40001"),
            UPDATE("供应商修改异常","40002" ),
		    DELETE("供应商删除异常","40003"),
		    THAW("供应商解冻异常", "40004"),
		    OPERATING("供应商解冻异常", "40004"),
		    FREEZE("供应商冻结异常","40005"),
		    FROZEN("供应商暂冻异常","40006"),
		    EXAMINATIONPASSED("供应商审核通过异常","40007"),
		    AUDITNOTPASSED("供应商审核未通过异常","40008"),
		    DETAIL("供应商详情查看异常","40008" ),
			GET( "供应商暂无数据","40014");
			private String name;
		    private String index;

            supplierExtion(String name, String index) {
                this.name = name;
                this.index = index;
            }
		    // 普通方法  
		    public static String getName(String index) {  
		        for (supplierExtion c : supplierExtion.values()) {  
		            if (c.getIndex().equals(index)) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    }  
		    // 普通方法  
		    public static String getIndex(String index) {  
		        for (supplierExtion c : supplierExtion.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return null;  
		    } 
		    public static List<Object> getEnumBySupplierExtionList(){
				List<Object> list = new ArrayList<Object>();
				 for (supplierExtion c : supplierExtion.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getIndex() {
				return index;
			}
			public void setIndex(String index) {
				this.index = index;
			}
		}
		
		//TODO******************************商户枚举定义start*************************************************************
		/**
		 * 商户类型 isDelete 是否删除(0.删除，1未删除)默认为1 ;
		 * @author 何文浪
		 * @时间 2017-5-17
		 */
        public enum merchantTypeIsDelete {
            ZERO("删除 ", (byte) 0), ONE("未删除", (byte) 1);
            private String name;
            private byte index;

            merchantTypeIsDelete(String name, byte index) {
                this.name = name;
                this.index = index;
            }
		    // 普通方法  
		    public static String getName(byte index) {  
		        for (merchantTypeIsDelete c : merchantTypeIsDelete.values()) {  
		            if (c.getIndex() == index) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    } 
		 // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (merchantTypeIsDelete c : merchantTypeIsDelete.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    }  
		    public static List<Object> getEnumByMerchantTypeIsDeleteList(){
				List<Object> list = new ArrayList<Object>();
				 for (merchantTypeIsDelete c : merchantTypeIsDelete.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		
		/**
		 * 商户类型 MerchanTypeLevel 分类级别 1 一级分类 2 二级分类  ;
		 * @author 何文浪
		 * @时间 2017-5-17
		 */
        public enum merchantTypeMerchanTypeLevel {
            ONE(" 一级分类", (byte) 1), TWO("二级分类", (byte) 2);
            private String name;
            private byte index;

            merchantTypeMerchanTypeLevel(String name, byte index) {
                this.name = name;
                this.index = index;
            }
		    // 普通方法  
		    public static String getName(byte index) {  
		        for (merchantTypeMerchanTypeLevel c : merchantTypeMerchanTypeLevel.values()) {  
		            if (c.getIndex() == index) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    }  
		    // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (merchantTypeMerchanTypeLevel c : merchantTypeMerchanTypeLevel.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    }  
		    public static List<Object> getEnumByMerchantTypeMerchanTypeLevelList(){
				List<Object> list = new ArrayList<Object>();
				 for (merchantTypeMerchanTypeLevel c : merchantTypeMerchanTypeLevel.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		
		/**
		 * 商户 status 0：审核中 1：审核通过 2：未通过 （原来对应值：审核未通过0  审核未通过2  审核通过正常1 冻结  3）  ;
		 * @author 何文浪
		 * @时间 2017-5-17
		 */
        public enum merchantStatus {
            ZERO("待审核 ", (byte) 0), ONE("审核通过", (byte) 1), TWO("未通过", (byte) 2);
            private String name;
            private byte index;

            merchantStatus(String name, byte index) {
                this.name = name;
                this.index = index;
            }
		    
		    // 普通方法  
		    public static String getName(byte index) {  
		        for (merchantStatus c : merchantStatus.values()) {  
		            if (c.getIndex() == index) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    }  
		    // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (merchantStatus c : merchantStatus.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    }  
		    public static List<Object> getEnumByMerchantStatusList(){
				List<Object> list = new ArrayList<Object>();
				 for (merchantStatus c : merchantStatus.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		
		/**
		 * 商户 isFrozen 是否冻结 0:否 1:是;
		 * @author 何文浪
		 * @时间 2017-5-17
		 */
        public enum merchantIsFrozen {
            ZERO("正常 ", (byte) 0), ONE("冻结", (byte) 1);
            private String name;
            private byte index;

            merchantIsFrozen(String name, byte index) {
                this.name = name;
                this.index = index;
            }
		    // 普通方法  
		    public static String getName(byte index) {  
		        for (merchantIsFrozen c : merchantIsFrozen.values()) {  
		            if (c.getIndex() == index) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    }  
		    // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (merchantIsFrozen c : merchantIsFrozen.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    }  
		    public static List<Object> getEnumByMerchantIsFrozenList(){
				List<Object> list = new ArrayList<Object>();
				 for (merchantIsFrozen c : merchantIsFrozen.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		
		/**
		 * 商户 isDelete 是否删除 0:否 1：是;
		 * @author 何文浪
		 * @时间 2017-5-17
		 */
        public enum merchantIsDelete {
            ZERO("否 ", (byte) 0), ONE("是", (byte) 1);
            private String name;
            private byte index;

            merchantIsDelete(String name, byte index) {
                this.name = name;
                this.index = index;
            }
		    // 普通方法  
		    public static String getName(byte index) {  
		        for (merchantIsDelete c : merchantIsDelete.values()) {  
		            if (c.getIndex() == index) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    } 
		 // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (merchantIsDelete c : merchantIsDelete.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    }  
		    public static List<Object> getEnumByMerchantIsDeleteList(){
				List<Object> list = new ArrayList<Object>();
				 for (merchantIsDelete c : merchantIsDelete.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		/**
		 * 商户图片 type 图片类型 1 营业执照图片 2 身份证图片 3 门店照片;
		 * @author 何文浪
		 * @时间 2017-5-17
		 */
        public enum merchantImageType {
            ONE("营业执照图片", (byte) 1), TWO("身份证图片", (byte) 2), THREE("门店照片", (byte) 3);
            private String name;
            private byte index;

            merchantImageType(String name, byte index) {
                this.name = name;
                this.index = index;
            }
			// 普通方法  
		    public static String getName(byte index) {  
		        for (merchantImageType c : merchantImageType.values()) {  
		            if (c.getIndex() == index) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    } 
		    // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (merchantImageType c : merchantImageType.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    }  
		    public static List<Object> getEnumByMerchantImageTypeList(){
				List<Object> list = new ArrayList<Object>();
				 for (merchantImageType c : merchantImageType.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		
		/**
		 * 商户异常枚举码 ;
		 * @author 何文浪
		 * @时间 2017-5-19
		 */
        public enum merchantExtion {
            //基本操作
            ADD("商户新增异常","60001"),
            UPDATE("商户修改异常","60002"),
		    DELETE("商户删除异常","60003" ),
		    GET("商户暂无数据","60004"),
        	DETAIL("获取商户详细异常","60006"),
        	THAW("商户解冻异常","60007"),
        	FREEZE("商户冻结异常","60008"),
        	EXAMINATIONPASSED("商户审核通过异常","60009" ),
        	AUDITNOTPASSED("商户审核通过异常","600010"),
        	OPERATING("商户审核不通过异常","600011");
		    
			private String name;
		    private String index;

            merchantExtion(String name, String index) {
                this.name = name;
                this.index = index;
            }
		    // 普通方法  
		    public static String getName(String index) {  
		        for (merchantExtion c : merchantExtion.values()) {  
		            if (c.getIndex().equals(index)) {  
		                return c.name;  
		            }  
		        }  
		        return null;  
		    } 
		    // 普通方法  
		    public static String getIndex(String index) {  
		        for (merchantExtion c : merchantExtion.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return null;  
		    }  
		    public static List<Object> getEnumByMerchantExtionList(){
				List<Object> list = new ArrayList<Object>();
				 for (merchantExtion c : merchantExtion.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getIndex() {
				return index;
			}
			public void setIndex(String index) {
				this.index = index;
			}
		}
		//TODO******************************代理商枚举定义start*************************************************************
		
		/**
		 * 代理商 status 审核状态：0：待审核，1：审核通过，2：未通过;
		 * @author 何文浪
		 * @时间 2017-5-17
		 */
        public enum agentStatus {
            ZERO("待审核 ", (byte) 0), ONE("审核通过", (byte) 1), TWO("未通过", (byte) 2);
            private String name;
            private byte index;

            agentStatus(String name, byte index) {
                this.name = name;
                this.index = index;
            }
		    // 普通方法  
		    public static String getName(byte index) {  
		    	for (agentStatus c : agentStatus.values()) {  
		    		if (c.getIndex() == index) {  
		    			return c.name;  
		    		}  
		    	}  
		    	return null;  
		    } 
		    // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (agentStatus c : agentStatus.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    } 
		    public static List<Object> getEnumByAgentStatusList(){
				List<Object> list = new ArrayList<Object>();
				 for (agentStatus c : agentStatus.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}

    //TODO******************************代理商枚举定义start*************************************************************

    /**
     * 代理商等级;
     *
     * @author 何文浪
     * @时间 2017-5-17
     */
    public enum agentLevel {
        CITY_AGENT("市级代理 ", (byte) 1), COUNTY_AGENT("县级代理", (byte) 2), COMMUNITY_AGENT("社区代理", (byte) 3), ADMIN("管理员", (byte) 4);
        private String name;
        private byte index;

        agentLevel(String name, byte index) {
            this.name = name;
            this.index = index;
        }

        // 普通方法
        public static agentLevel getAgentLevelByIndex(byte index) {
            for (agentLevel c : agentLevel.values()) {
                if (c.getIndex() == index) {
                    return c;
                }
            }
            return null;
        }

        // 普通方法
        public static String getName(byte index) {
            for (agentLevel c : agentLevel.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }

        // 普通方法
        public static byte getIndex(byte index) {
            for (agentLevel c : agentLevel.values()) {
                if (c.getIndex() == index) {
                    return c.index;
                }
            }
            return 99;
        }

        public static List<Object> getEnumByAgentLevelList() {
            List<Object> list = new ArrayList<>();
            for (agentLevel c : agentLevel.values()) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", c.getIndex());
                map.put("name", c.getName());
                list.add(map);
            }
            return list;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public byte getIndex() {
            return index;
        }

        public void setIndex(byte index) {
            this.index = index;
        }
    }

    /**
     * 代理商 isDelete 是否删除 0:否 1：是;
     * @author 何文浪
		 * @时间 2017-5-17
     */
    public enum agentIsDelete {
        ZERO("否 ", (byte) 0), ONE("是", (byte) 1);
        private String name;
        private byte index;

        agentIsDelete(String name, byte index) {
            this.name = name;
            this.index = index;
        }
		    // 普通方法  
		    public static String getName(byte index) {  
		    	for (agentIsDelete c : agentIsDelete.values()) {  
		    		if (c.getIndex() == index) {  
		    			return c.name;  
		    		}  
		    	}  
		    	return null;  
		    } 
		    // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (agentIsDelete c : agentIsDelete.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    } 
		    public static List<Object> getEnumByAgentIsDeleteList(){
				List<Object> list = new ArrayList<Object>();
				 for (agentIsDelete c : agentIsDelete.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		/**
		 * 代理商 isFrozen 是否冻结 0:否 1:是2.暂冻;
		 * @author 何文浪
		 * @时间 2017-5-17
         */
        public enum agentIsFrozen {
            ZERO("正常 ", (byte) 0), ONE("冻结", (byte) 1), TWO("暂冻", (byte) 2);
            private String name;
            private byte index;

            agentIsFrozen(String name, byte index) {
                this.name = name;
                this.index = index;
            }
			// 普通方法  
		    public static String getName(byte index) {  
		    	for (agentIsFrozen c : agentIsFrozen.values()) {  
		    		if (c.getIndex() == index) {  
		    			return c.name;  
		    		}  
		    	}  
		    	return null;  
		    } 
		    // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (agentIsFrozen c : agentIsFrozen.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    } 
		    public static List<Object> getEnumByAgentIsFrozenList(){
				List<Object> list = new ArrayList<Object>();
				 for (agentIsFrozen c : agentIsFrozen.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		
		/**
		 * 代理商图片类型 Type 图片类型 1 营业执照图片 2 身份证图片 3 门店照片;
		 * @author 何文浪
		 * @时间 2017-6-5
         */
        public enum agentImageType {
            ONE("营业执照图片 ", (byte) 1), TWO("身份证图片", (byte) 2), THREE("门店照片", (byte) 3);
            private String name;
            private byte index;

            agentImageType(String name, byte index) {
                this.name = name;
                this.index = index;
            }
			// 普通方法  
		    public static String getName(byte index) {  
		    	for (agentImageType c : agentImageType.values()) {  
		    		if (c.getIndex() == index) {  
		    			return c.name;  
		    		}  
		    	}  
		    	return null;  
		    } 
		    // 普通方法  
		    public static byte getIndex(byte index) {  
		        for (agentImageType c : agentImageType.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return 99;  
		    } 
		    public static List<Object> getEnumByAgentIsFrozenList(){
				List<Object> list = new ArrayList<Object>();
				 for (agentImageType c : agentImageType.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public byte getIndex() {
				return index;
			}
			public void setIndex(byte index) {
				this.index = index;
			}
		}
		/**
		 * 代理商异常枚举码 ;
		 * @author 何文浪
		 * @时间 2017-5-19
         */
        public enum agentExtion {
            //代理商
            ADD("代理商新增异常","50001"),
            UPDATE("代理商修改异常","50002"),
		    DELETE("代理商删除异常","50003" ),
		    GET("代理商暂无数据","50004"),
		    ADD_IMAGE( "代理商图片新增异常","50005"),
		    UPDATE_IMAGE("代理商图片修改异常","50006"),
		    DELETE_IMAGE("代理商图片删除异常","50007"),
		    GET_DETAIL("获取代理商详细异常","50008"),
		    ENTITY_HAVE("已存在本级代理商","50009"),
		    THAW("代理商操作异常","50015"),
//		    FREEZE("50016", "代理商冻结异常"),
//		    FROZEN("50017", "代理商暂冻异常"),
			//代理商充值提现
		    ADD_RECHARGE("代理商充值记录新增异常","50018" ),
		    ADD_WITHDRAW("代理商提现记录新增异常","50019");
			private String name;
		    private String index;

            agentExtion(String name, String index) {
                this.name = name;
                this.index = index;
            }
		    // 普通方法  
		    public static String getName(String index) {  
		    	for (agentExtion c : agentExtion.values()) {  
		    		if (c.getIndex().equals(index)) {  
		    			return c.name;  
		    		}  
		    	}  
		    	return null;  
		    } 
		 // 普通方法  
		    public static String getIndex(String index) {  
		        for (agentExtion c : agentExtion.values()) {  
		            if (c.getIndex() == index) {  
		                return c.index;  
		            }  
		        }  
		        return null;  
		    } 
		    public static List<Object> getEnumByAgentExtionList(){
				List<Object> list = new ArrayList<Object>();
				 for (agentExtion c : agentExtion.values()){
					 Map<String,Object> map = new HashMap<String,Object>();
					 map.put("id", c.getIndex());
					 map.put("name", c.getName());
					 list.add(map);
				 }
				return list;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getIndex() {
				return index;
			}
			public void setIndex(String index) {
				this.index = index;
			}
		}


}
