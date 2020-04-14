package by.repository;

import by.entity.City;
import by.repository.filedb.DB;
import by.repository.filedb.InMemoryDB;

import java.util.List;

public class InMemoryCityRepository implements CityRepository {
    private List<City> cities;
    private DB inMemoryDB = new InMemoryDB();

    private InMemoryCityRepository() {
        this.cities = inMemoryDB.getList(City.class);
    }

    @Override
    public void add(City city) {
        int id = inMemoryDB.getId(City.class);
        city.setId(++id);
        cities.add(city);
        System.out.println("Город " + city.getName() + " добавлен");
        inMemoryDB.change(cities, id, City.class);
    }

    @Override
    public void delete(int id) {
        for (City city : cities) {
            if (city == null) break;
            if (city.getId() == id) {
                cities.remove(city);
                System.out.println("Город " + city.getId() + " удален");
            }
            break;
        }
        inMemoryDB.change(cities, id, City.class);
    }

    @Override
    public void delete(String name) {
        int id = inMemoryDB.getId(City.class);
        for (City city : cities) {
            if (city == null) break;
            if (city.getName().equals(name)) {
                cities.remove(city);
                System.out.println("Город " + city.getName() + " удален");
            }
            break;
        }
        inMemoryDB.change(cities, id, City.class);
    }

    @Override
    public boolean update(String name, int id) {
        for (City city : cities) {
            if (city == null) break;
            if (city.getId() == id) {
                city.setName(name);
                System.out.println("Город " + city.getId() + " изменен на: " + name);
                return true;
            }
            break;
        }
        inMemoryDB.change(cities, id, City.class);
        return false;
    }

    @Override
    public City[] findAll() {
        return cities.toArray(new City[0]);
    }

    @Override
    public City findByName(String name) {
        for (City city : cities) {
            if (city.getName() == null) continue;
            if (city.getName().equals(name)) {
                System.out.println("Город найден: " + city.getName());
                return city;
            }
            break;
        }
        System.out.println("Город не найден");
        return null;
    }

    @Override
    public City findById(int id) {
        for (City city : cities) {
            if (city.getId() == 0) continue;
            if (city.getId() == id) {
                System.out.println("Город найден: " + city.getName());
                return city;
            }
            break;
        }
        System.out.println("Город не найден");
        return null;
    }
}
