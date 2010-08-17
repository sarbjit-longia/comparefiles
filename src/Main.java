import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import com.merge.folder.JCompareWindow;
import com.merge.menubar.MenuBar;
import com.merge.splitpane.AppSplitPane;
import com.merge.splitpane.TabComponent;
import com.merge.splitpane.TabPane;
import com.merge.statusbar.AppStatusBar;
import com.merge.toolbar.ToolBar;


public class Main {


	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() { 
				try{
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				}catch(Exception e){e.printStackTrace();}
				final Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				JFrame frame = new JFrame("Merge");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(dim.width - 20, dim.height - 20);
				frame.setJMenuBar(new MenuBar());
				frame.setLayout(new BorderLayout());
				frame.add(ToolBar.getInstance(), BorderLayout.NORTH);
				frame.add(new AppStatusBar(), BorderLayout.SOUTH);
				//frame.add(new AppSplitPane("file.txt", "file2.txt"), BorderLayout.CENTER);
				JCompareWindow window = new JCompareWindow();
				window.setVisible(true);
				window.setPaths("c:\\a","c:\\x");
				TabPane tab = new TabPane();
				AppSplitPane p = new AppSplitPane("file.txt", "file2.txt");
				tab.add(window);
				tab.add(p);
				tab.setTabComponentAt(0, new TabComponent(tab, window.getTitle()));
				tab.setTabComponentAt(1, new TabComponent(tab, p.getName()));
				p = new AppSplitPane("file2.txt", "file.txt");
				tab.add(p);
				tab.setTabComponentAt(2, new TabComponent(tab, p.getName()));
				frame.add(tab, BorderLayout.CENTER);
				//frame.add(new MToolBar(), BorderLayout.NORTH);
				frame.setLocation(0, 0);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.show();
			}
		});
	}
}
