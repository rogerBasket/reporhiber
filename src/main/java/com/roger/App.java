package com.roger;


import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.HashMap;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.roger.model.Usuarios;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        HibernateFactory hf = HibernateFactory.getInstance();
        SessionFactory factory = hf.getSessionFactory();
        Session session = factory.getCurrentSession();
        //Session session = factory.openSession();
        
        /*session.beginTransaction();
        session.save(new Usuarios("user1","pass1",new Date(),"prueba1@gmail.com",new Perfiles(2)));
        session.getTransaction().commit();*/
        
        session.beginTransaction();
        List<?> l = session.createCriteria("com.roger.model.Usuarios").list();
        Set<?> distintos = new LinkedHashSet<Object>(l);
//        List<?> l = session.createQuery("from Usuarios").list();
        Iterator<?> i = distintos.iterator();
        while(i.hasNext()) {
//        	Usuarios u = (Usuarios)i.next();
        	System.out.println(i.next());
        }
        
       // session.close();
        factory.close();
        
        crearReporte(distintos);
    }
    
    public static void crearReporte(Set<?> distintos) {
    	 try {
         	String fileName = JasperFillManager.fillReportToFile("/home/roger/report1.jasper",
         			new HashMap<String,Object>(),new JRBeanCollectionDataSource(distintos));
         	
         	if(fileName != null)
         		JasperExportManager.exportReportToPdfFile(fileName,"/home/roger/report1.pdf");
         } catch(Exception jre) {
         	jre.printStackTrace();
         }
    }
}
