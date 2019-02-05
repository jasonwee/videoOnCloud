package play.learn.java.design.mvp;

import java.io.Serializable;

public class FileSelectorPresenter implements Serializable {
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 1210314339075855074L;

	/**
	 * The View component that the presenter interacts with.
	 */
	private FileSelectorView view;

	/**
	 * The Model component that the presenter interacts with.
	 */
	private FileLoader loader;

	/**
	 * Constructor
	 * 
	 * @param view
	 *            The view component that the presenter will interact with.
	 */
	public FileSelectorPresenter(FileSelectorView view) {
		this.view = view;
	}

	/**
	 * Sets the {@link FileLoader} object, to the value given as parameter.
	 * 
	 * @param loader
	 *            The new {@link FileLoader} object(the Model component).
	 */
	public void setLoader(FileLoader loader) {
		this.loader = loader;
	}

	/**
	 * Starts the presenter.
	 */
	public void start() {
		view.setPresenter(this);
		view.open();
	}

	/**
	 * An "event" that fires when the name of the file to be loaded changes.
	 */
	public void fileNameChanged() {
		loader.setFileName(view.getFileName());
	}

	/**
	 * Ok button handler
	 */
	public void confirmed() {
		if (loader.getFileName() == null || loader.getFileName().equals("")) {
			view.showMessage("Please give the name of the file first!");
			return;
		}

		if (loader.fileExists()) {
			String data = loader.loadData();
			view.displayData(data);
		} else {
			view.showMessage("The file specified does not exist.");
		}
	}

	/**
	 * Cancels the file loading process.
	 */
	public void cancelled() {
		view.close();
	}
}
