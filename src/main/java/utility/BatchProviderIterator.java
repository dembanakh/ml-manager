package utility;

import java.io.IOException;
import java.util.Iterator;

class BatchProviderIterator implements Iterator<Batch> {

    private BatchProvider provider;

    BatchProviderIterator(BatchProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean hasNext() {
        return provider.hasNext();
    }

    @Override
    public Batch next() {
        try {
            return provider.next();
        } catch (IOException e) {
            return null;
        }
    }

}
