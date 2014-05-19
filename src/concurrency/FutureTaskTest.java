package concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * ����FutureTask���÷�����������֧�߳��������̣߳�����ȡ�÷�֧�̵߳�ִ�н��������FutureTask
 * 
 * @author Administrator
 * 
 */
public class FutureTaskTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CountNum cn = new CountNum(0);
		// FutureTask<Integer> ����ı�ʾ���ص���Integer
		FutureTask<Integer> ft = new FutureTask<Integer>(cn);
		Thread td = new Thread(ft);
		System.out.println("futureTask��ʼִ�м���:" + System.currentTimeMillis());
		td.start();
		System.out.println("main ���߳̿�����Щ��������:" + System.currentTimeMillis());
		try {
			// futureTask��get������������֪������ȡ�ý��Ϊֹ
			Integer result = ft.get();
			System.out.println("����Ľ����:" + result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("ȡ�÷�֧�߳�ִ�еĽ�������߳̿��Լ���������������");
	}

}

class CountNum implements Callable<Integer> {
	private Integer sum;

	public CountNum(Integer sum) {
		this.sum = sum;
	}

	public Integer call() throws Exception {
		for (int i = 0; i < 100; i++) {
			sum = sum + i;
		}
		// ����5���ӣ��۲����߳���Ϊ��Ԥ�ڵĽ�������̻߳����ִ�У���Ҫȡ��FutureTask�Ľ���ǵȴ�ֱ����ɡ�
		Thread.sleep(3000);
		System.out.println("futureTask ִ�����" + System.currentTimeMillis());
		return sum;
	}

}