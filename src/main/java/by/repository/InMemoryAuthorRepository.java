package by.repository;

import by.entity.Author;
import by.repository.filedb.DB;
import by.repository.filedb.InMemoryDB;

import java.util.List;

public class InMemoryAuthorRepository implements AuthorRepository {
  private List<Author> authors;
  private DB inMemoryDB = new InMemoryDB();

  public InMemoryAuthorRepository() {
    this.authors = inMemoryDB.getList(Author.class);
  }

  @Override
  public void add(Author author) {
    int id = inMemoryDB.getId(Author.class);
    author.setId(++id);
    authors.add(author);
    System.out.println("Автор " + author.getName() + " добавлен");
    inMemoryDB.change(authors, id, Author.class);
  }

  @Override
  public void delete(int id) {
    for (Author author : authors) {
      if (author == null) break;
      if (author.getId() == id) {
        authors.remove(author);
        System.out.println("Автор " + author.getName() + " удален");
        break;
      }
    }
    inMemoryDB.change(authors, id, Author.class);
  }

  @Override
  public boolean updateAuthorById(String name, int id) {
    for (Author author: authors){
      if (author == null) break;
      if (author.getId() == id){
        author.setName(name);
        System.out.println("Автор " + author.getId() + " изменен на: " + name);
        return true;
      }
    }
    inMemoryDB.change(authors, id, Author.class);
    return false;
  }

  @Override
  public Author findById(int id) {
    for (Author author : authors) {
      if (author == null) continue;
      if (author.getId() == id){
        System.out.println("Автор найден: " + author.getName());
        return author;
      }
      break;
    }
    System.out.println("Данного автора нет в базе");
    return null;
  }

  @Override
  public Author findByName(String name) {
    for (Author author : authors) {
      if (author == null) continue;
      if (author.getName().equals(name)){
        System.out.println("Автор найден: " + author.getName());
        return author;
      }
      break;
    }
    System.out.println("Данного автора нет в базе");
    return null;
  }

  @Override
  public Author[] findAll() {
    return authors.toArray(new Author[0]);
  }
}




