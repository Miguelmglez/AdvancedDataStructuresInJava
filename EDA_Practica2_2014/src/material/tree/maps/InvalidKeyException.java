package material.tree.maps;

/**
 * Thrown when a key is determined to be invalid.
 * @author Roberto Tamassia
 */
public class InvalidKeyException  extends RuntimeException {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public InvalidKeyException (String message) {
    super (message);
  }
}
