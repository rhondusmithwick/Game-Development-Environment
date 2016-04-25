package api;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.collect.Collections2;

import datamanagement.XMLReader;
import groovy.lang.GroovyShell;

/**
 * The interface for an Level, which holds all the entities.
 *
 * @author Rhondu Smithwick, Tom Wu
 */
public interface ILevel extends ISerializable {

	/**
	 * Names the entity System
	 *
	 * @param name
	 *            with the name
	 */
	void setName(String name);

	/**
	 * Gets the Entity Systems name
	 *
	 * @return string entity system name
	 */
	String getName();

	/**
	 * Gets the metadata about this level
	 * 
	 * @return Metadata in the form of a map
	 */
	Map<String, String> getMetadata();

	/**
	 * Add a piece of metadata to this level
	 * 
	 * @param key
	 *            the field name (e.g. "Description")
	 * @param value
	 *            the field value
	 */
	void addMetadata(String key, String value);

	/**
	 * Sets the metadata
	 * 
	 * @param metadata
	 */
	void setMetadata(Map<String, String> metadata);

	String init(GroovyShell shell, ISystemManager game);

	void update(double dt);


	/**
	 * @return event system
	 */
	IEventSystem getEventSystem();

	/**
	 * @return entity system
	 */
	IEntitySystem getEntitySystem();

	/**
	 * @return physics engine
	 */
	IPhysicsEngine getPhysicsEngine();

	/**
	 * Gets the Entity System's event system's XML path
	 * 
	 * @return string of event system file path
	 */
	String getEventSystemPath();

	/**
	 * Sets the Entity System's event system's XML path
	 * 
	 * @param eventSystemPath
	 *            string of event system file path
	 */
	void setEventSystemPath(String eventSystemPath);

	/**
	 * Creates an entity.
	 *
	 * @return the entity created
	 */
	IEntity createEntity();

	/**
	 * Adds an entity.
	 *
	 * @param entity
	 *            the entity to be added
	 * @return the added entity
	 */
	IEntity addEntity(IEntity entity);

	/**
	 * Get an entity based on its id.
	 *
	 * @param id
	 *            of the entity
	 * @return entity with provided id
	 */
	IEntity getEntity(String id);

	/**
	 * Get all entites in the system.
	 *
	 * @return collection of entities
	 */
	Collection<IEntity> getAllEntities();

