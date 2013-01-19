/*
 * Created on Aug 19, 2004
 *
 * 
 */
package applications;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.*;
import java.util.*;
import java.nio.channels.*;

import data.LinguisticDataAbstract;

import morph.Decomposition;
import morph.MorphInuk;

/**
 * @author Marta
 *
 */
public class DecomposeHansardTest extends TestCase {

	
	String fileGoldStandard = "ressources/goldstandardHansard.txt";
	String fileTargetSuccessfulAnalysis = "ressources/target_successful_analysis_";
	String fileSuccessfulAnalysis = "outputFiles/successful_analysis_";
	String fileFailedAnalysis = "outputFiles/failed_analysis_";
	String filePrevSuccessNowFailedAnalysis = "outputFiles/previous_success_now_failed_analysis_";
	String fileFullSuccessfulAnalysis = "outputFiles/full_successful_analysis_";
	
	BufferedReader readerGoldStandard;
	BufferedReader readerTargetSuccessfulAnalysis;
	
	BufferedWriter writerSuccessfulAnalysis;
	BufferedWriter writerFullSuccessfulAnalysis;
	BufferedWriter writerFailedAnalysis;
	BufferedWriter writerPrevSuccessNowFailedAnalysis;
	
	Hashtable hashTargetSuccessfulAnalysis = null;
	
	Hashtable errorMessages = new Hashtable();
	
	Vector newSuccessfullyAnalyzedWords = new Vector();
	
	int nbWordsToBeAnalyzed = 0;
	int nbSuccessfulAnalyses = 0;
	int nbNewSuccessfulAnalyses = 0;
	int nbPrevSuccessNowFailedAnalyses = 0;
	int nbFailedAnalyses = 0;
	int nbTargetSuccessfulAnalyses = -1;
	
