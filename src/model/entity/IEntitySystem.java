package model.entity;

import model.component.IComponent;
import api.ISerializable;
import datamanagement.IDataReader;
import datamanagement.XMLReader;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public interface IEntitySystem extends ISerializable {

    IEntity createEntity();

    IEntity addEntity(IEntity entity);

    IEntity getEntity(int id);

    Collection<IEntity> getAllEntities();

    boolean containsID(int id);

    default boolean containsEntity(IEntity entity) {
        return containsID(entity.getID());
    }

    default List<IEntity> addEntities(IEntity... entities) {
        return addEntities(Arrays.asList(entities));
    }

    default List<IEntity> addEntities(List<IEntity> entities) {
        return entities.stream().map(this::addEntity).collect(Collectors.toList());
    }

    boolean removeEntity(int id);

    int getNextAvailableID();

    default IEntity createEntityFromLoad(String fileName) {
        IDataReader<IEntity> reader = new XMLReader<>();
        IEntity entity = reader.readSingleFromFile(fileName);
        addEntity(entity);
        return entity;
    }

    default IEntity createEntityFromDefault(String defaultFileName) {
        IEntity entity = createEntity();
        IDataReader<IComponent> reader = new XMLReader<>();
        List<IComponent> components = reader.readFromFile(defaultFileName);
        entity.addComponents(components);
        return entity;
    }

    default <T extends IComponent> List<T> getAllComponentsOfType(Class<T> componentType) {
        return getAllEntities().stream().map(e -> e.getComponentList(componentType))
                .filter(Objects::nonNull).flatMap(List::stream).collect(Collectors.toList());
    }

    default <T extends IComponent> Set<IEntity> getEntitiesWithComponentType(Class<T> componentType) {
        Predicate<IEntity> hasComponent = (e) -> e.hasComponent(componentType);
        return getAllEntities().stream().filter(hasComponent).collect(Collectors.toSet());
    }

    default <T extends IComponent> List<T> getComponentOfEntity(int id, Class<T> componentType) {
        return getEntity(id).getComponentList(componentType);
    }
}
