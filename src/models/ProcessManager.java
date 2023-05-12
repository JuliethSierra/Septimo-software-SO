package models;

import java.util.ArrayList;

public class ProcessManager {


    private ArrayList<Process> inQueue, currentList, readyList;

    public ProcessManager(){
        this.inQueue = new ArrayList<>();
        this.currentList = new ArrayList<>();
        this.readyList = new ArrayList<>();

    }

    public boolean isAlreadyProcessName(String name){
        for(Process process: inQueue){
            if(process.getName().equals(name))
                return true;
        }
        return false;
    }

    public void addToInQueue(Process process){
        this.inQueue.add(process);
    }

    public Object[][] getProcessListAsMatrixObject(ArrayList<Process> list){
        return this.parseArrayListToMatrixObject(list);
    }

    private Object[][] parseArrayListToMatrixObject(ArrayList<Process> list){
        int sizeQueue = list.size();
        Object[][] processList = new Object[sizeQueue][5];

        for(int i = 0; i < sizeQueue; i++){
            processList[i][0] = list.get(i).getName();
            processList[i][1] = list.get(i).getTime();
            processList[i][2] = list.get(i).getSize();
        }
        return processList;
    }

    public Process getProcessInQueue(int indexDataInTable) {
        return this.inQueue.get(indexDataInTable);
    }

    public void updateProcessInQueue(Process newProcess, int indexDataInTable) {
        this.inQueue.set(indexDataInTable, newProcess);
    }
    public void deleteProcessInQueue(int indexDataInTable) {
        this.inQueue.remove(indexDataInTable);
    }



    public ArrayList<Process> getInQueue() {
        return inQueue;
    }
}