	static final int decompositionNotFoundInGoldStandard = 1;
	static final int hasFailedButPreviouslySucceded = 2;
	static final int hasFailed = 3;
	static final int hasMoreSuccessfulAnalyses = 4;
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		fileTargetSuccessfulAnalysis += this.getClass().getSimpleName() + ".txt";
		fileSuccessfulAnalysis += this.getClass().getSimpleName() + ".txt";
		fileFailedAnalysis += this.getClass().getSimpleName() + ".txt";
		filePrevSuccessNowFailedAnalysis += this.getClass().getSimpleName() + ".txt";
		fileFullSuccessfulAnalysis += this.getClass().getSimpleName() + ".txt";
	}
	
	/*
	 * This method reads goldstandard.txt line by line, sends the word to be analyzed to
	 *  decomposeWord. The word was analyzed if decomposeWord returns a non-empty array. 
	 * In that case it should compare each array element to the decomposition in the gold standard. 
	 * If it's the same, it's written in the succesful_analysis file. If not, we consider it as a failure,
	 * the word and its decompositions are put in a failed_analysis file.
	 * 
	 * 
	 * If the array is empty, the analysis failed. In that case it should be checked if
	 * it was previously successful by checking the appropriate hashtable. If yes, it should be
	 * written in a prev_succes_now_failed_analysis file.  If not, it's written in a failed_analysis file.   
	 * 
	 */

	public void testDecomposer() throws Exception {
//		Debogage.init();
		LinguisticDataAbstract.init("csv");
		openFilesForReadingAndWriting ();
		StringTokenizer st;
		
		System.out.println("Analyzing the words. Please wait ...");

		Calendar startCalendar = Calendar.getInstance();
		
		while ((st=readLine(readerGoldStandard)) != null) {
		    boolean noProcessing = false;
		    String wordId = st.nextToken();
			String wordToBeAnalyzed = st.nextToken();
			String goldStandardDecomposition = st.nextToken();
			System.out.print("> :"+wordToBeAnalyzed+":");
			Decomposition [] decs = null;
			/*
			 * *x: x is a proper name of some sort
			 * ?x: x's real decomposition is unknown
			 * #x: x is known to contain an error, typo or orthographic
			 * 
			 * Those x words are not analyzed in this test.
			 * 
			 * @x: x is not to be considered only in the test destined to users
			 */
			if (!wordId.startsWith("*") && !wordId.startsWith("?") && !wordId.startsWith("#")) {
                try {
                    decs = MorphInuk.decomposeWord(wordToBeAnalyzed);
                } catch (OutOfMemoryError e) {
                    decs = new Decomposition[]{};
                    System.out.print(" Out of memory error");
                }
                nbWordsToBeAnalyzed++;
                System.out.println(" []");
            } else {
			    noProcessing = true;
				System.out.println(" [x]");
			}

			if (noProcessing) {
			    
			}
			//No decompositions: the analyzer fails to decompose the word	
        	else if (decs.length == 0) {
        	    // Either the word was previously analyzed with success and now failed,
        	    // or it was not analyzed with success.
        		if (hashTargetSuccessfulAnalysis != null && hashTargetSuccessfulAnalysis.containsKey(wordId)){
            		//if it was previously successful and now it failed write the word in the appropriate file
            		//the test is red
        			writerPrevSuccessNowFailedAnalysis.write(wordId);
        			writerPrevSuccessNowFailedAnalysis.newLine();
        			writerFailedAnalysis.write("!!!" + wordId + " " + wordToBeAnalyzed + "------------------------");
        			writerFailedAnalysis.newLine();
        			writerFailedAnalysis.write(">>>" + goldStandardDecomposition);
        			writerFailedAnalysis.newLine();
        			writerFailedAnalysis.newLine();
        			writeErrorMessage(hasFailedButPreviouslySucceded);
        			nbPrevSuccessNowFailedAnalyses++;
        		} else {
        		//if it failed as before write it in the appropriate file	
        			writerFailedAnalysis.write(wordId + " "+ wordToBeAnalyzed + "------------------------");
        			writerFailedAnalysis.newLine();
        			writerFailedAnalysis.write(">>>" + goldStandardDecomposition);
        			writerFailedAnalysis.newLine();
        			writerFailedAnalysis.newLine();
//        			writeErrorMessage(hasFailed);
        		}   
    			nbFailedAnalyses++;
        	} else {
        		//the analyzer returned a decomposition (or decompositions) 
        		//now we test to see if one of them matches the one in goldstandard.txt
        		int i;
        		for (i = 0; i < decs.length; i++) {
//        			if (decs[i].toStrSimple().equals(goldStandardDecomposition)){
            	    if (decs[i].toStr2().equals(goldStandardDecomposition)){
        			    // Successful analysis
        				writerSuccessfulAnalysis.write(wordId + " " + wordToBeAnalyzed + " " + goldStandardDecomposition + " " + (i+1));
        				writerSuccessfulAnalysis.newLine();
        				if (hashTargetSuccessfulAnalysis != null && 
        				        !hashTargetSuccessfulAnalysis.containsKey(wordId)) {
        				    // New successful analysis
        				    writeErrorMessage(hasMoreSuccessfulAnalyses);
        				    newSuccessfullyAnalyzedWords.add(wordId);
        				    nbNewSuccessfulAnalyses++;
        				}
        				nbSuccessfulAnalyses++;
        				// Write the full decomposition in file 'full_successful_analysis_Decomposer...Test.txt'
        				writerFullSuccessfulAnalysis.write(wordId+" "+wordToBeAnalyzed+" "+decs[i].toStr2());
        				writerFullSuccessfulAnalysis.newLine();
        				writerFullSuccessfulAnalysis.flush();
                		break; //this is to avoid repetitions, as we can have same decompositions for one word. toStr method avoids this, but here we're using toString
        			}
        		}
        		
        		//if the counter for the for loop reaches decs.length, that means that neither of the decompositions was equal to the gold standard one
        		if (i == decs.length){
        		    // Failure
            		//if it was previously successful and now it failed write the word in the appropriate file
            		//the test is red
            		if (hashTargetSuccessfulAnalysis != null && hashTargetSuccessfulAnalysis.containsKey(wordId)){
            			writerPrevSuccessNowFailedAnalysis.write(wordId);
            			writerPrevSuccessNowFailedAnalysis.newLine();
            			writerFailedAnalysis.write("!!!" + wordId + " " + wordToBeAnalyzed + "------------------------");
            			writerFailedAnalysis.newLine();
            			writerFailedAnalysis.write(">>>" + goldStandardDecomposition);
            			writerFailedAnalysis.newLine();
            			for (i = 0; i < decs.length; i++) {
//            				writerFailedAnalysis.write(decs[i].toStr2());
            				writerFailedAnalysis.newLine();
            			}
            			writerFailedAnalysis.newLine();
            			writeErrorMessage(hasFailedButPreviouslySucceded);
            			nbPrevSuccessNowFailedAnalyses++;
            		} else {
            		//write it in the appropriate file	
            			writerFailedAnalysis.write(wordId + " " + wordToBeAnalyzed + "------------------------");
            			writerFailedAnalysis.newLine();
            			writerFailedAnalysis.write(">>>" + goldStandardDecomposition);
            			writerFailedAnalysis.newLine();
            			for (i = 0; i < decs.length; i++) {
//            				writerFailedAnalysis.write(decs[i].toStr2());
            				writerFailedAnalysis.newLine();
            			}
            			writerFailedAnalysis.newLine();
//            			writeErrorMessage(hasFailed);
            		}   
        			nbFailedAnalyses++;
    			}
        	}
		}
		
		Calendar endCalendar = Calendar.getInstance();
		
		long time = endCalendar.getTimeInMillis() - startCalendar.getTimeInMillis();
		
		float percentageSuccessfulAnalyses = (float)nbSuccessfulAnalyses / (float)nbWordsToBeAnalyzed;
		writerSuccessfulAnalysis.write("--- ");
		writerSuccessfulAnalysis.write(new Integer(nbSuccessfulAnalyses).toString());
		writerSuccessfulAnalysis.write(" /");
		writerSuccessfulAnalysis.write(new Integer(nbWordsToBeAnalyzed).toString());
		writerSuccessfulAnalysis.write(" ");		
		writerSuccessfulAnalysis.write(Float.toString(percentageSuccessfulAnalyses));
		writerSuccessfulAnalysis.newLine();
		closeFiles();
		System.out.println("Done.\n");
		System.out.println("Nb. of successful analyses: "+nbSuccessfulAnalyses+" /"+nbWordsToBeAnalyzed+" ("+ nbTargetSuccessfulAnalyses + ")");
		System.out.println("Success rate: " + Float.toString(percentageSuccessfulAnalyses));
		System.out.println();
		System.out.println("Nb. of successful analyses: "+nbSuccessfulAnalyses);
		System.out.println("Nb. of new successful analyses: "+nbNewSuccessfulAnalyses);
		System.out.println("Nb. of failed analyses: "+nbFailedAnalyses);
		System.out.println("Nb. of previous successful now failed analyses: "+nbPrevSuccessNowFailedAnalyses);
        if (newSuccessfullyAnalyzedWords.size() != 0) {
            StringBuffer words = new StringBuffer();
            words.append("\nNew successfully analyzed words: ");
            for (int m = 0; m < newSuccessfullyAnalyzedWords.size(); m++)
                words.append((String) newSuccessfullyAnalyzedWords.elementAt(m)
                        + " ");
            words.append("\n");
            System.out.println(words);
        }

		System.out.println("");
		System.out.println("Time in milliseconds: "+time);

		String errorMessagesForPrint =  printErrorMessages(errorMessages);
		//The test is red if at least one error message is produced
		assertTrue("\nThe following error messages were produced by this analysis: \n" + errorMessagesForPrint, errorMessages.isEmpty());
	}
	
		
	
	protected void writeErrorMessage(int key) {
        if (!errorMessages.containsKey(new Integer(key))) {
            switch (key) {
            case decompositionNotFoundInGoldStandard:
                errorMessages
                        .put(
                                new Integer(key),
                                "--- Check the file "
                                        + fileFailedAnalysis
                                        + " for the words that were decomposed but whose decomposition is different from the gold standard one.\n");
                break;
            case hasFailedButPreviouslySucceded:
                errorMessages
                        .put(
                                new Integer(key),
                                "--- Check the file "
                                        + filePrevSuccessNowFailedAnalysis
                                        + " for a list of words whose analysis previously succeeded but now fails.\n"
                                        + "The same words can be found in the file "
                                        + fileFailedAnalysis
                                        + " marked with !!!.\n");
                break;
            case hasFailed:
                errorMessages
                        .put(
                                new Integer(key),
                                "--- Check the file "
                                        + fileFailedAnalysis
                                        + " for a list of words whose analysis failed. The gold standard decomposition is marked with >>>.\n");
                break;
            case hasMoreSuccessfulAnalyses:
                errorMessages.put(new Integer(key),
                        "--- More successful analyses.  Copy the file "
                                + fileSuccessfulAnalysis + " to the file "
                                + fileTargetSuccessfulAnalysis + ".\n");
                break;
            }
        }
    }
	
	protected String printErrorMessages (Hashtable hashOfErrorMessages) {
		String errorMessagesForPrint = "";
		if (!hashOfErrorMessages.isEmpty()) {
			Object[] arrayOfErrorMessages = hashOfErrorMessages.values().toArray();
			for (int i = 0; i < hashOfErrorMessages.size(); i++){
				System.out.println((String)arrayOfErrorMessages[i]);
				errorMessagesForPrint = errorMessagesForPrint.concat((String)arrayOfErrorMessages[i]);
			}
		}
		return errorMessagesForPrint;
	}
	
		
	protected void openFilesForReadingAndWriting () throws Exception {
		try {
			readerGoldStandard = new BufferedReader(new FileReader(fileGoldStandard));
		} catch (FileNotFoundException e) {
			System.out.println("The file goldstandard.txt is not found in the *ressources* folder. Move it there and try again.");
			System.exit(1);
		}
		try {
			readerTargetSuccessfulAnalysis = new BufferedReader(new FileReader(fileTargetSuccessfulAnalysis));
			createHashTargetSuccessfulAnalysis ();
			readerTargetSuccessfulAnalysis.close();
		} catch (Exception e) {
			readerTargetSuccessfulAnalysis = null;
			System.out.println("Only for the first run the file " + fileTargetSuccessfulAnalysis + " cannot be found. If this is not the first run, something is wrong.");
		}
		
		writerSuccessfulAnalysis = new BufferedWriter(new FileWriter(fileSuccessfulAnalysis));
		writerFullSuccessfulAnalysis = new BufferedWriter(new FileWriter(fileFullSuccessfulAnalysis));
		writerFailedAnalysis = new BufferedWriter(new FileWriter(fileFailedAnalysis));
		writerPrevSuccessNowFailedAnalysis = new BufferedWriter(new FileWriter(filePrevSuccessNowFailedAnalysis));
	}
	
	protected void closeFiles () throws IOException {
		readerGoldStandard.close(); 
		
		writerSuccessfulAnalysis.close();
		writerFullSuccessfulAnalysis.close();
		writerFailedAnalysis.close();
		writerPrevSuccessNowFailedAnalysis.close();
		
		//copyFile(new File(fileSuccessfulAnalysis), new File(filePreviousSuccessfulAnalysis));
		//(new File(fileSuccessfulAnalysis)).delete();
	}
	
	protected StringTokenizer readLine(BufferedReader bufReader) throws IOException {
		String line = bufReader.readLine();
		StringTokenizer st;
		if (line != null) {
			st = new StringTokenizer (line);
		} else {
			return null;
		}
		return st;
	}
	
	protected void createHashTargetSuccessfulAnalysis () throws IOException {
		hashTargetSuccessfulAnalysis = new Hashtable();
		StringTokenizer st;
		while ((st=readLine(readerTargetSuccessfulAnalysis)) != null) {
		    String wordId = st.nextToken();
		    if (hashTargetSuccessfulAnalysis.containsKey(wordId))
		        System.out.println("***************"+wordId+"****************");
		    if (wordId.equals("---")) {
//		        nbTargetSuccessfulAnalyses = new Integer(st.nextToken()).intValue();
		        nbTargetSuccessfulAnalyses = hashTargetSuccessfulAnalysis.size();
		    } else {
		        String x[] = new String[]{st.nextToken(),st.nextToken()};
		        hashTargetSuccessfulAnalysis.put(wordId,x);
		    }
		}
	}
	
	protected void copyFile(File in, File out) throws IOException {
	     FileChannel sourceChannel = new FileInputStream(in).getChannel();
	     FileChannel destinationChannel = new FileOutputStream(out).getChannel();
	     sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
	     sourceChannel.close();
	     destinationChannel.close();
	}

	public static Test suite() { 

		/* Junit uses reflexion to add automatically all the methods of TestFoo 

		whose name begins by "test" 

		*/ 

		return new TestSuite(DecomposeHansardTest.class); 

    } 

	public static void main(String [] args) {
		junit.textui.TestRunner.run(suite());	}
	
}
