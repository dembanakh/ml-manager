package utility;

import java.io.Serializable;

public interface Batch extends Serializable {
    int getSize();
    Object getData();
}
