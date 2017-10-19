package com.ph.shopping.common.util.calculation;


/**
 * 
 * @ClassName: ProfitCalculation
 * @Description: 管理费计算公式
 * @author WQiang
 * @date 2017年3月27日 下午2:34:33
 */
public class ProfitCalculation {
	/*********************************** 北京批发商分润 ****************************************/
	private static double D_13 = 0.13;// 13%
	private static double D_10 = 0.1;// 10%
	private static double D_0_3 = 0.003;// 0.3%
	private static double D_69 = 0.69;// 69%
	private static double D_0_5 = 0.005;// 0.5%
	private static double D_0_02 = 0.0002;// 0.02%
	private static double D_0_03 = 0.0003;// 0.03%

	private static double D_15 = 0.15;// 15%
	private static double D_7 = 0.07;// 7%
	private static double D_4_4 = 0.044;// 4.4%
	private static double D_2_2 = 0.022;// 2.2%

	// 获取批发商分润
	public static double getB(double A, double C) {
		return (A - C);
	}

	// 管理费
	public static double getD(double A) {
		return (D_13 * A);
	}

	// 普惠管理费
	public static double getE(double A) {
		return (D_10 * A);
	}

	// 易商通管理费
	public static double getF(double A) {
		return (getD(A) - getE(A));
	}

	// 市级代理分润
	public static double getG(double A) {
		return (D_0_3 * D_69 * A);
	}

	// 县级代理分润
	public static double getH(double A) {
		return (D_0_5 * D_69 * A);
	}

	// 市级推广师分润
	public static double getK1(double A) {
		return (D_0_02 * A);
	}

	// 县级推广师分润
	public static double getK2(double A) {
		return (D_0_03 * A);
	}

	// 管理费支出合计
//	public static double getP(double A) {
//		return (getG(A) + getH(A) + getK1(A) + getK2(A));
//	}

	// 管理费剩余
//	public static double getN(double A) {
//		return (getF(A) - getP(A));
//	}

	// 供应链管理费
	public static double getR(double A) {
		return (D_15 * A);
	}

	// 普惠供应链收入
	public static double getS(double A) {
		return (D_7 * A);
	}

	// 易商通供应链收入
	public static double getT(double A) {
		return (getR(A) - getS(A));
	}

	// 市级代理分润
	public static double getU(double A) {
		return (D_4_4 * D_69 * A);
	}

	// 县级代理分润
	public static double getW(double A) {
		return (D_2_2 * D_69 * A);
	}
	
	//供应链支出合计
//	public static double getY(double A) {
//		return (getU(A) + getW(A));
//	}
	//供应链剩余
//	public static double getQ(double A) {
//		return (getT(A) - getY(A));
//	}

	/***************************************** 北京批发商分润结束 ***************************************/
	/***************************************** 本地 ***************************************/
	
	private static int TEN = 10;
	private static int THIRTEEN = 13;
	
	private static double D_3_0 = 0.3;//30%
	private static double D_5_0 = 0.5;//50%
	private static double D_100 = 1;//100%
	private static double D_2 = 0.02;//2%
	private static double D_3 = 0.03;//3%
	private static double D_5 = 0.05;//5%
	
	// 获取13 * A
//	public static double get13A(double A) {
//		return (13 * A);
//	}

	// 管理费
	public static double getChargeFee(double X, double A) {
		return (X * A);
	}

	// 普惠管理费
	public static double getPhChargeFee(double X, double A) {
		return (X * TEN / THIRTEEN * A);
	}

	// 易商通管理费
	public static double getYstChargeFee(double X, double A) {
		return (getChargeFee(X, A) - getPhChargeFee(X, A));
	}

	// 市级代理分润
	public static double getCityAgency(double X, double A) {
		return (X * D_3_0 / THIRTEEN * D_69 * A);
	}

	// 县级代理分润
	public static double getCountyAgency(double X, double A) {
		return (X * D_5_0 / THIRTEEN * D_69 * A);
	}

