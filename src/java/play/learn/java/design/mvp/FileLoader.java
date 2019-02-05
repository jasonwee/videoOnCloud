package play.learn.java.design.mvp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;

public class FileLoader implements Serializable {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -4745803872902019069L;

	/**
	 * Indicates if the file is loaded or not.
	 */
	private boolean loaded;

	/**
	 * The name of the file that we want to load.
	 */
	private String fileName;

	/**
	 * Loads the data of the file specified.
	 */
	public String loadData() {
		String dataFileName = this.fileName;
		try (BufferedReader br = new BufferedReader(new FileReader(new File(dataFileName)))) {
			StringBuilder sb = new StringBuilder();
			String line;

			while ((line = br.readLine()) != null) {
				sb.append(line).append('\n');
			}

			this.loaded = true;

			return sb.toString();
		} catch (Exception e) {
			System.out.printf("File %s does not exist", dataFileName);
		}

		return null;
	}

	/**
	 * Sets the path of the file to be loaded, to the given value.
	 * 
	 * @param fileName
	 *            The path of the file to be loaded.
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return fileName The path of the file to be loaded.
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * @return True, if the file given exists, false otherwise.
	 */
	public boolean fileExists() {
		return new File(this.fileName).exists();
	}

	/**
	 * @return True, if the file is loaded, false otherwise.
	 */
	public boolean isLoaded() {
		return this.loaded;
	}
}
