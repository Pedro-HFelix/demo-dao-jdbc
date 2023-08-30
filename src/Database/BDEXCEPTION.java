package Database;

import java.io.Serial;

public class BDEXCEPTION extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public BDEXCEPTION(String msg){
        super(msg);
    }
}
