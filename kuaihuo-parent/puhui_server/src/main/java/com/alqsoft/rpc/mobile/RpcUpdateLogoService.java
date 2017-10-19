package com.alqsoft.rpc.mobile;

import org.alqframework.result.Result;

import com.alqsoft.entity.member.Member;

public interface RpcUpdateLogoService {
	/**更换LOGO
	 * @param attachmentId
	 * @param member
	 * @return
	 */
	public Result updateLogo(Long attachmentId, Member member);
	
	/**更换会员LOGO
	 * @param attachmentId
	 * @param member
	 * @return
	 */
	public Result updatememberlogo(Long attachmentId, Member member);
	
	/**修改批发商服务
	 * @param service
	 * @return
	 */
	public Result updatebusiness(String service, Member member);
	/**修改批发商专业
	 * @param major
	 * @param member
	 * @return
	 */
	public Result updatemajor( String major, Member member);
	/**修改批发商服务内容
	 * @param id
	 * @param servicedigest
	 * @param member
	 * @return
	 */
	public Result updateservicedigest(String servicedigest, Member member);

}
