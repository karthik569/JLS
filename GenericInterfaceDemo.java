// JLS §9.1.2 – Generic Interfaces

import java.util.*;


interface Repository<T> {
    void save(T entity);
    T findById(Long id);
}

class User {
    private Long id;
    private String name;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "'}";
    }
}

class UserRepository implements Repository<User> {
    private Map<Long, User> storage = new HashMap<>();

    @Override
    public void save(User user) {
        storage.put(user.id, user);
    }

    @Override
    public User findById(Long id) {
        return storage.get(id);
    }
}

public class GenericInterfaceDemo {
    public static void main(String[] args) {
        Repository<User> repo = new UserRepository();
        repo.save(new User(1L, "Alice"));
        System.out.println(repo.findById(1L));
    }
}
