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
	
	static int z = 1;//计时器开关
	static int x = 1;// 音效开关
	static int y = 1;// 音乐切换开关
	
	JButton b1 = new JButton("重新开始");
	JButton b2 = new JButton("结束游戏");
	JButton b3 = new JButton("我要悔棋");
	JButton b4 = new JButton("音乐");
	//JButton b5 = new JButton("确定");
	JButton b6 = new JButton("切换音乐");
	Vector v = new Vector(); // 所有的每步走棋信息
	Vector white = new Vector(); // 白方走棋信息
	Vector black = new Vector(); // 黑方走棋信息

	int h = 300; // 网格的高
	int w = 300; // 网格的宽
	int s = 8; // 棋子的半径
	int p = 20; // 网格的间距

	public chessboard() throws Exception {
		
		// 创建窗口
		this.setTitle("五子棋");

		this.setSize(600, 450); // 设置棋盘大小和位置
		this.setLocation(600, 200);

		this.setLayout(new BorderLayout());// 设置按钮及布局
		JPanel p1 = new JPanel();

		JPanel p2 = new JPanel() {
			public void paint(Graphics g) { // 绘制棋盘的网格和棋子
				// super.paint(g);
				int j = 150; // 横向位置
				int k = 20; // 竖向位置

				g.setColor(Color.orange); // 画棋盘
				g.fillRect(j - 20, k - 20, h + 40, w + 40);
				g.setColor(Color.black); // 画棋盘框
				g.drawRect(j - 20, k - 20, h + 40, w + 40);
				for (int i = j; i <= 300 + j; i = i + p)// 画竖线
					g.drawLine(i, k, i, k + h);
				for (int i = k; i <= 300 + k; i = i + p) // 画横线
					g.drawLine(j, i, j + w, i);

				for (int i = 0; i < v.size(); i++) { // 画棋子
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
        //Panel p2=new mypanel();		//创建棋盘网格
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
		this.setResizable(false); // 禁止调整窗口大小
		this.setVisible(true);// 窗口可见
		// pack();

	}

	public static void main(String[] args) throws Exception {
		chessboard ssd = new chessboard();
	}

	@Override
	public void mouseClicked(MouseEvent e) { // 创建监听器


		int x, y;
		x = e.getX();
		y = e.getY();

		if ((x > 460 || x < 140) || (y < 10 || y > 330)) {
			System.out.println("这里不能下棋！");
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
				System.out.println("已经有棋了！");
			} else {
				v.add(x + "-" + y);
				this.repaint();
				if (v.size() % 2 == 0) {
					white.add(x + "-" + y);
					this.victory(x, y, white);
					System.out.println("黑棋");

				} else {

					black.add(x + "-" + y);
					this.victory(x, y, black);
					System.out.println("白棋");
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
			JOptionPane.showMessageDialog(this, "没有棋可悔");
		} else {
			if (v.size() % 2 == 0) { // 判断是白棋悔棋，还是黑棋悔棋
				v.remove(v.lastElement());
				this.repaint();
			} else {
				v.remove(v.lastElement());
				this.repaint();
			}
		}
	}

	private void victory(int x, int y, Vector contain) { // 判断是否胜利
		int cv = 0; // 垂直方向棋子数量
		int ch = 0; // 水平方向棋子数量
		int ci1 = 0; // 斜面方向棋子数量1
		int ci2 = 0; // 斜面方向棋子数量2

		// 计算水平方向棋子数量
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

		// 计算垂直方向棋子数量
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

		// 计算45°斜面方向棋子数量
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

		// 计算135°斜面方向棋子数量
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
			System.out.println(v.size() + "步棋");
			if (v.size() % 2 == 0) {
				// 判断是黑棋赢，还是白棋赢
				JOptionPane.showMessageDialog(null, "黑棋赢了");
				time.thread.stop();
			} else {
				JOptionPane.showMessageDialog(null, "白棋赢了");
				time.thread.stop();
			}
			this.v.clear();
			this.black.clear();
			this.white.clear();
			this.repaint();
			z = 2;//方便再开一局时更新计时器
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
		// 计数线程
		private CountingThread thread;
		// 记录程序开始时间
		private long programStart = System.currentTimeMillis();
		//
		// private long pauseStart = programStart;
		private JLabel label = new JLabel("游戏已开始："+INITIAL_LABEL_TEXT);
		JFrame jf = new JFrame();

		public Timers() {
			label.setVisible(true);
			jf.setSize(500, 200);
			jf.setLocation(600, 0);
			jf.setTitle("计时器");
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
					label.setText("游戏已开始："+format(elapsed));
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
