package wuzigm;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
//import java.awt.color.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;

import java.io.*;
import java.net.*;

public class chessboard extends JFrame implements MouseListener, Serializable, Runnable {
	Music2 music;
	Timers time;
	
	static int z = 1;//��ʱ������
	static int x = 1;// ��Ч����
	static int y = 1;// �����л�����
	
	JButton b1 = new JButton("���¿�ʼ");
	JButton b2 = new JButton("������Ϸ");
	JButton b3 = new JButton("��Ҫ����");
	JButton b4 = new JButton("����");
	//JButton b5 = new JButton("ȷ��");
	JButton b6 = new JButton("�л�����");
	Vector v = new Vector(); // ���е�ÿ��������Ϣ
	Vector white = new Vector(); // �׷�������Ϣ
	Vector black = new Vector(); // �ڷ�������Ϣ

	int h = 300; // ����ĸ�
	int w = 300; // ����Ŀ�
	int s = 8; // ���ӵİ뾶
	int p = 20; // ����ļ��

	public chessboard() throws Exception {
		
		// ��������
		this.setTitle("������");

		this.setSize(600, 450); // �������̴�С��λ��
		this.setLocation(600, 200);

		this.setLayout(new BorderLayout());// ���ð�ť������
		JPanel p1 = new JPanel();

		JPanel p2 = new JPanel() {
			public void paint(Graphics g) { // �������̵����������
				// super.paint(g);
				int j = 150; // ����λ��
				int k = 20; // ����λ��

				g.setColor(Color.orange); // ������
				g.fillRect(j - 20, k - 20, h + 40, w + 40);
				g.setColor(Color.black); // �����̿�
				g.drawRect(j - 20, k - 20, h + 40, w + 40);
				for (int i = j; i <= 300 + j; i = i + p)// ������
					g.drawLine(i, k, i, k + h);
				for (int i = k; i <= 300 + k; i = i + p) // ������
					g.drawLine(j, i, j + w, i);

				for (int i = 0; i < v.size(); i++) { // ������
					// System.out.println("hhhh");
					if (i % 2 == 0)
						g.setColor(Color.white);
					else
						g.setColor(Color.black);

					String str = (String) v.get(i);
					String tmp[] = str.split("-");
					int a = Integer.parseInt(tmp[0]);

					int b = Integer.parseInt(tmp[1]);
					g.fillOval(a - s, b - s, 16, 16);

				}

			}
		};
        //Panel p2=new mypanel();		//������������
		//JPanel p3 = new JPanel();

		p1.add(b4);
		p1.add(b1);
		p1.add(b3);
		p1.add(b2);
		p1.add(b6);
		//p3.add(b5);
		this.add(p1, BorderLayout.SOUTH);
		this.add(p2, BorderLayout.CENTER);
		//this.add(p3, BorderLayout.NORTH);
		b1.addMouseListener(this);
		b2.addMouseListener(this);
		b3.addMouseListener(this);
		b4.addMouseListener(this);
		//b5.addMouseListener(this);
		b6.addMouseListener(this);
		p2.addMouseListener(this);

		p1.setBackground(Color.gray);


		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false); // ��ֹ�������ڴ�С
		this.setVisible(true);// ���ڿɼ�
		// pack();

	}

	public static void main(String[] args) throws Exception {
		chessboard ssd = new chessboard();
	}

	@Override
	public void mouseClicked(MouseEvent e) { // ����������


		int x, y;
		x = e.getX();
		y = e.getY();

		if ((x > 460 || x < 140) || (y < 10 || y > 330)) {
			System.out.println("���ﲻ�����壡");
		}else {
			if(z == 2){
				time.jf.dispose();
				z = 1;
			}
			if(z == 1){
				time = new Timers();
				z = 0;
			}
			if (-10 < (x - 150) % p && (x - 150) % p < 10)
				x = x - (x - 150) % p;
			else
				x = x - (x - 150) % p + p;
			if (-10 < (y - 20) % p && (y - 20) % p < 10)
				y = y - (y - 20) % p;
			else
				y = y - (y - 20) % p + p;
			if (v.contains(x + "-" + y)) {
				System.out.println("�Ѿ������ˣ�");
			} else {
				v.add(x + "-" + y);
				this.repaint();
				if (v.size() % 2 == 0) {
					white.add(x + "-" + y);
					this.victory(x, y, white);
					System.out.println("����");

				} else {

					black.add(x + "-" + y);
					this.victory(x, y, black);
					System.out.println("����");
				}
			}
//	System.out.print(x+"  "+y+"  ");
//	System.out.println(v.size());
//	System.out.println(v);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
//	 TODO Auto-generated method stub
		try {
			if ((JButton) e.getSource() == b1) {				
				if(z == 0){
					time.jf.dispose();
					//time = new Timers();
					z = 1;
				}
				kai();
				
			} else if ((JButton) e.getSource() == b2) {
				guan();
				time.jf.dispose();
			} else if ((JButton) e.getSource() == b3) {
				huiqi();
			} else if ((JButton) e.getSource() == b4) {
				if (x == 1) {
					music = new Music2();
					music.Music1();
					x = x + 1;
					y = y + 1;
				} else if (x % 2 == 0) {
					// System.out.println(x);
					music.Stop(music.au);
					x = x + 1;
				} else if (x % 2 == 1) {
					// System.out.println(x);
					music = new Music2();
					music.Music1();
					x = x + 1;
				}

			} else if ((JButton) e.getSource() == b6) {
				if (y == 1) {
					music.str = music.str1;
				} else if (y % 2 == 1) {
					music.Stop(music.au);
					music.str = music.str1;
					music = new Music2();
					music.Music1();
					y++;
				} else if (y % 2 == 0) {
					music.Stop(music.au);
					music.str = music.str2;
					music = new Music2();
					music.Music1();
					y++;
				}
			}
		} catch (Exception e1) {
		}

	}

	public void kai() {
		this.v.clear();
		this.black.clear();
		this.white.clear();
		this.repaint();
		System.out.println("qi" + v.size());
	}

	public void guan() {
		System.exit(0);
	}

	public void huiqi() {
		if (v.isEmpty()) {
			JOptionPane.showMessageDialog(this, "û����ɻ�");
		} else {
			if (v.size() % 2 == 0) { // �ж��ǰ�����壬���Ǻ������
				v.remove(v.lastElement());
				this.repaint();
			} else {
				v.remove(v.lastElement());
				this.repaint();
			}
		}
	}

	private void victory(int x, int y, Vector contain) { // �ж��Ƿ�ʤ��
		int cv = 0; // ��ֱ������������
		int ch = 0; // ˮƽ������������
		int ci1 = 0; // б�淽����������1
		int ci2 = 0; // б�淽����������2

		// ����ˮƽ������������
		for (int i = 1; i < 5; i++) {
			if (contain.contains((x + i * 20) + "-" + y)) {
				ch++;

			} else {
				break;
			}
		}
		for (int i = 1; i < 5; i++) {
			if (contain.contains((x - i * 20) + "-" + y)) {
				ch++;
			} else {
				System.out.println(white.size());
				break;
			}
		}

		// ���㴹ֱ������������
		for (int i = 1; i < 5; i++) {
			if (contain.contains(x + "-" + (y + i * 20))) {
				cv++;
			} else {
				break;
			}
		}
		for (int i = 1; i < 5; i++) {
			if (contain.contains(x + "-" + (y - i * 20))) {
				cv++;
			} else {
				break;
			}
		}

		// ����45��б�淽����������
		for (int i = 1; i < 5; i++) {
			if (contain.contains((x + i * 20) + "-" + (y + i * 20))) {
				ci1++;
			} else {
				break;
			}
		}
		for (int i = 1; i < 5; i++) {
			if (contain.contains((x - i * 20) + "-" + (y - i * 20))) {
				ci1++;
			} else {
				break;
			}
		}

		// ����135��б�淽����������
		for (int i = 1; i < 5; i++) {
			if (contain.contains((x + i * 20) + "-" + (y - i * 20))) {
				ci2++;
			} else {
				break;
			}
		}
		for (int i = 1; i < 5; i++) {
			if (contain.contains((x - i * 20) + "-" + (y + i * 20))) {
				ci2++;
			} else {
				break;
			}
		}

		if (ch >= 4 || cv >= 4 || ci1 >= 4 || ci2 >= 4) {
			System.out.println(v.size() + "����");
			if (v.size() % 2 == 0) {
				// �ж��Ǻ���Ӯ�����ǰ���Ӯ
				JOptionPane.showMessageDialog(null, "����Ӯ��");
				time.thread.stop();
			} else {
				JOptionPane.showMessageDialog(null, "����Ӯ��");
				time.thread.stop();
			}
			this.v.clear();
			this.black.clear();
			this.white.clear();
			this.repaint();
			z = 2;//�����ٿ�һ��ʱ���¼�ʱ��
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	class Timers extends JFrame {
		private static final long serialVersionUID = 1L;
		private static final String INITIAL_LABEL_TEXT = "00:00:00";
		// �����߳�
		private CountingThread thread;
		// ��¼����ʼʱ��
		private long programStart = System.currentTimeMillis();
		//
		// private long pauseStart = programStart;
		private JLabel label = new JLabel("��Ϸ�ѿ�ʼ��"+INITIAL_LABEL_TEXT);
		JFrame jf = new JFrame();

		public Timers() {
			label.setVisible(true);
			jf.setSize(500, 200);
			jf.setLocation(600, 0);
			jf.setTitle("��ʱ��");
			jf.add(label);
			jf.setVisible(true);

			// System.out.println("111");
			thread = new CountingThread();
			thread.start();

		}

		private class CountingThread extends Thread {
			private CountingThread() {
				setDaemon(true);
			}

			public void run() {
				while (true) {
					long elapsed = System.currentTimeMillis() - programStart;
					label.setText("��Ϸ�ѿ�ʼ��"+format(elapsed));
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
						System.exit(1);
					}
				}
			}

			private String format(long elapsed) {
				int hour, minute, second, mili;
				mili = (int) (elapsed % 1000);
				elapsed = elapsed / 1000;
				second = (int) (elapsed % 60);
				elapsed = elapsed / 60;
				minute = (int) (elapsed % 60);
				elapsed = elapsed / 60;
				hour = (int) (elapsed % 60);
				return String.format("%2d:%2d:%2d", hour, minute, second);
			}

		}
	}

}

class Music2 {
	static String str1 = "quiet.wav";
	static String str2 = "sunday.wav";
	static String str = str1;
	File f;
	URI uri;
	URL url;
	static AudioClip au;

	void Music1() {
		try {
			f = new File(str);
			uri = f.toURI();
			url = uri.toURL();
			au = Applet.newAudioClip(url);
			au.loop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void Stop(AudioClip au) {
		au.stop();
	}
}
