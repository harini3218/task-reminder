package com.example.taskreminder.csv;

import com.example.taskreminder.model.Task;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Service
public class TaskcsvService {

    public void writeTasksToCsv(List<Task> tasks, Writer writer) throws IOException {

        CSVPrinter csvPrinter = new CSVPrinter(writer,
                CSVFormat.DEFAULT.withHeader("ID", "Title", "Description", "Due Date", "Completed"));

        for (Task task : tasks) {
            csvPrinter.printRecord(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getDueDate(),
                    task.isCompleted()
            );
        }

        csvPrinter.flush();
    }
}
