package spanningTree;

import java.io.IOException;

/*
 * Custom Input file to override the default console input files
 * Author: Rohan Shukla
 */

public final class ConsoleInput 
{
	private static boolean goodLastRead = false;
	public static boolean lastReadWasGood()
	{
		return goodLastRead;
	}

		public static String readToWhiteSpace(boolean skipLeadingWhiteSpace) throws IOException
		{
			String input = "";
			char nextChar;
			while (Character.isWhitespace(nextChar = (char)System.in.read()))
			{
				//accumulate leading white space if skipLeadingWhiteSpace is false:
				if (!skipLeadingWhiteSpace)
				{
					input += nextChar;
				}
			}
			//the first non white space character:
			input += nextChar;

			//accumulate characters until white space is reached:
			while (!Character.isWhitespace(nextChar = (char)System.in.read()))
			{
				input += nextChar;
			}

			goodLastRead = input.length() > 0;
			return input;
		}

		public static String scanfRead() throws IOException
		{
			return scanfRead(null, -1);
		}

		public static String scanfRead(String unwantedSequence) throws IOException
		{
			return scanfRead(unwantedSequence, -1);
		}

		@SuppressWarnings("deprecation")
		public static String scanfRead(String unwantedSequence, int maxFieldLength) throws IOException
		{
			String input = "";

			char nextChar;
			if (unwantedSequence != null)
			{
				nextChar = '\0';
				for (int charIndex = 0; charIndex < unwantedSequence.length(); charIndex++)
				{
					if (Character.isWhitespace(unwantedSequence.charAt(charIndex)))
					{
						//ignore all subsequent white space:
						while (Character.isWhitespace(nextChar = (char)System.in.read()))
						{
						}
					}
					else
					{
						//ensure each character matches the expected character in the sequence:
						nextChar = (char)System.in.read();
						if (nextChar != unwantedSequence.charAt(charIndex))
							return null;
					}
				}

				input = (new Character(nextChar)).toString();
				if (maxFieldLength == 1)
					return input;
			}

			while (!Character.isWhitespace(nextChar = (char)System.in.read()))
			{
				input += nextChar;
				if (maxFieldLength == input.length())
					return input;
			}

			return input;
	}
}