	/**
	 * Get all the components in this entity system.
	 * 
	 * @return all the components in this entity system
	 */
	default List<IComponent> getAllComponents() {
		return getAllEntities().stream().map(IEntity::getAllComponents).flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	/**
	 * Get all IDs in this system.
	 *
	 * @return all the IDs in this system
	 */
	default Collection<String> getAllIDS() {
		return Collections2.transform(getAllEntities(), IEntity::getID);
	}

	/**
	 * Annihilate everything as our good friend Tom puts it.
	 * 
	 * @see #getAllIDS()
	 * @see #removeEntity(String)
	 */
	default void clear() {
		getAllIDS().stream().forEach(this::removeEntity);
	}

	/**
	 * Check whether this system contains an entity with provided ID.
	 *
	 * @param id
	 *            to check
	 * @return true if system contains this entity
	 */
	boolean containsID(String id);

	/**
	 * Check if system contains this entity.
	 *
	 * @param entity
	 *            to check
	 * @return true if system contains this entity
	 * @see #containsID(String)
	 * @see IEntity#getID()
	 */
	default boolean containsEntity(IEntity entity) {
		return containsID(entity.getID());
	}

	/**
	 * Add list of entities.
	 *
	 * @param entities
	 *            list of entities to add
	 * @return list of entities
	 * @see #addEntity(IEntity)
	 */
	default List<IEntity> addEntities(List<IEntity> entities) {
		return entities.stream().map(this::addEntity).collect(Collectors.toList());
	}

	/**
	 * Add array/varargs of entities.
	 *
	 * @param entities
	 *            to add
	 * @return list of entities
	 * @see #addEntities(List)
	 */
	default List<IEntity> addEntities(IEntity... entities) {
		return addEntities(Arrays.asList(entities));
	}

	/**
	 * Remove entity with this ID.
	 *
	 * @param id
	 *            to remove
	 * @return true if removed
	 */
	boolean removeEntity(String id);

	/**
	 * Checks if any entities are in this system.
	 *
	 * @return true if system contains no entities
	 */
	boolean isEmpty();

	/**
	 * Created an entity from a file containing an entity.
	 *
	 * @param fileName
	 *            of file with the entity
	 * @return the entity loaded
	 * @see IDataReader#readSingleFromFile(String)
	 */
	default IEntity createEntityFromLoad(String fileName) {
		IDataReader<IEntity> reader = new XMLReader<>();
		IEntity entity = reader.readSingleFromFile(fileName);
		addEntity(entity);
		return entity;
	}

	/**
	 * Create an entity from a file of components.
	 *
	 * @param defaultFileName
	 *            of the file
	 * @return entity with components in this file
	 * @see IDataReader#readFromFile(String)
	 */
	default IEntity createEntityFromDefault(String defaultFileName) {
		IEntity entity = createEntity();
		IDataReader<IComponent> reader = new XMLReader<>();
		List<IComponent> components = reader.readFromFile(defaultFileName);
		entity.forceAddComponents(components, true);
		addEntity(entity);
		return entity;
	}

	/**
	 * Get all the components of a type.
	 *
	 * @param componentType
	 *            class to get
	 * @param <T>
	 *            the type of component
	 * @return all the components of the type
	 * @see IEntity#getComponentList(Class)
	 */
	default <T extends IComponent> Collection<T> getAllComponentsOfType(Class<T> componentType) {
		Predicate<List<T>> nonEmpty = l -> !l.isEmpty();
		return getAllEntities().stream().map(e -> e.getComponentList(componentType)).filter(nonEmpty)
				.flatMap(Collection::stream).collect(Collectors.toList());
	}

	/**
	 * Get all the entities with this component Type.
	 *
	 * @param componentType
	 *            the component type
	 * @param <T>
	 *            the type of component
	 * @return all the entities with this component type
	 * @see IEntity#hasComponent(Class)
	 */
	default <T extends IComponent> Set<IEntity> getEntitiesWithComponent(Class<T> componentType) {
		Predicate<IEntity> hasComponent = (e) -> e.hasComponent(componentType);
		return getAllEntities().stream().filter(hasComponent).collect(Collectors.toSet());
	}

	/**
	 * Get entities with all these components (list).
	 *
	 * @param componentClasses
	 *            components to check
	 * @return all entities with these components
	 * @see IEntity#hasComponents(List)
	 */
	default Set<IEntity> getEntitiesWithComponents(List<Class<? extends IComponent>> componentClasses) {
		Predicate<IEntity> hasComponents = (e) -> e.hasComponents(componentClasses);
		return getAllEntities().stream().filter(hasComponents).collect(Collectors.toSet());
	}

	/**
	 * Get entities with all these components (array or varargs).
	 *
	 * @param componentClasses
	 *            components to check
	 * @return all entities with these components
	 * @see #getEntitiesWithComponents(List)
	 */
	@SuppressWarnings("unchecked")
	default Set<IEntity> getEntitiesWithComponents(Class<? extends IComponent>... componentClasses) {
		return getEntitiesWithComponents(Arrays.asList(componentClasses));
	}

	/**
	 * Get component using an id
	 *
	 * @param <T>
	 *            type of component
	 * @param id
	 *            of the entity
	 * @param componentType
	 *            the type of component
	 * @return component with type T of entity with id id
	 * @see IEntity#getComponentList(Class)
	 */
	default <T extends IComponent> List<T> getComponentOfEntity(String id, Class<T> componentType) {
		return getEntity(id).getComponentList(componentType);
	}

	/**
	 * Get all the entities with provided name.
	 *
	 * @param name
	 *            provided name
	 * @return list of entities with this name
	 */
	default List<IEntity> getEntitiesWithName(String name) {
		Predicate<IEntity> isName = (e) -> (Objects.equals(e.getName(), name));
		return getAllEntities().stream().filter(isName).collect(Collectors.toList());
	}

	/**
	 * Get all entity names in this system.
	 *
	 * @return all names in this system
	 */
	default Collection<String> getAllNames() {
		return Collections2.transform(getAllEntities(), IEntity::getName);
	}

	/**
	 * Remove all the Bindings from all the components in this Entity System.
	 */
	default void removeAllBindingsFromComponents() {
		getAllComponents().stream().forEach(IComponent::removeBindings);
	}
}
