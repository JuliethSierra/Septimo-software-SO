package models;

import java.math.BigInteger;
import java.util.ArrayList;

public class ProcessManager {

    private final int PROCESS_TIME = 1;
    private ArrayList<Process> inQueue, currentList;
    private ArrayList<PartitionReport> spaceList,processList,readyList,newInqueue, dispatchList, executionList, expirationList, finishedList, finishedPartition, partitionList;
    private ArrayList<Partition> partitions;
    private ArrayList<Compaction> compactions;

    public ProcessManager(){
        this.partitions = new ArrayList<>();
        this.inQueue = new ArrayList<>();
        this.currentList = new ArrayList<>();
        this.readyList = new ArrayList<>();
        this.newInqueue = new ArrayList<>();
        this.dispatchList = new ArrayList<>();
        this.executionList = new ArrayList<>();
        this.expirationList = new ArrayList<>();
        this.finishedPartition = new ArrayList<>();
        this.finishedList = new ArrayList<>();
        this.processList = new ArrayList<>();
        this.compactions = new ArrayList<>();
        this.spaceList = new ArrayList<>();
        this.partitionList = new ArrayList<>();
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

    public Object[][] getProcessListAsMatrixReportObject(ArrayList<PartitionReport> list){
        return this.parseArrayListToMatrixReportObject(list);
    }

    private Object[][] parseArrayListToMatrixReportObject(ArrayList<PartitionReport> list){
        int sizeQueue = list.size();
        Object[][] processList = new Object[sizeQueue][5];

        for(int i = 0; i < sizeQueue; i++){
            processList[i][0] = list.get(i).getPartitionName();
            processList[i][1] = list.get(i).getProcess().getName();
            processList[i][2] = list.get(i).getProcess().getTime();
            processList[i][3] = list.get(i).getProcess().getSize();
        }
        return processList;
    }

    public Object[][] getProcessListAsMatrixReportCon(ArrayList<Compaction> list){
        return this.parseArrayListToMatrixReportCon(list);
    }

    private Object[][] parseArrayListToMatrixReportCon(ArrayList<Compaction> list){
        int sizeQueue = list.size();
        Object[][] processList = new Object[sizeQueue][2];

        for(int i = 0; i < sizeQueue; i++){
            processList[i][0] = list.get(i).getName();
            processList[i][1] = list.get(i).getSize();
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

    public void setInQueue(ArrayList<Process> inQueue) {
        this.inQueue = inQueue;
    }

    public ArrayList<Process> getCurrentList() {
        return currentList;
    }

    public void setCurrentList(ArrayList<Process> currentList) {
        this.currentList = currentList;
    }

    public ArrayList<PartitionReport> getReadyList() {
        return readyList;
    }

    public void setReadyList(ArrayList<PartitionReport> readyList) {
        this.readyList = readyList;
    }

    public ArrayList<PartitionReport> getDispatchList() {
        return dispatchList;
    }

    public void setDispatchList(ArrayList<PartitionReport> dispatchList) {
        this.dispatchList = dispatchList;
    }

    public ArrayList<PartitionReport> getExecutionList() {
        return executionList;
    }

    public void setExecutionList(ArrayList<PartitionReport> executionList) {
        this.executionList = executionList;
    }

    public ArrayList<PartitionReport> getExpirationList() {
        return expirationList;
    }

    public void setExpirationList(ArrayList<PartitionReport> expirationList) {
        this.expirationList = expirationList;
    }

    public ArrayList<PartitionReport> getFinishedList() {
        return finishedList;
    }

    public void setFinishedList(ArrayList<PartitionReport> finishedList) {
        this.finishedList = finishedList;
    }

    public ArrayList<PartitionReport> getFinishedPartition() {
        return finishedPartition;
    }

    public void setFinishedPartition(ArrayList<PartitionReport> finishedPartition) {
        this.finishedPartition = finishedPartition;
    }

    public ArrayList<Partition> getPartitions() {
        return partitions;
    }

    public void setPartitions(ArrayList<Partition> partitions) {
        this.partitions = partitions;
    }

    public ArrayList<PartitionReport> getProcessList() {
        return processList;
    }

    public void setProcessList(ArrayList<PartitionReport> processList) {
        this.processList = processList;
    }

    public ArrayList<PartitionReport> getSpaceList() {
        return spaceList;
    }

    public void setSpaceList(ArrayList<PartitionReport> spaceList) {
        this.spaceList = spaceList;
    }

    public ArrayList<Compaction> getCompactions() {
        return compactions;
    }

    public void setCompactions(ArrayList<Compaction> compactions) {
        this.compactions = compactions;
    }

    public ArrayList<PartitionReport> getNewInqueue() {
        return newInqueue;
    }

    public void setNewInqueue(ArrayList<PartitionReport> newInqueue) {
        this.newInqueue = newInqueue;
    }

    public void cleanAllLists() {
        this.inQueue.clear();
        this.currentList.clear();
        this.readyList.clear();
        this.dispatchList.clear();
        this.executionList.clear();
        this.expirationList.clear();
        this.finishedList.clear();
        this.finishedPartition.clear();
        this.partitions.clear();
        this.newInqueue.clear();
        this.processList.clear();
        this.compactions.clear();
        this.spaceList.clear();
    }

    public void initSimulation(){
        this.init();
        this.initNewInQueue();
        this.copyToCurrentProcess();
        this.initLoadToReady();
        for (int i = 0; i < readyList.size(); i++) {
            this.loadToDispatchQueue(new PartitionReport(readyList.get(i).getPartitionName(), new Process(readyList.get(i).getProcess().getName(), readyList.get(i).getProcess().getTime(), readyList.get(i).getProcess().getSize())));
            if(readyList.get(i).getProcess().getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 1 || readyList.get(i).getProcess().getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 0){
                this.loadToExecQueue(new PartitionReport(readyList.get(i).getPartitionName(), new Process(readyList.get(i).getProcess().getName(), this.consumeTimeProcess(readyList.get(i).getProcess()), readyList.get(i).getProcess().getSize())));

            }else{
                this.loadToExecQueue(new PartitionReport(readyList.get(i).getPartitionName(), new Process(readyList.get(i).getProcess().getName(), readyList.get(i).getProcess().getTime(), readyList.get(i).getProcess().getSize())));
            }
            if (!(readyList.get(i).getProcess().getTime().compareTo(BigInteger.valueOf(0)) == 0)) {
                if (readyList.get(i).getProcess().getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 1) {
                    this.loadToExpirationQueue(new PartitionReport(readyList.get(i).getPartitionName(), new Process(readyList.get(i).getProcess().getName(), this.consumeTimeProcess(readyList.get(i).getProcess()), readyList.get(i).getProcess().getSize())));
                    this.loadToReadyQueue(new PartitionReport(readyList.get(i).getPartitionName(), new Process(readyList.get(i).getProcess().getName(), this.consumeTimeProcess(readyList.get(i).getProcess()), readyList.get(i).getProcess().getSize())));
                } else {
                    this.loadToFinishedQueue(new PartitionReport(readyList.get(i).getPartitionName(), new Process(readyList.get(i).getProcess().getName(), BigInteger.valueOf(0), readyList.get(i).getProcess().getSize())));
                }

            }
        }
    }

    private BigInteger consumeTimeProcess(Process process) {
        return (process.getTime().subtract(BigInteger.valueOf(PROCESS_TIME)));
    }

    public void initNewInQueue(){
        for (int i = 0; i < inQueue.size(); i++) {
            newInqueue.add(new PartitionReport("part"+String.valueOf(i+1), inQueue.get(i)));
        }
    }
    private void initLoadToReady() {
        readyList.addAll(newInqueue);
    }

    private void init() {
        readyList.addAll(newInqueue);
        /*inQueue.add(new Process("p1", new BigInteger("10"), new BigInteger("10")));
        inQueue.add(new Process("p2", new BigInteger("5"), new BigInteger("15")));
        inQueue.add(new Process("p3", new BigInteger("15"), new BigInteger("10")));
        inQueue.add(new Process("p4", new BigInteger("4"), new BigInteger("5")));*/

       /* inQueue.add(new Process("p11", new BigInteger("5"), new BigInteger("11")));
        inQueue.add(new Process("p15", new BigInteger("7"), new BigInteger("15")));
        inQueue.add(new Process("p18", new BigInteger("8"), new BigInteger("18")));
        inQueue.add(new Process("p6", new BigInteger("3"), new BigInteger("6")));
        inQueue.add(new Process("p9", new BigInteger("4"), new BigInteger("9")));
        inQueue.add(new Process("p20", new BigInteger("2"), new BigInteger("20")));
        inQueue.add(new Process("p13", new BigInteger("3"), new BigInteger("13")));*/

      /*  inQueue.add(new Process("p11", new BigInteger("5"), new BigInteger("11")));
        inQueue.add(new Process("p15", new BigInteger("7"), new BigInteger("15")));
        inQueue.add(new Process("p18", new BigInteger("8"), new BigInteger("18")));
        inQueue.add(new Process("p6", new BigInteger("3"), new BigInteger("6")));
        inQueue.add(new Process("p9", new BigInteger("4"), new BigInteger("9")));
        inQueue.add(new Process("p20", new BigInteger("2"), new BigInteger("20")));
        inQueue.add(new Process("p13", new BigInteger("3"), new BigInteger("13")));
        inQueue.add(new Process("p14", new BigInteger("2"), new BigInteger("14")));*/
    }

    public void copyToCurrentProcess(){
        currentList.addAll(inQueue);
    }

    private void loadToReadyQueue(PartitionReport process) {
        this.readyList.add(process);
    }

    private void loadToProcessList(PartitionReport process) {
        this.processList.add(process);
    }
    private void loadToDispatchQueue(PartitionReport partitionReport) {
        this.dispatchList.add(partitionReport);
    }
    private void loadToExecQueue(PartitionReport process) {
        this.executionList.add(process);
    }
    private void loadToExpirationQueue(PartitionReport process) {
        this.expirationList.add(process);
    }
    private void loadToFinishedQueue(PartitionReport process) {
        this.finishedList.add(process);
    }

    private void loadToCompactions(Compaction process) {
        this.compactions.add(process);
    }

    public BigInteger findMaxTime(){
        BigInteger num = new BigInteger("0");
        BigInteger num1 = new BigInteger("0");
        for (int i = 0; i < newInqueue.size(); i++) {
            if(newInqueue.get(i).getProcess().getTime().compareTo(num)==1){
                num = newInqueue.get(i).getProcess().getTime();
            }
        }
        num1 = (num.divide(BigInteger.valueOf(PROCESS_TIME))).multiply(BigInteger.valueOf(newInqueue.size()));
        num1 = num1.subtract(BigInteger.valueOf(1));
        return num1;
    }



    public void addCompactions(){
        this.iniSpace();
        int processCount = 0;
        int size =0;
        int partitionCount = newInqueue.size()+1;
        for (int i = 0; i <= findMaxTime().intValue(); i++) {
            if (processList.get(i).getProcess().getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 1 || processList.get(i).getProcess().getTime().compareTo(BigInteger.valueOf(PROCESS_TIME)) == 0) {
                this.loadToProcessList(new PartitionReport(processList.get(i).getPartitionName(), new Process(processList.get(i).getProcess().getName(), processList.get(i).getProcess().getTime().subtract(BigInteger.valueOf(PROCESS_TIME)), processList.get(i).getProcess().getSize())));
            } else {
                this.loadToProcessList(new PartitionReport(processList.get(i).getPartitionName(), new Process(processList.get(i).getProcess().getName(), new BigInteger("0"), processList.get(i).getProcess().getSize())));
            }
            processCount++;
            if (processCount == newInqueue.size()) {
                ArrayList<PartitionReport> processTimeInZero = new ArrayList<>();
                for (int j = i+1; j < processList.size(); j++) {
                    if(processList.get(j).getProcess().getTime().compareTo(BigInteger.valueOf(0)) == 0){
                        processTimeInZero.add(new PartitionReport(processList.get(j).getPartitionName(), new Process(processList.get(j).getProcess().getName(), processList.get(j).getProcess().getTime(), processList.get(j).getProcess().getSize())));
                        size+=processList.get(j).getProcess().getSize().intValue();
                    }else{
                        spaceList.add(new PartitionReport(processList.get(j).getPartitionName(),new Process(processList.get(j).getProcess().getName(), processList.get(j).getProcess().getTime(), processList.get(j).getProcess().getSize())));
                        if(!partitionNameAlreadyExist(processList.get(j).getPartitionName())){
                            partitionList.add(new PartitionReport(processList.get(j).getPartitionName(),new Process(processList.get(j).getProcess().getName(), processList.get(j).getProcess().getTime(), processList.get(j).getProcess().getSize())));
                        }
                    }

                }
                processCount =0;
                spaceList.addAll(processTimeInZero);
                if(processTimeInZero.size() > 1){
                    if(partitionList.size() > 0){
                        if(partitionList.get(partitionList.size()-1).getProcess().getTime().compareTo(BigInteger.valueOf(0)) == 0){
                            if(partitionList.get(partitionList.size()-1).getProcess().getSize().compareTo(BigInteger.valueOf(size)) == -1){
                                partitionList.add(new PartitionReport("part"+partitionCount++, new Process("Libre", new BigInteger("0"), BigInteger.valueOf(size))));
                            }
                        }else{
                            partitionList.add(new PartitionReport("part"+partitionCount++, new Process("Libre", new BigInteger("0"), BigInteger.valueOf(size))));
                        }
                    }else {
                        partitionList.add(new PartitionReport("part"+partitionCount++, new Process("Libre", new BigInteger("0"), BigInteger.valueOf(size))));
                    }
                }
                size =0;
            }
        }

        this.fillCompactions();
        System.out.println("Compactions:");
        System.out.println(compactions.toString());

        System.out.println("PartitionList:");
        System.out.println(partitionList.toString());

        System.out.println("SpaceList:");
        System.out.println(spaceList.toString());

        System.out.println("Finish:");
        System.out.println(finishedList.toString());
    }


    private void fillCompactions(){
        int counter = 1;
        for (int i = 0; i < partitionList.size(); i++) {
            if (partitionList.get(i).getProcess().getTime().compareTo(BigInteger.ZERO) == 0){
                loadToCompactions(new Compaction("Compact" + counter++ , partitionList.get(i).getProcess().getSize()));
            }
        }
    }
    private boolean partitionNameAlreadyExist(String partitionName){
        for (int i = 0; i < partitionList.size(); i++) {
            if (partitionList.get(i).getPartitionName().equals(partitionName)){
                return true;
            }
        }
        return false;
    }
    public void iniSpace(){
        for (int i = 0; i < inQueue.size(); i++) {
            processList.add(new PartitionReport("part"+String.valueOf(i+1), inQueue.get(i)));
        }
    }
    public void cleanQueueList(){
        inQueue.clear();
    }

    public void cleanNewQueueList(){
        newInqueue.clear();
    }

}
