package com.alqsoft.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2015年4月25日 上午11:41:18
 * 
 */
public class NettyClientHandler extends ChannelHandlerAdapter{
	
		
	 @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) {
		 ByteBuf in = (ByteBuf) msg;
		    while(in.isReadable()){
		    	System.out.print((char)in.readByte());
		    }
		    ctx.close();
	    }
	 
	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	    	System.out.println("错误失去连接");
	        cause.printStackTrace();
	        ctx.close();
	    }
}
