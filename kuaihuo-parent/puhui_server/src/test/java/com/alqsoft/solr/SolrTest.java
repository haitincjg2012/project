package com.alqsoft.solr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alqsoft.service.home.HomeService;
import com.alqsoft.solr.entity.Home;
import com.alqsoft.solr.entity.User;
import com.alqsoft.solr.repository.SolrProductRepository;
import com.alqsoft.solr.service.SolrHomeService;

/**
 * 测试spring data solr
 * @author zhangcan
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class SolrTest {
	
	@Autowired
	private HomeService homeService;
	@Autowired
	private SolrHomeService solrHomeService;
	@Autowired
	private SolrProductRepository solrProductRepositoty;
	/**
	 * 新增房源 并且添加到 solr数据库中
	 */
	@Test
	public void testAddHome(){
//		Home h = new Home();
//		h.setAreaName("房山"+RandomStringUtils.randomAlphanumeric(20));
//		h.setFeature("有钥匙"+RandomStringUtils.randomAlphanumeric(20));
//		h.setHouseType("2室2厅"+RandomStringUtils.randomAlphanumeric(20));
//		h.setMaxPrice(100d);
//		h.setMinPrice(10d);
//		h.setMaxSquare(140d);
//		h.setMinSquare(10d);
//		h.setRoomNum("2室"+RandomStringUtils.randomAlphanumeric(20));
//		h.setTitle("海景房,学区房,特惠");
//		homeService.saveAndModify(h);
//		solrHomeService.saveAndModify(h);
	}
	@Test
	public void testFindAll(){
		Home home2 = new Home();
		home2.setAddress("我爱厦门哈哈");
		home2.setEmail("zhangzhaocan@yeah.net");
		home2.setId("3459");
		home2.setHouseType("南北走向");
		home2.setMytitle("标题");
		solrHomeService.saveAndModify(home2);
		
		Page<Home> homes = solrHomeService.findByTitle("爱厦门",0,5);
		System.out.println("查处的大小为：：："+homes.getNumberOfElements());
		for (Home home : homes.getContent()) {
			System.out.println("数据----" + home.getId()+home.getHouseType()+home.getMytitle());
		}
	}
	@Test
	public void test() {
		User user2 = new User();
		user2.setAddress("我爱厦门哈哈");
		user2.setEmail("zhangzhaocan@yeah.net");
		user2.setId("333");
		solrProductRepositoty.save(user2);
		
		Page<User> u = solrProductRepositoty.findByAddressUsingAnnotatedQuery(
				"我爱厦门", new PageRequest(0, 2));
		System.out.println(u.getNumberOfElements());
		for (User user : u.getContent()) {
			System.out.println("数据----" + user.getAddress() + user.getEmail());
		}
	}
}
