package manager;

import tasks.Task;
import tasks.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager{


    Map<Integer, Node> nodeMap = new HashMap<>();

    private Node first;

    private Node last;

    @Override
    public void add(Task task){
        removeNode(task.getId()); // убираем узел, если он уже есть
        linkLast(task);
        nodeMap.put(task.getId(), last);
    }

    private void removeNode(int id){
        Node node = nodeMap.remove(id);
        if (node == null){
            return; // выходим, если нет такого элемента
        }
        if (node.getPrev() != null){
            //не первая
            node.getPrev().setNext(node.getNext());
            if (node.getNext() == null){
                //последняя нода
                last = node.getPrev();
            } else {
                node.getNext().setPrev(node.getPrev());
            }
        } else {
            //если первая
            first = node.getNext();
            if (first == null) {
                //всего одна нода
                last = null;
            } else {
                first.setPrev(null);
            }
        }
    }

    private void linkLast(Task task){
        Node node = new Node(task, last, null);
        if (first == null){
            first = node; // если список был пустой
        } else {
            last.setNext(node);
        }
        last = node;
    }

    public List<Task> getTasks(){
        Node current = first;
        List<Task> tasks = new ArrayList<>();
        while (current != null){
            tasks.add(current.getTask());
            current = current.getNext();
        }
        return tasks;
    }



    public List<Task> getHistory(){
        return getTasks();
    }

    public void remove(int id) {
        removeNode(id);
    }

    public void setNodeMap(Map<Integer, Node> nodeMap) {
        this.nodeMap = nodeMap;
    }
}
