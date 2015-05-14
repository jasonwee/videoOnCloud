package play.learn.weka;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;

public class LearnWeka {

	private static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;

		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}

		return inputReader;
	}

	private static Evaluation classify(Classifier model, Instances trainingSet,
			Instances testingSet) throws Exception {
		Evaluation evaluation = new Evaluation(trainingSet);

		model.buildClassifier(trainingSet);
		evaluation.evaluateModel(model, testingSet);

		return evaluation;
	}

	private static double calculateAccuracy(FastVector predictions) {
		double correct = 0;
		
		for (int i = 0; i < predictions.size(); i++) {
			NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
			if (np.predicted() == np.actual())
				correct++;
		}
		
		return 100 * correct / predictions.size();
	}
	
	public static Instances[][] crossValidationSplit(Instances data, int numberOfFolds) {
		Instances[][] split = new Instances[2][numberOfFolds];
		
		for (int i = 0; i < numberOfFolds; i++) {
			split[0][i] = data.trainCV(numberOfFolds, i);
			split[1][i] = data.testCV(numberOfFolds, i);
		}
		
		return split;
	}

	public static void main(String[] args) {
		
		try {
		BufferedReader datafile = readDataFile("src/resources/weather.txt");

		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);

		// do 10-split cross validation
		Instances[][] split = crossValidationSplit(data, 10);

		// Separate split into training and testing arrays
		Instances[] trainingSplits = split[0];
		Instances[] testingSplits = split[1];

		// use a set of classifiers
		Classifier[] models = { new J48(), // a decision tree
				new PART(), 
				new DecisionTable(), // decision table majority classifier
				new DecisionStump() }; // one-level decision tree

		// run for each model
		for (int j = 0; j < models.length; j++) {

			// collect every group of predictions for current model in a
			// FastVector
			FastVector predictions = new FastVector();

			// for each training-testing split pair, train and test the
			// classifier
			for (int i = 0; i < trainingSplits.length; i++) {
				Evaluation validation = classify(models[j], trainingSplits[i],
						testingSplits[i]);

				predictions.appendElements(validation.predictions());

				// summary for each training-testing pair.
				System.out.println(models[j].toString());
			}

			// calculate overall accuracy of current classifier on all splits
			double accuracy = calculateAccuracy(predictions);

			// print current classifier's name and accuracy in a complicated,
			// but nice-looking way.
			System.out.println("Accuracy of "
					+ models[j].getClass().getSimpleName() + ": "
					+ String.format("%.2f%%", accuracy)
					+ "\n-------------------------------");
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
