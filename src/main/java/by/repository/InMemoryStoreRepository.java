package by.repository;

import by.entity.Store;
import by.repository.filedb.DB;
import by.repository.filedb.InMemoryDB;

import java.util.List;

public class InMemoryStoreRepository implements StoreRepository {
    private List<Store> stores;
    private DB inMemoryDB = new InMemoryDB();

    private InMemoryStoreRepository() {
        this.stores = inMemoryDB.getList(Store.class);
    }

    @Override
    public void add(Store store) {
        int lastStoreId = inMemoryDB.getId(Store.class);
        store.setId(++lastStoreId);
        stores.add(store);
        System.out.println("Магазин " + store.getName() + " добавлен");
        inMemoryDB.change(stores, lastStoreId, Store.class);
    }

    @Override
    public void delete(int id) {
        for (Store store : stores) {
            if (store == null) break;
            if (store.getId() == id) {
                stores.remove(store);
                System.out.println("Магазин " + store.getId() + " удален");
            }
            break;
        }
        inMemoryDB.change(stores, id, Store.class);
    }

    @Override
    public boolean update(String name, int id) {
        for (Store store : stores) {
            if (store == null) break;
            if (store.getId() == id) {
                store.setName(name);
                System.out.println("Название магазина изменено на: " + name);
                return true;
            }
            break;
        }
        inMemoryDB.change(stores, id, Store.class);
        return false;
    }

    @Override
    public Store[] findAll() {
        return stores.toArray(new Store[0]);
    }

    @Override
    public Store findByName(String name) {
        for (Store store : stores) {
          if (store == null) continue;
            if (store.getName().equals(name)) {
                System.out.println("Магазин найден: " + store.getName());
                return store;
            }
            break;
        }
        System.out.println("Магазин не найден");
        return null;
    }
}
