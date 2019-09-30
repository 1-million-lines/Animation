import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Timer;

public class Animation {

	private static class State {
		private final String text = "Hello world!";
		private int startIndex = 0;
		private boolean rotateRight = true;
	}

	public static void main(final String... args) {
		State state = new State();

		JLabel label = new JLabel(state.text);
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				state.rotateRight = !state.rotateRight;
			}
		});

		TimerTask task = new TimerTask() {
			public void run() {
				int delta = state.rotateRight ? 1 : -1;
				state.startIndex = (state.startIndex + state.text.length() +
					delta) % state.text.length();
				label.setText(rotate(state.text, state.startIndex));
			}
		};
		Timer timer = new Timer(false);
		timer.schedule(task, 0, 500);

		JFrame rot = new JFrame();
		rot.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		rot.add(label);
		rot.pack();
		rot.setLocationRelativeTo(null);
		rot.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				timer.cancel();
			}
		});
		rot.setVisible(true);
	}

	private static String rotate(String text, int startIndex) {
		char[] rotated = new char[text.length()];
		for (int i = 0; i < text.length(); i++) {
			rotated[i] = text.charAt((startIndex + i) % text.length());
		}
		return String.valueOf(rotated);
	}
}
