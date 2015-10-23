package main;

import java.util.Random;

public class RNJ
{
	private static final String[] QUESTIONS = 
	{
		"Weeee... are?",
		"How can you identify a good joke?",
		"Who is that?",
		"What's the sitch?",
		"What's Jake doin'?",
		"Cards against...?",
		"Use Brink with...",
		"You think you're all that..."
	};
	
	private static final String[] ANSWERS = 
	{
		"THE CRYSTAL GEMS!",
		"BUT HER AIM IS GETTING BETTER!",
		"The author of the journals, my brother!",
		"BOOYA!",
		"BACON PANCAKES!",
		"Humidity!",
		"DOWN!",
		"BUT YOU'RE NOT!"
	};
	
	private static Random ran = new Random();
	
	public static String random()
	{
		return QUESTIONS[ran.nextInt(QUESTIONS.length)] + " - " + ANSWERS[ran.nextInt(ANSWERS.length)];
	}
}
