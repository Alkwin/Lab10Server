public class Person {
    String name;
    ClientThread thread;

    public String getName() {
        return name;
    }

    public ClientThread getThread() {
        return thread;
    }

    public Person(String name, ClientThread thread) {
        this.name = name;
        this.thread = thread;
    }
}
