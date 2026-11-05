package service;

import model.Task;
import java.util.Comparator;
import java.util.List;

public class TaskServiceImpl extends InMemoryCrudService<Task> implements TaskService {

    @Override
    public List<Task> findAllSortedByNameAndByDeadline() {  
        Comparator<Task> nameAndDeadlineComparator = Comparator
                .comparing(Task::getName, String.CASE_INSENSITIVE_ORDER)
                .thenComparingInt(Task::getDeadlineDays);

        return findAllSorted(nameAndDeadlineComparator);
    }
}