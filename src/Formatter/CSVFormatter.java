package Formatter;

import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CSVFormatter{
    public static String toString(Task task){
        String result = "";
        result += String.valueOf(task.getId()) + ",";
        result += task.getType().toString() + ",";
        result += task.getTitle().toString() + ",";
        result += task.getProgress().toString() + ",";
        result += task.getStartTime().toString() + ",";
        result += task.getDuration().toString() + ",";
        if (task.getType().equals(TypeOfTask.SUBTASK)){
            SubTask sub = (SubTask) task;
            result += task.getDescription().toString() + ",";
            result += String.valueOf(sub.getEpicID());
        } else {
            result += task.getDescription().toString();
        }
        return result;
    }

    public static String getHeader(){
        return "id,type,name,status,startTime,duration,description,epic" + "\n";
    }

    public static List<Integer> createHistoryFromString(String line) throws IllegalArgumentException{
        List<Integer> history = Collections.emptyList();
        String[] elements = line.split(",");
        //history = Arrays.asList(elements);
        Integer[] intArray = new Integer[elements.length];
        int i = 0;
        for (String str : elements){
            try{
                intArray[i] = Integer.parseInt(str);
                i++;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Не число: " + str + " на позиции " + i, e);
            }
        }
        history = Arrays.asList(intArray);
        return history;
    }

    public static Task fromString(String[] array){
        Task taskResult;
        if (array[1].equals("SUBTASK")){
            taskResult = new SubTask(array[2], array[6], Progress.valueOf(array[3]), LocalDateTime.parse(array[4]), Duration.parse(array[5]),Integer.parseInt(array[7]));
            taskResult.setId(Integer.parseInt(array[0]));
        } else if (array[1].equals("TASK")){
            taskResult  = new Task(array[2], array[6], Progress.valueOf(array[3]), LocalDateTime.parse(array[4]), Duration.parse(array[5]));
            taskResult.setId(Integer.parseInt(array[0]));
        } else if (array[1].equals("EPIC")){
            taskResult  = new EpicTask(array[2], array[6], Progress.valueOf(array[3]), LocalDateTime.parse(array[4]), Duration.parse(array[5]));
            taskResult.setId(Integer.parseInt(array[0]));
        } else {
            taskResult = null;
        }
        return taskResult;
    }

    public static String historyToString(List<Task> list){
        String[] array = new String[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = String.valueOf(list.get(i).getId());
        }
        String result =  String.join(",", array);
        return result;
    }
}
