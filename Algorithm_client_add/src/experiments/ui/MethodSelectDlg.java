
/**
 * 方法选择对话框
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Set;
import java.util.Vector;

import org.dom4j.DocumentException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import simulate.data.BayesParamsInfo;
import simulate.data.ESParamsInfo;
import simulate.data.GAParamsInfo;
import simulate.data.MethodInfo;
import simulate.data.PSOParamsInfo;

import simulate.data.TaskInfo;

public class MethodSelectDlg extends Dialog {

	private Combo preexp;
	private Combo preopt;
	private Table table_1;
	private Text text_3;
	private Text text_num;
	private Text text;
	private Combo combo;
	protected Object result = 0;
	protected Shell shell;
	private String path;
	private String taskName; // 当前任务的名称
	private TaskInfo task; // 任务对象
	private boolean flag;// 任务是否已经提交的标志量
	public Vector<MethodInfo> sMethod; // 方法列表
	private MethodInfo[] selectedMethod; // 选中的方法
	private TableItem[] tableItem;
	private TableItem oldItem;
	protected Group group_3;
	protected Group group;
	protected Group group_1;
	protected Vector<TaskInfo> tasksinfo = new Vector<TaskInfo>();
	protected Button useold;
	protected Button usenew;
	protected Button settingsButton;
	protected int curCol = 0;
	protected TableEditor editor;
	protected Label label_num;
	protected Group group_2;
	private boolean isPrePt = false;

	public boolean isPrePt() {
		return isPrePt;
	}

	/**
	 * @param task
	 *            任务对象
	 */
	public void setTask(TaskInfo task) {
		this.task = task;
	}

	/**
	 * @return 任务对象
	 */
	public TaskInfo getTask() {
		return task;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskName() {
		return taskName;
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 * @param style
	 */
	public MethodSelectDlg(Shell parent, int style, String path) {
		super(parent, style);
		this.path = path;
		task = new TaskInfo();
		sMethod = new Vector<MethodInfo>();
		flag = false;
		taskName = ""; //$NON-NLS-1$
	}

	/**
	 * Create the dialog
	 * 
	 * @param parent
	 */
	public MethodSelectDlg(Shell parent, String path) {
		this(parent, SWT.NONE, path);
		this.path = path;
		task = new TaskInfo();
		sMethod = new Vector<MethodInfo>();
		flag = false;
		taskName = ""; //$NON-NLS-1$
	}

	/**
	 * Open the dialog
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	/**
	 * Create contents of the dialog
	 */
	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(527, 520);
		// MethodSelectDlg.0=方法选择
		shell.setText(Messages.getString("MethodSelectDlg.0")); //$NON-NLS-1$

		group = new Group(shell, SWT.NONE);
		// MethodSelectDlg.1=方法选择
		group.setText(Messages.getString("MethodSelectDlg.1")); //$NON-NLS-1$
		group.setBounds(10, 98, 501, 126);

		final Label label = new Label(group, SWT.NONE);
		// MethodSelectDlg.2=实验生成器
		label.setText(Messages.getString("MethodSelectDlg.2")); //$NON-NLS-1$
		label.setBounds(5, 25, 128, 15);

		group_1 = new Group(shell, SWT.NONE);
		group_1.setBounds(81, 528, 203, 220);
		// MethodSelectDlg.3=方法属性
		group_1.setText(Messages.getString("MethodSelectDlg.3")); //$NON-NLS-1$

		label_num = new Label(group_1, SWT.NONE);
		label_num.setBounds(21, 43, 73, 20);
		// MethodSelectDlg.4=次 数：
		label_num.setText(Messages.getString("MethodSelectDlg.4")); //$NON-NLS-1$
		label_num.setVisible(false);

		text_num = new Text(group_1, SWT.BORDER);
		text_num.setBounds(114, 40, 80, 20);
		text_num.setVisible(false);
		text_num.setText("20"); //$NON-NLS-1$
		table_1 = new Table(shell, SWT.FULL_SELECTION | SWT.MULTI | SWT.BORDER);

		/*
		 * 验证实验次数的输入
		 */

		text_num.addVerifyListener(new VerifyListener() {

			@Override
			public void verifyText(final VerifyEvent e) {
				// TODO Auto-generated method stub

				if (e.text.length() == 1) {
					boolean b = ("0123456789".indexOf(e.text) >= 0); //$NON-NLS-1$
					e.doit = b;
				}
			}

		});

		/*
		 * 修改实验次数时触发的事件
		 */
		if (!flag) {
			text_num.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					// TODO Auto-generated method stub
					if (text.getText() == " ")text.setText("1"); //$NON-NLS-1$ //$NON-NLS-2$
					if (Integer.parseInt(text.getText()) == 0) {
						MessageBox msg = new MessageBox(shell.getShell(),
								SWT.ICON_ERROR | SWT.OK);
						// MethodSelectDlg.9=错误
						msg.setText(Messages.getString("MethodSelectDlg.9")); //$NON-NLS-1$
						// MethodSelectDlg.10=次数不能为0！
						msg
								.setMessage(Messages
										.getString("MethodSelectDlg.10")); //$NON-NLS-1$
						msg.open();
						return;
					}
					for (int i = 0; i < table_1.getSelectionCount(); i++) {
						selectedMethod[i].setNum(Integer.parseInt(text_num
								.getText()));
					}
				}

			});
		}
		group_2 = new Group(group_1, SWT.NONE);
		// MethodSelectDlg.11=运行方式
		group_2.setText(Messages.getString("MethodSelectDlg.11")); //$NON-NLS-1$
		group_2.setBounds(10, 85, 184, 125);
		group_2.setVisible(false);

		final Button button = new Button(group_2, SWT.RADIO);
		/*
		 * 单击并行单选按钮触发的事件
		 */
		if (!flag) {
			button.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					if (button.getSelection()) {
						for (int i = 0; i < table_1.getSelectionCount(); i++) {
							// MethodSelectDlg.12=并行
							selectedMethod[i].setRunStyle(Messages
									.getString("MethodSelectDlg.12")); //$NON-NLS-1$
						}
						text_3.setEnabled(true);
					}
				}
			});
		}
		button.setBounds(10, 37, 65, 16);
		// MethodSelectDlg.13=并行
		button.setText(Messages.getString("MethodSelectDlg.13")); //$NON-NLS-1$

		final Button button_1 = new Button(group_2, SWT.RADIO);
		/*
		 * 单击串行单选按钮触发的事件
		 */
		if (!flag) {
			button_1.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					if (button_1.getSelection()) {
						for (int i = 0; i < table_1.getSelectionCount(); i++) {
							// MethodSelectDlg.14=串行
							selectedMethod[i].setRunStyle(Messages
									.getString("MethodSelectDlg.14")); //$NON-NLS-1$
						}
						text_3.setEnabled(false);
					}

				}
			});
		}
		button_1.setBounds(110, 37, 65, 16);
		// MethodSelectDlg.15=串行
		button_1.setText(Messages.getString("MethodSelectDlg.15")); //$NON-NLS-1$
		button_1.setSelection(true);

		final Label label_4 = new Label(group_2, SWT.NONE);
		label_4.setBounds(10, 82, 94, 22);
		// MethodSelectDlg.16=并行数目：
		label_4.setText(Messages.getString("MethodSelectDlg.16")); //$NON-NLS-1$

		/*
		 * 并行数目检查
		 */
		text_3 = new Text(group_2, SWT.BORDER);
		text_3.setBounds(110, 79, 58, 20);
		text_3.setText("1"); //$NON-NLS-1$
		text_3.setEnabled(false);
		/*
		 * 验证并行数目的输入
		 */
		if (!flag) {
			text_3.addVerifyListener(new VerifyListener() {

				@Override
				public void verifyText(final VerifyEvent e) {
					// TODO Auto-generated method stub

					if (e.text.length() == 1) {
						boolean b = ("0123456789".indexOf(e.text) >= 0); //$NON-NLS-1$
						e.doit = b;
					}
				}

			});
		}
		/*
		 * 修改并行数目时触发的事件
		 */
		if (!flag) {
			text_3.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					// TODO Auto-generated method stub
					if (text.getText() == "")text.setText("1"); //$NON-NLS-1$ //$NON-NLS-2$
					if (Integer.parseInt(text.getText()) == 0) {
						MessageBox msg = new MessageBox(shell.getShell(),
								SWT.ICON_ERROR | SWT.OK);
						// MethodSelectDlg.21=错误
						msg.setText(Messages.getString("MethodSelectDlg.21")); //$NON-NLS-1$
						// MethodSelectDlg.22=次数不能为0！
						msg
								.setMessage(Messages
										.getString("MethodSelectDlg.22")); //$NON-NLS-1$
						msg.open();
						return;
					}
					for (int i = 0; i < table_1.getSelectionCount(); i++) {
						selectedMethod[i].setParallelNum(Integer
								.parseInt(text_3.getText()));
					}
				}

			});
		}

		if (!flag) {
			table_1.addMouseListener(new MouseAdapter() {
				public void mouseDoubleClick(final MouseEvent e) {

					Point point = new Point(e.x, e.y);

					final TableItem item = table_1.getItem(point);// 点击对应item
					if (item != null) {
						final int curItem = table_1.indexOf(item);
						int columnsize = table_1.getColumnCount();
						editor = new TableEditor(table_1);
						// 添加文本编辑
						for (int i = 0; i < columnsize; i++) {
							curCol = i;
							if (item.getStyle() == SWT.READ_ONLY) {
								final Text texteditor5 = new Text(table_1,
										SWT.READ_ONLY);
								texteditor5.computeSize(SWT.DEFAULT, table_1
										.getItemHeight());
								editor.grabHorizontal = true;
								editor.minimumHeight = texteditor5.getSize().y;
								editor.minimumWidth = texteditor5.getSize().x;
								editor.setEditor(texteditor5, item, curCol);
								texteditor5.setText(item.getText(curCol));
								texteditor5.forceFocus();
								if (curCol == 0) {
									sMethod.get(curItem).setMethodName(
											texteditor5.getText());
								} else if (curCol == 1) {
									sMethod.get(curItem).setNum(
											Integer.parseInt(texteditor5
													.getText()));
								} else if (curCol == 2) {
									sMethod.get(curItem).setRunStyle(
											texteditor5.getText());
								} else if (curCol == 3) {
									sMethod.get(curItem).setParallelNum(
											Integer.parseInt(texteditor5
													.getText()));
								}

								texteditor5.dispose();

							} else {
								Rectangle rect = item.getBounds(i);
								if (rect.contains(point)) {

									Control oldEditor = editor.getEditor();
									if (oldEditor != null) {
										oldEditor.dispose();
										System.out.println("Release Editor"); //$NON-NLS-1$
									}
									// 方法是不允许修改的
									if (curCol == 0) {
										final Text texteditor1 = new Text(
												table_1, SWT.READ_ONLY);
										texteditor1.computeSize(SWT.DEFAULT,
												table_1.getItemHeight());
										editor.grabHorizontal = true;
										editor.minimumHeight = texteditor1
												.getSize().y;
										editor.minimumWidth = texteditor1
												.getSize().x;
										editor.setEditor(texteditor1, item,
												curCol);
										texteditor1.setText(item
												.getText(curCol));
										texteditor1.forceFocus();
										sMethod.get(curItem).setMethodName(
												texteditor1.getText());
										texteditor1.dispose();
									} else if (curCol == 2) {
										final Combo cmbeditor = new Combo(
												table_1, SWT.READ_ONLY);
										cmbeditor.computeSize(SWT.DEFAULT,
												table_1.getItemHeight());
										editor.grabHorizontal = true;
										editor.minimumHeight = cmbeditor
												.getSize().y;
										editor.minimumWidth = cmbeditor
												.getSize().x;
										editor.setEditor(cmbeditor, item,
												curCol);
										cmbeditor.setText(item.getText(curCol));
										cmbeditor.forceFocus();
										cmbeditor.add(Messages
												.getString("MethodSelectDlg.39")); //$NON-NLS-1$
										cmbeditor.add(Messages
												.getString("MethodSelectDlg.40")); //$NON-NLS-1$
										cmbeditor.select(0);
										cmbeditor
												.addModifyListener(new ModifyListener() {

													@Override
													public void modifyText(
															ModifyEvent arg0) {
														String txtString = cmbeditor
																.getText();
														cmbeditor
																.setText(item
																		.getText(curCol));
														editor
																.getItem()
																.setText(
																		curCol,
																		txtString);
														sMethod
																.get(curItem)
																.setRunStyle(
																		txtString);
														cmbeditor.dispose();
													}
												});
									} else if (curCol == 3) {

										final Text texteditor;
										texteditor = new Text(table_1, SWT.NONE);
										texteditor.computeSize(SWT.DEFAULT,
												table_1.getItemHeight());
										editor.grabHorizontal = true;
										editor.minimumHeight = texteditor
												.getSize().y;
										editor.minimumWidth = texteditor
												.getSize().x;
										editor.setEditor(texteditor, item,
												curCol);
										texteditor
												.setText(item.getText(curCol));
										texteditor.forceFocus();

										texteditor
												.addKeyListener(new KeyAdapter() {
													@Override
													public void keyPressed(
															KeyEvent e) {
														if (e.keyCode == 13) {
															Text text = (Text) editor
																	.getEditor();
															if (text == null)
																return;
															String txtString = text
																	.getText();
															if (txtString != "") { //$NON-NLS-1$
																editor
																		.getItem()
																		.setText(
																				curCol,
																				txtString);
															}

															// 设置方法值
															switch (curCol) {
															// case 1:
															// sMethod
															// .get(curItem)
															// .setNum(
															// Integer
															// .parseInt(txtString));
															// break;
															case 3:
																sMethod
																		.get(
																				curItem)
																		.setParallelNum(
																				Integer
																						.parseInt(txtString));
																break;
															}
															texteditor
																	.dispose();

														}
													}
												});

										texteditor
												.addFocusListener(new FocusAdapter() {
													public void focusLost(
															FocusEvent e) {
														Text text = (Text) editor
																.getEditor();
														if (text == null)
															return;
														String txtString = text
																.getText();
														if (text.getText() != "") { //$NON-NLS-1$
															editor
																	.getItem()
																	.setText(
																			curCol,
																			text
																					.getText());
														}

														// 设置方法值
														System.out
																.print(curCol);
														switch (curCol) {
														// case 1:
														// sMethod
														// .get(curItem)
														// .setNum(
														// Integer
														// .parseInt(txtString));
														// break;
														case 3:
															sMethod
																	.get(
																			curItem)
																	.setParallelNum(
																			Integer
																					.parseInt(txtString));
															break;
														}

														texteditor.dispose();
													}
												});

										texteditor
												.addVerifyListener(new VerifyListener() {

													@Override
													public void verifyText(
															final VerifyEvent e) {
														// TODO Auto-generated
														// method
														// stub

														if (e.text.length() == 1) {
															boolean b = ("0123456789".indexOf(e.text) >= 0); //$NON-NLS-1$
															e.doit = b;
														}
													}
												});
									} else {

										final Text texteditor;

										if (item.getText().equals(
												combo.getItem(3))
												|| item.getText().equals(
														combo.getItem(4))
												|| item.getText().equals(
														combo.getItem(5))
												|| item.getText().equals(
														combo.getItem(2))) {
											texteditor = new Text(table_1,
													SWT.READ_ONLY);
										} else {
											texteditor = new Text(table_1,
													SWT.NONE);
										}
										texteditor.computeSize(SWT.DEFAULT,
												table_1.getItemHeight());
										editor.grabHorizontal = true;
										editor.minimumHeight = texteditor
												.getSize().y;
										editor.minimumWidth = texteditor
												.getSize().x;
										editor.setEditor(texteditor, item,
												curCol);
										texteditor
												.setText(item.getText(curCol));
										texteditor.forceFocus();

										texteditor
												.addKeyListener(new KeyAdapter() {
													@Override
													public void keyPressed(
															KeyEvent e) {
														if (e.keyCode == 13) {
															Text text = (Text) editor
																	.getEditor();
															if (text == null)
																return;
															String txtString = text
																	.getText();
															if (txtString != "") { //$NON-NLS-1$
																editor
																		.getItem()
																		.setText(
																				curCol,
																				txtString);
															}

															// 设置方法值
															switch (curCol) {
															case 1:
																sMethod
																		.get(
																				curItem)
																		.setNum(
																				Integer
																						.parseInt(txtString));
																break;
															// case 3:
															// sMethod
															// .get(curItem)
															// .setParallelNum(
															// Integer
															// .parseInt(txtString));
															// break;
															}
															texteditor
																	.dispose();

														}
													}
												});

										texteditor
												.addFocusListener(new FocusAdapter() {
													public void focusLost(
															FocusEvent e) {
														Text text = (Text) editor
																.getEditor();
														if (text == null)
															return;
														String txtString = text
																.getText();
														if (text.getText() != "") { //$NON-NLS-1$
															editor
																	.getItem()
																	.setText(
																			curCol,
																			text
																					.getText());
														}

														// 设置方法值
														System.out
																.print(curCol);
														switch (curCol) {
														case 1:
															sMethod
																	.get(
																			curItem)
																	.setNum(
																			Integer
																					.parseInt(txtString));
															break;
														// case 3:
														// sMethod
														// .get(curItem)
														// .setParallelNum(
														// Integer
														// .parseInt(txtString));
														// break;
														}

														texteditor.dispose();
													}
												});

										texteditor
												.addVerifyListener(new VerifyListener() {

													@Override
													public void verifyText(
															final VerifyEvent e) {
														// TODO Auto-generated
														// method
														// stub

														if (e.text.length() == 1) {
															boolean b = ("0123456789".indexOf(e.text) >= 0); //$NON-NLS-1$
															e.doit = b;
														}
													}
												});
									}
									break;
								}

							}
						}
					}
				}
			});
		} else {
			table_1.addMouseListener(new MouseAdapter() {
				public void mouseDoubleClick(final MouseEvent e) {

					Point point = new Point(e.x, e.y);

					final TableItem item = table_1.getItem(point);// 点击对应item
					if (item != null) {
//						final int curItem = table_1.indexOf(item);
//						int columnsize = table_1.getColumnCount();
						MethodInfo ps = (MethodInfo) item.getData();
						//Object pps = ps.getParamStruct();
						if (ps.getMethodName().equals(Messages.getString("MethodSelectDlg.29"))) { //$NON-NLS-1$
							BayesSettingDialog bs = new BayesSettingDialog(shell);
							BayesParamsInfo bayes = (BayesParamsInfo) ps.getParamStruct();
							bs.setBestNum(bayes.getIntBest());
							bs.setMostNum(bayes.getIntMost());
							bs.setFlag(true);
							System.out.println(ps.getParamStruct().toString());
							bs.open();
						}
						else if(ps.getMethodName().equals(Messages.getString("MethodSelectDlg.30"))) //$NON-NLS-1$
						{
							System.out.println("进入遗传算法"); //$NON-NLS-1$
							GASettingsDialog ga = new GASettingsDialog(shell);
						//	System.out.println(pps.toString());
							System.out.println(ps.getParamStruct().toString());
							
							GAParamsInfo gas = (GAParamsInfo) ps.getParamStruct();
							System.out.println("继续向前"); //$NON-NLS-1$
						    ga.setKInt(gas.getK());
							System.out.println("K :" + gas.getK()); //$NON-NLS-1$
							ga.setNgInt(gas.getNG());
							ga.setPc1Int(gas.getPc1());
							ga.setPc2Int(gas.getPc2());
							ga.setPmInt(gas.getPm());
							ga.setPtInt(gas.getPt());
						    ga.setFlag(true);
							System.out.println(ps.getMethodName().toString());
							ga.open();
						}
						else if(ps.getMethodName().equals(Messages.getString("MethodSelectDlg.apga"))) //$NON-NLS-1$
						{
							System.out.println("进入聚类遗传算法"); //$NON-NLS-1$
							APGASettingsDialog apga = new APGASettingsDialog(shell);
						//	System.out.println(pps.toString());
							System.out.println(ps.getParamStruct().toString());
							
							APGAParamsInfo apgas = (APGAParamsInfo) ps.getParamStruct();
							System.out.println("继续向前"); //$NON-NLS-1$
							apga.setKInt(apgas.getK());
							System.out.println("K :" + apgas.getK()); //$NON-NLS-1$
							apga.setNgInt(apgas.getNG());
							apga.setPc1Int(apgas.getPc1());
							apga.setPc2Int(apgas.getPc2());
							apga.setPmInt(apgas.getPm());
							apga.setPtInt(apgas.getPt());
							apga.setFlag(true);
							System.out.println(ps.getMethodName().toString());
							apga.open();
						}
						else if(ps.getMethodName().equals(Messages.getString("MethodSelectDlg.31"))) //$NON-NLS-1$
						{
							System.out.println("进入进化算法"); //$NON-NLS-1$
							PSOSettingsDialog pso = new PSOSettingsDialog(shell);
							PSOParamsInfo psopi = (PSOParamsInfo) ps.getParamStruct();
							pso.setC1Int(psopi.getC1());
							pso.setC2Int(psopi.getC2());
							pso.setLamdaInt(psopi.getLamda());
							pso.setNInt(psopi.getN());
							pso.setWInt(psopi.getW());
							pso.setFlag(true);
							pso.open();
						}
						else if(ps.getMethodName().equals(Messages.getString("MethodSelectDlg.es"))) //$NON-NLS-1$
						{
							ESSettingDialog es = new ESSettingDialog(shell);
							ESParamsInfo esp = (ESParamsInfo) ps.getParamStruct();
							es.setIntNG(esp.getNgInt());
							es.setIntOneFifth_gen(esp.getOneFifth_gen());
							es.setDoubleChSigma(esp.getChSigma());
							es.setFlag(true);
							es.open();
							
						}
						
						System.out.println("ssssssssss"); //$NON-NLS-1$
					}
				}
			});
		}
		table_1.setBounds(10, 230, 501, 220);
		table_1.setLinesVisible(true);
		table_1.setHeaderVisible(true);
		String[] colName = new String[] { Messages.getString("MethodSelectDlg.42"), Messages.getString("MethodSelectDlg.43"), Messages.getString("MethodSelectDlg.44"), Messages.getString("MethodSelectDlg.55") };  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		for (String name : colName) {
			TableColumn newColumnTableColumn = new TableColumn(table_1,
					SWT.NONE);
			newColumnTableColumn.setWidth(92);
			newColumnTableColumn.setText(name);
		}
		if (flag) {
            table_1.removeAll();
			// // 提取选择任务的试验算法
			// int index = 1;
			// Vector<MethodInfo> mis = GetTaskExp(index);
			//
			// if (mis.isEmpty())
			// return;
			//
			// // 复制信息并添加到列表
			// Iterator<MethodInfo> it_mi = mis.iterator();
			// while (it_mi.hasNext()) {
			// MethodInfo mi = it_mi.next();
			//
			// TableItem item = new TableItem(table_1, SWT.READ_ONLY);
			// int nItem = table_1.indexOf(item);
			// item.setText(new String[] { mi.getMethodName(),
			// Integer.toString(mi.getNum()),
			// mi.getRunStyle(),
			// Integer.toString(mi.getParallelNum()) });
			// }
			// Vector<MethodInfo> miss = GetTaskOpt(index);
			//
			// if (miss.isEmpty())
			// return;
			//
			// // 复制信息并添加到列表
			// Iterator<MethodInfo> it_mis = miss.iterator();
			// while (it_mis.hasNext()) {
			// MethodInfo mi3 = it_mis.next();
			//
			// TableItem items = new TableItem(table_1, SWT.READ_ONLY);
			// items.setText(new String[] { mi3.getMethodName(),
			// Integer.toString(mi3.getNum()),
			// mi3.getRunStyle(),
			// Integer.toString(mi3.getParallelNum()) });
			// }
			Vector<MethodInfo> miss = GetTaskAll(taskName);

			if (miss.isEmpty())
				return;

			// 复制信息并添加到列表
			Iterator<MethodInfo> it_mis = miss.iterator();
			while (it_mis.hasNext()) {
				MethodInfo mi3 = it_mis.next();

				TableItem items = new TableItem(table_1, SWT.READ_ONLY);
				items.setText(new String[] { mi3.getMethodName(),
						Integer.toString(mi3.getNum()), mi3.getRunStyle(),
						Integer.toString(mi3.getParallelNum()) });
				
				items.setData(mi3);
			}

			System.out.println("ssssssssssss"); //$NON-NLS-1$
		}

		/*
		 * 读取以前设置了的信息
		 */
		sMethod = task.getSMethod();
		for (int i = 0; i < sMethod.size(); i++) {
			MethodInfo m = sMethod.get(i);
			TableItem item = new TableItem(table_1, SWT.NONE);
			item.setText(m.getMethodName());
		}

		final Menu menu = new Menu(table_1);
		MenuItem item = new MenuItem(menu, SWT.PUSH);
		// MethodSelectDlg.23=删除
		item.setText(Messages.getString("MethodSelectDlg.23")); //$NON-NLS-1$
		/*
		 * 删除菜单
		 */
		if (!flag)
			item.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					int[] index = table_1.getSelectionIndices();
					// 删除表格中的内容
					table_1.remove(index);
					for (int i = 0; i < index.length; i++) {
						System.out.println(index[i]);
						if ((index[i] - i) >= 0) {
							index[i] = index[i] - i;
						}
						// 删除方法列表中的内容
						if((sMethod.get(index[i]).equals(Messages.getString("MethodSelectDlg.27")) && isPrePt) ||
								(sMethod.get(index[i]).equals(Messages.getString("Box-Behnken")) && isPrePt)){
							isPrePt = false;
						}
						sMethod.remove(index[i]);
					}
				}
			});

		/*
		 * final TableColumn newColumnTableColumn = new TableColumn(table_1,
		 * SWT.CENTER); newColumnTableColumn.setWidth(256);
		 * //MethodSelectDlg.24=方法列表
		 * newColumnTableColumn.setText(Messages.getString
		 * ("MethodSelectDlg.24")); //$NON-NLS-1$
		 */

		/*
		 * 方法列表操作
		 */
		if (!flag)
			table_1.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent arg0) {
					// TODO Auto-generated method stub
					System.out.println(table_1.getSelectionCount());
					System.out.println(table_1.getItemCount());
					table_1.setMenu(menu);
					// 选中零行
					if (table_1.getSelectionCount() == 0) {
						// tableItem[0] = oldItem;
					}
					// 选中一行
					if (table_1.getSelectionCount() == 1) {
						int index = table_1.getSelectionIndex();
						tableItem[0] = table_1.getItem(index);
						selectedMethod[0] = sMethod.get(index);
						oldItem = tableItem[0];
						if (tableItem[0].getText().equals(combo.getItem(0))) {

							if (table_1.getSelectionCount() == 1) {
								label_num.setVisible(true);
								text_num.setVisible(true);
								group_2.setVisible(true);
							} else {
								label_num.setVisible(true);
								text_num.setVisible(true);
								text_num.setFocus();
								group_2.setVisible(false);
							}
							// 修改控件显示为当前选中方法设置
							// MethodSelectDlg.25=串行
							if (selectedMethod[0].getRunStyle().equals(
									Messages.getString("MethodSelectDlg.25"))) { //$NON-NLS-1$
								button_1.setSelection(true);
								button.setSelection(false);
								// text_3.setText("1");
								text_3.setEnabled(false);
							} else {
								button.setSelection(true);
								button_1.setSelection(false);
								text_3.setEnabled(true);
								text_3.setText(String.valueOf(selectedMethod[0]
										.getParallelNum()));
							}

						} else if (tableItem[0].getText().equals(
								combo.getItem(1))) {
							label_num.setVisible(false);
							text_num.setVisible(false);
							group_2.setVisible(false);
						} else {

							label_num.setVisible(false);
							text_num.setVisible(false);
							group_2.setVisible(true);
							if (button.getSelection())
								text_3.setEnabled(true);
							else
								text_3.setEnabled(false);
							// 修改控件显示为当前选中方法设置
							// MethodSelectDlg.26=串行
							if (selectedMethod[0].getRunStyle().equals(
									Messages.getString("MethodSelectDlg.26"))) { //$NON-NLS-1$
								button_1.setSelection(true);
								button.setSelection(false);
								text_3.setEnabled(false);
							} else {
								button.setSelection(true);
								button_1.setSelection(false);
								text_3.setEnabled(true);
								text_3.setText(String.valueOf(selectedMethod[0]
										.getParallelNum()));
							}
						}
					}
					// 选中多行
					else if (table_1.getSelectionCount() > 1) {
						int index[] = table_1.getSelectionIndices();
						for (int i = 0; i < index.length; i++) {
							tableItem[i] = table_1.getItem(index[i]);
							selectedMethod[i] = sMethod.get(index[i]);
						}
						label_num.setVisible(false);
						text_num.setVisible(false);
						group_2.setVisible(false);
					}
				}

			});

		combo = new Combo(group, SWT.READ_ONLY);
		// MethodSelectDlg.27=拉丁超立方体 MethodSelectDlg.29=贝叶斯更新
		// MethodSelectDlg.30=遗传算法
		// MethodSelectDlg.31=进化算法
		/**
		 * 在此处放置要设置的算法名称，要与服务器端判断算法的那个名称一致
		 */
		combo
				.setItems(new String[] {
						Messages.getString("MethodSelectDlg.27"), "Box-Behnken", Messages.getString("MethodSelectDlg.29"), Messages.getString("MethodSelectDlg.30"), Messages.getString("MethodSelectDlg.31"), Messages.getString("MethodSelectDlg.es"), Messages.getString("MethodSelectDlg.apga")}); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		combo.setBounds(139, 22, 174, 20);
		// combo.select(0);

		/*
		 * 选中方法时触发的事件
		 */
		if (!flag)
			combo.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent arg0) {
					// TODO Auto-generated method stub
					/*
					 * if(table_1.getItemCount()!=0){ if
					 * ((combo.getText().equals(combo.getItem(0)))){ MessageBox
					 * msg = new MessageBox(shell.getShell(), SWT.ICON_WARNING|
					 * SWT.OK); msg.setText("警告");
					 * msg.setMessage("不符合此类型的添加条件！或此类型的方法已有！"); msg.open();
					 * return; } for (int i = 0; i < table_1.getItemCount();
					 * i++) { if (!combo.getText().equals(combo.getItem(0))) {
					 * if (!table_1.getItem(i).getText().equals(
					 * combo.getItem(0))) { MessageBox msg = new
					 * MessageBox(shell .getShell(), SWT.ICON_WARNING | SWT.OK);
					 * msg.setText("警告"); msg.setMessage("此类型的方法已有！");
					 * msg.open(); return; } } } // TableItem item = new
					 * TableItem(table_1,SWT.NONE); // item.setText("co) //
					 * table_1. if(table_1.getSelectionCount()!=1){ MessageBox
					 * msg = new MessageBox(shell .getShell(), SWT.ICON_WARNING
					 * | SWT.OK); msg.setText("警告");
					 * msg.setMessage("必须选择添加的位置！"); msg.open(); return; } }
					 */

					if (combo.getText().equals(combo.getItem(0))) {
						text.setEnabled(false);
						settingsButton.setEnabled(false);
						TableItem item = new TableItem(table_1, SWT.NONE);
						item.setText(new String[] { combo.getText(),
								"1", Messages
								.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						MethodInfo method = new MethodInfo(combo.getText());
						method.setNum(1);
						method.setRunStyle(Messages
								.getString("MethodSelectDlg.39")); //$NON-NLS-1$
						method.setParallelNum(1);
						sMethod.add(method);

						selectedMethod = new MethodInfo[table_1.getItemCount()];
						tableItem = new TableItem[table_1.getItemCount()];
					} else if (combo.getText().equals(combo.getItem(1))) {
						// text.setEnabled(false);
						TableItem item = new TableItem(table_1, SWT.NONE);
						item.setText(new String[] { combo.getText(),
								"1", Messages
								.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

						MethodInfo method = new MethodInfo(combo.getText());
						method.setNum(1);
						method.setRunStyle(Messages
								.getString("MethodSelectDlg.39")); //$NON-NLS-1$
						method.setParallelNum(1);
						sMethod.add(method);

						selectedMethod = new MethodInfo[table_1.getItemCount()];
						tableItem = new TableItem[table_1.getItemCount()];
						// 如果是优化算法
					} else if (combo.getText().equals(combo.getItem(2))
							|| combo.getText().equals(combo.getItem(3))
							|| combo.getText().equals(combo.getItem(4))
							|| combo.getText().equals(combo.getItem(5))
							//加入的新算法的话要到这里面作判断，比如我加入的算法放在数组的第7个位置则加入
							//如下代码
							|| combo.getText().equals(combo.getItem(6))) {
						text.setEnabled(true);
						settingsButton.setEnabled(true);
						text.setFocus();
					} else {
						text.setEnabled(true);
						text.setFocus();
					}

					/*
					 * //if(combo.getText().equals(combo.getItem(0))) {
					 * text_1.setEnabled(false); TableItem item = new
					 * TableItem(table_1, SWT.NONE);
					 * item.setText(combo.getText());
					 * 
					 * // 设置属性
					 * 
					 * 
					 * MethodInfo method = new MethodInfo(combo.getText());
					 * sMethod.add(method);
					 * 
					 * selectedMethod = new MethodInfo[table_1.getItemCount()];
					 * tableItem = new TableItem[table_1.getItemCount()]; } else
					 * if (combo.getText().equals(combo.getItem(1))) { //
					 * text.setEnabled(false); TableItem item = new
					 * TableItem(table_1, SWT.NONE);
					 * item.setText(combo.getText());
					 * 
					 * MethodInfo method = new MethodInfo(combo.getText());
					 * sMethod.add(method);
					 * 
					 * selectedMethod = new MethodInfo[table_1.getItemCount()];
					 * tableItem = new TableItem[table_1.getItemCount()]; } else
					 * { text.setEnabled(true); text.setFocus(); }
					 */
					label_num.setVisible(false);
					text_num.setVisible(false);
					group_2.setVisible(false);
					// table_1.setFocus();
				}
			});

		/*
		 * 验证迭代次数的输入
		 */

		/*
		 * 迭代次数控件失去焦点时的触发事件
		 */

		final Label label_2 = new Label(group, SWT.NONE);
		label_2.setBounds(29, 76, 415, 40);
		// MethodSelectDlg.45=算法说明适用分布注意问题
		label_2.setText(Messages.getString("MethodSelectDlg.45")); //$NON-NLS-1$

		final Label label_1 = new Label(group, SWT.NONE);
		label_1.setBounds(315, 25, 66, 17);
		// MethodSelectDlg.32=迭代次数
		label_1.setText(Messages.getString("MethodSelectDlg.32")); //$NON-NLS-1$

		text = new Text(group, SWT.BORDER);
		text.setBounds(391, 22, 34, 20);
		text.setEnabled(false);
		text.setText("1"); //$NON-NLS-1$
		if (!flag)
			text.addVerifyListener(new VerifyListener() {

				@Override
				public void verifyText(final VerifyEvent e) {
					// TODO Auto-generated method stub

					if (e.text.length() == 1) {
						boolean b = ("0123456789".indexOf(e.text) >= 0); //$NON-NLS-1$
						e.doit = b;
					}
				}
			});
		if (!flag)
			text.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					// 如果是贝叶斯方法,遗传算法，进化算法，退出
					if (combo.getText().equals(combo.getItem(2))
							|| combo.getText().equals(combo.getItem(3))
							|| combo.getText().equals(combo.getItem(4))
							|| combo.getText().equals(combo.getItem(5))
							//加入新算法的判断代码
							|| combo.getTest().equals(combo.getItem(6))){

						return;
					}

					// TODO Auto-generated method stub
					if (text.getText() == "")text.setText("1"); //$NON-NLS-1$ //$NON-NLS-2$
					if (Integer.parseInt(text.getText()) == 0) {
						MessageBox msg = new MessageBox(shell.getShell(),
								SWT.ICON_ERROR | SWT.OK);
						// MethodSelectDlg.37=错误
						msg.setText(Messages.getString("MethodSelectDlg.37")); //$NON-NLS-1$
						// MethodSelectDlg.38=迭代次数不能为0！
						msg
								.setMessage(Messages
										.getString("MethodSelectDlg.38")); //$NON-NLS-1$
						msg.open();
						return;
					}

					if (table_1.getItemCount() <= 1
							|| table_1.getSelectionCount() == 0) {
						// MethodSelectDlg.39=串行
						String sRunStyle = Messages
								.getString("MethodSelectDlg.39"); //$NON-NLS-1$
						if (button.getSelection()) {
							// MethodSelectDlg.40=并行
							sRunStyle = Messages
									.getString("MethodSelectDlg.40"); //$NON-NLS-1$
						}
						int num = Integer.parseInt(text_3.getText());
						for (int i = 0; i < Integer.parseInt(text.getText()); i++) {
							TableItem item = new TableItem(table_1, SWT.NONE);

							item.setText(new String[] { combo.getText(),
									"1", Messages
									.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

							MethodInfo method = new MethodInfo(combo.getText());
							method.setNum(1);
							method.setParallelNum(1);
							method.setRunStyle(sRunStyle);
							// MethodSelectDlg.41=并行
							if (sRunStyle == Messages
									.getString("MethodSelectDlg.41")) //$NON-NLS-1$
								method.setParallelNum(num);
							sMethod.add(method);
						}
						selectedMethod = new MethodInfo[table_1.getItemCount()];
						tableItem = new TableItem[table_1.getItemCount()];
						text.setEnabled(false);
						// settingsButton.setEnabled(false);
					}
					// 在方法列表中显示迭代次数个优化算法
					else {
						tableItem = table_1.getSelection();
						TableItem selectedItem = tableItem[0];
						int a = table_1.indexOf(selectedItem);
						System.out.println("a" + a); //$NON-NLS-1$
						int b = table_1.getItemCount();
						System.out.println("b" + b); //$NON-NLS-1$
						int num = table_1.getItemCount()
								- table_1.indexOf(selectedItem) - 1;
						String[] tempItem = new String[num];
						MethodInfo[] tempMethod = new MethodInfo[num];
						for (int j = 0; j < num; j++) {
							System.out
									.println(table_1.indexOf(selectedItem) + 1);
							tempItem[j] = table_1.getItem(
									table_1.indexOf(selectedItem) + 1)
									.getText();
							table_1.remove(table_1.indexOf(selectedItem) + 1);
							tempMethod[j] = sMethod.remove(table_1
									.indexOf(selectedItem) + 1);

						}
						for (int i = 0; i < Integer.parseInt(text.getText()); i++) {

							TableItem item = new TableItem(table_1, SWT.NONE);
							item.setText(new String[] { combo.getText(),
									"1", Messages
									.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

							MethodInfo method = new MethodInfo(combo.getText());
							method.setNum(1);
							method.setParallelNum(1);
							method.setRunStyle(Messages
									.getString("MethodSelectDlg.39")); //$NON-NLS-1$
							sMethod.add(method);
						}
						for (int k = 0; k < num; k++) {
							TableItem item = new TableItem(table_1, SWT.NONE);
							item.setText(new String[] { tempItem[k],
									"1", Messages
									.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
							tempMethod[k].setNum(1);
							tempMethod[k].setParallelNum(1);
							tempMethod[k].setRunStyle(Messages
									.getString("MethodSelectDlg.39")); //$NON-NLS-1$
							sMethod.add(tempMethod[k]);
						}
						selectedMethod = new MethodInfo[table_1.getItemCount()];
						tableItem = new TableItem[table_1.getItemCount()];
						text.setEnabled(false);
						settingsButton.setEnabled(false);
					}
					text.setText("1"); //$NON-NLS-1$
				}

			});

		settingsButton = new Button(group, SWT.NONE);
		settingsButton.setText(Messages.getString("MethodSelectDlg.5")); //$NON-NLS-1$
		settingsButton.setBounds(431, 21, 60, 20);
		settingsButton.setEnabled(false);
		if (!flag)
			settingsButton.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					// 点击ok按钮的操作
					// System.out.println("thefirst");

					// 如果是贝叶斯算法，弹出设置most和best属性的对话框
					if (combo.getText().equals(combo.getItem(2))) {
						BayesSettings bs = new BayesSettings();
						bs.setProperty();
						if (text.getText() == "")return; //$NON-NLS-1$ //$NON-NLS-2$
						if (Integer.parseInt(text.getText()) == 0) {
							MessageBox msg = new MessageBox(shell.getShell(),
									SWT.ICON_ERROR | SWT.OK);
							// MethodSelectDlg.37=错误
							msg.setText(Messages
									.getString("MethodSelectDlg.37")); //$NON-NLS-1$
							// MethodSelectDlg.38=迭代次数不能为0！
							msg.setMessage(Messages
									.getString("MethodSelectDlg.38")); //$NON-NLS-1$
							msg.open();
							return;
						}
						int intMost = bs.getMostNum();
						int intBest = bs.getBestNum();
						System.out.println("intMost:" + intMost); //$NON-NLS-1$
						System.out.println("intBest:" + intBest); //$NON-NLS-1$
						BayesParamsInfo paramStruct = new BayesParamsInfo(
								intMost, intBest);
						if (bs.getResult() == 0) {
							return;
						}
						if (table_1.getItemCount() <= 1
								|| table_1.getSelectionCount() == 0) {
							// MethodSelectDlg.39=串行
							String sRunStyle = Messages
									.getString("MethodSelectDlg.39"); //$NON-NLS-1$
							if (button.getSelection()) {
								// MethodSelectDlg.40=并行
								sRunStyle = Messages
										.getString("MethodSelectDlg.40"); //$NON-NLS-1$
							}
							int num = Integer.parseInt(text_3.getText());

							for (int i = 0; i < Integer
									.parseInt(text.getText()); i++) {
								TableItem item = new TableItem(table_1,
										SWT.NONE);

								item.setText(new String[] { combo.getText(),
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

								MethodInfo method = new MethodInfo(combo
										.getText());
								method.setNum(1);
								method.setParallelNum(1);
								method.setRunStyle(sRunStyle);
								method.setParamStruct(paramStruct);
								// MethodSelectDlg.41=并行
								if (sRunStyle == Messages
										.getString("MethodSelectDlg.41")) //$NON-NLS-1$
									method.setParallelNum(num);
								sMethod.add(method);
							}
							selectedMethod = new MethodInfo[table_1
									.getItemCount()];
							tableItem = new TableItem[table_1.getItemCount()];
							text.setEnabled(false);
							settingsButton.setEnabled(false);
						}
						// 在方法列表中显示迭代次数个优化算法
						else {
							tableItem = table_1.getSelection();
							TableItem selectedItem = tableItem[0];
							int a = table_1.indexOf(selectedItem);
							System.out.println("a" + a); //$NON-NLS-1$
							int b = table_1.getItemCount();
							System.out.println("b" + b); //$NON-NLS-1$
							int num = table_1.getItemCount()
									- table_1.indexOf(selectedItem) - 1;
							String[] tempItem = new String[num];
							MethodInfo[] tempMethod = new MethodInfo[num];
							for (int j = 0; j < num; j++) {
								System.out.println(table_1
										.indexOf(selectedItem) + 1);
								tempItem[j] = table_1.getItem(
										table_1.indexOf(selectedItem) + 1)
										.getText();
								table_1
										.remove(table_1.indexOf(selectedItem) + 1);
								tempMethod[j] = sMethod.remove(table_1
										.indexOf(selectedItem) + 1);

							}
							for (int i = 0; i < Integer
									.parseInt(text.getText()); i++) {

								TableItem item = new TableItem(table_1,
										SWT.NONE);
								item.setText(new String[] { combo.getText(),
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

								MethodInfo method = new MethodInfo(combo
										.getText());
								method.setNum(1);
								method.setParallelNum(1);
								method.setRunStyle(Messages
										.getString("MethodSelectDlg.39")); //$NON-NLS-1$
								method.setParamStruct(paramStruct);
								sMethod.add(method);
							}
							for (int k = 0; k < num; k++) {
								TableItem item = new TableItem(table_1,
										SWT.NONE);
								item.setText(new String[] { tempItem[k],
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
								tempMethod[k].setNum(1);
								tempMethod[k].setParallelNum(1);
								tempMethod[k].setRunStyle(Messages
										.getString("MethodSelectDlg.39")); //$NON-NLS-1$
								tempMethod[k].setParamStruct(paramStruct);
								sMethod.add(tempMethod[k]);
							}
							selectedMethod = new MethodInfo[table_1
									.getItemCount()];
							tableItem = new TableItem[table_1.getItemCount()];
							text.setEnabled(false);
							settingsButton.setEnabled(false);
						}
						//text.setText("1"); //$NON-NLS-1$	
					}

					// 如果是遗传算法，弹出设置属性的对话框
					if (combo.getText().equals(combo.getItem(3))) {
						GASettings gas = new GASettings();
						gas.setProperty();
						if (text.getText() == "")return; //$NON-NLS-1$ //$NON-NLS-2$
						if (Integer.parseInt(text.getText()) == 0) {
							MessageBox msg = new MessageBox(shell.getShell(),
									SWT.ICON_ERROR | SWT.OK);
							// MethodSelectDlg.37=错误
							msg.setText(Messages
									.getString("MethodSelectDlg.37")); //$NON-NLS-1$
							// MethodSelectDlg.38=迭代次数不能为0！
							msg.setMessage(Messages
									.getString("MethodSelectDlg.38")); //$NON-NLS-1$
							msg.open();
							return;
						}
						double Pc1 = gas.getPc1();// 交叉概率1
						double Pc2 = gas.getPc2();// 交叉概率2
						double Pm = gas.getPm();// 变异概率
						double K = gas.getK();// 温度系数
						double Pt = gas.getPt();// 降温系数
						int NG = gas.getNG();// 内部迭代次数
						GAParamsInfo gapInfo = new GAParamsInfo(Pc1, Pc2, Pm,
								K, Pt, NG);
						System.out
								.println("Pc1 = " + Pc1 + "Pc2 = " + Pc2 + "Pm = " + Pm + "K = " + K + "Pt = " + Pt + "NG = " + NG); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
						if (gas.getResult() == 0) {
							return;
						}
						if (table_1.getItemCount() <= 1
								|| table_1.getSelectionCount() == 0) {
							// MethodSelectDlg.39=串行
							String sRunStyle = Messages
									.getString("MethodSelectDlg.39"); //$NON-NLS-1$
							if (button.getSelection()) {
								// MethodSelectDlg.40=并行
								sRunStyle = Messages
										.getString("MethodSelectDlg.40"); //$NON-NLS-1$
							}
							int num = Integer.parseInt(text_3.getText());
							for (int i = 0; i < Integer
									.parseInt(text.getText()); i++) {
								TableItem item = new TableItem(table_1,
										SWT.NONE);

								item.setText(new String[] { combo.getText(),
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

								MethodInfo method = new MethodInfo(combo
										.getText());
								method.setNum(1);
								method.setParallelNum(1);
								method.setRunStyle(sRunStyle);
								method.setParamStruct(gapInfo);
								// MethodSelectDlg.41=并行
								if (sRunStyle == Messages
										.getString("MethodSelectDlg.41")) //$NON-NLS-1$
									method.setParallelNum(num);
								sMethod.add(method);
							}
							selectedMethod = new MethodInfo[table_1
									.getItemCount()];
							tableItem = new TableItem[table_1.getItemCount()];
							text.setEnabled(false);
							settingsButton.setEnabled(false);
						}
						// 在方法列表中显示迭代次数个优化算法
						else {
							tableItem = table_1.getSelection();
							TableItem selectedItem = tableItem[0];
							int a = table_1.indexOf(selectedItem);
							System.out.println("a" + a); //$NON-NLS-1$
							int b = table_1.getItemCount();
							System.out.println("b" + b); //$NON-NLS-1$
							int num = table_1.getItemCount()
									- table_1.indexOf(selectedItem) - 1;
							String[] tempItem = new String[num];
							MethodInfo[] tempMethod = new MethodInfo[num];
							for (int j = 0; j < num; j++) {
								System.out.println(table_1
										.indexOf(selectedItem) + 1);
								tempItem[j] = table_1.getItem(
										table_1.indexOf(selectedItem) + 1)
										.getText();
								table_1
										.remove(table_1.indexOf(selectedItem) + 1);
								tempMethod[j] = sMethod.remove(table_1
										.indexOf(selectedItem) + 1);

							}
							for (int i = 0; i < Integer
									.parseInt(text.getText()); i++) {

								TableItem item = new TableItem(table_1,
										SWT.NONE);
								item.setText(new String[] { combo.getText(),
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

								MethodInfo method = new MethodInfo(combo
										.getText());
								method.setNum(1);
								method.setParallelNum(1);
								method.setRunStyle(Messages
										.getString("MethodSelectDlg.39")); //$NON-NLS-1$
								method.setParamStruct(gapInfo);
								sMethod.add(method);
							}
							for (int k = 0; k < num; k++) {
								TableItem item = new TableItem(table_1,
										SWT.NONE);
								item.setText(new String[] { tempItem[k],
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
								tempMethod[k].setNum(1);
								tempMethod[k].setParallelNum(1);
								tempMethod[k].setRunStyle(Messages
										.getString("MethodSelectDlg.39")); //$NON-NLS-1$
								tempMethod[k].setParamStruct(gapInfo);
								sMethod.add(tempMethod[k]);
							}
							selectedMethod = new MethodInfo[table_1
									.getItemCount()];
							tableItem = new TableItem[table_1.getItemCount()];
							text.setEnabled(false);
							settingsButton.setEnabled(false);
						}
						//text.setText("1"); //$NON-NLS-1$	
					}

					// 如果是进化算法，弹出设置属性的对话框
					if (combo.getText().equals(combo.getItem(4))) {
						PSOSettings psos = new PSOSettings();
						psos.setProperty();
						if (text.getText() == "")return; //$NON-NLS-1$ //$NON-NLS-2$
						if (Integer.parseInt(text.getText()) == 0) {
							MessageBox msg = new MessageBox(shell.getShell(),
									SWT.ICON_ERROR | SWT.OK);
							// MethodSelectDlg.37=错误
							msg.setText(Messages
									.getString("MethodSelectDlg.37")); //$NON-NLS-1$
							// MethodSelectDlg.38=迭代次数不能为0！
							msg.setMessage(Messages
									.getString("MethodSelectDlg.38")); //$NON-NLS-1$
							msg.open();
							return;
						}
						double c1 = psos.getC1();// 局部学习因子
						double c2 = psos.getC2();// 全局学习因子
						double w = psos.getW();// 惯性权重
						double lamda = psos.getLamda();// 退火系数
						int N = psos.getN();// 内部迭代次数
						PSOParamsInfo psopInfo = new PSOParamsInfo(c1, c2, w,
								lamda, N);
						System.out
								.println("c1 = " + c1 + ",c2 = " + c2 + ",w = " + w + ",lamda = " + lamda + ",N=" + N); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
						if (psos.getResult() == 0) {
							return;
						}
						if (table_1.getItemCount() <= 1
								|| table_1.getSelectionCount() == 0) {
							// MethodSelectDlg.39=串行
							String sRunStyle = Messages
									.getString("MethodSelectDlg.39"); //$NON-NLS-1$
							if (button.getSelection()) {
								// MethodSelectDlg.40=并行
								sRunStyle = Messages
										.getString("MethodSelectDlg.40"); //$NON-NLS-1$
							}
							int num = Integer.parseInt(text_3.getText());
							for (int i = 0; i < Integer
									.parseInt(text.getText()); i++) {
								TableItem item = new TableItem(table_1,
										SWT.NONE);

								item.setText(new String[] { combo.getText(),
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

								MethodInfo method = new MethodInfo(combo
										.getText());
								method.setNum(1);
								method.setParallelNum(1);
								method.setRunStyle(sRunStyle);
								method.setParamStruct(psopInfo);
								// MethodSelectDlg.41=并行
								if (sRunStyle == Messages
										.getString("MethodSelectDlg.41")) //$NON-NLS-1$
									method.setParallelNum(num);
								sMethod.add(method);
							}
							selectedMethod = new MethodInfo[table_1
									.getItemCount()];
							tableItem = new TableItem[table_1.getItemCount()];
							text.setEnabled(false);
							settingsButton.setEnabled(false);
						}
						// 在方法列表中显示迭代次数个优化算法
						else {
							tableItem = table_1.getSelection();
							TableItem selectedItem = tableItem[0];
							int a = table_1.indexOf(selectedItem);
							System.out.println("a" + a); //$NON-NLS-1$
							int b = table_1.getItemCount();
							System.out.println("b" + b); //$NON-NLS-1$
							int num = table_1.getItemCount()
									- table_1.indexOf(selectedItem) - 1;
							String[] tempItem = new String[num];
							MethodInfo[] tempMethod = new MethodInfo[num];
							for (int j = 0; j < num; j++) {
								System.out.println(table_1
										.indexOf(selectedItem) + 1);
								tempItem[j] = table_1.getItem(
										table_1.indexOf(selectedItem) + 1)
										.getText();
								table_1
										.remove(table_1.indexOf(selectedItem) + 1);
								tempMethod[j] = sMethod.remove(table_1
										.indexOf(selectedItem) + 1);

							}
							for (int i = 0; i < Integer
									.parseInt(text.getText()); i++) {

								TableItem item = new TableItem(table_1,
										SWT.NONE);
								item.setText(new String[] { combo.getText(),
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

								MethodInfo method = new MethodInfo(combo
										.getText());
								method.setNum(1);
								method.setParallelNum(1);
								method.setRunStyle(Messages
										.getString("MethodSelectDlg.39")); //$NON-NLS-1$
								method.setParamStruct(psopInfo);
								sMethod.add(method);
							}
							for (int k = 0; k < num; k++) {
								TableItem item = new TableItem(table_1,
										SWT.NONE);
								item.setText(new String[] { tempItem[k],
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
								tempMethod[k].setNum(1);
								tempMethod[k].setParallelNum(1);
								tempMethod[k].setRunStyle(Messages
										.getString("MethodSelectDlg.39")); //$NON-NLS-1$
								tempMethod[k].setParamStruct(psopInfo);
								sMethod.add(tempMethod[k]);
							}
							selectedMethod = new MethodInfo[table_1
									.getItemCount()];
							tableItem = new TableItem[table_1.getItemCount()];
							text.setEnabled(false);
							settingsButton.setEnabled(false);
						}
					}
					if (combo.getText().equals(combo.getItem(5))) {
						ESSetting es = new ESSetting();
						es.setProperty();
						if (text.getText() == "")return; //$NON-NLS-1$ //$NON-NLS-2$
						if (Integer.parseInt(text.getText()) == 0) {
							MessageBox msg = new MessageBox(shell.getShell(),
									SWT.ICON_ERROR | SWT.OK);
							// MethodSelectDlg.37=错误
							msg.setText(Messages
									.getString("MethodSelectDlg.37")); //$NON-NLS-1$
							// MethodSelectDlg.38=迭代次数不能为0！
							msg.setMessage(Messages
									.getString("MethodSelectDlg.38")); //$NON-NLS-1$
							msg.open();
							return;
						}
						int ngInt = es.getngInt();// 内部迭代次数
						double chSigma = es.getchSigma();// 变异方差改变系数
						int OneFifth_gen = es.getOneFifth_gen();// 1/5代数
						ESParamsInfo esInfo = new ESParamsInfo(ngInt, chSigma,
								OneFifth_gen);
						System.out.println("ngInt = " + ngInt + "chSigma = " //$NON-NLS-1$ //$NON-NLS-2$
								+ chSigma + "OneFifth_gen = " + OneFifth_gen); //$NON-NLS-1$
						if (es.getResult() == 0) {
							return;
						}
						if (table_1.getItemCount() <= 1
								|| table_1.getSelectionCount() == 0) {
							// MethodSelectDlg.39=串行
							String sRunStyle = Messages
									.getString("MethodSelectDlg.39"); //$NON-NLS-1$
							if (button.getSelection()) {
								// MethodSelectDlg.40=并行
								sRunStyle = Messages
										.getString("MethodSelectDlg.40"); //$NON-NLS-1$
							}
							int num = Integer.parseInt(text_3.getText());
							for (int i = 0; i < Integer
									.parseInt(text.getText()); i++) {
								TableItem item = new TableItem(table_1,
										SWT.NONE);

								item.setText(new String[] { combo.getText(),
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

								MethodInfo method = new MethodInfo(combo
										.getText());
								method.setNum(1);
								method.setParallelNum(1);
								method.setRunStyle(sRunStyle);
								method.setParamStruct(esInfo);
								// MethodSelectDlg.41=并行
								if (sRunStyle == Messages
										.getString("MethodSelectDlg.41")) //$NON-NLS-1$
									method.setParallelNum(num);
								sMethod.add(method);
							}
							selectedMethod = new MethodInfo[table_1
									.getItemCount()];
							tableItem = new TableItem[table_1.getItemCount()];
							text.setEnabled(false);
							settingsButton.setEnabled(false);
						}
						// 在方法列表中显示迭代次数个优化算法
						else {
							tableItem = table_1.getSelection();
							TableItem selectedItem = tableItem[0];
							int a = table_1.indexOf(selectedItem);
							System.out.println("a" + a); //$NON-NLS-1$
							int b = table_1.getItemCount();
							System.out.println("b" + b); //$NON-NLS-1$
							int num = table_1.getItemCount()
									- table_1.indexOf(selectedItem) - 1;
							String[] tempItem = new String[num];
							MethodInfo[] tempMethod = new MethodInfo[num];
							for (int j = 0; j < num; j++) {
								System.out.println(table_1
										.indexOf(selectedItem) + 1);
								tempItem[j] = table_1.getItem(
										table_1.indexOf(selectedItem) + 1)
										.getText();
								table_1
										.remove(table_1.indexOf(selectedItem) + 1);
								tempMethod[j] = sMethod.remove(table_1
										.indexOf(selectedItem) + 1);

							}
							for (int i = 0; i < Integer
									.parseInt(text.getText()); i++) {

								TableItem item = new TableItem(table_1,
										SWT.NONE);
								item.setText(new String[] { combo.getText(),
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

								MethodInfo method = new MethodInfo(combo
										.getText());
								method.setNum(1);
								method.setParallelNum(1);
								method.setRunStyle(Messages
										.getString("MethodSelectDlg.39")); //$NON-NLS-1$
								method.setParamStruct(esInfo);
								sMethod.add(method);
							}
							for (int k = 0; k < num; k++) {
								TableItem item = new TableItem(table_1,
										SWT.NONE);
								item.setText(new String[] { tempItem[k],
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
								tempMethod[k].setNum(1);
								tempMethod[k].setParallelNum(1);
								tempMethod[k].setRunStyle(Messages
										.getString("MethodSelectDlg.39")); //$NON-NLS-1$
								tempMethod[k].setParamStruct(esInfo);
								sMethod.add(tempMethod[k]);
							}
							selectedMethod = new MethodInfo[table_1
									.getItemCount()];
							tableItem = new TableItem[table_1.getItemCount()];
							text.setEnabled(false);
							settingsButton.setEnabled(false);
						}
						//text.setText("1"); //$NON-NLS-1$	
					}
					
					//加入新算法的信息获取和设置代码，模仿上面的代码做适当的修改
					
					// 如果是聚类遗传算法，弹出设置属性的对话框
					if (combo.getText().equals(combo.getItem(6))) {
						APGASettings apgas = new APGASettings();
						apgas.setProperty();
						if (text.getText() == "")return; //$NON-NLS-1$ //$NON-NLS-2$
						if (Integer.parseInt(text.getText()) == 0) {
							MessageBox msg = new MessageBox(shell.getShell(),
									SWT.ICON_ERROR | SWT.OK);
							// MethodSelectDlg.37=错误
							msg.setText(Messages
									.getString("MethodSelectDlg.37")); //$NON-NLS-1$
							// MethodSelectDlg.38=迭代次数不能为0！
							msg.setMessage(Messages
									.getString("MethodSelectDlg.38")); //$NON-NLS-1$
							msg.open();
							return;
						}
						double Pc1 = apgas.getPc1();// 交叉概率1
						double Pc2 = apgas.getPc2();// 交叉概率2
						double Pm = apgas.getPm();// 变异概率
						double K = apgas.getK();// 温度系数
						double Pt = apgas.getPt();// 降温系数
						int NG = apgas.getNG();// 内部迭代次数
						APGAParamsInfo apgapInfo = new APGAParamsInfo(Pc1, Pc2, Pm,
								K, Pt, NG);
						System.out
								.println("Pc1 = " + Pc1 + "Pc2 = " + Pc2 + "Pm = " + Pm + "K = " + K + "Pt = " + Pt + "NG = " + NG); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
						if (apgas.getResult() == 0) {
							return;
						}
						if (table_1.getItemCount() <= 1
								|| table_1.getSelectionCount() == 0) {
							// MethodSelectDlg.39=串行
							String sRunStyle = Messages
									.getString("MethodSelectDlg.39"); //$NON-NLS-1$
							if (button.getSelection()) {
								// MethodSelectDlg.40=并行
								sRunStyle = Messages
										.getString("MethodSelectDlg.40"); //$NON-NLS-1$
							}
							int num = Integer.parseInt(text_3.getText());
							for (int i = 0; i < Integer
									.parseInt(text.getText()); i++) {
								TableItem item = new TableItem(table_1,
										SWT.NONE);

								item.setText(new String[] { combo.getText(),
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

								MethodInfo method = new MethodInfo(combo
										.getText());
								method.setNum(1);
								method.setParallelNum(1);
								method.setRunStyle(sRunStyle);
								method.setParamStruct(apgapInfo);
								// MethodSelectDlg.41=并行
								if (sRunStyle == Messages
										.getString("MethodSelectDlg.41")) //$NON-NLS-1$
									method.setParallelNum(num);
								sMethod.add(method);
							}
							selectedMethod = new MethodInfo[table_1
									.getItemCount()];
							tableItem = new TableItem[table_1.getItemCount()];
							text.setEnabled(false);
							settingsButton.setEnabled(false);
						}
						// 在方法列表中显示迭代次数个优化算法
						else {
							tableItem = table_1.getSelection();
							TableItem selectedItem = tableItem[0];
							int a = table_1.indexOf(selectedItem);
							System.out.println("a" + a); //$NON-NLS-1$
							int b = table_1.getItemCount();
							System.out.println("b" + b); //$NON-NLS-1$
							int num = table_1.getItemCount()
									- table_1.indexOf(selectedItem) - 1;
							String[] tempItem = new String[num];
							MethodInfo[] tempMethod = new MethodInfo[num];
							for (int j = 0; j < num; j++) {
								System.out.println(table_1
										.indexOf(selectedItem) + 1);
								tempItem[j] = table_1.getItem(
										table_1.indexOf(selectedItem) + 1)
										.getText();
								table_1
										.remove(table_1.indexOf(selectedItem) + 1);
								tempMethod[j] = sMethod.remove(table_1
										.indexOf(selectedItem) + 1);

							}
							for (int i = 0; i < Integer
									.parseInt(text.getText()); i++) {

								TableItem item = new TableItem(table_1,
										SWT.NONE);
								item.setText(new String[] { combo.getText(),
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

								MethodInfo method = new MethodInfo(combo
										.getText());
								method.setNum(1);
								method.setParallelNum(1);
								method.setRunStyle(Messages
										.getString("MethodSelectDlg.39")); //$NON-NLS-1$
								method.setParamStruct(apgapInfo);
								sMethod.add(method);
							}
							for (int k = 0; k < num; k++) {
								TableItem item = new TableItem(table_1,
										SWT.NONE);
								item.setText(new String[] { tempItem[k],
										"1", Messages
										.getString("MethodSelectDlg.39"), "1" }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
								tempMethod[k].setNum(1);
								tempMethod[k].setParallelNum(1);
								tempMethod[k].setRunStyle(Messages
										.getString("MethodSelectDlg.39")); //$NON-NLS-1$
								tempMethod[k].setParamStruct(apgapInfo);
								sMethod.add(tempMethod[k]);
							}
							selectedMethod = new MethodInfo[table_1
									.getItemCount()];
							tableItem = new TableItem[table_1.getItemCount()];
							text.setEnabled(false);
							settingsButton.setEnabled(false);
						}
						//text.setText("1"); //$NON-NLS-1$	
					}
				}

			});

		final Button OK = new Button(shell, SWT.NONE);
		/*
		 * 单击确定按钮，接受设置的方法
		 */

		OK.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				if (!flag) {
					String mthdName = combo.getText();

					if (/* mthdName.isEmpty() || */text_num.getText().isEmpty()) {
						MessageBox msg = new MessageBox(shell.getShell(),
								SWT.ICON_ERROR | SWT.OK);
						// MethodSelectDlg.46=错误
						msg.setText(Messages.getString("MethodSelectDlg.46")); //$NON-NLS-1$
						// MethodSelectDlg.47=实验方法或迭代次数不能为空！
						msg
								.setMessage(Messages
										.getString("MethodSelectDlg.47")); //$NON-NLS-1$
						msg.open();
						return;
					}

					if (Integer.parseInt(text_num.getText()) == 0) {
						MessageBox msg = new MessageBox(shell.getShell(),
								SWT.ICON_ERROR | SWT.OK);
						// MethodSelectDlg.48=错误
						// MethodSelectDlg.49=次数不能为0！
						msg.setText(Messages.getString("MethodSelectDlg.48")); //$NON-NLS-1$
						msg
								.setMessage(Messages
										.getString("MethodSelectDlg.49")); //$NON-NLS-1$
						msg.open();
						return;

					}

					if (sMethod.size() == 0) {
						MessageBox msg = new MessageBox(shell.getShell(),
								SWT.ICON_ERROR | SWT.OK);
						// MethodSelectDlg.48=错误
						msg.setText(Messages.getString("MethodSelectDlg.48")); //$NON-NLS-1$
						msg
								.setMessage(Messages
										.getString("MethodSelectDlg.72")); //$NON-NLS-1$
						msg.open();
						return;
					}

					if (!checkAlgorithm()) {
						return;
					}
					// 给相应的TaskInfo赋值
					task.setSMethod(sMethod);
					// System.out.println(sMethod.toString());

					result = 1;
					shell.close();
				} else {
					result = 0;
					shell.close();
				}
			}
		});

		// MethodSelectDlg.50=确定
		OK.setText(Messages.getString("MethodSelectDlg.50")); //$NON-NLS-1$
		OK.setBounds(344, 460, 64, 22);

		final Button CANCEL = new Button(shell, SWT.NONE);
		CANCEL.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(final SelectionEvent e) {
				result = 0;
				shell.close();
			}
		});
		// MethodSelectDlg.51=取消
		CANCEL.setText(Messages.getString("MethodSelectDlg.51")); //$NON-NLS-1$
		CANCEL.setBounds(433, 460, 64, 22);
		// 删除表格内所有内容
		if (!flag)
			table_1.removeAll();

		group_3 = new Group(shell, SWT.NONE);
		// MethodSelectDlg.52=选用先前任务结果
		group_3.setText(Messages.getString("MethodSelectDlg.52")); //$NON-NLS-1$
		group_3.setBounds(10, 25, 501, 67);

		final Label label_3 = new Label(group_3, SWT.NONE);
		label_3.setBounds(8, 32, 140, 17);
		// MethodSelectDlg.53=优化算法
		label_3.setText(Messages.getString("MethodSelectDlg.53")); //$NON-NLS-1$

		Vector<String> taskNames = new Vector<String>();
		Iterator<TaskInfo> it_jobs = tasksinfo.iterator();
		while (it_jobs.hasNext()) {
			TaskInfo taskInfo = (TaskInfo) it_jobs.next();
			taskNames.add(taskInfo.sTaskName);
		}
		String[] strTaskNames = new String[0];
		strTaskNames = taskNames.toArray(strTaskNames);

		preopt = new Combo(group_3, SWT.READ_ONLY);
		if (!flag)
			preopt.addModifyListener(new ModifyListener() {
				public void modifyText(final ModifyEvent arg0) {

					// 提取选择任务的优化算法
					int index = preopt.getSelectionIndex();
					Vector<MethodInfo> mis = GetTaskOpt(index);

					if (mis.isEmpty())
						return;

					// 复制信息并添加到列表
					Iterator<MethodInfo> it_mi = mis.iterator();
					while (it_mi.hasNext()) {
						MethodInfo mi = it_mi.next();

						TableItem item = new TableItem(table_1, SWT.NONE);
						item.setText(new String[] { mi.getMethodName(),
								Integer.toString(mi.getNum()),
								mi.getRunStyle(),
								Integer.toString(mi.getParallelNum()) });
						sMethod.add(mi);
					}
				}
			});
		// 列出先前包含优化算法的任务
		preopt.setItems(strTaskNames);
		preopt.setBounds(154, 29, 95, 20);

		final Label label_3_1 = new Label(group_3, SWT.NONE);
		label_3_1.setBounds(283, 32, 95, 17);
		// MethodSelectDlg.54=试验算法
		label_3_1.setText(Messages.getString("MethodSelectDlg.54")); //$NON-NLS-1$

		preexp = new Combo(group_3, SWT.READ_ONLY);
		if (!flag)
			preexp.addModifyListener(new ModifyListener() {
				public void modifyText(final ModifyEvent arg0) {

					// 提取选择任务的试验算法
					int index = preexp.getSelectionIndex();
					Vector<MethodInfo> mis = GetTaskExp(index);

					if (mis.isEmpty())
						return;

					// 复制信息并添加到列表
					Iterator<MethodInfo> it_mi = mis.iterator();
					while (it_mi.hasNext()) {
						MethodInfo mi = it_mi.next();

						TableItem item = new TableItem(table_1, SWT.READ_ONLY);
						int nItem = table_1.indexOf(item);
						item.setText(new String[] { mi.getMethodName(),
								Integer.toString(mi.getNum()),
								mi.getRunStyle(),
								Integer.toString(mi.getParallelNum()) });
						sMethod.add(mi);
					}
					isPrePt = true;
				}
			});
		// 列出先前包含试验算法的任务
		preexp.setItems(strTaskNames);
		preexp.setBounds(396, 29, 95, 20);

		useold = new Button(shell, SWT.RADIO);
		if (!flag)
			useold.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {
					select(1);
				}
			});
		useold.setBounds(23, 25, -28, -6);

		usenew = new Button(shell, SWT.RADIO);
		if (!flag)
			usenew.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(final SelectionEvent e) {

					select(2);
				}
			});
		usenew.setBounds(23, 97, -7, -1);

		// 初始化使用创建新任务配置的方式
		// select(2);

		// 初始化先前为提交的算法设置，将其显示到列表框中
		showMethod();
	}

	public void showMethod() {
		if (task.getSMethod().size() > 0) {
			Vector<MethodInfo> methodInfos = task.getSMethod();
			for (MethodInfo methodInfo : methodInfos) {
				if (methodInfo.getMethodName().equals(Messages.getString("MethodSelectDlg.27"))) { //$NON-NLS-1$
					text.setEnabled(false);
					settingsButton.setEnabled(false);
					TableItem item = new TableItem(table_1, SWT.NONE);
					item.setText(new String[] { methodInfo.getMethodName(),
							String.valueOf(methodInfo.getNum()),
							methodInfo.getRunStyle(),
							String.valueOf(methodInfo.getParallelNum()) }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					// MethodInfo method = new MethodInfo(combo.getText());
					// method.setNum(1);
					//					method.setRunStyle("串行"); //$NON-NLS-1$
					// method.setParallelNum(1);
					// sMethod.add(method);

					selectedMethod = new MethodInfo[table_1.getItemCount()];
					tableItem = new TableItem[table_1.getItemCount()];
				} else if (methodInfo.getMethodName().equals("Box-Behnken")) { //$NON-NLS-1$
					// text.setEnabled(false);
					TableItem item = new TableItem(table_1, SWT.NONE);
					item.setText(new String[] { methodInfo.getMethodName(),
							String.valueOf(methodInfo.getNum()),
							methodInfo.getRunStyle(),
							String.valueOf(methodInfo.getParallelNum()) }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

					// MethodInfo method = new MethodInfo(combo.getText());
					// method.setNum(1);
					//					method.setRunStyle("串行"); //$NON-NLS-1$
					// method.setParallelNum(1);
					// sMethod.add(method);

					selectedMethod = new MethodInfo[table_1.getItemCount()];
					tableItem = new TableItem[table_1.getItemCount()];
					// 如果是优化算法
				} else if (methodInfo.getMethodName().equals(Messages.getString("MethodSelectDlg.29")) //$NON-NLS-1$
						|| methodInfo.getMethodName().equals(Messages.getString("MethodSelectDlg.30")) //$NON-NLS-1$
						|| methodInfo.getMethodName().equals(Messages.getString("MethodSelectDlg.31")) //$NON-NLS-1$
						|| methodInfo.getMethodName().equals(Messages.getString("MethodSelectDlg.es"))
						//这个地方也加一个新加算法的判断
						|| methodInfo.getMethodName().equals(Messages.getString("MethodSelectDlg.apga"))
				) { //$NON-NLS-1$
					TableItem item = new TableItem(table_1, SWT.NONE);
					item.setText(new String[] { methodInfo.getMethodName(),
							String.valueOf(methodInfo.getNum()),
							methodInfo.getRunStyle(),
							String.valueOf(methodInfo.getParallelNum()) }); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

					// MethodInfo method = new MethodInfo(combo.getText());
					// method.setNum(1);
					//					method.setRunStyle("串行"); //$NON-NLS-1$
					// method.setParallelNum(1);
					// sMethod.add(method);

					selectedMethod = new MethodInfo[table_1.getItemCount()];
					tableItem = new TableItem[table_1.getItemCount()];
				} else {
					text.setEnabled(true);
					text.setFocus();
				}
				label_num.setVisible(false);
				text_num.setVisible(false);
				group_2.setVisible(false);
			}
		}
	}

	/**
	 * 校验算法流程的正确性
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Boolean checkAlgorithm() {
		// 存放设置的实验算法名称
		List<String> experimentList = new ArrayList<String>();
		// 存放设置的优化算法名称
		Set<String> optimizeList = new HashSet<String>();

		// 存放所有的实验算法名称
		List<String> tempExperimentList = new ArrayList<String>();
		// 存放所有的优化算法名称
		List<String> tempOptimizeList = new ArrayList<String>();
		// 添加实验算法名称
		tempExperimentList.add(Messages.getString("MethodSelectDlg.27")); //$NON-NLS-1$
		tempExperimentList.add("Box-Behnken"); //$NON-NLS-1$
		tempExperimentList.add("Plackeet-Burman"); //$NON-NLS-1$
		// 添加优化算法名称
		tempOptimizeList.add(Messages.getString("MethodSelectDlg.29")); //$NON-NLS-1$
		tempOptimizeList.add(Messages.getString("MethodSelectDlg.30")); //$NON-NLS-1$
		tempOptimizeList.add(Messages.getString("MethodSelectDlg.31")); //$NON-NLS-1$
		tempOptimizeList.add(Messages.getString("MethodSelectDlg.es")); //$NON-NLS-1$
		tempOptimizeList.add(Messages.getString("MethodSelectDlg.apga")); //$NON-NLS-1$
		for (Iterator iterator = sMethod.iterator(); iterator.hasNext();) {
			MethodInfo methodInfo = (MethodInfo) iterator.next();
			String methodName = methodInfo.getMethodName();
			if (tempExperimentList.contains(methodName)) {
				experimentList.add(methodName);
			}
			if (tempOptimizeList.contains(methodName)) {
				optimizeList.add(methodName);
			}
		}

		// 只允许选择一种实验设计方法
		if (experimentList.size() > 1) {
			MessageBox msg = new MessageBox(shell.getShell(), SWT.ICON_ERROR
					| SWT.OK);
			// MethodSelectDlg.46=错误
			msg.setText(Messages.getString("MethodSelectDlg.46")); //$NON-NLS-1$
			msg.setMessage(Messages.getString("MethodSelectDlg.79")); //$NON-NLS-1$
			msg.open();
			return false;
		}

		// 只允许选择一种优化设计方法
		if (optimizeList.size() > 1) {
			MessageBox msg = new MessageBox(shell.getShell(), SWT.ICON_ERROR
					| SWT.OK);
			// MethodSelectDlg.46=错误
			msg.setText(Messages.getString("MethodSelectDlg.46")); //$NON-NLS-1$
			msg.setMessage(Messages.getString("MethodSelectDlg.80")); //$NON-NLS-1$
			msg.open();
			return false;
		}

		// 不允许贝叶斯更新算法或RMS算法单独运行
		if (experimentList.size() == 0) {
			Iterator iterator = optimizeList.iterator();
			while (iterator.hasNext()) {
				String optimizeName = (String) iterator.next();
				if (optimizeName.equals(Messages.getString("MethodSelectDlg.29"))) { //$NON-NLS-1$
					MessageBox msg = new MessageBox(shell.getShell(),
							SWT.ICON_ERROR | SWT.OK);
					// MethodSelectDlg.46=错误
					msg.setText(Messages.getString("MethodSelectDlg.46")); //$NON-NLS-1$
					msg.setMessage(Messages.getString("MethodSelectDlg.82")); //$NON-NLS-1$
					msg.open();
					return false;
				}
			}
		}

		if (experimentList.size() == 1 && optimizeList.size() == 1) {
			String fistName = sMethod.get(0).getMethodName();
			// 必须是实验算法在前，优化算法在后
			if (!tempExperimentList.contains(fistName)) {
				MessageBox msg = new MessageBox(shell.getShell(),
						SWT.ICON_ERROR | SWT.OK);
				// MethodSelectDlg.46=错误
				msg.setText(Messages.getString("MethodSelectDlg.46")); //$NON-NLS-1$
				msg.setMessage(Messages.getString("MethodSelectDlg.83")); //$NON-NLS-1$
				msg.open();
				return false;
			}
			String experimentName = experimentList.get(0);
			String optimizeName = null;
			Iterator iterator = optimizeList.iterator();
			while (iterator.hasNext()) {
				optimizeName = (String) iterator.next();
			}
			// 贝叶斯更新能且只能与拉丁超立方体搭配
			// RSM能且只能与Box-Behnken搭配
			if (Messages.getString("MethodSelectDlg.29").equals(optimizeName)) { //$NON-NLS-1$
				if (!Messages.getString("MethodSelectDlg.27").equals(experimentName)) { //$NON-NLS-1$
					MessageBox msg = new MessageBox(shell.getShell(),
							SWT.ICON_ERROR | SWT.OK);
					// MethodSelectDlg.46=错误
					msg.setText(Messages.getString("MethodSelectDlg.46")); //$NON-NLS-1$
					msg.setMessage(Messages.getString("MethodSelectDlg.86")); //$NON-NLS-1$
					msg.open();
					return false;
				}
			}
		}
		return true;
	}

	public void select(int index) {

		switch (index) {
		case 1:
			group_3.setEnabled(true);
			group.setEnabled(false);
			group_1.setEnabled(false);
			break;
		case 2:
			group_3.setEnabled(false);
			group.setEnabled(true);
			group_1.setEnabled(true);
			break;
		default:
			break;
		}
	}

	public static void main(String[] args) throws DocumentException {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		/*
		 * ReadUnParamXML resolvedUnParameter = new
		 * ReadUnParamXML("c:\\Parameter.xml");
		 * resolvedUnParameter.resolvedUParameter(); String path = "e:\\";
		 */
		MethodSelectDlg window = new MethodSelectDlg(shell, SWT.NONE,
				"c:\\Parameter.xml"); //$NON-NLS-1$
		/*
		 * window.setCount(resolvedUnParameter.getCount());
		 * window.setMax(resolvedUnParameter.getMax());
		 * window.setMin(resolvedUnParameter.getMin());
		 * window.setUnParameterName(resolvedUnParameter.getParameterName());
		 * window
		 * .setUnParameterSource(resolvedUnParameter.getUnParameterSource());
		 */
		window.setFlag(true);
		System.out.println();
		window.open();
		TaskInfo task = window.getTask();
		Vector<MethodInfo> method = task.getSMethod();
	}

	public void SetSubmitedjob(Vector<TaskInfo> tiVector) {

		tasksinfo = tiVector;
	}

	public Vector<MethodInfo> GetTaskExp(int index) {

		Vector<MethodInfo> mis = new Vector<MethodInfo>();

		if (index < 0 || index > tasksinfo.size() - 1)
			return mis;

		TaskInfo ti = tasksinfo.get(index);
		Iterator<MethodInfo> it_m = ti.getSMethod().iterator();
		while (it_m.hasNext()) {
			MethodInfo methodInfo = (MethodInfo) it_m.next();
			// MethodSelectDlg.56=拉丁超立方体
			if (methodInfo.getMethodName().equalsIgnoreCase(
					Messages.getString("MethodSelectDlg.56"))) { //$NON-NLS-1$
				mis.add(methodInfo);
			}
		}

		return mis;
	}

	public Vector<MethodInfo> GetTaskOpt(int index) {

		Vector<MethodInfo> mis = new Vector<MethodInfo>();

		if (index < 0 || index > tasksinfo.size() - 1)
			return mis;

		TaskInfo ti = tasksinfo.get(index);
		Iterator<MethodInfo> it_m = ti.getSMethod().iterator();
		while (it_m.hasNext()) {
			MethodInfo methodInfo = (MethodInfo) it_m.next();
			// MethodSelectDlg.57=贝叶斯更新
			// MethodSelectDlg.58=遗传算法
			// MethodSelectDlg.59=进化算法
			if (methodInfo.getMethodName().equalsIgnoreCase(
					Messages.getString("MethodSelectDlg.57")) || //$NON-NLS-1$
					methodInfo.getMethodName().equalsIgnoreCase(
							Messages.getString("MethodSelectDlg.58")) || //$NON-NLS-1$
					methodInfo.getMethodName().equalsIgnoreCase(
							Messages.getString("MethodSelectDlg.59")) ||//$NON-NLS-1$
					methodInfo.getMethodName().equalsIgnoreCase(
							Messages.getString("MethodSelectDlg.apga")) //$NON-NLS-1$
					|| methodInfo.getMethodName().equalsIgnoreCase(
							Messages.getString("MethodSelectDlg.es"))) { //$NON-NLS-1$
				mis.add(methodInfo);
			}
		}
		return mis;
	}

	public Vector<MethodInfo> GetTaskAll(String strTask) {

		Vector<MethodInfo> mis = new Vector<MethodInfo>();

		if (strTask.equals("")) //$NON-NLS-1$
			return mis;
		TaskInfo ti = null;
		for (int i = 0; i < tasksinfo.size(); i++) {
			TaskInfo temp = tasksinfo.get(i);
			if (temp.sTaskName.equals(strTask)) {
				ti = tasksinfo.get(i);
			}
		}

		Iterator<MethodInfo> it_m = ti.getSMethod().iterator();
		while (it_m.hasNext()) {
			MethodInfo methodInfo = (MethodInfo) it_m.next();
			mis.add(methodInfo);
		}
		return mis;
	}
}
