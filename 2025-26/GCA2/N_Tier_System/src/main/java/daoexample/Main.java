package daoexample;

import daoexample.dao.JdbcTaskDao;
import daoexample.dao.TaskDao;
import daoexample.domain.Task;

import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        TaskDao dao = new JdbcTaskDao();

        createTaskDemo(dao);
        readTaskDemo(dao);
        updateTaskDemo(dao);
        deleteTaskDemo(dao);
    }

    private static void createTaskDemo(TaskDao dao) {
        try {
            int newId = dao.insert("Write DAO notes example", "TODO");
            System.out.println("CREATE");
            System.out.println("Inserted task with id: " + newId);
            System.out.println();
        }
        catch (Exception e) {
            System.out.println("CREATE failed");
            e.printStackTrace();
        }
    }

    private static void readTaskDemo(TaskDao dao) {
        try {
            System.out.println("READ");

            Optional<Task> found = dao.findById(1);
            if (found.isPresent())
                System.out.println("Found by id: " + found.get());
            else
                System.out.println("No task found with id 1");

            List<Task> allTasks = dao.findAll();
            System.out.println("All tasks:");
            for (Task task : allTasks)
                System.out.println(task);

            System.out.println();
        }
        catch (Exception e) {
            System.out.println("READ failed");
            e.printStackTrace();
        }
    }

    private static void updateTaskDemo(TaskDao dao) {
        try {
            System.out.println("UPDATE");

            boolean updated = dao.updateStatus(1, "DONE");
            System.out.println("Updated task 1: " + updated);

            Optional<Task> updatedTask = dao.findById(1);
            if (updatedTask.isPresent())
                System.out.println("After update: " + updatedTask.get());

            System.out.println();
        }
        catch (Exception e) {
            System.out.println("UPDATE failed");
            e.printStackTrace();
        }
    }

    private static void deleteTaskDemo(TaskDao dao) {
        try {
            System.out.println("DELETE");

            int tempId = dao.insert("Temporary task for delete demo", "TODO");
            System.out.println("Inserted temporary task with id: " + tempId);

            boolean deleted = dao.deleteById(tempId);
            System.out.println("Deleted task " + tempId + ": " + deleted);

            Optional<Task> deletedTask = dao.findById(tempId);
            if (deletedTask.isEmpty())
                System.out.println("Confirmed: task no longer exists");

            System.out.println();
        }
        catch (Exception e) {
            System.out.println("DELETE failed");
            e.printStackTrace();
        }
    }
}