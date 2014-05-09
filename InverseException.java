public class InverseException extends Exception {


    public InverseException()
    {
	super();
    }

    public InverseException(String message)
    {
	super(message);
    }

    public InverseException
	(Throwable exc)
    {
	super(exc);
    }

    public InverseException(String msg, Throwable exc)
    {
	super(msg, exc);
    }
}