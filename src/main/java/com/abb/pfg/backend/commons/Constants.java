package com.abb.pfg.backend.commons;

/**
 * Constants class for services.
 * 
 * @author Adrian Barco Barona
 * @version 1.0
 *
 */
public class Constants {
	
	public interface Controllers {
		
		public interface Users{
			public static final String PATH = "/users";
		}
		
		public interface Students{
			public static final String PATH = "/students";
		}
		
		public interface Companies{
			public static final String PATH = "/companies";
		}
		
		public interface Adminis{
			public static final String PATH = "/admins";
		}
		
		public interface JobOffers{
			public static final String PATH = "/jobOffers";
		}
		
		public interface Requests{
			public static final String PATH = "/requests";
		}
		
		public interface Chats{
			public static final String PATH = "/chats";
		}
		
		public interface Messages{
			public static final String PATH = "/messages";
		}
		
		public interface Multimedia{
			public static final String PATH ="/multimedia";
		}
		
		public interface Resource{
			public static final String PATH="/resources";
		}
	}

}
