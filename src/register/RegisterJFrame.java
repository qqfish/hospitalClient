/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package register;

import entity.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fish
 */
public class RegisterJFrame extends javax.swing.JFrame {

    private static final int SERVER_PORT = 10000;
    private boolean connected;
    private Socket requestSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Vector tableElement;
    private Vector tableHead;

    private class UpdateDepartment extends Thread {

	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public UpdateDepartment() throws UnknownHostException, IOException {
	    requestSocket = new Socket("localhost", SERVER_PORT);
	    System.out.println("Connected to localhost in port 2004");
	    out = new ObjectOutputStream(requestSocket.getOutputStream());
	    out.flush();
	    in = new ObjectInputStream(requestSocket.getInputStream());
	    out.writeObject("registerDepartment");
	    out.flush();
	    start();
	}

	public void run() {
	    while (connected) {
		try {
		    EntityTable e = (EntityTable) in.readObject();
		    if (e.getType().equals("EntityList")) {
			EntityList el = (EntityList) e;
			departmentBox.removeAllItems();
			for (int i = 0; i < el.getList().size(); i++) {
			    departmentBox.addItem(el.getList().get(i));
			}
			departmentBox.updateUI();

		    }
		} catch (IOException ex) {
		    Logger.getLogger(RegisterJFrame.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
		    Logger.getLogger(RegisterJFrame.class.getName()).log(Level.SEVERE, null, ex);
		}

	    }
	}
    }

    private class UpdateListener extends Thread {

	private Socket requestSocket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public UpdateListener() throws UnknownHostException, IOException {
	    requestSocket = new Socket("localhost", SERVER_PORT);
	    System.out.println("Connected to localhost in port 2004");
	    out = new ObjectOutputStream(requestSocket.getOutputStream());
	    out.flush();
	    in = new ObjectInputStream(requestSocket.getInputStream());
	    out.writeObject("registerUpdate");
	    out.flush();
	    start();
	}

	@Override
	public void run() {
	    while (connected) {
		try {
		    EntityTable e = (EntityTable) in.readObject();
		    //System.out.println(e.getType());
		    if (e.getType().equals("update")) {
			//System.out.println("updateBegin");
			tableElement.clear();
			while (true) {
			    EntityTable e1 = (EntityTable) in.readObject();
			    //System.out.println("11");
			    if (e1.getType().equals("end")) {
				break;
			    } else if (e1.getType().equals("HistoryInfo")) {
				HistoryInfo hi = (HistoryInfo) e1;
				Vector tmp = new Vector();
				tmp.add(hi.getHistoryId());
				tmp.add(hi.getPatientId());
				tmp.add(hi.getDoctorId());
				tmp.add(hi.getPrice());
				tableElement.add(tmp);
			    }
			}
			System.out.println("end");
			mainTable.updateUI();
		    }
		} catch (IOException ex) {
		    Logger.getLogger(RegisterJFrame.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
		    Logger.getLogger(RegisterJFrame.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	    EntityTable bye = new EntityTable();
	    bye.setType("bye");
	    try {
		out.writeObject(bye);
		out.flush();
	    } catch (IOException ex) {
		Logger.getLogger(RegisterJFrame.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    /**
     * Creates new form RegisterJFrame
     */
    public RegisterJFrame() throws UnknownHostException, IOException, ClassNotFoundException {
	connected = true;
	tableElement = new Vector();
	tableHead = new Vector();
	tableHead.add("病单号");
	tableHead.add("病人ID");
	tableHead.add("医生ID");
	tableHead.add("总价");

	initComponents();
	requestSocket = new Socket("localhost", SERVER_PORT);
	System.out.println("Connected to localhost in port 2004");
	out = new ObjectOutputStream(requestSocket.getOutputStream());
	out.flush();
	in = new ObjectInputStream(requestSocket.getInputStream());
	out.writeObject("register");
	out.flush();
	new UpdateListener();
	new UpdateDepartment();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        regWindow = new javax.swing.JDialog();
        regPatientName = new javax.swing.JTextField();
        regPatientAge = new javax.swing.JTextField();
        regPatientId = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        regConfirm = new javax.swing.JButton();
        regCancel = new javax.swing.JButton();
        maleButton = new javax.swing.JRadioButton();
        femaleButton = new javax.swing.JRadioButton();
        completeWindow = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        OKB = new javax.swing.JButton();
        bg = new javax.swing.ButtonGroup();
        ErrorInfo = new javax.swing.JDialog();
        jLabel6 = new javax.swing.JLabel();
        OKB2 = new javax.swing.JButton();
        confirmButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        patientIdField = new javax.swing.JTextField();
        registerButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainTable = new javax.swing.JTable();
        departmentBox = new javax.swing.JComboBox();

        regWindow.setTitle("注册新病历");
        regWindow.setBounds(new java.awt.Rectangle(100, 100, 400, 300));
        regWindow.setModal(true);
        regWindow.setResizable(false);
        regWindow.setUndecorated(true);

        regPatientAge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regPatientAgeActionPerformed(evt);
            }
        });

        regPatientId.setEditable(false);
        regPatientId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regPatientIdActionPerformed(evt);
            }
        });

        jLabel1.setText("用户ID");

        jLabel2.setText("用户名");

        jLabel3.setText("性别");

        jLabel4.setText("年龄");

        regConfirm.setText("登记");
        regConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regConfirmActionPerformed(evt);
            }
        });

