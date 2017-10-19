package cm.ph.shopping.facade.order.entity;



public class PhPurchaseList {
    /** 表流水 */
    private Long id;
    
    /** 商品id */
    private Long productId;

    /** 商品名称 */
    private String productName;

	/** 商品编码 */
    private String productCode;

	/** 数量 */
    private Integer num;

    /** 单价 */
    private Long price;

    /** 总金额 */
    private Long totalMoney;

    /** 收货地址 */
    private Long getAddressId;
    
    /** 单价 */
    private double price1;
    
    /** 进货单id */
    private Long managerOrderId;
    
    /** 供应商id */
    private Long supplyerId;
    /** 用户id*/
    private Long userId;

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSupplyerId() {
		return supplyerId;
	}

	public void setSupplyerId(Long supplyerId) {
		this.supplyerId = supplyerId;
	}

	public Long getManagerOrderId() {
		return managerOrderId;
	}

	public void setManagerOrderId(Long managerOrderId) {
		this.managerOrderId = managerOrderId;
	}

	public double getPrice1() {
		return price1;
	}

	public void setPrice1(double price1) {
		this.price1 = price1;
	}

	public double getTotalMoney1() {
		return totalMoney1;
	}

	public void setTotalMoney1(double totalMoney1) {
		this.totalMoney1 = totalMoney1;
	}

	/** 总金额 */
    private double totalMoney1;

	public Long getGetAddressId() {
        return getAddressId;
    }

    public Long getId() {
        return id;
    }

    public Integer getNum() {
        return num;
    }

    public Long getPrice() {
        return price;
    }

    public String getProductCode() {
        return productCode;
    }

    public Long getProductId() {
		return productId;
	}

    public String getProductName() {
        return productName;
    }

    public Long getTotalMoney() {
        return totalMoney;
    }

    public void setGetAddressId(Long getAddressId) {
        this.getAddressId = getAddressId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public void setProductId(Long productId) {
		this.productId = productId;
	}

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public void setTotalMoney(Long totalMoney) {
        this.totalMoney = totalMoney;
    }
}