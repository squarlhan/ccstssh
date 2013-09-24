package cn.edu.jlu.ccst.opc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Timer;
import java.util.TimerTask;

import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

public class Runme implements WrapperListener{
	public static final int SUCCESS = 0;            // 表示程序执行成功

    public static final String COMMAND = "java -jar opcread.jar";    // 要执行的语句

    public static final String SUCCESS_MESSAGE = "程序执行成功！";

    public static final String ERROR_MESSAGE = "程序执行出错：";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WrapperManager.start( new Runme(), args );
	}

	@Override
	public void controlEvent(int event) {
		// TODO Auto-generated method stub
		if ((event == WrapperManager.WRAPPER_CTRL_LOGOFF_EVENT)
				&& (WrapperManager.isLaunchedAsService() || WrapperManager
						.isIgnoreUserLogoffs())) {
		} else {
			WrapperManager.stop(0);
		}
	}

	@Override
	public Integer start(String[] arg0) {
		// TODO Auto-generated method stub
		int timespan = 20;
		FileReader read;
		try {
			read = new FileReader("opcConfig.ini");
			BufferedReader br = new BufferedReader(read);
			String row;
			while ((row = br.readLine()) != null) {
				if(row.trim().startsWith("timespan")){
					String[] hs = row.trim().split("=");
					timespan = Integer.parseInt(hs[1].trim());
				}
			}
			br.close();
			System.out.println(timespan);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timer timer = new Timer();
        timer.schedule(new TimerTask(){
        	public void run() {
        		try {
        			System.out.println("********");
        			Process process = Runtime.getRuntime().exec(COMMAND);
					 // 打印程序输出
			        readProcessOutput(process);

			        // 等待程序执行结束并输出状态
			        int exitCode = process.waitFor();

			        if (exitCode == SUCCESS) {
			            System.out.println(SUCCESS_MESSAGE);
			        } else {
			            System.err.println(ERROR_MESSAGE + exitCode);
			        }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }, 1000, timespan*1000);
		return null;
	}

	@Override
	public int stop(int arg0) {
		// TODO Auto-generated method stub
			System.out.println("OPC DEMO STOPPING...");
			return arg0;
	}
	
	   /**
     * 打印进程输出
     *
     * @param process 进程
     */
    private void readProcessOutput(final Process process) {
        // 将进程的正常输出在 System.out 中打印，进程的错误输出在 System.err 中打印
        read(process.getInputStream(), System.out);
        read(process.getErrorStream(), System.err);
    }

    // 读取输入流
    private void read(InputStream inputStream, PrintStream out) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

