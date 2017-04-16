package org.panwar;

import org.panwar.dao.JdbcDaoImp;
import org.panwar.model.Circle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcDemo {

	public static void main(String[] args) {
		
		
		ApplicationContext context =new ClassPathXmlApplicationContext("springJdbc.xml");
		JdbcDaoImp daoImp = context.getBean("jdbcDaoImp",JdbcDaoImp.class);
		Circle circle = daoImp.getCircle(1);
		Circle circleOne = new Circle();
		circleOne.setId(5);
		circleOne.setName("Fifth Circle");
		//daoImp.createNewTable("Rectangle");
		System.out.println(circle.getName());
		daoImp.insertRecordInCircleUsingNamedParameter(circleOne);
		System.out.println(daoImp.getCircleCount());
		System.out.println(daoImp.getAllCircle(2).toString());
		System.out.println(daoImp.getAllCircleName().toString());
	}
}
