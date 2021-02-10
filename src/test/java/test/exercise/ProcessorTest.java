package test.exercise;

import main.exercise.exception.FilePathInvalidException;
import main.exercise.exception.FileProcessException;
import main.exercise.processor.Processor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;

import java.io.IOException;

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

    @Test(expected = Exception.class)
    public void testRun_EmptyArgs() throws FilePathInvalidException, IOException {
        String[] args = {};
        processor.run(args);
    }

}