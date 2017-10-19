package com.alqsoft.service.area;

import static org.junit.Assert.fail;

import java.io.File;
import java.util.List;

import org.alqframework.xml.dom4j.Dom4jUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alqsoft.entity.area.Area;

/**
 * 
 * 
 * @author 张靠勤
 * @e-mail 627658539@qq.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2013-11-4 上午11:04:23
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:/applicationContext.xml")
public class AreaServiceTest {

	@Autowired
	private AreaService areaService;
	
	@Test
	public void testSaveAndModify() throws DocumentException {
		Document document=Dom4jUtils.getReadDocument(new File("F://citycode.xml"));
		List list=document.selectNodes("//State");
		for (Object object : list) {
			Element element=(Element) object;
			Area area=new Area();
			area.setName(element.attributeValue("Name"));
			area.setParentId(0);
			area.setAreaId(0L);
			areaService.saveAndModify(area);
			System.out.println(area.getId());
			List list2=element.elements("City");
			for (Object object2 : list2) {
				Element element2=(Element) object2;
				Area area2=new Area();
				area2.setName(element2.attributeValue("Name"));
				area2.setParentId(1);
				area2.setAreaId(area.getId());
				areaService.saveAndModify(area2);
				System.out.println(area2.getId());
				List list3=element2.elements("Region");
				for (Object object3 : list3) {
					Element element3=(Element) object3;
					Area area3=new Area();
					area3.setName(element3.attributeValue("Name"));
					area3.setParentId(2);
					area3.setAreaId(area2.getId());
					areaService.saveAndModify(area3);
					System.out.println(area3.getId());
				}
			}
		}
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testGet() {
		fail("Not yet implemented");
	}

}