        regCancel.setText("取消");
        regCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regCancelActionPerformed(evt);
            }
        });

        maleButton.setText("男");
        maleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maleButtonActionPerformed(evt);
            }
        });

        femaleButton.setText("女");
        femaleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                femaleButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout regWindowLayout = new javax.swing.GroupLayout(regWindow.getContentPane());
        regWindow.getContentPane().setLayout(regWindowLayout);
        regWindowLayout.setHorizontalGroup(
            regWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(regWindowLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(regWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(regWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(regWindowLayout.createSequentialGroup()
                        .addComponent(regConfirm)
                        .addGap(18, 18, 18)
                        .addComponent(regCancel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, regWindowLayout.createSequentialGroup()
                        .addComponent(maleButton)
                        .addGap(18, 18, 18)
                        .addComponent(femaleButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(regPatientName, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(regPatientAge, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(regPatientId, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(50, 50, 50))
        );
        regWindowLayout.setVerticalGroup(
            regWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(regWindowLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(regWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regPatientId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(regWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regPatientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(regWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(maleButton)
                    .addComponent(femaleButton))
                .addGap(18, 18, 18)
                .addGroup(regWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regPatientAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                .addGroup(regWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(regConfirm)
                    .addComponent(regCancel))
                .addGap(41, 41, 41))
        );

        completeWindow.setBounds(new java.awt.Rectangle(100, 100, 394, 193));
        completeWindow.setResizable(false);

        jLabel5.setText("成功完成");

        OKB.setText("OK");
        OKB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout completeWindowLayout = new javax.swing.GroupLayout(completeWindow.getContentPane());
        completeWindow.getContentPane().setLayout(completeWindowLayout);
        completeWindowLayout.setHorizontalGroup(
            completeWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(completeWindowLayout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addGroup(completeWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(OKB)
                    .addComponent(jLabel5))
                .addContainerGap(144, Short.MAX_VALUE))
        );
        completeWindowLayout.setVerticalGroup(
            completeWindowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(completeWindowLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(OKB)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        bg.add(maleButton);
        bg.add(femaleButton);

        ErrorInfo.setBounds(new java.awt.Rectangle(100, 100, 322, 180));
        ErrorInfo.setModal(true);
        ErrorInfo.setUndecorated(true);

        jLabel6.setText("请输入正确的信息");

        OKB2.setText("OK");
        OKB2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKB2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ErrorInfoLayout = new javax.swing.GroupLayout(ErrorInfo.getContentPane());
        ErrorInfo.getContentPane().setLayout(ErrorInfoLayout);
        ErrorInfoLayout.setHorizontalGroup(
            ErrorInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ErrorInfoLayout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addGroup(ErrorInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(ErrorInfoLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(OKB2)))
                .addContainerGap(114, Short.MAX_VALUE))
        );
        ErrorInfoLayout.setVerticalGroup(
            ErrorInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ErrorInfoLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(OKB2)
                .addContainerGap(70, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("挂号");

        confirmButton.setText("确认收款");
        confirmButton.setActionCommand("confirm");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("删除");
        deleteButton.setActionCommand("delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        patientIdField.setToolTipText("");
        patientIdField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patientIdFieldActionPerformed(evt);
            }
        });

        registerButton.setText("挂号");
        registerButton.setActionCommand("register");
        registerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        mainTable.setModel(new javax.swing.table.DefaultTableModel(
            tableElement,
            tableHead
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
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
        mainTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(mainTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(deleteButton)
                        .addGap(18, 18, 18)
                        .addComponent(confirmButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(patientIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(departmentBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(patientIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(registerButton)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(departmentBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmButton)
                    .addComponent(deleteButton))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmButtonActionPerformed
	int historyId = (int) mainTable.getValueAt(mainTable.getSelectedRow(), 0);
	ConfirmCash cc = new ConfirmCash(historyId);
	//System.out.println(historyId);
	try {
	    out.writeObject(cc);
	    out.flush();
	    completeWindow.setVisible(true);
	} catch (IOException ex) {
	    Logger.getLogger(RegisterJFrame.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_confirmButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
	int historyId = (int) mainTable.getValueAt(mainTable.getSelectedRow(), 0);
	DeleteHistory dh = new DeleteHistory(historyId);
	//System.out.println(historyId);
	try {
	    out.writeObject(dh);
	    out.flush();
	    completeWindow.setVisible(true);
	} catch (IOException ex) {
	    Logger.getLogger(RegisterJFrame.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void registerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerButtonActionPerformed
	String patientId = patientIdField.getText();
	String department = (String) departmentBox.getSelectedItem();
	System.out.println(department);
	if (department != null && patientId != null) {
	    SearchPatient sp = new SearchPatient(patientId, department);
	    try {
		out.writeObject(sp);
		out.flush();
		boolean hasId = in.readBoolean();
		if (hasId) {
		    completeWindow.setVisible(true);
		} else {
		    regPatientId.setText(patientId);
		    regWindow.setVisible(true);
		}
	    } catch (IOException ex) {
		Logger.getLogger(RegisterJFrame.class.getName()).log(Level.SEVERE, null, ex);
	    }
	} else {
	    ErrorInfo.setVisible(true);
	}
    }//GEN-LAST:event_registerButtonActionPerformed

    private void patientIdFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patientIdFieldActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_patientIdFieldActionPerformed

    private void maleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maleButtonActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_maleButtonActionPerformed

    private void femaleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_femaleButtonActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_femaleButtonActionPerformed

    private void regConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regConfirmActionPerformed
	String patientId = regPatientId.getText();
	String patientName = regPatientName.getText();
	String patientAge = regPatientAge.getText();
	System.out.println(patientAge == null);
	boolean gender = false;
	if (maleButton.isSelected()) {
	    gender = false;
	} else if (femaleButton.isSelected()) {
	    gender = true;
	}
	if (patientId != "" && patientName != "" && patientAge != "") {
	    try {
		short age = 0;
		age = Short.parseShort(patientAge);
		NewPatient np = new NewPatient(patientName, age, gender);

		out.writeObject(np);
		out.flush();
		regWindow.setVisible(false);
		completeWindow.setVisible(true);
	    } catch (IOException ex) {
		Logger.getLogger(RegisterJFrame.class.getName()).log(Level.SEVERE, null, ex);
	    } catch (NumberFormatException e) {
		ErrorInfo.setVisible(true);
	    }
	} else {
	    ErrorInfo.setVisible(true);
	}
    }//GEN-LAST:event_regConfirmActionPerformed

    private void regPatientAgeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regPatientAgeActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_regPatientAgeActionPerformed

    private void OKBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKBActionPerformed
	completeWindow.setVisible(false);
    }//GEN-LAST:event_OKBActionPerformed

    private void regPatientIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regPatientIdActionPerformed
	// TODO add your handling code here:
    }//GEN-LAST:event_regPatientIdActionPerformed

    private void OKB2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKB2ActionPerformed
	ErrorInfo.setVisible(false);
    }//GEN-LAST:event_OKB2ActionPerformed

    private void regCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regCancelActionPerformed
	EntityTable e = new EntityTable();
	e.setType("cancel");
	regWindow.setVisible(false);
	try {
	    out.writeObject(e);
	    out.flush();
	} catch (IOException ex) {
	    Logger.getLogger(RegisterJFrame.class.getName()).log(Level.SEVERE, null, ex);
	}
    }//GEN-LAST:event_regCancelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
	/*
	 * Set the Nimbus look and feel
	 */
	//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
	 * If Nimbus (introduced in Java SE 6) is not available, stay with the
	 * default look and feel. For details see
	 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
	 */
	try {
	    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		if ("Nimbus".equals(info.getName())) {
		    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		    break;
		}
	    }
	} catch (ClassNotFoundException ex) {
	    java.util.logging.Logger.getLogger(RegisterJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (InstantiationException ex) {
	    java.util.logging.Logger.getLogger(RegisterJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (IllegalAccessException ex) {
	    java.util.logging.Logger.getLogger(RegisterJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	} catch (javax.swing.UnsupportedLookAndFeelException ex) {
	    java.util.logging.Logger.getLogger(RegisterJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
	}
	//</editor-fold>

	/*
	 * Create and display the form
	 */
	java.awt.EventQueue.invokeLater(new Runnable() {

	    public void run() {
		try {
		    new RegisterJFrame().setVisible(true);
		} catch (UnknownHostException ex) {
		    Logger.getLogger(RegisterJFrame.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
		    Logger.getLogger(RegisterJFrame.class.getName()).log(Level.SEVERE, null, ex);
		} catch (ClassNotFoundException ex) {
		    Logger.getLogger(RegisterJFrame.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	});
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog ErrorInfo;
    private javax.swing.JButton OKB;
    private javax.swing.JButton OKB2;
    private javax.swing.ButtonGroup bg;
    private javax.swing.JDialog completeWindow;
    private javax.swing.JButton confirmButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JComboBox departmentBox;
    private javax.swing.JRadioButton femaleButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable mainTable;
    private javax.swing.JRadioButton maleButton;
    private javax.swing.JTextField patientIdField;
    private javax.swing.JButton regCancel;
    private javax.swing.JButton regConfirm;
    private javax.swing.JTextField regPatientAge;
    private javax.swing.JTextField regPatientId;
    private javax.swing.JTextField regPatientName;
    private javax.swing.JDialog regWindow;
    private javax.swing.JButton registerButton;
    // End of variables declaration//GEN-END:variables
}
