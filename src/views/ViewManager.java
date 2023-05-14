package views;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigInteger;

public class ViewManager extends JFrame {

    private PanelMenu panelMenu;
    private PanelTable panelTable;
    private DialogCreateProcess dialogCreateProcess;
    private PanelMenuReports panelMenuReports;
    private Object[][] inQueue, readyList, finished, currentList, dispatchList, executionList, expirationList, finishedList, spaceList, condensations;

    private boolean isPartitionsMenuActive = false;
    public ViewManager(ActionListener actionListener, KeyListener keyListener){
        this.setLayout(new BorderLayout());
        this.setTitle("Sexto Software");
        this.setFont(ConstantsGUI.MAIN_MENU);
        this.setSize(700, 500);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.getContentPane().setBackground(Color.decode("#f2e9e4"));
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.initComponents(actionListener, keyListener);
        this.setVisible(true);
    }

    private void initComponents(ActionListener actionListener, KeyListener keyListener) {
        this.panelMenu = new PanelMenu(actionListener);
        this.add(this.panelMenu, BorderLayout.WEST);

        this.panelTable = new PanelTable();
        this.add(this.panelTable, BorderLayout.CENTER);

        this.dialogCreateProcess = new DialogCreateProcess(actionListener, keyListener);
        this.panelMenuReports = new PanelMenuReports(actionListener);

        this.inQueue = new Object[0][0];
        this.readyList = new Object[0][0];
        this.finished = new Object[0][0];
        this.currentList = new Object[0][0];

    }

    public void showCreateProcessDialog(){
        this.dialogCreateProcess.changeButtonToCreate();
        this.dialogCreateProcess.setVisible(true);
    }

    public void showModifyProcessDialog(){
        this.dialogCreateProcess.changeButtonToModify();
        this.dialogCreateProcess.setVisible(true);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public String getProcessName(){
        return this.dialogCreateProcess.getNameProcess();
    }
    public BigInteger getProcessTime(){
        return this.dialogCreateProcess.getTimeProcess();
    }

    public BigInteger getProcessSize(){
        return this.dialogCreateProcess.getProcessSize();
    }

    public void setValuesToTable(Object[][] list, String title) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(list, ConstantsGUI.HEADERS_WITHOUT_PARTITION);
        this.panelTable.changeTitle(title);
        this.panelTable.setTableProcess(defaultTableModel);
    }

    public void setValuesToTableReport(Object[][] list, String title) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(list, ConstantsGUI.HEADERS_WITH_PARTITION);
        this.panelTable.changeTitle(title);
        this.panelTable.setTableProcess(defaultTableModel);
    }

    public void setValuesToTableReportCondensations(Object[][] list, String title) {
        DefaultTableModel defaultTableModel = new DefaultTableModel(list, ConstantsGUI.HEADERS_SPACE);
        this.panelTable.changeTitle(title);
        this.panelTable.setTableProcess(defaultTableModel);
    }

    public void hideCreateAndModifyProcessDialog(){
        this.dialogCreateProcess.setVisible(false);
        this.dialogCreateProcess.cleanAllFields();
        SwingUtilities.updateComponentTreeUI(this);
    }

    public int getIndexDataInTable(){
        return this.panelTable.getIndexDataProcess();
    }

    public void setProcessName(String processName){
        this.dialogCreateProcess.setProcessName(processName);
    }
    public void setProcessTime(BigInteger processTime){
        this.dialogCreateProcess.setTimeProcess(processTime);
    }
    public void setProcessSize(BigInteger processSize){
        this.dialogCreateProcess.setProcessSize(processSize);
    }

    public void changeToReportMenu() {
        this.remove(this.panelMenu);
        this.add(this.panelMenuReports, BorderLayout.WEST);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public void setValuesToCurrentProcess(){
        this.setValuesToTable(this.currentList, "Procesos Enviados");
    }


    public int getInQueueSize(){
        return this.currentList.length;
    }


    public void setCurrentList(Object[][] currentList) {
        this.currentList = currentList;
    }
    public void setInQueueList(Object[][] inQueue) {
        this.inQueue = inQueue;
    }

    public void setReadyList(Object[][] readyList) {
        this.readyList = readyList;
    }
    public void setDispatchList(Object[][] dispatchList) {
        this.dispatchList = dispatchList;
    }
    public void setExecutionList(Object[][] executionList) {
        this.executionList = executionList;
    }
    public void setExpirationList(Object[][] expirationList) {
        this.expirationList = expirationList;
    }
    public void setFinishedList(Object[][] finishedList) {
        this.finishedList = finishedList;
    }

    public void setSpaceList(Object[][] spaceList) {
        this.spaceList = spaceList;
    }
    public void setCondensationList(Object[][] condensations) {
        this.condensations = condensations;
    }

    public void setValuesToReadyReport(){
        this.setValuesToTableReport(this.readyList, "Procesos Listos");
    }
    public void setValuesToDispatchReport(){
        this.setValuesToTableReport(this.dispatchList, "Procesos Despachados");
    }

    public void setValuesToExecReport(){
        this.setValuesToTableReport(this.executionList, "Procesos en Ejecución");
    }

    public void setValuesToExepReport(){
        this.setValuesToTableReport(this.expirationList, "Procesos Expirados");
    }
    public void setValuesToFinishedReport(){
        this.setValuesToTableReport(this.finishedList, "Procesos Finalizados");
    }
    public void setValuesToSpaceReport(){
        this.setValuesToTableReport(this.spaceList, "Huecos en particiones");
    }
    public void setValuesToCondensationsReport(){
        this.setValuesToTableReportCondensations(this.condensations, "condensciones");
    }

    public boolean getIsPartitionsMenuActive(){
        return this.isPartitionsMenuActive;
    }
    public void setPartitionsMenuActive(boolean isActive){
        this.isPartitionsMenuActive = isActive;
    }
    public void changeToMainMenu(){
        this.remove(panelMenuReports);
        this.add(this.panelMenu, BorderLayout.WEST);
        SwingUtilities.updateComponentTreeUI(this);

    }

}
