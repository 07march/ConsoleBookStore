package by.repository;

import by.entity.Author;
import by.repository.filedb.DB;
import by.repository.filedb.FileDB;

import java.util.List;

public class FileAuthorRepository implements AuthorRepository {
    private List<Author> authors;
    private DB file = new FileDB();

    public FileAuthorRepository() {
        this.authors = file.getList(Author.class);
    }

//    public static void main(String[] args) {
//        AuthorRepository authorRepository = new FileAuthorRepository();
//        authorRepository.add(new Author("Red"));
//        authorRepository.add(new Author("White"));
//        authorRepository.add(new Author("White2"));
//        System.out.println(Arrays.toString(authorRepository.findAll()));
////        authorRepository.delete(1);
//        authorRepository.updateAuthorById("Res", 3);
//        System.out.println(Arrays.toString(authorRepository.findAll()));
//    }

    @Override
    public void add(Author a) {
        int lastAuthorId = file.getId(Author.class);
        a.setId(++lastAuthorId);
        authors.add(a);
        System.out.println("Автор " + a.getName() + " добавлен");
        file.change(authors, lastAuthorId, Author.class);
    }

    @Override
    public void delete(int id) {
        for (Author a : authors) {
            if (a == null) break;
            if (a.getId() == id) {
                authors.remove(a);
                System.out.println("Автор " + a.getName() + " удален");
                break;
            }
        }
        file.change(authors, id, Author.class);
    }

    @Override
    public boolean updateAuthorById(String name, int id) {
        for (Author author : authors) {
            if (author == null) break;
            if (author.getId() == id) {
                author.setName(name);
                System.out.println("Автор " + author.getId() + " изменен на: " + name);
                return true;
            }
        }
        file.change(authors, id, Author.class);
        //        Author author = authors.get(id);
//        author.setName(name);
//        System.out.println("Автор " + author.getId() + " изменен на: " + name);
//        file.change(authors, id, Author.class);
//        return true;
        return false;
    }

    @Override
    public Author findById(int id) {
        for (Author author : authors) {
            if (author == null) continue;
            if (author.getId() == id) {
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
            if (author.getName().equals(name)) {
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
