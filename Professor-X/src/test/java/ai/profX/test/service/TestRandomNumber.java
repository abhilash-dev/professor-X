package ai.profX.test.service;

import java.util.Random;

public class TestRandomNumber {

	public static void main(String[] args) {
		Random random = new Random(System.nanoTime());
		for(int i=0;i<10;i++)
			System.out.println(random.nextInt(265));
	}

}
