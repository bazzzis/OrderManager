/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import DAO.OrderDAO;
import Domain.Address;
import Domain.Order;
import Domain.OrderDetails;
import Domain.State;
import MySQL.MySqlAddressDao;
import MySQL.MySqlOrderDao;
import MySQL.MySqlOrderDetails;
import MySQL.MysqlStateDao;
import java.util.Date;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Bazis
 */
public class OrderManager extends javax.swing.JFrame {

    List<Order> ordersList;
    MySqlOrderDao orderDao = new MySqlOrderDao();
    MySqlAddressDao addressDao = new MySqlAddressDao();
    int orderId = 0;
    int orderLineId = 0;
    int selectedLineRow = 0;
    int selectedRow = 0;

    /**
     * Creates new form OrderManager
     */
    public OrderManager() {

        initComponents();
        List<Order> ordersList = orderDao.getAll();

        for (Order ordersList1 : ordersList) {
            DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();
            tableModel.addRow(new Object[]{ordersList1.getOrderId(), ordersList1.getPickUpCompanyName(), ordersList1.getPickUpStreetName(), ordersList1.getPickUpCity(),
                ordersList1.getPickUpZipCode(), ordersList1.getPickUpCountry(), ordersList1.getPickupReference(), ordersList1.getDeliveryCompanyName(), ordersList1.getDeliveryStreetName(),
                ordersList1.getDeliveryCity(), ordersList1.getDeliveryZipCode(), ordersList1.getDeliveryCountry(), ordersList1.getDeliveryReference(), ordersList1.getPickUpDate().toString(),
                ordersList1.getPickUpTime().toString(), ordersList1.getDeliveryDate().toString(), ordersList1.getDeliveryTime().toString(), ordersList1.getDeliveryConditions(), ordersList1.isBlock()});
        }

        jTable1.setRowSelectionAllowed(true);
        ListSelectionModel cellSelectionModel = jTable1.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jTable2.setRowSelectionAllowed(true);
        ListSelectionModel cellSelectionMod = jTable2.getSelectionModel();
        cellSelectionMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                jTextField1.setEditable(false);

                selectedRow = jTable1.getSelectedRow();
                //int[] selectedColumns = jTable1.getSelectedColumns();

                //for (int i = 0; i < selectedRow.length; i++) {
                //for (int j = 0; j < selectedColumns.length; j++) {
                orderId = (Integer) jTable1.getValueAt(selectedRow, 0);
                //}
                //}
                Order order = orderDao.read(orderId, true);
                Address pickUpaddress = new Address();
                Address deliveryAddress = new Address();
                Address invoiceAddress = new Address();

                String deliveryref = order.getDeliveryReference();
                String invoiceref = order.getInvoiceReference();

                if (order.getPickUpRelation() != 0) {
                    pickUpaddress = addressDao.read(order.getPickUpRelation(), true);

                    Integer pckRelation = order.getPickUpRelation();
                    jTextFieldPCKR.setText(pckRelation.toString());
                    jTextField1PCKCN.setText(pickUpaddress.getCompanyName());
                    jTextFieldPCKSTR.setText(pickUpaddress.getStreetName());
                    jTextFieldPCKCTY.setText(pickUpaddress.getCity());
                    Integer pickUpZip = pickUpaddress.getZipCode();
                    jTextFieldPCKZIP.setText(pickUpZip.toString());
                    jTextFieldPCKCOU.setText(pickUpaddress.getCountry());
                    jTextFieldPCKREF.setText(order.getPickupReference());

                } else {
                    Integer pckRelation = order.getPickUpRelation();
                    jTextFieldPCKR.setText(pckRelation.toString());
                    jTextField1PCKCN.setText(order.getPickUpCompanyName());
                    jTextFieldPCKSTR.setText(order.getPickUpStreetName());
                    jTextFieldPCKCTY.setText(order.getPickUpCity());
                    Integer pickUpZip = order.getPickUpZipCode();
                    jTextFieldPCKZIP.setText(pickUpZip.toString());
                    jTextFieldPCKCOU.setText(order.getPickUpCountry());
                    jTextFieldPCKREF.setText(order.getPickupReference());
                }
                if (order.getDeliveryRelation() != 0) {
                    deliveryAddress = addressDao.read(order.getDeliveryRelation(), true);

                    Integer delRelation = order.getDeliveryRelation();
                    jTextFieldDELR.setText(delRelation.toString());
                    jTextFieldDELCMN.setText(deliveryAddress.getCompanyName());
                    jTextFieldDELSTR.setText(deliveryAddress.getStreetName());
                    jTextFieldDELCTY.setText(deliveryAddress.getCity());
                    Integer delUpZip = deliveryAddress.getZipCode();
                    jTextFieldDELZIP.setText(delUpZip.toString());
                    jTextFieldDELCOU.setText(deliveryAddress.getCountry());
                    jTextFieldDELREF.setText(order.getDeliveryReference());

                } else {
                    Integer delRelation = order.getDeliveryRelation();
                    jTextFieldDELR.setText(delRelation.toString());
                    jTextFieldDELCMN.setText(order.getDeliveryCompanyName());
                    jTextFieldDELSTR.setText(order.getDeliveryStreetName());
                    jTextFieldDELCTY.setText(order.getDeliveryCity());
                    Integer delUpZip = order.getDeliveryZipCode();
                    jTextFieldDELZIP.setText(delUpZip.toString());
                    jTextFieldDELCOU.setText(order.getDeliveryCountry());
                    jTextFieldDELREF.setText(order.getDeliveryReference());
                }

                if (order.getInvoiceRelation() != 0) {
                    invoiceAddress = addressDao.read(order.getInvoiceRelation(), true);
                    Integer invRelation = order.getInvoiceRelation();
                    jTextFieldINVR.setText(invRelation.toString());
                    jTextFieldINVCMN.setText(invoiceAddress.getCompanyName());
                    jTextFieldINVSTR.setText(invoiceAddress.getStreetName());
                    jTextFieldINVCTY.setText(invoiceAddress.getCity());
                    Integer invZip = invoiceAddress.getZipCode();
                    jTextFieldINVZIP.setText(invZip.toString());
                    jTextFieldINVCOU.setText(invoiceAddress.getCountry());
                    jTextFieldINVREF.setText(order.getInvoiceReference());

                } else {

                    JOptionPane.showMessageDialog(null, "Invoice relation is wrong, \nplease adjust it to the once available in database  ", "Error!", JOptionPane.ERROR_MESSAGE);

                }

                jTextField1.setText("");
                jTextField2.setText("");
                jTextField4.setText("");
                jTextField3.setText("");

                DefaultTableModel orderDetails = (DefaultTableModel) jTable2.getModel();
                MySqlOrderDetails detailService = new MySqlOrderDetails();
                List<OrderDetails> listDetails = detailService.readByProductId(orderId);

                if (listDetails != null) {

                    int rowCount = orderDetails.getRowCount();
                    for (int i = rowCount - 1; i >= 0; i--) {
                        orderDetails.removeRow(i);
                    }
                    for (OrderDetails listDetail : listDetails) {

                        orderDetails.addRow(new Object[]{listDetail.getId(), listDetail.getPackagingType(), listDetail.getDescription(), listDetail.getWeight()});

                    }

                    ListSelectionModel table2SelectionModel = jTable2.getSelectionModel();
                    table2SelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

                    cellSelectionMod.addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {

                            selectedLineRow = jTable2.getSelectedRow();
                            System.out.println("Selected row " + selectedLineRow);
                            //int[] selectedColumns = jTable1.getSelectedColumns();

//                            for (int i = 0; i < selectedRow.length; i++) {
                            //for (int j = 0; j < selectedColumns.length; j++) {
                            //   String temp = jTable2.getValueAt(selectedRow, 0);
                            if (selectedLineRow != -1) {
                                System.out.println("Value of o = " + jTable2.getValueAt(selectedLineRow, 0));
                                orderLineId = (Integer) jTable2.getValueAt(selectedLineRow, 0);

                                OrderDetails editableDetail = detailService.readById(orderLineId);

                                jTextField1.setText(editableDetail.getId().toString());
                                jTextField2.setText(editableDetail.getPackagingType());
                                jTextField4.setText(editableDetail.getDescription());
                                jTextField3.setText(editableDetail.getWeight().toString());
                                //}
//                            }
                            }
                        }
                    });

                }

                DefaultTableModel stateTable = (DefaultTableModel) jTable3.getModel();
                MysqlStateDao stateService = new MysqlStateDao();
                List<State> listStates = stateService.readByOrderId(orderId);

                if (listStates != null) {

                    int rowCount = stateTable.getRowCount();
                    for (int i = rowCount - 1; i >= 0; i--) {
                        stateTable.removeRow(i);
                    }
                    for (State listState : listStates) {

                        stateTable.addRow(new Object[]{listState.getOrderId(), listState.getAction(), listState.getOk(), listState.getTimeStamp()});

                    }
                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTextFieldOrderNumber = new javax.swing.JTextField();
        jLabelOrderNumber = new javax.swing.JLabel();
        jComboBoxReference = new javax.swing.JComboBox();
        jTextFieldReference = new javax.swing.JTextField();
        jButtonSearch = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButtonAddOrder = new javax.swing.JButton();
        jButtonBlock = new javax.swing.JButton();
        jButtonUnblock = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        Action = new javax.swing.JLabel();
        jComboBoxAction = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxOk = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        addState = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jTextFieldPCKREF = new javax.swing.JTextField();
        jTextFieldPCKZIP = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldPCKSTR = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField1PCKCN = new javax.swing.JTextField();
        jTextFieldPCKCOU = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldPCKCTY = new javax.swing.JTextField();
        jTextFieldPCKR = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jTextFieldDELREF = new javax.swing.JTextField();
        jTextFieldDELZIP = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldDELSTR = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jTextFieldDELCMN = new javax.swing.JTextField();
        jTextFieldDELCOU = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextFieldDELCTY = new javax.swing.JTextField();
        jTextFieldDELR = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jTextFieldINVREF = new javax.swing.JTextField();
        jTextFieldINVZIP = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jTextFieldINVSTR = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTextFieldINVCMN = new javax.swing.JTextField();
        jTextFieldINVCOU = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextFieldINVCTY = new javax.swing.JTextField();
        jTextFieldINVR = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextFieldOrderNumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldOrderNumberActionPerformed(evt);
            }
        });

        jLabelOrderNumber.setText("Order Number");

        jComboBoxReference.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pick-up reference", "Delivery reference", "Invoice reference" }));
        jComboBoxReference.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxReferenceActionPerformed(evt);
            }
        });

        jButtonSearch.setText("Search");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonSearch))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jLabelOrderNumber)
                        .addGap(25, 25, 25)
                        .addComponent(jTextFieldOrderNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBoxReference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jTextFieldReference, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldOrderNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelOrderNumber)
                    .addComponent(jComboBoxReference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldReference, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jButtonSearch))
        );

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order Id", "Pick-up CMPNY name", "Pick-up street", "Pick-up city", "Pick-up Zip", "Pick-up country", "Pick-up reference", "Delivery CMPNY name", "Delivery Street", "Delivery City", "delivery Zip", "Delivery Country", "Delivery reference", "Pick-up date", "Pick-up time", "Delivery date", "Delivery time", "Delivery conditions", "Block"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jButtonAddOrder.setText("add order");
        jButtonAddOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddOrderActionPerformed(evt);
            }
        });

        jButtonBlock.setText("block");
        jButtonBlock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBlockActionPerformed(evt);
            }
        });

        jButtonUnblock.setText("unblock");
        jButtonUnblock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUnblockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButtonAddOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonBlock, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonUnblock, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonAddOrder)
                    .addComponent(jButtonBlock)
                    .addComponent(jButtonUnblock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
        );

        Action.setText("Action:");

        jComboBoxAction.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pick-up", "Cross dock in", "Cross dock out", "Delivery" }));

        jLabel5.setText("ok/nok:");

        jComboBoxOk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ok", "nok" }));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order", "Action", "ok/nok", "Timestamp"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);

        addState.setText("add");
        addState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addStateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(Action)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addState, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Action)
                    .addComponent(jComboBoxAction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxOk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addState))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("State", jPanel3);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Pick-up address"));
        jPanel7.setAutoscrolls(true);
        jPanel7.setName(""); // NOI18N
        jPanel7.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jPanel7PropertyChange(evt);
            }
        });

        jLabel7.setText("Country");

        jLabel8.setText("City and zip code");

        jLabel9.setText("reference");

        jLabel10.setText("Relation");

        jLabel11.setText("Street");

        jLabel12.setText("Company Name");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel12)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldPCKREF, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(jTextFieldPCKR)
                    .addComponent(jTextField1PCKCN)
                    .addComponent(jTextFieldPCKSTR)
                    .addComponent(jTextFieldPCKCOU)
                    .addComponent(jTextFieldPCKCTY))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldPCKZIP, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPCKR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField1PCKCN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPCKSTR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPCKCTY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jTextFieldPCKZIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPCKCOU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextFieldPCKREF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder("Delivery address"));
        jPanel9.setAutoscrolls(true);
        jPanel9.setName(""); // NOI18N

        jLabel19.setText("Country");

        jLabel20.setText("City and zip code");

        jLabel21.setText("reference");

        jLabel22.setText("Relation");

        jLabel23.setText("Street");

        jLabel24.setText("Company Name");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel24)
                    .addComponent(jLabel22)
                    .addComponent(jLabel23)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldDELREF, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(jTextFieldDELR)
                    .addComponent(jTextFieldDELCMN)
                    .addComponent(jTextFieldDELSTR)
                    .addComponent(jTextFieldDELCTY)
                    .addComponent(jTextFieldDELCOU))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldDELZIP, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDELR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(jTextFieldDELCMN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDELSTR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDELCTY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jTextFieldDELZIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldDELCOU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextFieldDELREF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Invoice address"));
        jPanel10.setAutoscrolls(true);
        jPanel10.setName(""); // NOI18N

        jLabel25.setText("Country");

        jLabel26.setText("City and zip code");

        jLabel27.setText("reference");

        jLabel28.setText("Relation");

        jLabel29.setText("Street");

        jLabel30.setText("Company Name");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel30)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldINVREF, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(jTextFieldINVR)
                    .addComponent(jTextFieldINVCMN)
                    .addComponent(jTextFieldINVSTR)
                    .addComponent(jTextFieldINVCOU)
                    .addComponent(jTextFieldINVCTY))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextFieldINVZIP, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldINVR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jTextFieldINVCMN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldINVSTR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldINVCTY, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(jTextFieldINVZIP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldINVCOU, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(jTextFieldINVREF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Address", jPanel4);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order Line ID", "Packaging", "Description", "Weight"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable2);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Order LIne ID");

        jLabel2.setText("Packaging");

        jLabel3.setText("Weight");

        jLabel4.setText("Description");

        jButton1.setText("New");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addGap(32, 32, 32)
                        .addComponent(jTextField4, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(102, 102, 102))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Order lines", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jTabbedPane2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 373, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jTextFieldOrderNumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldOrderNumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldOrderNumberActionPerformed

    private void jComboBoxReferenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxReferenceActionPerformed

        System.out.println(evt.getActionCommand());

    }//GEN-LAST:event_jComboBoxReferenceActionPerformed

    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed
        DefaultTableModel tableModel = (DefaultTableModel) jTable1.getModel();

        if (!jTextFieldOrderNumber.getText().isEmpty()) {
            try {
                int textFieldOrderNumberValue = Integer.parseInt(jTextFieldOrderNumber.getText().trim());
                ordersList = orderDao.getOrdersById(textFieldOrderNumberValue);
            } catch (NumberFormatException e) {
                LOG.log(Level.WARNING, e.toString());
                JOptionPane.showMessageDialog(null, "Number format exception \nProvide a valid number value! ", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        } else if (!jTextFieldReference.getText().isEmpty()) {
            String comboBoxValue = String.valueOf(jComboBoxReference.getSelectedItem());
            String textFieldReferenceValue = jTextFieldReference.getText().trim();
            int relType;
            switch (comboBoxValue) {
                case "Pick-up reference":
                    relType = 1;
                    break;
                case "Delivery reference":
                    relType = 2;
                    break;
                case "Invoice reference":
                    relType = 3;
                    break;
                default:
                    relType = 0;

            }
            if (relType != 0) {
                ordersList = orderDao.findByReference(textFieldReferenceValue, relType);
            }
        } else {
            ordersList = orderDao.getAll();
        }

        if (!ordersList.isEmpty()) {

            int rowCount = tableModel.getRowCount();
            for (int i = rowCount - 1; i >= 0; i--) {
                tableModel.removeRow(i);
            }

            for (Order ordersList1 : ordersList) {
                System.out.println("" + tableModel.getRowCount());
                tableModel.addRow(new Object[]{ordersList1.getOrderId(), ordersList1.getPickUpCompanyName(), ordersList1.getPickUpStreetName(), ordersList1.getPickUpCity(),
                    ordersList1.getPickUpZipCode(), ordersList1.getPickUpCountry(), ordersList1.getPickupReference(), ordersList1.getDeliveryCompanyName(), ordersList1.getDeliveryStreetName(),
                    ordersList1.getDeliveryCity(), ordersList1.getDeliveryZipCode(), ordersList1.getDeliveryCountry(), ordersList1.getDeliveryReference(), ordersList1.getPickUpDate().toString(),
                    ordersList1.getPickUpTime().toString(), ordersList1.getDeliveryDate().toString(), ordersList1.getDeliveryTime().toString(), ordersList1.getDeliveryConditions(), ordersList1.isBlock()});
            }

        } else {
            JOptionPane.showMessageDialog(null, "There are no orders to show \nChoose a valid option! ", "Attention!", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_jButtonSearchActionPerformed

    private void jPanel7PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jPanel7PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel7PropertyChange

    private void jButtonAddOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddOrderActionPerformed

        OrderEntryForm oe = new OrderEntryForm();
        oe.pack();
        oe.setVisible(true);

    }//GEN-LAST:event_jButtonAddOrderActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        jTextField1.setText("");
        jTextField2.setText("");
        jTextField4.setText("");
        jTextField3.setText("");

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.out.println("value \"" + jTextField1.getText() + "\"");
        try {
            OrderDetails od = new OrderDetails();

            od.setPackagingType(jTextField2.getText().trim());
            od.setDescription(jTextField4.getText().trim());
            od.setWeight(Double.parseDouble(jTextField3.getText().trim()));
            od.setOrderID(orderId);

            MySqlOrderDetails detailService = new MySqlOrderDetails();

            if (!jTextField1.getText().equals("")) {
                od.setId(Integer.parseInt(jTextField1.getText().trim()));
                detailService.update(od);

                jTable2.setValueAt(od.getId(), selectedLineRow, 0);
                jTable2.setValueAt(od.getPackagingType(), selectedLineRow, 1);
                jTable2.setValueAt(od.getDescription(), selectedLineRow, 2);
                jTable2.setValueAt(od.getWeight(), selectedLineRow, 3);
            } else {
                detailService.create(od);
                DefaultTableModel orderDetails = (DefaultTableModel) jTable2.getModel();
                orderDetails.addRow(new Object[]{od.getId(), od.getPackagingType(), od.getDescription(), od.getWeight()});
            }

        } catch (NumberFormatException e) {

            LOG.log(Level.SEVERE, e.toString());
            JOptionPane.showMessageDialog(null, "Number format exception \nProvide a valid number value! ", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        
        State state = new State();
        state.setOrderId(orderId);
        state.setOk("ok");
        state.setAction("order line updated");
        MysqlStateDao stateService = new MysqlStateDao();
        stateService.add(state);


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        MySqlOrderDetails detailService = new MySqlOrderDetails();
        if (!jTextField1.getText().equals("")) {
            try{
                System.out.println("order detail id "+jTextField1.getText());
            detailService.deleteByODetails(Integer.parseInt(jTextField1.getText()));
            State state = new State();
        state.setOrderId(orderId);
        state.setOk("ok");
        state.setAction("order line deleted");
        MysqlStateDao stateService = new MysqlStateDao();
        stateService.add(state);
            
        }catch(NumberFormatException e){
        LOG.log(Level.SEVERE," Delete order line is not performed" ,e);
        }
            
            }

        DefaultTableModel orderDetails = (DefaultTableModel) jTable2.getModel();
        orderDetails.removeRow(selectedLineRow);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void addStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addStateActionPerformed

        State state = new State();
        state.setOrderId(orderId);
        state.setAction((String) jComboBoxAction.getSelectedItem());
        state.setOk((String) jComboBoxOk.getSelectedItem());
        Date date = new Date();

        state.setTimeStamp(date.toString());
        MysqlStateDao stateService = new MysqlStateDao();
        stateService.add(state);
        DefaultTableModel stateTable = (DefaultTableModel) jTable3.getModel();
        stateTable.addRow(new Object[]{state.getOrderId(), state.getAction(), state.getOk(), state.getTimeStamp()});

    }//GEN-LAST:event_addStateActionPerformed

    private void jButtonBlockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBlockActionPerformed

        orderDao.block(orderId);
        System.out.println("Selected line " + selectedRow);
        jTable1.setValueAt(true, selectedRow, 18);
        State state = new State();
        state.setOrderId(orderId);
        state.setOk("ok");
        state.setAction("Order blocked");
        MysqlStateDao stateService = new MysqlStateDao();
        stateService.add(state);
        
    }//GEN-LAST:event_jButtonBlockActionPerformed

    private void jButtonUnblockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUnblockActionPerformed

orderDao.unBlock(orderId);
        System.out.println("Selected line " + selectedRow);
        jTable1.setValueAt(false, selectedRow, 18);
        State state = new State();
        state.setOrderId(orderId);
        state.setOk("ok");
        state.setAction("Order unblocked");
        MysqlStateDao stateService = new MysqlStateDao();
        stateService.add(state);
    }//GEN-LAST:event_jButtonUnblockActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OrderManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Action;
    private javax.swing.JButton addState;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonAddOrder;
    private javax.swing.JButton jButtonBlock;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonUnblock;
    private javax.swing.JComboBox jComboBoxAction;
    private javax.swing.JComboBox jComboBoxOk;
    private javax.swing.JComboBox jComboBoxReference;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelOrderNumber;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField1PCKCN;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextFieldDELCMN;
    private javax.swing.JTextField jTextFieldDELCOU;
    private javax.swing.JTextField jTextFieldDELCTY;
    private javax.swing.JTextField jTextFieldDELR;
    private javax.swing.JTextField jTextFieldDELREF;
    private javax.swing.JTextField jTextFieldDELSTR;
    private javax.swing.JTextField jTextFieldDELZIP;
    private javax.swing.JTextField jTextFieldINVCMN;
    private javax.swing.JTextField jTextFieldINVCOU;
    private javax.swing.JTextField jTextFieldINVCTY;
    private javax.swing.JTextField jTextFieldINVR;
    private javax.swing.JTextField jTextFieldINVREF;
    private javax.swing.JTextField jTextFieldINVSTR;
    private javax.swing.JTextField jTextFieldINVZIP;
    private javax.swing.JTextField jTextFieldOrderNumber;
    private javax.swing.JTextField jTextFieldPCKCOU;
    private javax.swing.JTextField jTextFieldPCKCTY;
    private javax.swing.JTextField jTextFieldPCKR;
    private javax.swing.JTextField jTextFieldPCKREF;
    private javax.swing.JTextField jTextFieldPCKSTR;
    private javax.swing.JTextField jTextFieldPCKZIP;
    private javax.swing.JTextField jTextFieldReference;
    // End of variables declaration//GEN-END:variables
  private static final Logger LOG = Logger.getLogger(OrderManager.class.getName());
}
