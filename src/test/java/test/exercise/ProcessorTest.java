package test.exercise;

import main.exercise.exception.AlgorithmTypeInvalidException;
import main.exercise.exception.FilePathInvalidException;
import main.exercise.processor.Processor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ProcessorTest {

    @Mock
    private Logger logger;

    private Processor processor;

    @Before
    public void setup() {
        initMocks(this);
        processor = new Processor();
    }

    @Test
    public void testRun__ProcessesLogFile_Default_WithoutPriorityQueue() {
        String[] args = {"src/test/resources/logfile.txt"};
        String test_result = processor.run(args);
        String result = "1 -> 1\n" + "2 -> 6\n" + "3 -> 0\n" + "4 -> 4\n" + "5 -> INVALID\n" + "6 -> INVALID\n" + "7 -> INVALID\n" + "8 -> 7\n";

        assertEquals(result, String.valueOf(test_result));

    }

    @Test
    public void testRun__ProcessesLogFile_Algorithm1_WithoutPriorityQueue() {
        String[] args = {"src/test/resources/logfile.txt", "without_pq"};
        String test_result = processor.run(args);
        String result = "1 -> 1\n" + "2 -> 6\n" + "3 -> 0\n" + "4 -> 4\n" + "5 -> INVALID\n" + "6 -> INVALID\n" + "7 -> INVALID\n" + "8 -> 7\n";

        assertEquals(result, String.valueOf(test_result));

    }

    @Test
    public void testRun__ProcessesLogFile_Algorithm1_WithPriorityQueue() {
        String[] args = {"src/test/resources/logfile.txt", "with_pq"};
        String test_result = processor.run(args);
        String result = "1 -> 1\n" + "2 -> 6\n" + "3 -> 0\n" + "4 -> 4\n" + "5 -> INVALID\n" + "6 -> INVALID\n" + "7 -> INVALID\n" + "8 -> 7\n";

        assertEquals(result, String.valueOf(test_result));

    }

}