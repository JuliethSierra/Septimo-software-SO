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
        //this.viewManager = new ViewManager(this, this);
        this.processManager = new ProcessManager();
        this.initSimulation1();
        System.out.println("hola");
        processManager.addCondensations();
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
            case "Enviar":
                this.initSimulation();
                break;
            case "Actuales":
                this.setValuesToCurrentProcess();
                break;
            case "Listos":
                this.setValuesToReadyReport();
                break;
            case "Despachados":
                this.setValuesToDispatchReport();
                break;
            case "Ejecucion":
                this.setValuesToExecReport();
                break;
            case "Expirados":
                this.setValuesToExepReport();
                break;
            case "Finalizados":
                this.setValuesToFinishedReport();
                break;
            case "Huecos":
                this.setValuesToSpaceReport();
                break;
            case "Condensaciones":
                this.setValuesToCondensationsReport();
                break;
            case "ManualUsuario":
                this.openManual();
                break;
            case "Atras":
                this.changeToMenu();
                break;
            case "Salir":
                System.exit(0);
                break;
        }
    }

    //método de prueba
    private void initSimulation1(){
        processManager.initSimulation();
    }
    private void initSimulation(){
        if(this.processManager.getInQueue().size() == 0){
            Utilities.showErrorDialog("Debe ingresar al menos un proceso para iniciar la simulación");
        } else{
            int response = Utilities.showConfirmationWarning();
            if(response == 0) {
                processManager.initSimulation();
                processManager.addCondensations();
                Utilities.showDoneCPUProcess();
                processManager.cleanQueueList();
                processManager.cleanNewQueueList();
                processManager.copyToCurrentProcess();
                this.cleanMainTableProcess();
                this.loadReportList();
            }
        }
    }

    private void loadReportList(){
        viewManager.setCurrentList(processManager.getProcessListAsMatrixObject(processManager.getCurrentList()));
        viewManager.setInQueueList(processManager.getProcessListAsMatrixObject(processManager.getInQueue()));
        viewManager.setReadyList(processManager.getProcessListAsMatrixReportObject(processManager.getReadyList()));
        viewManager.setDispatchList(processManager.getProcessListAsMatrixReportObject(processManager.getDispatchList()));
        viewManager.setExecutionList(processManager.getProcessListAsMatrixReportObject(processManager.getExecutionList()));
        viewManager.setExpirationList(processManager.getProcessListAsMatrixReportObject(processManager.getExpirationList()));
        viewManager.setFinishedList(processManager.getProcessListAsMatrixReportObject(processManager.getFinishedList()));
        viewManager.setSpaceList(processManager.getProcessListAsMatrixReportObject(processManager.getSpaceList()));
        viewManager.setCondensationList(processManager.getProcessListAsMatrixReportCon(processManager.getCondensations()));
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

    private void cleanMainTableProcess(){
        this.viewManager.setValuesToTable(processManager.getProcessListAsMatrixObject(processManager.getInQueue()), "Procesos Existentes");
    }

    public void setValuesToCurrentProcess(){
        this.viewManager.setValuesToCurrentProcess();
    }
    public void setValuesToReadyReport(){
        this.viewManager.setValuesToReadyReport();
    }
    public void setValuesToDispatchReport(){
        this.viewManager.setValuesToDispatchReport();
    }

    public void setValuesToExecReport(){
        this.viewManager.setValuesToExecReport();
    }

    public void setValuesToExepReport(){
        this.viewManager.setValuesToExepReport();
    }
    public void setValuesToFinishedReport(){
        this.viewManager.setValuesToFinishedReport();
    }
    public void setValuesToSpaceReport(){
        this.viewManager.setValuesToSpaceReport();
    }
    public void setValuesToCondensationsReport(){
        this.viewManager.setValuesToCondensationsReport();
    }

    private void changeToMenu(){
        if(this.viewManager.getIsPartitionsMenuActive()){
            this.viewManager.setPartitionsMenuActive(false);

        }
        else
            this.processManager.cleanAllLists();
        this.viewManager.setValuesToTable(this.processManager.getProcessListAsMatrixObject(this.processManager.getInQueue()), "Procesos Existentes");
        this.viewManager.changeToMainMenu();
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
