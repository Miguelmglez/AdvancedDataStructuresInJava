package material.tree.maps;

/**
 * Thrown when an entry is discovered to be invalid.
 * @author Eric Zamore
 */
public class InvalidEntryException  extends RuntimeException {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public InvalidEntryException (String message) {
    super (message);
  }
}
