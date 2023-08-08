package simplekafkaproducer;

import simplekafkaproducer.ui.MainFrame;

import javax.swing.*;
import java.util.Arrays;
import java.util.Optional;

public class Application {

  public static void main(String[] args) {
    System.setProperty("awt.useSystemAAFontSettings", "on");

    try {
      Optional<UIManager.LookAndFeelInfo> lookAndFeelInfo =
          Arrays.stream(UIManager.getInstalledLookAndFeels()).filter(lafInfo -> "Nimbus".equals(lafInfo.getName())).findFirst();
      if (lookAndFeelInfo.isPresent()) {
        javax.swing.UIManager.setLookAndFeel(lookAndFeelInfo.get().getClassName());
      }
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      System.out.println("L&F initialization error!");
      System.exit(-1);
    }

    MainFrame frame = new MainFrame();
    frame.setLocation(100, 100);
    frame.setVisible(true);
  }
}
