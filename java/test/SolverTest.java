import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Test;


public class SolverTest {
	
	@Test
	public void shouldParseResult() throws Exception {
		assertThat(Solver.parseResult(""), CoreMatchers.equalTo(0));
	}

}
