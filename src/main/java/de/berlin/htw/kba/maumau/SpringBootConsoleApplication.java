package de.berlin.htw.kba.maumau;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import de.berlin.htw.kba.maumau.table.controller.TableViewController;
import de.berlin.htw.kba.maumau.table.view.TableView;
import de.berlin.htw.kba.maumau.table.view.TableViewImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class SpringBootConsoleApplication.
 */
@SpringBootApplication
@ComponentScan("de.berlin.htw.kba.maumau")
@EnableScheduling
public class SpringBootConsoleApplication implements CommandLineRunner {

	/** The controller. */
	@Autowired
	private TableViewController controller;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(SpringBootConsoleApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.setLogStartupInfo(false);
		app.run(args);
	}

	/* (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@Override
	public void run(String... args) throws Exception {
		TableView tableView = new TableViewImpl();
		controller.setView(tableView);
		controller.initGameLobby();
	}
}
