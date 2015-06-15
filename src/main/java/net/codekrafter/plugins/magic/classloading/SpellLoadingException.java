
package net.codekrafter.plugins.magic.classloading;

public class SpellLoadingException extends Exception
{

	private String message;

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public SpellLoadingException(String m)
	{
		this.message = m;
	}
}
