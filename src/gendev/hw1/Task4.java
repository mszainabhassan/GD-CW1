package gendev.hw1;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.*;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.resource.impl.*;

import hw1.*;

public class Task4 {
	public static String instance = "task4-Instances/instance.xmi";

	public static void main(String[] args) {
		System.out.println("Creating and saving instance to file " + instance);

		Hw1Factory factory = Hw1Factory.eINSTANCE;
		BookingSystem root = factory.createBookingSystem();
		
		User user1 = factory.createUser();
		user1.setUsername("Marley");
		User user2 = factory.createUser();
		user2.setUsername("Scrooge");
		Event freeEvent1 = factory.createFreeEvent();
		user1.getWishlistItems().add(freeEvent1);
		user2.getWishlistItems().add(freeEvent1);
		
	    BookedEvent bookedEvent1 = factory.createBookedEvent();
	    user1.getBookedEvents().add(bookedEvent1);
	    freeEvent1.getBookedInstances().add(bookedEvent1);
	    bookedEvent1.setEventIsFree(true);
	    
	    PaymentDetails paymentDetails1 = factory.createPaymentDetails();
	    user1.setPaymentDetails(paymentDetails1);
	    PaymentDetails paymentDetails2 = factory.createPaymentDetails();
	    user2.setPaymentDetails(paymentDetails2);
	    
		// Attach things to root!
		root.getUsers().add(user1);
		root.getUsers().add(user2);
		root.getEvents().add(freeEvent1);
	    
	    Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	    Map<String,Object> map = reg.getExtensionToFactoryMap();
	    
	    map.put("xmi", new XMIResourceFactoryImpl());
	    ResourceSet resSet = new ResourceSetImpl();
	    Resource resource = resSet.createResource(URI.createURI(instance));
	    // Add root object to resource
	    resource.getContents().add(root);
	    
	    try {
	    	resource.save(Collections.EMPTY_MAP);
	    } catch (IOException err) {
	    	err.printStackTrace();
	    }
	}

}
