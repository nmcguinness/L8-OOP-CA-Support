package daoexample.dao;

import daoexample.domain.Task;

import java.util.List;
import java.util.Optional;

/// <summary>
/// DAO contract for reading and writing Task objects.
/// </summary>
public interface TaskDao {

    int insert(String title, String status) throws Exception;

    Optional<Task> findById(int id) throws Exception;

    List<Task> findAll() throws Exception;

    boolean updateStatus(int id, String newStatus) throws Exception;

    boolean deleteById(int id) throws Exception;
}