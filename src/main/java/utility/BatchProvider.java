package utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class BatchProvider implements Iterable<Batch> {

    private Iterator<Path> files;
    private Utility.DataType dataType;
    private int batchSize;
    private boolean _isLocal;
    private String _path;

    public BatchProvider(String path, Utility.DataType dataType, int batchSize, boolean isLocal) throws IOException {
        this._isLocal = isLocal;
        if (isLocal) {
            this.init(path);
        } else {
            this._path = path;
        }
        this.dataType = dataType;
        this.batchSize = batchSize;
    }

    public boolean isLocal() {
        return _isLocal;
    }

    public String getPath() { return _path; }

    public int getBatchSize() { return batchSize; }

    public Utility.DataType getDataType() { return dataType; }

    private void init(String path) throws IOException {
        this._path = path;
        String samplesDir = path + File.separator + Utility.SAMPLES;
        if (!Files.exists(Paths.get(samplesDir))) throw new IOException("There is no subdirectory named 'samples'");
        if (!Files.exists(Paths.get(path + File.separator + Utility.LABELS))) throw new IOException("There is no subdirectory named 'labels'");
        Iterable<Path> samplesIterable = Files.walk(Paths.get(samplesDir))
                .filter(Files::isRegularFile)::iterator;
        for (Path p : samplesIterable) {
            if (!Files.exists(Paths.get(Utility.sampleToLabelPath(p.toFile().getPath()))))
                throw new IOException("There is a sample without a corresponding label");
        }
        this.files = ((Iterable<Path>) Files.walk(Paths.get(samplesDir))
                .filter(Files::isRegularFile)::iterator).iterator();
    }

    boolean hasNext() {
        return files.hasNext();
    }

    Batch next() throws IOException {
        File[] sample = new File[this.batchSize];
        int i;
        for (i = 0; i < this.batchSize; i++) {
            if (!this.hasNext()) {
                break;
            }
            sample[i] = files.next().toFile();
        }
        return Utility.filesToBatch(sample, dataType, i);
    }

    @Override
    public Iterator<Batch> iterator() {
        return new BatchProviderIterator(this);
    }
}
