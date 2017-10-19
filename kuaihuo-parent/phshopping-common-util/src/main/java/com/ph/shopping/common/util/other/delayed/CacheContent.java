package com.ph.shopping.common.util.other.delayed;

import java.io.Serializable;

public class CacheContent<K, V> implements Serializable{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */
	private static final long serialVersionUID = -687024974925565608L;
	/**
	 * 缓存名字 key
	 */
	private K name;
	/**
	 * 缓存正文内容 value
	 */
	private V content;
	
	public CacheContent() {
		super();
	}

	public CacheContent(K k,V v){
		this.name = k;
		this.content = v;
	}

	public K getName() {
		return name;
	}

	public void setName(K name) {
		this.name = name;
	}

	public V getContent() {
		return content;
	}

	public void setContent(V content) {
		this.content = content;
	}
}