	// 社区代理分润
	public static double getCommunityAgency(double X, double A) {
		return (X * D_100 / THIRTEEN * D_69 * A);
	}

	// 推广师推广市级代理分润
	public static double getRecommendCity(double X, double A) {
		return (X * D_2 / THIRTEEN * A);
	}

	// 推广师推广县级代理分润
	public static double getRecommendCounty(double X, double A) {
		return (X * D_3 / THIRTEEN * A);
	}

	// 推广师推广社区级代理分润
	public static double getRecommendCommunity(double X, double A) {
		return (X * D_5 / THIRTEEN * A);
	}

	// 推广师推广商户代理分润
	public static double getRecommendMerchant(double X, double A) {
		return (X * D_3_0 / THIRTEEN * A);
	}

	// 管理费支出合计
	/*public static double getChargeTotal(double X, double A) {
		System.out.println("市代理:" + getCityAgency(X, A));
		System.out.println("县代理:" + getCountyAgency(X, A));
		System.out.println("社区代理:" + getCommunityAgency(X, A));
		System.out.println("市代推荐:" + getRecommendCity(X, A));
		System.out.println("县代推荐:" + getRecommendCounty(X, A));
		System.out.println("社区代推荐:" + getRecommendCommunity(X, A));
		System.out.println("商户推荐:" + getRecommendMerchant(X, A));
		return (getCityAgency(X, A) + getCountyAgency(X, A) + getCommunityAgency(X, A)
				+ getRecommendCity(X, A) + getRecommendCounty(X, A) + getRecommendCommunity(X, A)
				+ getRecommendMerchant(X, A));
	}*/

	// 管理费剩余
	/*public static double getChargeRemain(double X, double A) {
		return (getYstChargeFee(X, A) - getChargeTotal(X, A));
	}*/

	
	/****************************管理费分润方法结束**************************************/
	public synchronized static double doubleFormat(double d) {
		
		return Double.parseDouble(String.format("%.2f", d));
	}
	
	public static void main(String[] args) {
		System.out.println(getD(10));
		System.out.println(getE(10));
		System.out.println(getF(10));
	}
	

	// 零售价
	public static double getPrice(double A) {
		return A;
	}

	// 结算成本
	public static double getCost(double B) {
		return B;
	}

	// 物流费用
	public static double getLogisticsFee(double D) {
		return D;
	}

	// 门店进货价
	public static double getBuyExpense(double A) {
		return getPrice(A) * 0.61;
	}

	// 供应链利润
	public static double getChainProfit(double A) {
		return getPrice(A) * 0.15;
	}

	// 普惠供应链收入
	public static double getPhIncome(double A) {
		return getPrice(A) * 0.07;
	}

	// 易商通供应链收入
	public static double getYstIncome(double A) {
		return getPrice(A) * 0.08;
	}

	// 市级代理分润
	public static double getCityProfit(double A) {
		return getPrice(A) * 0.044 * 0.69;
	}

	// 县级代理分润
	public static double getCountyProfit(double A) {
		return getPrice(A) * 0.022 * 0.69;
	}

	// 社区代理分润
	public static double getCommunityProfit(double A) {
		return getPrice(A) * 0.014 * 0.69;
	}

	// 供应链支出合计
	public static double getChainTotal(double A) {
		return getPrice(A) * (0.044 * 0.69 + 0.022 * 0.69 + 0.014 * 0.69);
	}

	// 供应链剩余
	public static double getChainRemain(double A) {
		return getPrice(A) * (0.15 - 0.07 - 0.044 * 0.69 - 0.022 * 0.69 - 0.014 * 0.69);
	}

	// 易商通总余
	public static double getYstRemain(double A, double B, double D) {
		return getPrice(A) * 0.61 - B - D - 0.044 * 0.69 * getPrice(A) - 0.022 * 0.69 * getPrice(A)
				- 0.014 * 0.69 * getPrice(A) - 0.07 * getPrice(A);
	}

	// 普惠总余
	public static double getPhRemain(double A) {
		return getPrice(A) * 0.07;
	}
}
