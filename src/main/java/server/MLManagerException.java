package server;

import utility.Utility;

public class MLManagerException extends RuntimeException {

    private Utility.ErrorCause cause;

    MLManagerException(Utility.ErrorCause cause) {
        this.cause = cause;
    }

    public Utility.ErrorCause getErrorCause() {
        return cause;
    }

}
