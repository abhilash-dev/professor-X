package ai.profX.test.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.math.DoubleMath;

import ai.profX.config.AppConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class })
public class TestGameService {

	@Test
	public void test() {
		double positiveFraction = 0.0, negativeFraction = 0.0, entropy = 0.0;
		double pOfI = (double)6/10;
		double nOfI = (double)4/10;
		positiveFraction = (-1 * pOfI) * DoubleMath.log2(pOfI);
		negativeFraction = (-1 * nOfI) * DoubleMath.log2(nOfI);

		entropy = positiveFraction + negativeFraction;
		entropy *= (6 + 4) / 10;

		if (entropy != 0.0)
			entropy = 1 / entropy;
		else
			entropy = Double.POSITIVE_INFINITY;
	}
}
