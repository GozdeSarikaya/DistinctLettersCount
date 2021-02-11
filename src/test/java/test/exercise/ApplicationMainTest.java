package test.exercise;

import main.exercise.exception.AlgorithmTypeInvalidException;
import main.exercise.exception.FilePathInvalidException;
import main.exercise.exception.ParameterInvalidException;
import main.exercise.processor.Processor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationMainTest {

    private Processor processor;

    @Before
    public void setup() {
        initMocks(this);
        processor = new Processor();
    }

    @Test(expected = Exception.class)
    public void testRun_EmptyArgs() throws FilePathInvalidException {
        String[] args = {};
        processor.run(args);
    }

    @Test(expected = FilePathInvalidException.class)
    public void testRun_EmptyFilePath() throws FilePathInvalidException {
        String[] args = {""};
        processor.run(args);
    }

    @Test(expected = FilePathInvalidException.class)
    public void testRun_InvalidFilePath() throws FilePathInvalidException {
        String[] args = {"invalid"};
        processor.run(args);
    }


    @Test(expected = AlgorithmTypeInvalidException.class)
    public void testRun_InvalidAlgorithmType() throws AlgorithmTypeInvalidException {
        String[] args = {"src/test/resources/logfile.txt", "pq"};
        processor.run(args);
    }
}
