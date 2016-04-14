package remote_server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import api.IEntity;
import api.IEntitySystem;

@Deprecated 
// does not have to be IEntity 
public class BasicRMIServer extends UnicastRemoteObject implements IEntitySystem{
	
	public BasicRMIServer() throws RemoteException {
		super();
	}

	@Override
	public IEntity createEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEntity addEntity(IEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEntity getEntity(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IEntity> getAllEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsID(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeEntity(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	public static void main(String[] args) {
		try {
		Registry reg = LocateRegistry.getRegistry();
		reg.bind("entitysystem_server", new BasicRMIServer());
		System.out.println("Server is running!");
		} catch (Exception ex) { 
			System.out.println("something went wrong");
		}
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
