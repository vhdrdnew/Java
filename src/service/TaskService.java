package service;

import model.Task;
import java.util.List;

public interface TaskService extends CRUDService<Task> {

    List<Task> findAllSortedByNameAndByDeadline();
}