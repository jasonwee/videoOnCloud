package mock;

import java.util.ArrayList;
import java.util.List;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;
import mockit.VerificationsInOrder;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class UserTest {
	@Test
	public void testMethodinClassUsingMockup() {
		new MockUp<Skill>() {
			@Mock
			public List getBackendSkill() {
				return new ArrayList();
			}

			@Mock
			public List getUISkills() {
				return new ArrayList();
			}
		};
		
		User user = new User();
		System.out.println(user.getSkill("Both").toString());
		Assert.assertEquals(user.getSkill("Both").toString(), "[[]]"); // return [[]] instead of Java, JMockit, Extjs
																		// and jasmine as we mocked
	}

	@Test
	public void testMethodinClassUsingExpectation(@Mocked final Skill sk) {
		new NonStrictExpectations() {
			{
				sk.getBackendSkill();
				result = new ArrayList();
				sk.getUISkills();
				result = new ArrayList();
			}
		};
		User user = new User();
		Assert.assertEquals(user.getSkill("Both").toString(), "[[]]");// return instead of Java, JMockit, Extjs and
																		// jasmine as we mocked
	}
}
