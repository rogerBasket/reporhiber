package com.roger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateFactory {
	private static HibernateFactory instance;
	private static String url;
	private SessionFactory sessionFactory;
	
	static {
		Properties properties = new Properties();
		
		try {
			InputStream is = new FileInputStream("/home/roger/workspace_java/reporhiber/config.properties");
			
			properties.load(is);
			url = (String)properties.get("hibernate");
		} catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		instance = new HibernateFactory();
	}
	
	private HibernateFactory() {
		Configuration cfg = new Configuration();
		System.out.println(url);
		cfg.configure(new java.io.File(url));
		ServiceRegistryBuilder builder = new ServiceRegistryBuilder().applySettings(cfg.getProperties());
		sessionFactory = cfg.buildSessionFactory(builder.buildServiceRegistry());
	}
	
	public static HibernateFactory getInstance() {
		return instance;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
