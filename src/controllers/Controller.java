package controllers;

import models.Process;
import models.ProcessManager;
import views.Utilities;
import views.ViewManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigInteger;

public class Controller implements ActionListener, KeyListener {

    private ViewManager viewManager;
    private ProcessManager processManager;


    public Controller(){
        this.viewManager = new ViewManager(this, this);
        this.processManager = new ProcessManager();
        this.initSimulation();
        System.out.println("hola");
        processManager.sort();
    }
    public void sort(){
        for (int i = 0; i < processManager.getNewInqueue().size(); i++) {
            for (int j = 0; j < processManager.getFinishedList().size(); j++) {
                if (processManager.getNewInqueue().get(i).getPartitionName().equals(processManager.getFinishedList().get(j).getPartitionName()))
                    processManager.getFinishedPartition().add(processManager.getFinishedList().get(j));
            }
        }
        System.out.println(processManager.getFinishedPartition().toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "CrearProceso":
                this.showCreateProcessDialog();
                break;
            case "AñadirProceso":
                this.confirmAddProcess();
                break;
            case "CancelarAñadirProceso":
                this.cancelAddProcess();
                break;
            case "ModificarProceso":
                this.showModifyProcessDialog();
                break;
            case "ConfirmarModificacionProceso":
                this.confirmModifyProcess();
                break;
            case "EliminarProceso":
                this.deleteProcess();
                break;
            case "Reportes":
                this.changeToReportMenu();
                break;
            case "ManualUsuario":
                this.openManual();
                break;
            case "Salir":
                System.exit(0);
                break;
        }
    }

    private void initSimulation(){
        processManager.initSimulation();
    }

    private void showCreateProcessDialog(){
        this.viewManager.showCreateProcessDialog();
    }

    private void confirmAddProcess() {
        String processName = this.viewManager.getProcessName().trim();
        BigInteger timeProcess = this.viewManager.getProcessTime();
        BigInteger sizeProcess = this.viewManager.getProcessSize();

        if (processName.trim().isEmpty()) {
            Utilities.showErrorDialog("El nombre del proceso está vacío. Ingrese algún valor");
        } else if (this.processManager.isAlreadyProcessName(processName)) {
            Utilities.showErrorDialog("El nombre del proceso ya existe. Ingrese otro nombre");
        } else if (timeProcess.toString().equals("-1")) {
            Utilities.showErrorDialog("El tiempo del proceso está vacío. Ingrese un valor numérico entero");
        } else if (sizeProcess.toString().equals("-1")) {
            Utilities.showErrorDialog("El tamaño del proceso está vacío. Ingrese un valor numérico entero");
        } else {
            Process newProcess = new Process(processName, timeProcess, sizeProcess);
            this.processManager.addToInQueue(newProcess);
            this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
            this.viewManager.hideCreateAndModifyProcessDialog();
        }
    }

    private void cancelAddProcess(){
        this.viewManager.hideCreateAndModifyProcessDialog();
    }

    private void showModifyProcessDialog(){
        if(this.viewManager.getIndexDataInTable() == -1){
            Utilities.showErrorDialog("Debe seleccionar un proceso");
        }
        else {
            Process processToModify = this.processManager.getProcessInQueue(this.viewManager.getIndexDataInTable());
            this.viewManager.setProcessName(processToModify.getName());
            this.viewManager.setProcessTime(processToModify.getTime());
            this.viewManager.setProcessSize(processToModify.getSize());
            this.viewManager.showModifyProcessDialog();
        }
    }


    private void confirmModifyProcess(){
        Process processToModify = this.processManager.getProcessInQueue(this.viewManager.getIndexDataInTable());
        String modifyNameProcess = this.viewManager.getProcessName();

        if(modifyNameProcess.trim().equals("")){
            Utilities.showErrorDialog("El nombre del proceso está vacío. Ingrese algún valor");
        }
        else if(!processToModify.getName().equals(modifyNameProcess)
                && this.processManager.isAlreadyProcessName(modifyNameProcess)){
            Utilities.showErrorDialog("Ya existe un proceso con este nombre");
        }
        else if(this.viewManager.getProcessTime().toString().trim().equals("-1")){
            Utilities.showErrorDialog("El tiempo del proceso está vacío. Ingrese un valor numérico entero");
        }
        else if(this.viewManager.getProcessSize().toString().trim().equals("-1")){
            Utilities.showErrorDialog("El tamaño del proceso está vacío. Ingrese un valor numérico entero");
        }
        else {
            Process newProcess = new Process(this.viewManager.getProcessName(), this.viewManager.getProcessTime(), this.viewManager.getProcessSize());
            this.processManager.updateProcessInQueue(newProcess, this.viewManager.getIndexDataInTable());
            this.viewManager.hideCreateAndModifyProcessDialog();
            this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
        }
    }

    private void deleteProcess(){
        if(this.viewManager.getIndexDataInTable() == -1){
            Utilities.showErrorDialog("Debe seleccionar un proceso");
        }
        else {
            int confirmation = Utilities.showConfirmationWarning();
            if(confirmation == 0){
                this.processManager.deleteProcessInQueue(this.viewManager.getIndexDataInTable());
                this.viewManager.setValuesToCurrentProcess();
            }

        }

    }

    private void changeToReportMenu(){
        if(this.viewManager.getInQueueSize() != 0){
            this.viewManager.changeToReportMenu();
            this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
        }
        else {
            Utilities.showErrorDialog("Debe haber realizado alguna simulación para usar esta opción");
        }
    }


    private void openManual(){
        try{
            java.lang.Process p = Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+"C:\\Users\\Usuario\\Desktop\\SO\\Software\\Renovar - ICETEX 2023-1.pdf");
        } catch (Exception e){
            System.out.println("El archivo no se puede abrir");
        }
    }



    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c)) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public static void main(String[] args) {
        new Controller();
    }
}
