package remote_client;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;

import api.IEntitySystem;

public class BasicRMIClient {
	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) { 
		try { 
			System.setSecurityManager(new RMISecurityManager());
			IEntitySystem universe = (IEntitySystem) Naming.lookup("rmi://localhost/entitysystem_server");
			// add some code to place things in universe here
			// could reference groovy code
			
		} catch (Exception e) { 
			System.out.println("unable to access server");
		}
		
	}

}
