import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Methods {
	public List<String> getAllFiles(String dir) {
		List<String> result = new ArrayList<>();
		try (Stream<Path> walk = Files.walk(Paths.get(dir))) {

			result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<String> findAllComments(String file) {
		boolean kabutes = false;
		BufferedReader reader;
		List<String> allComments = new ArrayList<String>();
	
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();

			while (line != null) {

				if (line.matches(".*\".*\".*") == true) {
					if (line.replace(line.substring(line.indexOf("\""), line.lastIndexOf("\"") + 1), "")
							.contains("//")) {

						allComments.add(line.substring(line.indexOf("//") + 2));
					}
				} 
				else if ((line.matches(".*\".*") == true) && (kabutes == false)) {
					if (line.replace(line.substring(line.lastIndexOf("\"") + 1), "").contains("//")) {
						allComments.add(line.substring(line.indexOf("//") + 2));
					}
					kabutes = true;
				} 
				else if ((line.matches(".*\".*") == true) && (kabutes == true)) {
					if (line.replace(line.substring(line.lastIndexOf("\"") + 1), "").contains("//")) {
						allComments.add(line.substring(line.lastIndexOf("//") + 2));
						kabutes = false;
					}

				} 
				else {
					if (line.contains("//")) {
						allComments.add(line.substring(line.indexOf("//") + 2));
					}
				}
				line = reader.readLine();

			}
			reader.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return allComments;
	}

	public void writeComments(List<String> comments, String writeFile, String readFile) throws IOException {
		Writer output;
		output = new BufferedWriter(new FileWriter(writeFile, true));
		File file = new File(readFile);
		output.append("==========" + file.getName() + "==========");
		output.append("\n");
		output.close();
		int counter = 1;
		for (String tempComment : comments) {

			output = new BufferedWriter(new FileWriter(writeFile, true));
			output.append(counter + ". //" + tempComment);
			output.append("\n");
			output.close();
			counter++;
		}
	}
	public void saveAllComments(String readDir, String writeDir) throws IOException {
		Methods methods = new Methods();
		File file = new File(writeDir);
		FileWriter writer = new FileWriter(file);
		writer.write("");
		writer.close();
		List<String> failai = methods.getAllFiles(readDir);
		for (String failas : failai) {
			List<String> commentList = methods.findAllComments(failas);
			if (commentList.size() > 0) {
				methods.writeComments(commentList, writeDir, failas);

			}

		}
	}
}
