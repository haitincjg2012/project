/**
 * Project Name:alqsoft-easyui
 * File Name:FinalizeEscapeGC.java
 * Package Name:com.alqsoft.gc
 * Date:2017年2月22日下午3:10:32
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.alqsoft.gc;

import com.google.zxing.datamatrix.encoder.SymbolShapeHint;

/**
 * ClassName:FinalizeEscapeGC <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年2月22日 下午3:10:32 <br/>
 * @author   张灿
 * @version  一次对象自我救赎的演示
 * @since    JDK 1.8
 * @see 	 
 */
public class FinalizeEscapeGC {
	
	public static FinalizeEscapeGC SAVE_HOOK = null;
	
	public void isAlive(){
		System.out.println("yes, i am still alive!");
	}
	@Override
	protected void finalize() throws Throwable{
		super.finalize();
		System.out.println("finalize method executed!");
//		FinalizeEscapeGC.SAVE_HOOK = this;
	}
	
	public static void main(String[] args) throws InterruptedException{
		SAVE_HOOK = new FinalizeEscapeGC();
		//对象第一次成功的 拯救自己
		SAVE_HOOK = null;
		System.gc();
		//因为finalize方法优先级很低，所以暂停0.5秒以等待它
		Thread.sleep(500);
		if(SAVE_HOOK != null){
			SAVE_HOOK.isAlive();
		}else{
			System.out.println("no, i am dead!");
		}
		//下面这段代码与上面的相同，但是这次自救失败了
		SAVE_HOOK = null;
		System.gc();
		Thread.sleep(500);
		if(SAVE_HOOK != null){
			SAVE_HOOK.isAlive();
		}else{
			System.out.println("no, i am dead!");
		}
	}
}
